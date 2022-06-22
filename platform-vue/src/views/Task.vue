<template>
  <div>
    <div style="margin: 10px 0">
      <el-input placeholder="请输入任务标题" suffix-icon="el-icon-search" style="width: 200px" v-model="taskTitle"></el-input>
      <el-button style="margin-left: 5px " type="primary " @click="search">搜索</el-button>
      <el-button style="margin-left: 5px " type="warning " @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button  style="margin-left: 5px " type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
      <!--
      <el-button  style="margin-left: 5px " type="danger">批量删除<i class="el-icon-remove-outline"></i></el-button>
      <el-button  style="margin-left: 5px " type="primary">导入<i class="el-icon-bottom"></i></el-button>
      <el-button  style="margin-left: 5px " type="primary">导出<i class="el-icon-top"></i></el-button>
      -->
    </div>
    <!--多项选择函数
     <el-table :data="tableData" border stripe  @selection-change="handleSelectionChange">
     -->
    <el-table
        :data="tableData"
        border
        stripe
        :header-cell-class-name="'headerBg'"
    >
      <!-- 多项选择
       <el-table-column type="selection" width="40"> </el-table-column>
      -->
      <el-table-column prop="taskId" fixed="left" label="任务ID" width="60">
      </el-table-column>
      <el-table-column   prop="taskTitle" label="任务标题" width="120">
      </el-table-column>
      <el-table-column prop="taskDescription" label="任务描述"  width="200">
      </el-table-column>
      <el-table-column prop="taskPoints" label="任务奖励积分" width="100">
      </el-table-column>
      <el-table-column prop="taskStatus" label="运行状态" :formatter="stateFormat" width="80">
      </el-table-column>
      <el-table-column prop="commitStatus" label="提交状态" :formatter="commitFormat" width="80">
      </el-table-column>
      <el-table-column prop="releasedName" label="发布人" width="80">
      </el-table-column>
      <el-table-column prop="releasedTime" label="发布时间"  :formatter="dateFormat">
      </el-table-column>
      <el-table-column label="操作" width="405" align="center">
        <template slot-scope="scope">
          <el-button type="warning" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              style="margin-left: 10px"
              confirm-button-text='确认'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="del(scope.row.taskId)"
          >
            <el-button type="danger" slot="reference" >删除<i class="el-icon-remove"></i></el-button>
          </el-popconfirm>
          <el-button type="primary" style="margin-left: 10px" :disabled="scope.row.commitStatus!==0" @click="publishTask(scope.row.taskId)">发布<i class="el-icon-finished"></i></el-button>
          <el-button type="danger" :disabled="scope.row.commitStatus!==0" @click="returnTask(scope.row.taskId)">退回<i class="el-icon-refresh-left"></i></el-button>
          <el-button type="info" :disabled="scope.row.commitStatus===0" @click="cancelTask(scope.row.taskId)">终止<i class="el-icon-close"></i></el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
    <el-dialog title="任务信息" :visible.sync="dialogFormVisible" width="40%">
      <el-form :model="form" :rules="rules" label-width="80px" size="small" >
        <el-form-item class="item" label="任务标题:" prop="taskTitle" label-width="100px"><!--v-model和prop的属性名字一致-->
          <el-input placeholder="请输入任务标题" v-model="form.taskTitle"  clearable autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="任务描述:" label-width="100px" >
          <el-input v-model="form.taskDescription" clearable
                    type="textarea"
                    :autosize="{ minRows: 3, maxRows: 6}"
                    placeholder="请输入任务描述" autocomplete="off">
          </el-input>
        </el-form-item>
        <el-form-item label="奖励积分:" label-width="100px" prop="taskPoints"  >
          <el-input v-model.number="form.taskPoints"  clearable autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="发布时间:" label-width="100px" prop="releasedTime"  >
          <div class="block">
            <el-date-picker
                v-model="form.releasedTime"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="选择日期时间">
            </el-date-picker>
          </div>
        </el-form-item>

        <el-form-item >
          <el-button @click="dialogFormVisible = false" style="margin-left: 150px">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>


export default {
  name: "Task",
  props: {
  },
  data(){
    return{
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) :{},
      form: {},
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      dialogFormVisible: false,
      taskTitle: "",
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
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("/task/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          taskTitle: this.taskTitle
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
      //this.form.releasedBy = this.user.id
    },
    search(){
      this.request.get("/task/page",{
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
    handleAdd(){
      this.dialogFormVisible = true
      this.form = {}
    },
    handleEdit(row){
      this.form = Object.assign({},row)
      this.dialogFormVisible = true
    },
    save(){
      this.form.releasedBy = this.user.id
      this.form.releasedTime = new Date(this.form.releasedTime)
      this.request.post("/task",this.form).then(res =>{
          if(res.code === '200'){
          this.$message.success("发布任务成功")
          this.dialogFormVisible = false
          this.load()
        }else{
          this.$message.error("发布任务失败")
          this.dialogFormVisible = false
        }
      })
    },
    del(id){
      this.request.delete("/task/" + id).then(res =>{
        if(res.code === '200'){
          this.$message.success("删除任务成功")
          this.load()
        }else{
          this.$message.error("删除任务失败")
        }
      })
    },
    handleSelectionChange(val){ //处理批量删除
      this.multipleSelection = val;
    },
    publishTask(id){
      this.request.put("/task/publish/" + id).then(res =>{
        if(res.code === '200'){
          this.$message.success("发布任务成功")
          this.load()
        }else{
          this.$message.error("发布任务失败")
        }
      })
    },
    returnTask(id){
      this.request.put("/task/return/" + id).then(res =>{
        if(res.code === '200'){
          this.$message.success("退回任务成功")
          this.load()
        }else{
          this.$message.error("退回任务失败")
        }
      })
    },
    cancelTask(id){
      this.request.put("/task/cancel/" + id).then(res =>{
        if(res.code === '200'){
          this.$message.success("终止任务成功")
          this.load()
        }else{
          this.$message.error("终止任务失败")
        }
      })
    },
  }
}
</script>

<style >
.headerBg {
  background: #eee!important;
}

</style>