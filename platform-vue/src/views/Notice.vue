<template>
  <div>
    <div style="margin: 10px 0">
      <el-input placeholder="请输入公告标题" suffix-icon="el-icon-search" style="width: 200px" v-model="noticeTitle"></el-input>
      <el-button style="margin-left: 5px " type="primary " @click="search">搜索</el-button>
      <el-button style="margin-left: 5px " type="warning " @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button  style="margin-left: 5px " type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
    </div>
    <el-table
        :data="tableData"
        border
        stripe
        size="medium"
        :header-cell-class-name="'headerBg'"
    >
      <el-table-column prop="noticeId" fixed="left" label="公告ID" width="80">
      </el-table-column>
      <el-table-column   prop="noticeTitle" label="公告标题" width="200">
      </el-table-column>
      <el-table-column prop="noticeContent" label="公告内容"  >
      </el-table-column>
      <el-table-column prop="noticeStatus" label="发布状态">
        <template slot-scope="scope">
          <el-switch
              v-model="scope.row.noticeStatus"
              :active-value="0"
              :inactive-value="1"
              active-text="发布"
              inactive-text="关闭"
              @change="handleStatus(scope.row.noticeId,$event)">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="370" align="center">
        <template slot-scope="scope">
          <el-button type="warning" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              style="margin-left: 10px"
              confirm-button-text='确认'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="del(scope.row.noticeId)"
          >
            <el-button type="danger" slot="reference" >删除<i class="el-icon-remove"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5,10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
    <el-dialog title="消息公告" :visible.sync="dialogFormVisible" width="40%">
      <el-form :model="form" :rules="rules" label-width="80px" size="small" >
        <el-form-item class="item" label="公告标题:" prop="noticeTitle" label-width="100px"><!--v-model和prop的属性名字一致-->
          <el-input placeholder="请输入信息标题" v-model="form.noticeTitle"  clearable autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="公告内容:" label-width="100px" >
          <el-input v-model="form.noticeContent"
                    clearable
                    type="textarea"
                    :autosize="{ minRows: 3, maxRows: 6}"
                    placeholder="请输入任务描述" autocomplete="off">
          </el-input>
        </el-form-item>

        <el-form-item >
          <el-button @click="dialogFormVisible = false" style="margin-left: 150px">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Notice",
  data(){
    return{
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) :{},
      form: {},
      tableData: [],
      status:0,
      total: 0,
      pageNum: 1,
      pageSize: 10,
      dialogFormVisible: false,
      noticeTitle: "",
      rules: {
        noticeTitle: [
          { required: true, message: '请输入公告标题', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在 2 到 8 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.load()

  },
  methods:{
    load(){
      this.request.get("/notice/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          noticeTitle: this.noticeTitle
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    search(){
      this.request.get("/notice/page",{
        params:{
          pageNum: 1,
          pageSize: this.pageSize,
          noticeTitle: this.noticeTitle
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })

    },
    reset(){
      this.noticeTitle = ""
      this.load()
    },
    handleAdd(){
      this.dialogFormVisible = true
      this.form = {}
    },
    handleEdit(row){
      this.form = Object.assign({},row)
      this.dialogFormVisible = true
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum
      this.load()
    },
    save(){
      this.request.post("/notice",this.form).then(res =>{
        if(res.code === '200'){
          this.$message.success("保存公告成功")
          this.dialogFormVisible = false
          this.load()
        }else{
          this.$message.error("保存公告失败")
          this.dialogFormVisible = false
        }
      })
    },
    handleStatus(id,status){
      console.log(id)
      console.log(status)
      this.request.put("/notice/status/"+id+"/"+status).then(res=>{
        if(res.code === '200'){
          this.$message.success("更新公告状态成功")
          this.dialogFormVisible = false
          this.load()
        }else{
          this.$message.error("更新公告状态失败")
          this.dialogFormVisible = false
        }
      })
    }
  }
}
</script>

<style>
.headerBg {
  background: #eee!important;
}
</style>