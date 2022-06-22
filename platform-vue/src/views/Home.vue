<template>
  <div style="color: #666;font-size: 14px;">
    <div style="padding-bottom: 20px">

    </div>
    <div class="block">
      <el-carousel trigger="click" height="150px" :autoplay="false">
        <el-carousel-item v-for="item in notice" :key="item.noticeId">
          <h1 class="small">{{ item.noticeTitle }}</h1>
          <h3 class="small">{{ item.noticeContent }}</h3>
        </el-carousel-item>
      </el-carousel>
    </div>

    <div style="height: 1px; background: #ddd; margin: 20px 0"/>
    <el-row :gutter="30">
      <el-col :span="12">
        <div style="padding: 20px 0; font-size: 20px ;margin-left: 10px">校园信息互助平台</div>
        <el-card>
          校园互助信息平台是一个以互联网传统的论坛平台为启发，专门面向校内学生群体，以校内学生需求为导向建设的共享信息服务平台。
          <el-divider/>
          校园互助信息平台着力于解决周围的事,在运营过程中致力于解决大学生在生活中遇到的困难,同时为大学生提供最全面、最优质的服务,也可以增进学生之间的交流。
        </el-card>
      </el-col>
      <el-col :span="12">
        <div style="padding: 20px 0; font-size: 20px">技术栈</div>
        <el-row>
          <el-col :span="12" style="line-height: 30px">
            <div><b>后端</b></div>
            <div>SpringBoot2</div>
            <div>Mybatis</div>
            <div>Mybatis-plus</div>
            <div>Lombok</div>
            <div>Hutool</div>
          </el-col>
          <el-col :span="12" style="line-height: 30px">
            <b>前端</b>
            <div>Vue2</div>
            <div>Vue-Router</div>
            <div>VueX</div>
            <div>ElementUI</div>
            <div>Axios</div>
          </el-col>
        </el-row>
      </el-col>
    </el-row>

  </div>

</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      notice:[]
    }
  },
  created() {
    this.load()
  },
  mounted() {

  },
  methods: {
    load() {
      this.request.get("/notice").then(res => {
        this.notice = res.data
      }
      )

    },

  }

}
</script>

<style>
.el-carousel__item h1 {
  color: #303133;
  font-size: 22px;
  line-height: 50px;
  text-align: center;
}
.el-carousel__item h3 {
  color: #606266;
  font-size: 16px;
  opacity: 0.75;
  line-height: 50px;

  text-align: center;
}


.el-carousel__item:nth-child(2n) {
  background-color: #E0FFFF;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #EEE9E9;
}
</style>