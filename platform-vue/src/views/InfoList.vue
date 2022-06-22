<template>
  <div>
    <span style="font-size: 25px;margin-left: 450px;margin-bottom: 50px">校 园 信 息 公 告 栏</span>
    <div v-for="(item,i) in tableData" :key="i" class="author-title reply-father">
      <el-avatar class="header-img" :size="40" :src="item.avatar"></el-avatar>
      <div class="author-info" >
        <span class="author-name" >{{item.infoTitle}}</span>
        <span class="author-time" >{{item.releasedTime.replace("T", " ")}}</span>
      </div>
      <div class="icon-btn">
        <el-button  type="primary"  icon="el-icon-chat-dot-square" @click="showReplyInput(i,item.infoTitle,item.infoId)"></el-button>
      </div>
      <div class="talk-box">
        <p>
          <span class="reply">{{item.infoDescription}}</span>
        </p>
      </div>
      <div class="reply-box">
        <div v-for="(reply,j) in item.reply" :key="j" class="author-title">
          <el-avatar class="header-img" :size="40" :src="reply.avatar"></el-avatar>
          <div class="author-info">
            <span class="author-time">{{reply.reviewTime.replace("T", " ")}}</span>
          </div>
          <div class="icon-btn">
          </div>
          <div class="talk-box">
            <p>
              <span class="author-name">{{reply.userName}} :</span>
              <span style="font-size: 12px">{{reply.reviewDescription}}</span>
            </p>
          </div>
          <div class="reply-box">
          </div>
        </div>
      </div>
      <div  v-show="_inputShow(i)" class="my-reply my-comment-reply">
        <el-avatar class="header-img" :size="40" :src="user.avatar"></el-avatar>
        <div class="reply-info" >
          <div tabindex="0"
               contenteditable="true"
               spellcheck="false"
               placeholder="输入评论..."
               @input="onDivInput($event)"
               class="reply-input reply-comment-input"></div>
        </div>
        <div class=" reply-btn-box">
          <el-button class="reply-btn" size="small" @click="sendCommentReply(i,j)" type="primary">发表评论</el-button>
        </div>
      </div>
    </div>
    <div style="padding: 10px 0;margin-left: 400px;margin-top: 20px">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5 ,10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
    <el-backtop :bottom="100"
                :visibility-height="40">
    </el-backtop>
  </div>
</template>


