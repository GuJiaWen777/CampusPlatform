<template>
  <div>
    <el-tabs type="border-card" @tab-click="handleClick" v-model="activeName">
      <el-tab-pane name="first">
        <span slot="label"><i class="el-icon-s-help"></i> 奖励表 </span>
          <div style="margin: 10px 0">
            <el-input placeholder="请输入奖品名称" suffix-icon="el-icon-search" style="width: 200px" v-model="rewardName"></el-input>
            <el-button style="margin-left: 5px " type="primary " @click="search">搜索</el-button>
            <el-button style="margin-left: 5px " type="warning " @click="reset">重置</el-button>
          </div>
        <div style="margin: 10px 0">
          <el-button  style="margin-left: 5px "
                      type="primary"
                      @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
        </div>
        <el-table
            :data="tableData"
            border
            stripe
            size="medium"
        >
          <el-table-column prop="rewardId" fixed="left" label="奖品ID" width="100"></el-table-column>
          <el-table-column   prop="rewardName" label="奖品名称" width="250"></el-table-column>
          <el-table-column prop="rewardNum" label="剩余数量"  width="100"></el-table-column>
          <el-table-column prop="rewardOver" label="是否可兑换" :formatter="stateFormat" width="100"></el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button type="warning" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
            <el-popconfirm
              style="margin-left: 10px"
              confirm-button-text='确认'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="del(scope.row.rewardId)"
            >
            <el-button type="danger" slot="reference" >删除<i class="el-icon-remove"></i></el-button>
            </el-popconfirm>
          <el-button type="primary"
                     style="margin-left: 10px"
                     :disabled="scope.row.rewardOver===1"
                     @click="publishReward(scope.row.rewardId)"  >上架<i class="el-icon-arrow-up"></i></el-button>
          <el-button type="primary"
                     style="margin-left: 10px"
                     :disabled="scope.row.rewardOver===0"
                     @click="returnReward(scope.row.rewardId)">下架<i class="el-icon-arrow-down"></i></el-button>

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
    <el-dialog title="奖励品" :visible.sync="dialogFormVisible" width="40%">
      <el-form :model="form" :rules="rules" label-width="80px" size="small" >
        <el-form-item class="item" label="奖品名称:" prop="rewardName" label-width="100px"><!--v-model和prop的属性名字一致-->
          <el-input placeholder="请输入奖品名称" v-model="form.rewardName"  clearable autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item class="item" label="剩余数量:" prop="rewardNum" label-width="100px"><!--v-model和prop的属性名字一致-->
          <el-input placeholder="请输入剩余数量" v-model="form.rewardNum"  clearable autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item class="item" label="是否可兑换:" prop="rewardOver" label-width="100px"><!--v-model和prop的属性名字一致-->
          <el-radio-group v-model="form.rewardOver">
            <el-radio  :label= "1" autocomplete="off">是</el-radio>
            <el-radio  :label= "0" autocomplete="off">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="奖品图片:" prop="picture" >
        <el-upload
            class="avatar-uploader"
            :action="'http://localhost:8081/file/upload'"
            :show-file-list="false"
            :on-success="handlePictureSuccess"
            :before-upload="beforePictureUpload"
        >
          <img v-if="form.picture" :src="form.picture" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
        </el-form-item>
        <el-form-item >
          <el-button @click="dialogFormVisible = false" style="margin-left: 150px">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
      </el-tab-pane>
      <el-tab-pane name="second">
        <span slot="label"><i class="el-icon-help"></i> 奖励兑换表 </span>
        <div>
          <el-table :data="tableData"
                    stripe
                    style="margin-left: 100px;margin-top: 50px"
                    size="medium"
          >
            <el-table-column prop="id" fixed="left" label="记录ID"  width="100">
            </el-table-column>
            <el-table-column prop="rewardName" label="奖励名称"  width="100">
            </el-table-column>
            <el-table-column prop="userName" label="兑换人" width="120">
            </el-table-column>
            <el-table-column prop="rewardNum" label="兑换数量" width="140">
            </el-table-column>
            <el-table-column prop="rewardPoint" label="花费积分" width="140">
            </el-table-column>
            <el-table-column prop="ingestTime" label="兑换时间" width="200" :formatter="dateFormat">
            </el-table-column>
            <el-table-column label="操作" width="300" align="center">
              <template slot-scope="scope">
                <el-button type="success"
                           style="margin-left: 10px"
                           :disabled="scope.row.status===1"
                           @click="handleDeliver(scope.row.rewardId)">确认交付<i class="el-icon-success"></i>
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <div style="padding: 10px 0">
            <el-pagination
                style="margin-left: 450px;margin-top: 50px"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="pageNum"
                :page-sizes="[5, 10, 15, 20]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>

</template>

<script>
export default {
  name: "Reward",
  data(){
    return{
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) :{},
      form: {},
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      activeName: 'first',
      tabName: 'first',
      dialogFormVisible: false,
      rewardName: "",
      rules: {
        rewardName: [
          { required: true, message: '请输入奖品名称', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在 2 到 8 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.first()
  },
  methods:{
    first(){
      this.request.get("/reward/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          rewardName: this.rewardName
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    second(){
      this.request.get("/reward-exchange/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    search(){
      this.request.get("/reward/page",{
        params:{
          pageNum: 1,
          pageSize: this.pageSize,
          rewardName: this.rewardName
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })

    },
    reset(){
      this.rewardName = ""
      this.first()
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
      if(this.tabName ==='first'){
        this.first()
      }else if(this.tabName ==='second') {
        this.second()
      }
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum
      if(this.tabName ==='first'){
        this.first()
      }else if(this.tabName ==='second') {
        this.second()
      }

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
    save(){
      this.form.releasedBy = this.user.id
      this.form.releasedTime = new Date(this.form.releasedTime)
      this.request.post("/reward",this.form).then(res =>{
        if(res.code === '200'){
          this.$message.success("保存奖品成功")
          this.dialogFormVisible = false
          this.first()
        }else{
          this.$message.error("保存奖品失败")
          this.dialogFormVisible = false
        }
      })
    },
    publishReward(id){
      this.request.put("/reward/publish/" + id).then(res =>{
        if(res.code === '200'){
          this.$message.success("奖品上架成功")
          this.first()
        }else{
          this.$message.error("奖品上架失败")
        }
      })
    },
    returnReward(id){
      this.request.put("/reward/return/" + id).then(res =>{
        if(res.code === '200'){
          this.$message.success("奖品下架成功")
          this.first()
        }else{
          this.$message.error("奖品下架失败")
        }
      })
    },
    handleDeliver(rewardId){
      this.request.put("/reward-exchange/deliver/" + rewardId).then(res =>{
        if(res.code === '200'){
          this.$message.success("奖品交付成功")
          this.second()
        }else{
          this.$message.error("奖品交付失败")
        }
      })
    },
    stateFormat(row){
      if(row.rewardOver === 1){
          return "是"
      }
      else{
        return "否"
      }
    },
    handlePictureSuccess(res){
      this.form.picture = res
    },
    beforePictureUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    dateFormat(row){
      return this.$moment(row.releasedTime).format('YYYY-MM-DD HH:mm:ss')
    },

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
  width: 100px;
  height: 100px;
  line-height: 100px;
  text-align: center;
}
.avatar {
  width: 100px;
  height: 100px;
  display: block;
}

</style>