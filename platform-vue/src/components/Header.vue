<template>
  <div style="font-size:12px; line-height: 60px; display: flex">
  <div style="flex: 1">
    <span :class="collapseBtnClass" style="cursor: pointer;font-size: 18px" @click="collapse"></span>
    <el-breadcrumb separator="/" style="display: inline-block;margin-left: 10px">
      <el-breadcrumb-item :to="'/'">校园信息互助平台</el-breadcrumb-item>
      <el-breadcrumb-item >{{currentPathName}}</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
    <el-dropdown style="width: 150px; cursor: pointer;text-align: right">
      <el-avatar :src="user.avatar"
                 size="small" style="position: relative;top: 10px;right: 5px"></el-avatar>
      <span>{{user.name}}</span>
      <i class="el-icon-arrow-down el-icon--right" style="margin-left: 5px"></i>

      <el-dropdown-menu slot="dropdown" style="width:100px;text-align:center">
        <el-dropdown-item style="font-size: 12px;padding: 5px 0">
          <span @click="handleUser">查看个人信息</span>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 12px; padding: 5px 0">
          <router-link to="/password" >修改密码</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 12px; padding: 5px 0">
          <router-link to="/selfmessage" >留言反馈</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 12px;padding: 5px 0">
          <span style="text-decoration: none" @click="logout">退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
    <el-dialog title="个人详细信息" :visible.sync="dialogFormVisible" width="30%">
      <el-descriptions  :column="2" border>
        <el-descriptions-item label="用户名">
          <span>{{form.name}}</span>
        </el-descriptions-item>
        <el-descriptions-item label="性别">
          <span>{{genToString}}</span></el-descriptions-item>
        <el-descriptions-item label="学号">
          <span>{{form.num}}</span>
        </el-descriptions-item>
        <el-descriptions-item label="电话">
          <span>{{form.phone}}</span>
        </el-descriptions-item>
        <el-descriptions-item label="地址">
          <span>{{form.addr}}</span>
        </el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Header",
  props:{
    collapseBtnClass: String,
    collapse:Function,
    user:Object
  },
  computed:{
    currentPathName(){
      return this.$store.state.currentPathName;
    }
  },
  watch: {
    currentPathName(newVal ,oldVal){
      console.log(newVal)
    }
  },
  data(){
    return{
      dialogFormVisible:false,
      form:{},
      genToString:'男',
      radio: 1,
      avatar:'',
      rules: {
        name: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 8 个字符', trigger: 'blur' }
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
  methods:{
    logout(){
      this.$store.commit("logout")
      this.$message.success("退出成功")
    },
    handleUser(){
      this.dialogFormVisible = true
      this.request.get("/user/name/"+this.user.name).then(res =>{
        if(res.code === '200'){
          this.form = Object.assign({},res.data)
        }
      })
      if(this.form.gen == 0){
        this.genToString = '女'
      }else{
        this.genToString = '男'
      }
    },
  }
}
</script>

<style >
a {
  text-decoration: none;
}

.router-link-active {
  text-decoration: none;
}

</style>