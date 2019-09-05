<!--用户-信息组件-->
<template>
  <div>
    <div v-if="messages.length === 0">
      <div>您暂时还没有收到消息哦！</div>
    </div>
    <div v-if="messages.length > 0">
      <div>全部消息</div>
      <div class="message-item" v-for="(message, index) in messages" :key="index">
        <user-message-item :msg="message"></user-message-item>
      </div>
    </div>
  </div>
</template>

<script>
  import UserMessageItem from "./UserMessageItem";
  
  export default {
    name: "UserMessage",
    components: {UserMessageItem},
    data() {
      return {
        type: 1,
        messages: []
      }
    },
    mounted() {
      this.getMessages();
    },
    methods: {
      // 获取用户信息
      getMessages: function () {
        const _this = this;
        
        // 在这里发起请求
        this.$http.get('/user/getMessages', {
          params: {
            uid: 0,
            type: this.type
          },
        }).then(
          function (response) {
            if (response.data.code === 200) {
              _this.messages = response.data.data.myMessages;
              for(var i=0;i<_this.messages.length;i++){
                _this.messages[i].messageDate = response.data.data.myMessages[i].messageDate
              }
            } else {
              this.$message({showClose: true, message: response.data.message, type: 'error'});
            }
          }
        ).catch(function (err) {
          console.log(err);
        });
      }
    },
    
  }
</script>

<style scoped>
.message-item {
  margin: 2vmin 0;
  padding: 1vmin 0;
  border-bottom: 1px solid black;
}
</style>
