<template>
  <div id="app">
    <main-header :userIcon="userIcon"></main-header>
    <div id="router-view">
      <router-view />
    </div>
    <main-footer></main-footer>
  </div>
</template>

<script>
import MainHeader from "./components/MainHeader.vue";
import MainFooter from "./components/MainFooter";
import { getCookie } from "./tools.js";

export default {
  name: "app",
  components: {
    MainFooter,
    MainHeader
  },
  data() {
    return {
      userIcon: ""
    };
  },
  created() {
    if (getCookie("token")) {
      this.$store.commit("changeLoginStatus", true);
      this.userIcon = localStorage["userIcon"];
      console.log(this.$store.state.isLogin);
    }
  }
};
</script>

<style>
/*QQ的初始化CSS代码*/
body,
ol,
ul,
h1,
h2,
h3,
h4,
h5,
h6,
p,
th,
td,
dl,
dd,
form,
fieldset,
legend,
input,
textarea,
select {
  margin: 0;
  padding: 0;
}

/* TODO 移动端字体需要调整*/
body {
  font-size: 12px;
  font-family: Lato, Helvetica Neue, Helvetica, sans-serif;
  background: #fff;
  -webkit-text-size-adjust: 100%;
}

a {
  color: #2d374b;
  text-decoration: none;
}

a:hover {
  color: #cd0200;
  text-decoration: underline;
}

em {
  font-style: normal;
}

li {
  list-style: none;
}

img {
  border: 0;
  vertical-align: middle;
}

table {
  border-collapse: collapse;
  border-spacing: 0;
}

p {
  word-wrap: break-word;
}

#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#router-view {
  /* 通过设置router-view的min-height，使footer位置的自动适配,主内容不足一屏时显示在最底部，超出一屏时跟随主内容显示 */
  min-height: calc(100vh - 18vmin);
}
</style>
