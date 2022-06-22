<template>
  <div v-if="reset">
    <div style="margin-left:1%;margin-right:1%">
      <el-row>
        <el-col :span="4" v-for="(item) in tableData" :key="item.rewardId" :offset="1" >
          <div style="margin-top:15px">
            <el-card :body-style="{ padding: '0px'}" shadow="hover" >
              <img v-if="item.picture" :src="item.picture" class="avatar">
              <i v-else class="el-icon-box avatar-uploader-icon"></i>
              <div style="padding: 14px;">
                <span style="font-size: 20px"><strong>{{item.rewardName}}</strong></span><br>
                <span style="font-size: 14px">剩余数量:{{item.rewardNum}}</span>
                <div class="bottom clearfix">
                  <span style="font-size: 14px">所需积分:{{item.rewardPoint}}</span>
                  <br/>
                  <div style="margin-top: 10px">
                  <el-input-number size="mini" v-model="item.exchangeNum" :min="1" :max="item.rewardNum" ></el-input-number>

                  <el-button
                      type="text"
                      class="button"
                      style="font-size: 14px;margin-top: 5px"
                      @click="exchangeReward(item) ">兑换</el-button>
                  </div>
                </div>
              </div>
            </el-card>
          </div>
        </el-col>
      </el-row>
      <div style="margin-left:35%">
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
  </div>

</template>

<script>
export default {
  name: "RewardList",
  data(){
    return{
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) :{},
      tableData: [],
      total: 0,
      form:{},
      pageNum: 1,
      pageSize: 8,
      reset:false,
      rewardName:'',
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("/reward/page/common",{
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
    search(){
      this.request.get("/reward/page/common",{
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
    handleSizeChange(pageSize){
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum
      this.load()
    },
    exchangeReward(item){
      this.$confirm('您确认兑换吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.form.userId = this.user.id
        this.form.rewardId = item.rewardId
        console.log(item)
        if(item.rewardNum - item.exchangeNum >= 0){
          this.form.rewardNum = item.exchangeNum;
          this.request.post("/reward-exchange",this.form).then(res =>{
            if(res.code === '200'){
              this.$message.success("兑换奖品成功")
              this.load()
            }else {
              this.$message.error("积分不足，兑换奖品失败")
            }
          })
        }else {
          this.$message.error("奖品剩余不足，兑换奖品失败")
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消兑换'
        });
      });



    }

  },
  watch:{
    tableData:function(){
      this.$nextTick(function(){
        /*现在数据已经渲染完毕*/
        this.reset = true;
      })
    }
  }
}

</script>

<style >
avatar-uploader {
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
  height: 100px;
  line-height: 100px;
  text-align: center;
  width: 100%;
  display: block;
}
.avatar {
  width: 100px;
  height: 100px;
  line-height: 100px;
  display: block;
  margin-left: 50px;
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