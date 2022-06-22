<template>
  <el-tabs type="border-card" @tab-click="handleClick" v-model="activeName">
    <el-tab-pane name="first">
      <span slot="label"><i class="el-icon-s-help"></i> 我接受的 </span>
      <el-table :data="tableData"
                stripe
                @cell-click="getDetails"
                style="margin-left: 100px;margin-top: 50px"
                size="medium"
      >
        <el-table-column prop="taskTitle" label="任务标题" width="200">
        </el-table-column>
        <el-table-column prop="taskPoints" label="任务奖励积分" width="120">
        </el-table-column>
        <el-table-column prop="taskStatus" label="运行状态" :formatter="stateFormat" width="100">
        </el-table-column>
        <el-table-column prop="releasedName" label="发布人" width="140">
        </el-table-column>
        <el-table-column prop="releasedTime" label="发布时间" width="200" :formatter="dateFormat">
        </el-table-column>
        <el-table-column label="操作" align="center" width="300">
          <template slot-scope="scope">
            <el-button type="info"
                       style="margin-left: 10px"
                       @click="dialogFormVisible = true">查看详情<i class="el-icon-finished"></i></el-button>
            <el-popconfirm
                class="ml-5"
                confirm-button-text='确认'
                cancel-button-text='我再想想'
                icon="el-icon-info"
                icon-color="red"
                title="您确定终止吗？"
                @confirm="handleAbandon(scope.row.taskId)"
            >
              <el-button type="warning" slot="reference"
                         style="margin-left: 5px"
                         :disabled="scope.row.taskStatus===3 || scope.row.taskStatus===2">终止<i class="el-icon-close"></i></el-button>
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
    </el-tab-pane>
    <el-tab-pane name="second">
      <span slot="label"><i class="el-icon-help"></i> 我发布的 </span>
      <el-table :data="tableData"
                stripe
                @cell-click="getDetails"
                style="margin-left: 10px;margin-top: 50px"
                size="medium"
      >
        <el-table-column prop="taskTitle" label="任务标题" width="250">
        </el-table-column>
        <el-table-column prop="taskPoints" label="任务奖励积分" width="120">
        </el-table-column>
        <el-table-column prop="taskStatus" label="运行状态" :formatter="stateFormat" width="100">
        </el-table-column>
        <el-table-column prop="commitStatus" label="提交状态" :formatter="commitFormat" width="80">
        </el-table-column>
        <el-table-column prop="receivedName" label="接受人" width="140">
        </el-table-column>
        <el-table-column prop="releasedTime" label="发布时间" width="200" :formatter="dateFormat">
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
             <el-button type="info" style="margin-left: 10px" @click="dialogFormVisible = true">查看详情<i class="el-icon-finished"></i></el-button>
             <el-button type="warning" slot="reference"
                         style="margin-left: 5px"
                         :disabled="scope.row.taskStatus===3"
                         @click="handleAbandon(scope.row.taskId)">终止<i class="el-icon-close"></i></el-button>
              <el-popconfirm
                  class="ml-5"
                  confirm-button-text='确认'
                  cancel-button-text='我再想想'
                  icon="el-icon-info"
                  icon-color="red"
                  title="您确定删除吗？"
                  @confirm="handleDelete(scope.row.taskId)"
              >
              <el-button type="danger" slot="reference"
                         style="margin-left: 10px"
                         :disabled="scope.row.deleteStatus===1">删除<i class="el-icon-close"></i></el-button>
              <el-button type="success" slot="reference"
                         :disabled="scope.row.taskStatus!==1 || scope.row.commitStatus !==1"
                         @click="handleFinish(scope.row.taskId)">完成<i class="el-icon-check"></i></el-button>
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
      <el-dialog
          :visible.sync="dialogFormVisible"
          width="40%"
          :show-close="false"
          :append-to-body="true">
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
          <el-descriptions-item label="提交状态">
            <el-tag size="medium">{{commitStatus}}</el-tag>
          </el-descriptions-item>
        </el-descriptions>
        <el-button type="primary" style="margin-left: 260px;margin-top: 20px" @click.stop="dialogFormVisible = false">确定</el-button>
      </el-dialog>
    </el-tab-pane>
    <el-tab-pane name="third">
      <span slot="label"><i class="el-icon-circle-plus-outline"></i> 发布新任务 </span>
      <el-card
          shadow="hover"
          style="margin-left: 70px;width: 90%"
      >
      <el-form :model="form" :rules="rules" label-width="80px" size="medium" >
        <el-form-item class="item" label="任务标题:" prop="taskTitle" label-width="100px" style="margin-left: 100px"><!--v-model和prop的属性名字一致-->
          <el-input placeholder="请输入任务标题" v-model="form.taskTitle" style="width: 50%" clearable autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="任务描述:" label-width="100px" style="margin-left: 100px">
          <el-input v-model="form.taskDescription"
                    clearable
                    type="textarea"
                    :autosize="{ minRows: 3, maxRows: 6}"
                    style="width: 50%"
                    placeholder="请输入任务描述" autocomplete="off">
          </el-input>
        </el-form-item>
        <el-form-item label="奖励积分:" label-width="100px" prop="taskPoints" style="margin-left: 100px">
          <el-input v-model.number="form.taskPoints"  clearable autocomplete="off" style="width: 50%"></el-input>
        </el-form-item>
        <el-form-item label="发布时间:" label-width="100px" prop="releasedTime" style="margin-left: 100px">
          <div class="block">
            <el-date-picker
                v-model="form.releasedTime"
                type="datetime"
                placeholder="选择日期时间">
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item >
          <el-button @click="dialogFormVisible = false" style="margin-left: 200px;margin-top: 50px">取 消</el-button>
          <el-button type="primary" @click="save">发 布</el-button>
        </el-form-item>
      </el-form>
      </el-card>
    </el-tab-pane>
  </el-tabs>

