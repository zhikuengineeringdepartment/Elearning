<template>
  <div>
    <main-header @singOut="signOut" :userIcon="userIcon"></main-header>
    <div id="router-view">
      <router-view />
    </div>
    <main-footer></main-footer>
  </div>
</template>

<script>
import MainHeader from "../components/MainHeader";
import MainFooter from "../components/MainFooter";
import { getCookie } from "../tools.js";

export default {
  name: "index",
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
      this.userIcon = localStorage["userIcon"];
      // _this.$store.commit("setLoginStatus", true);
    }
  },
  methods: {
    signOut() {
      this.userIcon = "";
    }
  }
};
</script>

<style>
#router-view {
  /* 通过设置router-view的min-height，使footer位置的自动适配,主内容不足一屏时显示在最底部，超出一屏时跟随主内容显示 */
  min-height: calc(100vh - 18vmin);
}
</style>