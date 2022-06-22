<template>
  <div>
    <div style="margin: 10px 0">
      <el-input placeholder="请输入用户名" suffix-icon="el-icon-search" style="width: 200px" v-model="username"></el-input>
      <el-button style="margin-left: 5px " type="primary " @click="search">搜索</el-button>
      <el-button style="margin-left: 5px " type="warning " @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button  style="margin-left: 5px " type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
      <!--
      <el-button  style="margin-left: 5px " type="danger">批量删除<i class="el-icon-remove-outline"></i></el-button>
      <el-button  style="margin-left: 5px " type="primary">导入<i class="el-icon-bottom"></i></el-button>
      <el-button  style="margin-left: 5px " type="primary">导出<i class="el-icon-top"></i></el-button>
      -->
    </div>
    <!--多项选择函数
     <el-table :data="tableData" border stripe  @selection-change="handleSelectionChange">
     -->
    <el-table
        :data="tableData"
        border
        stripe
        :header-cell-class-name="'headerBg'"
    >
      <!-- 多项选择
       <el-table-column type="selection" width="40"> </el-table-column>
      -->
      <el-table-column prop="id" label="用户ID" width="80" fixed="left">
      </el-table-column>
      <el-table-column prop="name" label="用户名" width="120">
      </el-table-column>
      <el-table-column prop="role" label="角色" width="90">
        <template slot-scope="scope">
          <el-tag type="primary" v-if="scope.row.roleId === 1">管理员</el-tag>
          <el-tag type="success" v-if="scope.row.roleId === 2">普通用户</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="gen" label="性别" :formatter="stateFormat" width="80">
      </el-table-column>
      <el-table-column prop="phone" label="电话" width="120">
      </el-table-column>
      <el-table-column prop="addr" label="地址"width="240">
      </el-table-column>
      <el-table-column prop="num" label="学号" width="100">
      </el-table-column>
      <el-table-column prop="point" label="积分余额" width="70">
      </el-table-column>
      <el-table-column prop="level" label="等级经验" width="70">
      </el-table-column>
      <el-table-column prop="credit" label="信用分" width="70">
      </el-table-column>
      <el-table-column label="操作"  align="center">
        <template slot-scope="scope">
          <el-button type="warning" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确认'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="del(scope.row.id)"
          >
            <el-button type="danger" slot="reference">删除<i class="el-icon-remove"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form" :rules="rules" label-width="80px" size="small" >
        <el-form-item label="用户名" prop="name" ><!--v-model和prop的属性名字一致-->
          <el-input  v-model="form.name" :disabled="disabled" clearable autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色" v-show="this.form.id!==this.user.id">
          <el-select  v-model="form.roleId" placeholder="请选择角色" style="width: 100%">
            <el-option v-for="item in roles" :key="item.roleName" :label="item.roleName" :value="item.roleId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.gen">
          <el-radio  :label= "1" autocomplete="off">男</el-radio>
          <el-radio  :label= "0" autocomplete="off">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="学号 " prop="num" >
          <el-input v-model="form.num" clearable autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" clearable autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="addr">
          <el-input v-model="form.addr" clearable autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item style="margin-left: 70px">
          <el-button @click="dialogFormVisible = false" >取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "User",
  data(){
    return{
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) :{},
      disabled:false,
      tableData: [],
      roles:[],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      username: "",
      form: {},
      dialogFormVisible: false,
      multipleSelection: [],
      rules: {
        name: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur' }
        ],
        num: [
          { required: true, message: '请输入学号', trigger: 'blur' },
          { min: 8, max: 10, message: '长度在 8 到 10 个字符', trigger: 'blur' }
        ],
        phone: [
          { min: 11, max: 11, message: '请正确输入手机号', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("/user/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username
        }
      }).then(res => {
        this.tableData = res.data.userData
        this.total = res.data.total
      })
      this.request.get("/role").then(res => {
        this.roles = res.data
      })
    },
    search(){
      this.request.get("/user/page",{
        params:{
          pageNum: 1,
          pageSize: this.pageSize,
          username: this.username
        }
      }).then(res => {
        this.tableData = res.data.userData
        this.total = res.data.total
      })
    },
    reset(){
      this.username = ""
      this.load()
    },
    stateFormat(row) {
      if (row.gen === 1) {
        return "男";
      } else {
        return "女";
      }
    },
    handleSizeChange(pageSize){
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },
    handleAdd(){
      this.dialogFormVisible = true
      this.disabled = false
      this.form = {}
    },
    handleEdit(row){
      this.form = Object.assign({},row)
      this.dialogFormVisible = true
      this.disabled = true
    },
    save(){
      console.log(this.form)
      this.request.post("/user",this.form).then(res =>{
        if(res.code === '200'){
          this.$message.success("保存成功")
          this.dialogFormVisible = false

          this.load()
        }else{
          this.$message.error(res.msg)
          this.dialogFormVisible = false
        }
      })
    },
    del(id){
      this.request.delete("/user/"+id).then(res =>{
        if(res.code === '200'){
          this.$message.success("删除成功")
          this.load()
        }else{
          this.$message.error("删除失败")
        }
      })
    },
    handleSelectionChange(val){ //处理批量删除
      console.log(val)
      this.multipleSelection = val;
    }
  }
}
</script>

<style>
.headerBg {
  background: #eee!important;
}

</style>