<script>
const clickoutside = {
  // 初始化指令
  bind(el, binding, vnode) {
    function documentHandler(e) {
      // 这里判断点击的元素是否是本身，是本身，则返回
      if (el.contains(e.target)) {
        return false;
      }
      // 判断指令中是否绑定了函数
      if (binding.expression) {
        // 如果绑定了函数 则调用那个函数，此处binding.value就是handleClose方法
        binding.value(e);
      }
    }
    // 给当前元素绑定个私有变量，方便在unbind中可以解除事件监听
    el.vueClickOutside = documentHandler;
    document.addEventListener('click', documentHandler);
  },
  update() {},
  unbind(el, binding) {
    // 解除事件监听
    document.removeEventListener('click', el.vueClickOutside);
    delete el.vueClickOutside;
  },
};
export default {
  name:'InfoList',
  data(){
    return{
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) :{},
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      btnShow: false,
      index:0,
      input:false,
      replyComment:'',
      myName:'Lana Del Rey',
      myHeader:'https://ae01.alicdn.com/kf/Hd60a3f7c06fd47ae85624badd32ce54dv.jpg',
      myId:19870621,
      to: '',
      toId: 0
    }
  },
  created() {
    this.load()
  },
  directives: {clickoutside},
  methods: {
    load(){
      this.request.get("info/page/review",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          infoTitle: this.infoTitle
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
    inputFocus(){
      let replyInput = document.getElementById('replyInput');
      replyInput.style.padding= "8px 8px"
      replyInput.style.border ="2px solid blue"
      replyInput.focus()
    },
    hideReplyBtn(){
      this.btnShow = false
      replyInput.style.padding= "10px"
      replyInput.style.border ="none"
    },
    showReplyInput(i,infoTitle,infoId){
      this.input = true
      this.tableData[this.index].inputShow = false
      this.index = i
      this.tableData[i].inputShow = true
      this.to = infoTitle
      this.toId = infoId
    },
    _inputShow(i){
      return this.tableData[i].inputShow
    },
    sendCommentReply(i,j){
      if(!this.replyComment){
        this.$message({
          showClose: true,
          type:'warning',
          message:'评论不能为空'
        })
      }else{
        let review ={}
        let timeNow = new Date().getTime();
        //let time= this.dateStr(timeNow);
        review.infoId = this.toId
        review.userId = this.user.id
        review.reviewTime = new Date(timeNow);
        review.reviewDescription = this.replyComment
        this.request.post("/review",review).then(res =>{
          if(res.code === '200'){
            this.$message.success("发布评论成功")
            this.dialogFormVisible = false
            this.load()
          }else{
            this.$message.error("发布评论失败")
            this.dialogFormVisible = false
          }
        })
        this.replyComment = ''
        document.getElementsByClassName("reply-comment-input")[i].innerHTML = ""
      }
    },
    onDivInput: function(e) {
      this.replyComment = e.target.innerHTML;
    },
    dateStr(date){
      //获取js 时间戳
      let time=new Date().getTime();
      //去掉 js 时间戳后三位，与php 时间戳保持一致
      time=parseInt((time-date)/1000);
      //存储转换值
      let s;
      if(time<60*10){//十分钟内
        return '刚刚';
      }else if((time<60*60)&&(time>=60*10)){
        //超过十分钟少于1小时
        s = Math.floor(time/60);
        return  s+"分钟前";
      }else if((time<60*60*24)&&(time>=60*60)){
        //超过1小时少于24小时
        s = Math.floor(time/60/60);
        return  s+"小时前";
      }else if((time<60*60*24*30)&&(time>=60*60*24)){
        //超过1天少于30天内
        s = Math.floor(time/60/60/24);
        return s+"天前";
      }else{
        //超过30天ddd
        let date= new Date(parseInt(date));
        return date.getFullYear()+"/"+(date.getMonth()+1)+"/"+date.getDate();
      }
    }
  },
}
</script>

<style   scoped>
.my-reply {
  padding: 10px;
  background-color: #fafbfc;
}
.my-reply .header-img {
  display: inline-block;
  vertical-align: top;
}
.my-reply .reply-info {
  display: inline-block;
  margin-left: 5px;
  width: 90%;
}
@media screen and (max-width: 1200px) {
  .my-reply .reply-info {
    width: 80%;
  }
}
.my-reply .reply-info .reply-input {
  min-height: 20px;
  line-height: 22px;
  padding: 10px 10px;
  color: #ccc;
  background-color: #fff;
  border-radius: 5px;
}
.my-reply .reply-info .reply-input:empty:before {
  content: attr(placeholder);
}
.my-reply .reply-info .reply-input:focus:before {
  content: none;
}
.my-reply .reply-info .reply-input:focus {
  padding: 8px 8px;
  border: 2px solid #00f;
  box-shadow: none;
  outline: none;
}
.my-reply .reply-btn-box {
  height: 25px;
  margin: 10px 0;
}
.my-reply .reply-btn-box .reply-btn {
  position: relative;
  float: right;
  margin-right: 15px;
}
.my-comment-reply {
  margin-left: 50px;
}
.author-title:not(:last-child) {
  border-bottom: 1px solid rgba(178,186,194,0.3);
  padding: 10px;
  margin-left: 20px;
}
.author-title {
  padding: 10px;
}
.author-title .header-img {
  display: inline-block;
  vertical-align: top;
}
.author-title .author-info {
  display: inline-block;
  margin-left: 5px;
  width: 60%;
  height: 40px;
  line-height: 20px;
}
.author-title .author-info >span {
  display: block;
  cursor: pointer;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.author-title .author-info .author-name {
  margin-left: 5px;
  color: #000;
  font-size: 20px;
  font-weight: bold;
}
.author-title .author-info .author-time {
  margin: 5px;
  font-size: 14px;
}
.author-title .icon-btn {
  width: 30%;
  padding: 0 !important;
  float: right;
}
@media screen and (max-width: 1200px) {
  .author-title .icon-btn {
    width: 20%;
    padding: 7px;
  }
}
.author-title .icon-btn >span {
  cursor: pointer;
}
.author-title .icon-btn .iconfont {
  margin: 0 5px;
}
.author-title .talk-box {
  margin: 0 50px;
}
.author-title .talk-box >p {
  margin: 0;
}
.author-title .talk-box .reply {
  font-size: 20px;
  color: #000;
}
.author-title .reply-box {
  margin: 10px 0 0 50px;
  background-color: #efefef;
}

</style>
