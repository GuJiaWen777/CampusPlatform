<template xmlns:position="http://www.w3.org/1999/xhtml">
    <el-container style="min-height: 100%">
      <el-aside width="sideWidth+'px'" style="background-color: rgb(238, 241, 246); box-shadow:  2px 0 6px rgb(0 21 41 / 35%);min-height: 100vh">
        <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow"></Aside>
      </el-aside>

      <el-container>
        <el-header style=" border-bottom: 1px solid #ccc">
          <Header :collapse="collapse" :collapseBtnClass="collapseBtnClass" :user="user"></Header>
        </el-header>

        <el-main>
          <!--当前页面子路由在router-view展示-->
          <router-view @refreshUser="getUser" > </router-view>
        </el-main>
      </el-container>
    </el-container>
</template>

<script>

import Aside from "../components/Aside";
import Header from "../components/Header";

export default {
  data(){
    return{
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      user: {}
    }
  },
  components: {
    Aside,
    Header
  },
  created() {
    // 从后台获取最新的User数据
    this.getUser()
  },
  methods:{
    collapse(){ //点击按钮收缩
      this.isCollapse = !this.isCollapse
      if(this.isCollapse){
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      }else{
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    },
    getUser() {
      let name = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).name : ""
      if (name) {
        // 从后台获取User数据
        this.request.get("/user/name/" + name).then(res => {
          // 重新赋值后台的最新User数据
          this.user = res.data
        })
      }
    }
  }
}
</script>
