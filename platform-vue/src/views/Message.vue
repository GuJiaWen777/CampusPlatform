<template>
  <div>
    <div style="margin: 10px 0">
      <el-select v-model="type" clearable placeholder="请选择留言类型">
        <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-button style="margin-left: 5px " type="primary " @click="search">搜索</el-button>
      <el-button style="margin-left: 5px " type="warning " @click="reset">重置</el-button>
    </div>
    <el-table
        :data="tableData"
        border
        stripe
        size="medium"
        :header-cell-class-name="'headerBg'"
    >
      <el-table-column prop="messageId" fixed="left" label="留言ID" width="80">
      </el-table-column>
      <el-table-column prop="submitTime" label="提交时间" width="200" :formatter="dateFormat">
      </el-table-column>
      <el-table-column prop="type" label="问题类型" width="120" :formatter="typeFormat">
      </el-table-column>
      <el-table-column prop="description" label="情况说明">
      </el-table-column>
      <el-table-column  prop="contact" label="联系方式" width="200">
      </el-table-column>
      <el-table-column  prop="status" label="处理状态" width="140" :formatter="stateFormat">
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button
              type="warning"
              @click="handleReply(scope.row)" :disabled="scope.row.status===1">回复<i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              style="margin-left: 10px"
              confirm-button-text='确认'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="del(scope.row.messageId)"
          >
            <el-button type="danger" slot="reference" >删除<i class="el-icon-remove"></i></el-button>
          </el-popconfirm>
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

    <el-dialog title="回复" :visible.sync="dialogFormVisible" width="40%">
      <el-form :model="form" :rules="rules" label-width="80px" size="small" >
        <el-form-item class="item" label="情况说明:" prop="description" label-width="100px"><!--v-model和prop的属性名字一致-->
          <el-input   type="textarea" v-model="form.description"  :disabled="true" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item class="item" label="回复:" prop="reply" label-width="100px"><!--v-model和prop的属性名字一致-->
          <el-input   type="textarea" :rows="2" placeholder="请输入回复内容" v-model="form.reply"  clearable autocomplete="off"></el-input>
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
  name: "Message",
  data() {
    return {
      tableData: [],
      form:{},
      total: 0,
      pageNum: 1,
      pageSize: 5,
      type: '',
      dialogFormVisible: false,
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      options: [{
        value: '1',
        label: '任务问题'
      }, {
        value: '2',
        label: '账户问题'
      }, {
        value: '3',
        label: '奖励问题'
      }, {
        value: '4',
        label: '积分问题'
      }, {
        value: '5',
        label: '其他问题'
      }],
      rules: {
        reply: [
          { required: true, message: '请输入回复', trigger: 'blur' },
        ]
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    load(){
      this.request.get("/message/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          type: this.type
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })

    },
    search(){
      this.request.get("/message/page",{
        params:{
          pageNum: 1,
          pageSize: this.pageSize,
          type: this.type
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    reset(){
      this.load()
    },
    dateFormat(row){
      return this.$moment(row.reviewTime).format('YYYY-MM-DD HH:mm:ss')
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum
      this.load()
    },
    handleReply(row){
      this.form = Object.assign({},row)
      this.dialogFormVisible = true
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
    save(){
      let timestamp = new Date().getTime()
      this.form.replyTime = new Date(timestamp)
      this.form.status = 1
      this.request.post("/message",this.form).then(res =>{
        if(res.code === '200'){
          this.$message.success("留言回复成功")
          this.dialogFormVisible = false
          this.load()
        }else{
          this.$message.error("留言回复失败")
          this.dialogFormVisible = false
        }
      })
    },
    del(id){
      this.request.delete("/message/" + id).then(res =>{
        if(res.code === '200'){
          this.$message.success("删除留言成功")
          this.load()
        }else{
          this.$message.error("删除留言失败")
        }
      })
    },
  }
}
</script>

<style>
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
  width: 138px;
  height: 138px;
  line-height: 138px;
  text-align: center;
}
.avatar {
  width: 138px;
  height: 138px;
  display: block;
}
</style>