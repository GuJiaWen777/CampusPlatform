<template>
  <div style="height: 100%;">
  <el-menu :default-openeds="['1']" style="min-height: 100%;overflow-x:hidden"
           background-color="#000080"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
           class="side_container"
  >

    <div style="height: 60px;line-height: 60px;text-align: center" >
      <img src="../assets/logo.png" style="width: 20px; position: relative; top: 5px; margin-right: 5px" >
      <b style="color: #fff" v-show="logoTextShow">校园信息互助平台</b>
    </div>


    <div v-for="item in menus" :key="item.id">
      <div v-if="item.path">
        <el-menu-item :index="item.path">
          <i :class="item.icon"></i>
          <span slot="title">{{ item.name }}</span>
        </el-menu-item>
      </div>
      <div v-else>
        <el-submenu :index="item.id + ''">
          <template slot="title">
            <i :class="item.icon"></i>
            <span slot="title">{{ item.name }}</span>
          </template>
          <div  v-for="subItem in item.children" :key="subItem.id">
            <el-menu-item :index="subItem.path">
              <i :class="subItem.icon"></i>
              <span slot="title">{{ subItem.name }}</span>
            </el-menu-item>
          </div>
        </el-submenu>
      </div>
    </div>

  </el-menu>
  </div>
</template>

<script>
export default {
  name: "Aside",
  props:{
    isCollapse: Boolean,
    logoTextShow: Boolean
  },
  data() {
    return {
      menus: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")) : [],
      opens: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")).map(v => v.id + '') : []
    }
  }
}
</script>

<style>
.el-menu--collapse span {
  visibility: hidden;
}
.el-submenu.is-opened>.el-submenu__title .el-submenu__icon-arrow{
  display: none;

}

.el-submenu>.el-submenu__title .el-submenu__icon-arrow{
  display: none;

}


.sidebar-show .sidebar-bottom {
  position: absolute;
  width: 100%;
  bottom: 50px;
  left: 0;
  background-color: #2f3035;
}
.sidebar-show .sidebar-tool {
  font-size: 14px;
  align: center;
}

.side_container {
  max-height: 100vh;
  overflow-y: auto;
::-webkit-scrollbar {
 display: none;
 }
}
::-webkit-scrollbar {
  width: 5px;
  height: 1px;
}
::-webkit-scrollbar-track-piece {
  background-color: rgba(0, 0, 0, 0.2);
  -webkit-border-radius: 6px;
}
::-webkit-scrollbar-thumb:vertical {
  height: 5px;
  background-color: rgba(125, 125, 125, 0.7);
  -webkit-border-radius: 6px;
}
::-webkit-scrollbar-thumb:horizontal {
  width: 5px;
  background-color: rgba(125, 125, 125, 0.7);
  -webkit-border-radius: 6px;
}

</style>