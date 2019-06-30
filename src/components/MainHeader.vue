<!--页面页眉组件-->
<template>
  <div class="menu-bar">
    <!--智库logo-->
    <img class="header-icon" :src="sharingIdeaImage"/>
    <!--手机端tab-->
    <ul class="header-menu" v-if="$store.state.isMobile">
      <li class="sub-menu" v-for="(item, index) in mobileList" :key="item.id">
        <span v-if="$store.state.tabIndex === index" @click="menuClick(index)"
              class="content active">{{item.menu}}</span>
        <span v-else @click="menuClick(index)" class="content">{{item.menu}}</span>
      </li>
    </ul>
    <!--PC端tab-->
    <ul class="header-menu" v-else>
      <li class="sub-menu" v-for="(item, index) in pcList" :key="item.id">
        <span v-if="$store.state.tabIndex === index" @click="menuClick(index)"
              class="content active">{{item.menu}}</span>
        <span v-else @click="menuClick(index)" class="content">{{item.menu}}</span>
      </li>
    </ul>
    <!--用户头像/登录注册按钮-->
    <img class="header-user" :src="$store.state.user.userIcon" @click="menuClick(4)"/>
  </div>
</template>

<script>
  import {routerChange} from "../tools";
  
  export default {
    name: "MainHeader",
    methods: {
      menuClick: function (index) {
        this.$store.commit("setTabIndex", index);
        switch (this.$store.state.tabIndex) {
          case 0:
            routerChange("/", this);
            break;
          case 1:
            routerChange("/resources", this);
            break;
          case 2:
            routerChange("/article", this);
            break;
          case 3:
            routerChange("/about", this);
            break;
          case 4:
            if (this.$store.state.isLogin) {
              routerChange("/user", this);
            } else {
              routerChange("/user/login", this);
            }
            break;
          default:
            routerChange("/", this);
        }
      }
    },
    data() {
      return {
        sharingIdeaImage: require("../assets/sharing-idea-logo.png"),
        // userImage: require("../assets/user-default.png"),
        mobileList: [
          {id: 0, menu: "见解"},
          {id: 1, menu: "文件"},
          {id: 2, menu: "专栏"},
          {id: 3, menu: "关于"}
        ],
        pcList: [
          {id: 0, menu: "知识见解"},
          {id: 1, menu: "文件资源"},
          {id: 2, menu: "智库专栏"},
          {id: 3, menu: "关于智库"}
        ]
      }
    }
  }
</script>

<style lang="less" scoped>
  * {
    margin: 0;
    padding: 0;
  }
  
  li {
    list-style: none;
  }
  
  .menu-bar {
    height: 10vmin;
    width: 100vw;
    background: #fff;
    box-shadow: 0 2px 5px #ccc;
    display: flex;
    flex-direction: row;
    
    .header-icon {
      width: 7vmin;
      height: 7vmin;
      margin: 1.5vmin 3vmin;
    }
    
    .header-menu {
      width: 70vw;
      height: 10vmin;
      margin: 0 3vmin;
    }
    
    .sub-menu {
      float: left;
      width: 17vmin;
      
      .content {
        display: block;
        height: 10vmin;
        line-height: 10vmin;
        font-size: 2vmin;
        text-align: center;
      }
      
      .content.active {
        border-bottom: 2px solid #000099;
      }
    }
    
    .header-user {
      width: 5vmin;
      height: 5vmin;
      margin: 2.5vmin;
    }
  }
</style>
