<!--页面页眉组件-->
<template>
  <div class="menu-bar">
    <!--智库logo-->
    <img class="header-icon" :src="sharingIdeaImage" />
    <!--手机端tab-->
    <ul class="header-menu" v-if="$store.state.isMobile">
      <li class="sub-menu" style="width:20%;" v-for="item in mobileList" :key="item.path">
        <span
          v-if="$store.state.path.includes(item.path)"
          @click="menuClick(item.path)"
          class="content active"
        >{{ item.menu }}</span>
        <span v-else @click="menuClick(item.path)" class="content">
          {{
          item.menu
          }}
        </span>
      </li>
    </ul>
    <!--PC端tab-->
    <ul class="header-menu" v-else>
      <li class="sub-menu" v-for="item in pcList" :key="item.path">
        <span
          v-if="$store.state.path.includes(item.path)"
          @click="menuClick(item.path)"
          class="content active"
        >{{ item.menu }}</span>
        <span v-else @click="menuClick(item.path)" class="content">
          {{
          item.menu
          }}
        </span>
      </li>
    </ul>
    <!--用户头像/登录注册按钮-->
    <img
      class="header-user"
      :src="userIcon || $store.state.user.userIcon"
      @click="menuClick('/user')"
    />
  </div>
</template>

<script>
export default {
  name: "MainHeader",
  props: {
    userIcon: String
  },
  data() {
    return {
      sharingIdeaImage: require("../assets/sharing-idea-logo.png"),
      // userImage: require("../assets/user-default.png"),
      mobileList: [
        { menu: "见解", path: "/knowledge" },
        { menu: "文件", path: "/resources" },
        { menu: "论坛", path: "/forum" },
        { menu: "专栏", path: "/article" },
        { menu: "关于", path: "/about" }
      ],
      pcList: [
        { menu: "知识见解", path: "/knowledge" },
        { menu: "文件资源", path: "/resources" },
        { menu: "在线论坛", path: "/forum" },
        { menu: "智库专栏", path: "/article" },
        { menu: "智库周记", path: "/weekly/diary" },
        { menu: "关于智库", path: "/about" }
      ]
    };
  },
  methods: {
    menuClick: function(path) {
      console.log(path);
      if (path.includes("/user")) {
        if (this.$store.state.isLogin) {
          this.$router.push("/user");
        } else {
          this.$router.push("/user/login");
        }
      } else {
        this.$router.push(path);
      }
    }
  }
};
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
  height: 10vh;
  width: 100%;
  background: #fff;
  box-shadow: 0 2px 5px #ccc;
  display: flex;
  flex-direction: row;

  @media screen and (max-width: 800px) {
    .header-icon {
      width: 5vh;
      height: 5vh;
      margin: 3vh 2vh;
    }

    .header-menu {
      width: 82vw;
      height: 10vh;
    }
  }
  @media screen and (min-width: 801px) {
    .header-icon {
      width: 7vh;
      height: 7vh;
      margin: 1.5vh 3vh;
    }

    .header-menu {
      width: 82vw;
      height: 10vh;
      margin: 0 3vmin;
    }
  }

  .sub-menu {
    float: left;
    width: 16.6%;
    max-width: 17vmin;
    cursor: pointer;

    .content {
      display: block;
      height: 10vh;
      line-height: 10vh;
      font-size: 2vh;
      text-align: center;
    }

    .content.active {
      border-bottom: 2px solid #000099;
    }
  }

  .header-user {
    width: 5vh;
    height: 5vh;
    margin: 2.5vh;
    cursor: pointer;
  }
}
</style>
