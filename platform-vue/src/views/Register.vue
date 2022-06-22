<template>
  <div class="wrapper">
    <div style="margin: 200px auto; background-color: #fff;width: 350px;height: 450px;padding: 20px;border-radius: 10px">
      <div style="margin: 20px 0;text-align: center;font-size: 24px"><b>注 册</b></div>
      <el-form :model="user" :rules="rules" ref="userForm" >
        <el-form-item prop="name">
          <el-input  size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.name"></el-input>
        </el-form-item>
        <el-form-item prop="pwd">
          <el-input   size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.pwd"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPwd">
          <el-input  size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.confirmPwd"></el-input>
        </el-form-item>
        <el-form-item prop="num">
          <el-input  size="medium" style="margin: 10px 0" prefix-icon="el-icon-school"  v-model="user.num"></el-input>
        </el-form-item>
      </el-form>
      <div style="margin: 10px 0;text-align: center">
        <el-button plain size="big" autocomplete="off" style="margin-right: 10px;"@click="$router.push('/login')">返回登陆</el-button>
        <el-button type="primary" size="big" autocomplete="off" @click="register">确认注册</el-button>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: "Login",
  data(){
    return{
      user: {},
      rules: {
        name: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在 2 到 8 个字符', trigger: 'blur' }
        ],
        pwd: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' }
        ],
        confirmPwd: [
          { required: true, message: '请确认密码', trigger: 'blur' },
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' }
        ],
        num: [
          { required: true, message: '请输入学号', trigger: 'blur' },
          { min: 8, max: 10, message: '长度在 8 到 10 个字符', trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    register(){
      this.$refs["userForm"].validate((valid) => {
        if (valid) {//表单校验合法
          if(this.user.pwd!=this.user.confirmPwd){
            this.$message.error("两次输入密码不一致，请重新输入！")
            return false;
          }
          this.request.post("/user/register",this.user).then(res =>{
            if(res.code === '200'){
              this.$router.push("/login")
              this.$message.success("注册成功！")
            }else {
              this.$message.error(res.msg)
            }
          })
        }
      });
    }

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