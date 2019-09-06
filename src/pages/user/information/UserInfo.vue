<!--用户-用户信息组件-->
<template>
  <el-form ref="form" :model="baseInfo">
    <div class="user-image">
      <img id="user_avatar" :src="baseInfo.userAvatar">
      <el-button class="user-image-button" type="primary" disabled>更换头像</el-button>
    </div>
    <div class="user-info-form-item">
      <el-input class="user-info-form-input" v-model="baseInfo.userNick"></el-input>
      <el-button type="primary" class="user-info-form-button" disabled>修改昵称</el-button>
    </div>
    <div class="user-info-form-item">
      <el-input class="user-info-form-input" v-model="baseInfo.userEmail" disabled></el-input>
      <el-button type="primary" class="user-info-form-button" disabled>修改邮箱</el-button>
    </div>
    <div class="user-info-form-item">
      <el-input class="user-info-form-input" v-model="baseInfo.userPhone"></el-input>
      <el-button type="primary" class="user-info-form-button" disabled>修改电话</el-button>
    </div>
  </el-form>
</template>

<script>
  export default {
    name: "UserInfo",
    data() {
      return {
        baseInfo: {}
      }
    },
    mounted: function () {
      this.getUserInfo();
    },
    methods: {
      getUserInfo: function () {
        // 获取用户的基本信息
        const _this = this;
        
        // 在这里发起请求
        _this.$http.get('/user/getBaseInfo', {
          params: {
            uid: 0
          }
        }).then(
          function (response) {
            if (response.data.code === 200) {
              console.log(response.data);
              _this.baseInfo = response.data.data.baseInfo;
            } else {
              this.$message({showClose: true, message: response.data.message, type: 'error'});
            }
          }
        ).catch(function (err) {
          console.log(err);
        });
      }
    }
  }
</script>

<style lang="less" scoped>
  .user-info-form-item {
    display: flex;
    flex-direction: row;
    
    .user-info-form-input {
      width: 100vmin;
      margin: 2vmin 2vmin 2vmin 0;
    }
    
    .user-info-form-button {
      width: 30vmin;
      margin: 2vmin 0 2vmin 2vmin;
      padding: 1vmin;
      text-align: center;
      font-size: 2vmin;
      
    }
  }
  
  .user-image {
    display: flex;
    flex-direction: row;
    align-items: center;
    
    img {
      width: 30vmin;
      height: 30vmin;
    }
  
    .user-image-button {
      width: 20vmin;
      padding: 1vmin;
      text-align: center;
      font-size: 2vmin;
      margin: auto 2vmin;
    }
  }
</style>
