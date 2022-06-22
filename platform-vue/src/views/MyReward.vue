<template>
  <div>
    <el-table :data="tableData"
              stripe
              style="margin-left: 150px;margin-top: 50px"
              size="medium"
              @cell-click="getDetails"
              width="300"
    >
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
      <el-table-column label="操作" align="center" width="300">
        <template slot-scope="scope">
          <el-button type="info"
                     style="margin-left: 10px"
                     @click="dialogFormVisible = true">查看详情<i class="el-icon-finished"></i>
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
    <el-dialog
        :visible.sync="dialogFormVisible"
        width="40%"
        :show-close="false"
        :append-to-body="true">
      <el-descriptions title="详细信息"
                       :column="1"
                       border
                       direction="vertical" >
        <el-descriptions-item label="奖励名称">
          <span>{{description.rewardName}}</span>
        </el-descriptions-item>
        <el-descriptions-item label="兑换数量">
          <span>{{description.rewardNum}}</span></el-descriptions-item>
        <el-descriptions-item label="花费积分">
          <span>{{description.rewardPoint}}</span>
        </el-descriptions-item>
        <el-descriptions-item label="交付状态">
          <el-tag size="medium">{{statusFormat(description.status)}}</el-tag>
        </el-descriptions-item>
      </el-descriptions>
      <el-button type="primary" style="margin-left: 260px;margin-top: 20px" @click.stop="dialogFormVisible = false">确定</el-button>
    </el-dialog>
  </div>

</template>

<script>
export default {
  name: "MyReward",
  data(){
    return{
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) :{},
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      dialogFormVisible:  false,
      description:{}
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("/reward-exchange/page/" + this.user.id,{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
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
    dateFormat(row){
      return this.$moment(row.releasedTime).format('YYYY-MM-DD HH:mm:ss')
    },
    getDetails(row){
      this.description = row
    },
    statusFormat(status){
      if(status === 0){
        return '未交付'
      }else if(status === 1){
        return '已交付'
      }else{
        return '无'
      }
    }

  }
}
</script>

<style >


.el-table::before {
  height: 0px;
}
</style>