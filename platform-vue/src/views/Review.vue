<template>
  <div>
    <div style="margin: 10px 0">
      <el-input placeholder="请输入评论内容" suffix-icon="el-icon-search" style="width: 200px" v-model="reviewDescription"></el-input>
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
      <el-table-column prop="reviewId" fixed="left" label="评论ID" width="80">
      </el-table-column>
      <el-table-column  prop="infoTitle" label="所属信息" width="160">
      </el-table-column>
      <el-table-column prop="userName" label="发布人"  width="140">
      </el-table-column>
      <el-table-column prop="reviewDescription" label="评论内容">
      </el-table-column>
      <el-table-column prop="reviewTime" label="评论时间" width="200" :formatter="dateFormat">
      </el-table-column>
      <el-table-column label="操作" width="405" align="center">
        <template slot-scope="scope">
          <el-button type="warning" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              style="margin-left: 10px"
              confirm-button-text='确认'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="del(scope.row.reviewId)"
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
          :page-sizes="[10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
    <el-dialog title="评论" :visible.sync="dialogFormVisible" width="40%">
      <el-form :model="form" :rules="rules" label-width="80px" size="small" >
        <el-form-item class="item" label="评论内容:" prop="reviewDescription" label-width="100px"><!--v-model和prop的属性名字一致-->
          <el-input placeholder="请输入评论内容" v-model="form.reviewDescription"  clearable autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item class="item" label="所属信息:" prop="infoTitle" label-width="100px"><!--v-model和prop的属性名字一致-->
          <el-select v-model="form.infoId" placeholder="请选择">
            <el-option
                v-for="item in info"
                :key="item.infoId"
                :label="item.infoTitle"
                :value="item.infoId">
            </el-option>
          </el-select>
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
  name: "Review",
  props: {
  },
  data(){
    return{
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) :{},
      form: {},
      tableData: [],
      info:[],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      dialogFormVisible: false,
      reviewDescription: "",
      rules: {
        reviewDescription: [
          { required: true, message: '评论内容', trigger: 'blur' }
            ]
      }
    }
  },
  created() {
    this.load()
    this.request.get("/info").then(res => {
      this.info = res.data
    })
  },
  methods:{
    load(){
      this.request.get("/review/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          reviewDescription: this.reviewDescription
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })

    },
    search(){
      this.request.get("/review/page",{
        params:{
          pageNum: 1,
          pageSize: this.pageSize,
          reviewDescription: this.reviewDescription
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    reset(){
      this.reviewDescription = ""
      this.load()
    },
    dateFormat(row){
      return this.$moment(row.reviewTime).format('YYYY-MM-DD HH:mm:ss')
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum
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
    save(){
      this.form.userId = this.user.id
      let timestamp = new Date().getTime()
      this.form.reviewTime = new Date(timestamp)
      this.request.post("/review",this.form).then(res =>{
        if(res.code === '200'){
          this.$message.success("发布评论成功")
          this.dialogFormVisible = false
          this.load()
        }else{
          this.$message.error("发布评论失败")
          this.dialogFormVisible = false
        }
      })
    },
    del(id){
      this.request.delete("/review/" + id).then(res =>{
        if(res.code === '200'){
          this.$message.success("删除评论成功")
          this.load()
        }else{
          this.$message.error("删除评论失败")
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