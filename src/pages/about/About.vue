<!--关于智库页面-->
<template>
  <div>
    <div class="about-body">
      <about-swiper></about-swiper>
      <about-content></about-content>
    </div>
    <about-footer></about-footer>
  </div>
</template>

<script>
  import AboutFooter from "./AboutFooter";
  import AboutContent from "./AboutContent";
  import AboutSwiper from "./AboutSwiper";
  
  export default {
    name: "About",
    components: {AboutSwiper, AboutContent, AboutFooter},
    data() {
      return {
        courses: [],
        majors: []
      }
    }
  }
</script>

<style scoped>
  .about-body {
    min-height: calc(100vh - 69vmin);
  }
</style>
