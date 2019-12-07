<!--智库周记列表-->
<template>
  <el-table :data="tableData" stripe max-height="550" style="width: 100%">
    <el-table-column label="历史记录" align="center">
      <el-table-column prop="date" label="日期" align="center"></el-table-column>
      <el-table-column prop="url" label="内容(点击预览大图)" align="center">
        <template slot-scope="scope">
          <el-image
            :src="scope.row.url"
            :preview-src-list="srcList"
            @click="preview(scope.row.url)"
          ></el-image>
        </template>
      </el-table-column>
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  name: "imgTable",
  props: {
    tableData: Array
  },
  data() {
    return {
      srcList: []
    };
  },
  methods: {
    preview(url) {
      console.log(url);
      this.srcList = [url];
    }
  }
};
</script>