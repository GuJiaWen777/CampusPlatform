<template>
    <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
      <el-tab-pane label="我的资料" name="first">
        <div style="margin-left: 400px;margin-top: 5px">
          <el-card style="width: 500px;height: 600px">
            <el-form label-width="80px" size="small" :model="form" :rules="rules">
              <el-upload
                class="avatar-uploader"
                :action="'http://localhost:8081/file/upload'"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
              >
              <img v-if="form.avatar" :src="form.avatar" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
              <el-form-item label="用户名">
                <el-input :disabled="true" v-model="form.name" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="性别">
                <el-radio v-model="form.gen" :label= "1" autocomplete="off">男</el-radio>
                <el-radio v-model="form.gen" :label= "0" autocomplete="off">女</el-radio>
              </el-form-item>
              <el-form-item label="学号" prop="num">
                <el-input v-model="form.num" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="电话" prop="phone">
                <el-input v-model="form.phone" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="地址">
                <el-input v-model="form.addr" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="积分" >
                <el-tag effect="dark">{{form.point}}</el-tag>
              </el-form-item>
              <el-form-item label="等级" >
                <el-tag effect="dark">{{levelFormat(form.level)}}</el-tag>
              </el-form-item>
              <el-form-item label="信用分" >
                <el-tag effect="dark">{{form.credit}}</el-tag>
              </el-form-item>
              <el-form-item >
                <el-button type="primary"  style="margin-left: 120px" size="big" @click="save">保 存</el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </div>
    </el-tab-pane>
      <el-tab-pane label="我的成就" name="second">
        <div style="margin-left:10%;margin-right:1%">
          <el-row>
            <el-col :span="4" v-for="(item) in tableData" :key="item.achvId" :offset="1" >
              <div style="margin-top:15px">
                <el-card :body-style="{ padding: '0px'}" shadow="hover" >
                  <img v-if="item.picture" :src="item.picture" class="avatar">
                  <i v-else class="el-icon-trophy achievement-icon"></i>
                  <div style="padding: 14px;">
                    <span style="font-size: 20px;margin-left: 30px"><strong>{{item.achvName}}</strong></span><br>
                    <div class="bottom clearfix">
                      <div style="margin-top: 10px">
                        <el-tooltip
                            :content="item.achvDescription"
                            placement="top">
                          <el-button
                              type="text"
                              class="button"
                              v-if="!item.owned"
                              style="font-size: 14px;margin-top: 5px"
                              @click="handleFinish(item.achvId)">完成</el-button>
                          <el-button
                              type="text"
                              class="button"
                              v-else
                              style="font-size: 14px;margin-top: 5px"
                              :disabled="true" >已完成</el-button>

                        </el-tooltip>
                      </div>
                    </div>
                  </div>
                </el-card>
              </div>
            </el-col>
          </el-row>
          <div style="margin-left:35%;margin-top: 5px">
            <div class="block">
              <el-pagination
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page="pageNum"
                  :page-sizes="[4,8, 12, 16]"
                  :page-size="pageSize"
                  layout="total, sizes, prev, pager, next"
                  :total="total">
              </el-pagination>
            </div>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>

</template>

<script>
export default {
  name: "Person",
  data(){
    return{
      form:{},
      tableData: [],
      activeName:'first',
      total: 0,
      pageNum: 1,
      pageSize: 8,
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) :{},
      rules: {
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
    this.first()
  },
  methods:{
    async getUser() {
      return (await this.request.get("/user/name/" + this.user.name)).data
    },
    save(){
      this.form.roleId = this.user.roleId
      this.request.post("/user",this.form).then(res =>{
        if(res.code === '200'){
          this.$message.success("保存成功")
          // 触发父级更新User的方法
          this.$emit("refreshUser")
          // 更新浏览器存储的用户信息
          this.getUser().then(res => {
            res.token = JSON.parse(localStorage.getItem("user")).token
            localStorage.setItem("user", JSON.stringify(res))
          })
        }else{
          this.$message.error("保存失败")
        }
      })
    },
    handleAvatarSuccess(res){
      this.form.avatar = res
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    first(){
      this.getUser().then(res => {
        this.form = res
      })
    },
    second(){
      this.request.get("/achievement/page/"+this.user.id,{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    handleClick(tab, event){
      if(tab.name === 'first'){
        this.tabName ='first'
        this.first()
      }else if(tab.name === 'second'){
        this.tabName ='second'
        this.second()
      }else{
        console.log(tab, event);
      }
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize
      this.second()
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum
      this.second()
    },
    handleFinish(id){
      this.request.get("/achievement/finish/"+id+"/" + this.user.id).then(res =>{
        if(res.code === '200'){
          this.$message.success("恭喜你完成成就！")
          this.second()
        }else{
          this.$message.error("很遗憾还未完成！")
        }

      })
    },
    levelFormat(level){
      let newlevel = level / 100
      return parseInt(newlevel)
    }
  }
}
</script>

<style >
.avatar-uploader {
  text-align: center;
  padding-bottom: 10px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 80px;
  height: 80px;
  line-height: 100px;
  text-align: center;
}
.avatar {
  width: 80px;
  height: 80px;
  display: block;
}
.achievement-icon{
  font-size: 28px;
  color: #8c939d;
  height: 100px;
  line-height: 100px;
  text-align: center;
  width: 100%;
  display: block;
}
.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100px;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

</style>