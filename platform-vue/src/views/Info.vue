<template>
  <div>
    <div style="margin: 10px 0">
      <el-input placeholder="请输入信息标题" suffix-icon="el-icon-search" style="width: 200px" v-model="infoTitle"></el-input>
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
      <el-table-column prop="infoId" fixed="left" label="信息ID" width="80">
      </el-table-column>
      <el-table-column   prop="infoTitle" label="信息标题" width="200">
      </el-table-column>
      <el-table-column prop="infoDescription" label="信息描述"  >
      </el-table-column>
      <el-table-column prop="releasedName" label="发布人" width="100">
      </el-table-column>
      <el-table-column prop="releasedTime" label="发布时间" width="160" :formatter="dateFormat">
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
              @confirm="del(scope.row.infoId)"
          >
            <el-button type="danger" slot="reference" >删除<i class="el-icon-remove"></i></el-button>
          </el-popconfirm>
          <el-button type="primary" style="margin-left: 10px"
                     :disabled="scope.row.infoStatus===1"
                     @click="publishInfo(scope.row.infoId)">发布<i class="el-icon-finished"></i></el-button>
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
    <el-dialog title="校园信息" :visible.sync="dialogFormVisible" width="40%">
      <el-form :model="form" :rules="rules" label-width="80px" size="small" >
        <el-form-item class="item" label="信息标题:" prop="infoTitle" label-width="100px"><!--v-model和prop的属性名字一致-->
          <el-input placeholder="请输入信息标题" v-model="form.infoTitle"  clearable autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="信息描述:" label-width="100px" >
          <el-input v-model="form.infoDescription"
                    clearable
                    type="textarea"
                    :autosize="{ minRows: 3, maxRows: 6}"
                    placeholder="请输入任务描述" autocomplete="off">
          </el-input>
        </el-form-item>
        <el-form-item label="发布时间:" label-width="100px" prop="releasedTime"  >
          <div class="block">
            <el-date-picker
                v-model="form.releasedTime"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="选择日期时间">
            </el-date-picker>
          </div>
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
  name: "Info",
  data(){
    return{
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) :{},
      form: {},
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      dialogFormVisible: false,
      infoTitle: "",
      rules: {
        infoTitle: [
          { required: true, message: '请输入信息标题', trigger: 'blur' },
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
      this.request.get("/info/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          infoTitle: this.infoTitle
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })

    },
    search(){
      this.request.get("/info/page",{
        params:{
          pageNum: 1,
          pageSize: this.pageSize,
          infoTitle: this.infoTitle
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })

    },
    reset(){
      this.infoTitle = ""
      this.load()
    },
    dateFormat(row){
      return this.$moment(row.releasedTime).format('YYYY-MM-DD HH:mm:ss')
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
      this.form.releasedBy = this.user.id
      this.form.releasedTime = new Date(this.form.releasedTime)
      this.request.post("/info",this.form).then(res =>{
        if(res.code === '200'){
          this.$message.success("保存信息成功")
          this.dialogFormVisible = false
          this.load()
        }else{
          this.$message.error("保存信息失败")
          this.dialogFormVisible = false
        }
      })
    },
    publishInfo(id){
      this.request.put("/info/publish/" + id).then(res =>{
        if(res.code === '200'){
          this.$message.success("发布信息成功")
          this.load()
        }else{
          this.$message.error("发布信息失败")
        }
      })
    },
  }
}
</script>

<style>
.headerBg {
  background: #eee!important;
}
</style>