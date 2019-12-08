<template>
  <el-row type="flex" justify="center" style="padding:50px;">
    <el-col :span="15">
      <el-collapse v-model="activeNames">
        <el-collapse-item
          v-for="picture in data"
          :key="picture.date"
          :title="picture.date.split(' ')[0]"
          :name="picture.date"
        >
          <el-image
            :src="picture.url"
            :preview-src-list="srcList"
            style="width:90%;"
            @click="preview(picture.url)"
          ></el-image>
        </el-collapse-item>
      </el-collapse>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: "weeklyDiary",
  data() {
    return {
      activeNames: "1",
      data: [],
      srcList: []
    };
  },
  created() {
    this.getData();
  },
  methods: {
    getData() {
      let _this = this;
      _this.$http
        .get("/picture/chronology/list")
        .then(function(response) {
          if (response.data.code === 200) {
            _this.data = response.data.data.pictures;
            _this.activeNames = response.data.data.pictures[0].date;
            // response.data.data.pictures.forEach(element => {
            //   _this.data.push({
            //     date: element.date,
            //     url:
            //       "http://www.sharingideas.cn:12321/pictures/b657b89f9cd5c2e4f1137eae76d2037b/dac1b4468be06242854931a0fbe1885d/f7c750a7a4784546a17c020e5ab7168d.jpg"
            //   });
            // });
          } else {
            this.$message({
              showClose: true,
              message: response.data.message,
              type: "error"
            });
          }
        })
        .catch(function(err) {
          console.log(err);
        });
    },
    preview(url) {
      this.srcList = [url];
    }
  }
};
</script>