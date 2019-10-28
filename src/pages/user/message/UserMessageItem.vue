<!--用户-单条信息组件-->
<template>
  <div>
    <img :src="msg.toAvatar">
    <p>{{msg.toUserName}}<br>{{msg.messageDate}}</p>
    <p>{{msg.messageContent}}</p>
  </div>
</template>

<script>
  export default {
    name: "UserMessageItem",
    props: ["msg"]
  }
</script>

<style lang="less" scoped>
div {
  display: flex;
  flex-direction: row;
  
  img {
    width: 10vmin;
    height: 10vmin;
  }
  
  div {
    width: 40vmin;
    height: 10vmin;
    text-align: left;
    padding: 2vmin;
  }
  
  p {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    width: 50vmin;
    height: 10vmin;
    padding: 2vmin;
  }
}
</style>
