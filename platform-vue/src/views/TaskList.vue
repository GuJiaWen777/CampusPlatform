<template>
  <div>
    <div>

    </div>
    <div style="margin-left: 5px;margin-bottom: 10px">
      <el-input placeholder="请输入任务标题" suffix-icon="el-icon-search" style="width: 400px;margin-left: 350px;" size="medium" v-model="taskTitle"></el-input>
      <el-button style="margin-left: 5px " type="primary " @click="search">搜索</el-button>
      <el-button style="margin-left: 5px " type="warning " @click="reset">重置</el-button>
    </div>
    <el-table :data="tableData"
              stripe
              @cell-click="getDetails"
              style="margin-left: 100px;margin-top: 50px"
              size="medium"
    >
      <el-table-column prop="taskTitle" label="任务标题" width="250">
      </el-table-column>
      <el-table-column prop="taskPoints" label="任务奖励积分" width="120">
      </el-table-column>
      <el-table-column prop="taskStatus" label="运行状态" :formatter="stateFormat" width="100">
      </el-table-column>
      <el-table-column prop="releasedName" label="发布人" width="140">
      </el-table-column>
      <el-table-column prop="releasedTime" label="发布时间" width="200" :formatter="dateFormat">
      </el-table-column>
      <el-table-column label="操作" width="250" align="center">
        <template slot-scope="scope">
          <el-button type="info" style="margin-left: 10px" @click="dialogFormVisible = true">查看详情<i class="el-icon-finished"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确认'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定接受吗？"
              @confirm="handleReceive(scope.row.taskId)"
          >
          <el-button type="danger" slot="reference" :disabled="scope.row.taskStatus!==0|| blockStatus === 1 ||scope.row.releasedBy === user.id  ">接受<i class="el-icon-refresh-left"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          style="margin-left: 400px;margin-top: 50px"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
    <el-dialog :visible.sync="dialogFormVisible" width="40%" :show-close="false">
      <el-descriptions title="详细信息"
                       :column="1"
                       border
                       direction="vertical" >
        <el-descriptions-item label="任务标题">
          <span>{{description.taskTitle}}</span>
        </el-descriptions-item>
        <el-descriptions-item label="任务详情">
          <span>{{description.taskDescription}}</span></el-descriptions-item>
        <el-descriptions-item label="奖励积分">
          <span>{{description.taskPoints}}</span>
        </el-descriptions-item>
        <el-descriptions-item label="运行状态">
          <el-tag size="medium">{{status}}</el-tag>
        </el-descriptions-item>
      </el-descriptions>
      <el-button type="primary" style="margin-left: 260px;margin-top: 20px" @click.stop="dialogFormVisible = false">确定</el-button>
    </el-dialog>
  </div>
</template>

<script>
import {setRoutes} from "../router";

export default {
  name: "TaskList",
  props: {
  },
  data(){
    return{
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) :{},
      form: {},
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      dialogFormVisible: false,
      taskTitle: "",
      description: {},
      status:'待接受',
      taskId:0,
      blockStatus:localStorage.getItem("blockStatus") ? JSON.parse(localStorage.getItem("blockStatus")) :{},
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("/task/page/common",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          taskTitle: this.taskTitle
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
      this.request.get("/user/status/" + this.user.id).then(res =>{
        if(res.code === '200') {
          sessionStorage.setItem("blockStatus", JSON.stringify(res.data)) //更新浏览器存储信息
        }
        if(res.data === 1){
          this.$message({
            message: '警告，您已被拉黑,无法接受任务！',
            type: 'warning'
          });
        }
      })
      this.form.releasedBy = this.user.id
    },
    search(){
      this.request.get("/task/page/common",{
        params:{
          pageNum: 1,
          pageSize: this.pageSize,
          taskTitle: this.taskTitle
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    reset(){
      this.taskTitle = ""
      this.load()
    },
    stateFormat(row) {
      if (row.taskStatus === 0) {
        return "待接受";
      }
      else if (row.taskStatus === 1) {
        return "进行中";
      }
      else if (row.taskStatus === 2) {
        return "已完成";
      }
      else if (row.taskStatus === 3) {
        return "已终止";
      }else {
        return "空";
      }
    },
    commitFormat(row) {
      if (row.commitStatus === 0) {
        return "审核中";
      }
      else if (row.commitStatus === 1) {
        return "已通过";
      }
      else if (row.commitStatus === 2) {
        return "未通过";
      }else{
        return "空";
      }
    },
    dateFormat(row){
      return this.$moment(row.releasedTime).format('YYYY-MM-DD HH:mm:ss')
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum
      this.load()
    },
    getDetails(row){
      this.description = row
      if(this.description.taskStatus === 0){
        this.status = '待接受'
      }else if(this.description.taskStatus === 1){
        this.status = '进行中'
      }else if(this.description.taskStatus === 2){
        this.status = '已完成'
      }else{
        this.status = '无'
      }
    },
    handleReceive(id){
      this.taskId = id
      this.request.put("/task-received",{
        data:{
          taskId: this.taskId,
          userId: this.user.id
        }
      }).then(res => {
            if(res.code === '200'){
              this.$message.success("接受任务成功")
              this.load()
            }else{
              this.$message.error("接受任务失败")
            }
      })
    }
  }
}
</script>

<style scoped>

</style>