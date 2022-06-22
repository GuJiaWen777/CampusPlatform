<template>
  <div class="wrapper">
    <div style="margin: 200px auto; background-color: #fff;width: 350px;height: 300px;padding: 20px;border-radius: 10px;bac">
      <div style="margin: 20px 0;text-align: center;font-size: 24px"><b>登 陆</b></div>
      <el-form :model="user" :rules="rules" ref="userForm" @keyup.enter.native="login()">
        <el-form-item prop="name">
          <el-input  size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.name"></el-input>
        </el-form-item>
        <el-form-item prop="pwd">
          <el-input prop="pwd" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.pwd"></el-input>
        </el-form-item>
      </el-form>
      <div style="margin: 10px 0;text-align: center">
        <el-button plain size="big" autocomplete="off" style="margin-right: 10px;" @click="$router.push('/register')">注 册</el-button>
        <el-button type="primary" size="big" native-type="submit" autocomplete="off" @click="login">登 陆</el-button>
      </div>
    </div>

  </div>
</template>

<script>
import {setRoutes} from "../router";

export default {
  name: "Login",

  data(){
    return{
      user: {},
      rules: {
        name: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 8, message: '长度在 2 到 8 个字符', trigger: 'blur' }
        ],
        pwd: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    login(){
      this.$refs["userForm"].validate((valid) => {
        if (valid) {//表单校验合法
          this.request.post("/user/login",this.user).then(res =>{
            if(res.code === '200'){
              localStorage.setItem("user",JSON.stringify(res.data)) //存储用户信息到浏览器
              localStorage.setItem("menus",JSON.stringify(res.data.menus))
              localStorage.setItem("token",res.data.token)
              //动态设置当前路由
              setRoutes()
              this.$router.push("/")
              //this.$message.success("登陆成功！")
              this.$notify({
                title: '提示',
                message: res.data.name +'，欢迎您登陆！',
                type: 'success',
                position: 'bottom-right'
              });
            }else {
              this.$message.error(res.msg)
            }
          })
        }
      });
    },


  }
}
</script>

<style>
.wrapper{
  height: 100vh;
  background-image: url("../assets/img.png");
  overflow: hidden;
}
</style>