</template>

<script>
export default {
  name: "MyTask",
  data() {
    return {
      activeName: 'first',
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) :{},
      form:{},
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      dialogFormVisible: false,
      taskTitle: "",
      description: {},
      status: '',
      commitStatus: '',
      taskId: 0,
      tabName: 'first',
      rules: {
        taskTitle: [
          { required: true, message: '请输入任务标题', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在 2 到 8 个字符', trigger: 'blur' }
        ],
        taskPoints: [
          { required: true, message: '积分不能为空'},
          { type: 'number', message: '积分必须为数字值'}
        ],
        releasedTime :[
          { required: true, message: '发布时间不能为空'},
        ]
      }
    };
  },
  created() {
    this.first()
  },
  methods:{
    first(){
      this.request.get("/task-received/"+ this.user.id,{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          taskTitle: this.taskTitle
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    second(){
      this.request.get("/task/released/"+ this.user.id,{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          taskTitle: this.taskTitle
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })

    },
    third(){
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
      if(this.tabName ==='first'){
        this.first()
      }else if(this.tabName ==='second'){
        this.second()
      }else if(this.tabName ==='third'){
        this.third()
      }else{
        console.log(this.tabName)
      }
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum
      if(this.tabName ==='first'){
        this.first()
      }else if(this.tabName ==='second'){
        this.second()
      }else if(this.tabName ==='third'){
        this.third()
      }else{
        console.log(this.tabName)
      }
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
      if(this.description.commitStatus === 0){
        this.commitStatus = '审核中'
      }else if(this.description.commitStatus === 1){
        this.commitStatus = '已通过'
      }else if(this.description.commitStatus === 2){
        this.commitStatus = '未通过'
      }else{
        this.commitStatus = '无'
      }
    },
    handleClick(tab, event){
      if(tab.name === 'first'){
        this.tabName ='first'
        this.first()
      }else if(tab.name === 'second'){
        this.tabName ='second'
        this.second()
      }else if(tab.name === 'third'){
        this.tabName ='third'
        this.third()
      }else{
        console.log(tab, event);
      }
    },
    handleAbandon(id){
      this.request.put("/task/cancel/" + id +"/" +this.user.id).then(res =>{
        if(res.code === '200'){
          this.$message.success("终止任务成功")
          if(this.tabName ==='first'){
            this.first()
          }else if(this.tabName ==='second'){
            this.second()
          }
        }else{
          this.$message.error("终止任务失败")
        }
      })

    },
    handleDelete(id){//假删除
      this.request.put("/task/del/" + id).then(res =>{
        if(res.code === '200'){
          this.$message.success("删除任务成功")
          this.second()
        }else{
          this.$message.error("删除任务失败")
        }
      })

    },
    handleFinish(id){
      this.taskId = id
      this.request.put("/task-done",{
        data:{
          taskId: this.taskId,
        }
      }).then(res => {
        if(res.code === '200'){
          this.$message.success("完成任务成功")
          this.second()
        }else{
          this.$message.error("完成任务失败")
        }
      })

    },
    save(){
      this.form.releasedBy = this.user.id
      this.request.post("/task",this.form).then(res =>{
        if(res.code === '200'){
          this.$message.success("发布任务成功")
          this.dialogFormVisible = false
          this.second()
        }else{
          this.$message.error(res.msg)
          this.dialogFormVisible = false
        }
      })
    },
  }
}
</script>

<style>
.el-table::before {
  height: 0px;
}

</style>