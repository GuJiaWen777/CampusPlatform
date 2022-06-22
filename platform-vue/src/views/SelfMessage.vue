<template>
  <el-tabs type="border-card" @tab-click="handleClick" v-model="activeName">
    <el-tab-pane name="first">
      <span slot="label"><i class="el-icon-circle-plus-outline"></i> 留言咨询 </span>
      <el-card
          shadow="hover"
          style="margin-left: 50px;width: 90%"
      >
        <el-form :model="form" :rules="rules" label-width="80px" size="medium" >
          <el-form-item label="问题类型:" label-width="100px" prop="type" style="margin-left: 100px">
            <el-select v-model="form.type" clearable placeholder="请选择留言类型">
              <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="情况说明:" label-width="100px" prop="description" style="margin-left: 100px">
            <el-input v-model="form.description"
                      clearable
                      type="textarea"
                      :autosize="{ minRows: 3, maxRows: 6}"
                      style="width: 50%"
                      placeholder="情况说明" autocomplete="off">
            </el-input>
          </el-form-item>
          <el-form-item label="联系QQ:" label-width="100px" prop="contact" style="margin-left: 100px">
            <el-input v-model="form.contact"
                      style="width: 50%"
                      placeholder="联系QQ" autocomplete="off">
            </el-input>
          </el-form-item>
          <el-form-item style="margin-left: 200px">
            <el-button type="primary" @click="save">提 交</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-tab-pane>
    <el-tab-pane name="second">
      <span slot="label"><i class="el-icon-s-help"></i> 我的留言 </span>
      <el-table :data="tableData"
                stripe
                style="margin-left: 100px;margin-top: 50px"
                size="medium"
      >
        <el-table-column prop="submitTime" label="提交时间" width="200" :formatter="submitTimeFormat">
        </el-table-column>
        <el-table-column prop="type" label="问题类型" width="120" :formatter="typeFormat">
        </el-table-column>
        <el-table-column prop="description" label="情况说明" width="200">
        </el-table-column>
        <el-table-column prop="status" label="处理状态" :formatter="stateFormat" width="100">
        </el-table-column>
        <el-table-column prop="replyTime" label="回复时间" width="200" :formatter="replyTimeFormat">
        </el-table-column>
        <el-table-column prop="reply" label="客服回复" width="200">
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
    </el-tab-pane>

  </el-tabs>

</template>

<script>
export default {
  name: "SelfMessage",
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
      status: '',
      commitStatus: '',
      tabName: 'first',
      rules: {
        type: [
          { required: true, message: '请输入类型', trigger: 'blur' },
        ],
        description: [
          { required: true, message: '请输入说明', trigger: 'blur' },
        ],
        contact: [
          { required: true, message: '请输入联系QQ', trigger: 'blur' },
        ],
      },
      options: [{
        value: 1,
        label: '任务问题'
      }, {
        value: 2,
        label: '账户问题'
      }, {
        value: 3,
        label: '奖励问题'
      }, {
        value: 4,
        label: '积分问题'
      }, {
        value: 5,
        label: '其他问题'
      }]
    };
  },
  created() {
  },
  methods:{
    first(){
    },
    second(){
      this.request.get("/message/page/"+ this.user.id,{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })

    },
    typeFormat(row) {
      if(row.type === 1){
        return '任务问题'
      }else if(row.type === 2){
        return '账户问题'
      }else if(row.type === 3){
        return '奖励问题'
      }else if(row.type === 4){
        return '积分问题'
      }else if(row.type === 5){
        return '其他问题'
      }else{
        return '无'
      }

    },
    stateFormat(row) {
      if(row.status === 0){
        return '未回复'
      }else if(row.status === 1){
        return '已经回复'
      }else{
        return '无'
      }
    },
    submitTimeFormat(row){
      return this.$moment(row.submitTime).format('YYYY-MM-DD HH:mm:ss')
    },
    replyTimeFormat(row){
      if(row.replyTime !== null){
        return this.$moment(row.replyTime).format('YYYY-MM-DD HH:mm:ss')
      }

    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize
      if(this.tabName ==='first'){
        this.first()
      }else if(this.tabName ==='second'){
        this.second()
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
      }else{
        console.log(this.tabName)
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
      let timestamp = new Date().getTime()
      this.form.submitTime = new Date(timestamp)
      this.form.userId = this.user.id
      this.request.post("/message",this.form).then(res =>{
        if(res.code === '200'){
          this.$message.success("发布留言成功")
          this.first()
        }else{
          this.$message.error(res.msg)
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