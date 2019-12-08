<!--知识见解图片上传记录-->
<template>
  <el-table
    ref="filterTable"
    :data="tableData"
    stripe
    max-height="550"
    style="width: 100%"
    v-loading="loading"
    element-loading-text="加载中"
    element-loading-spinner="el-icon-loading"
  >
    <el-table-column label="上传记录" align="center">
      <el-table-column prop="name" label="文件名" align="center"></el-table-column>
      <el-table-column
        prop="course"
        label="所属课程"
        align="center"
        :filters="courses"
        :filter-method="filterHandler"
      ></el-table-column>
      <el-table-column
        prop="chapter"
        label="所属章"
        align="center"
        :filters="chapters"
        :filter-method="filterHandler"
      ></el-table-column>
      <el-table-column
        prop="section"
        label="所属节"
        align="center"
        :filters="sections"
        :filter-method="filterHandler"
      ></el-table-column>
    </el-table-column>
    <el-table-column label="上传记录" align="center">
      <template slot="header">
        <el-button type="primary" size="mini" @click="reload">刷新</el-button>
      </template>
      <el-table-column prop="url" label="URL" align="center"></el-table-column>
      <el-table-column label="预览(点击预览大图)" align="center">
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
  props: {
    courses: Array,
    chapters: Array,
    sections: Array,
    tableData: Array,
    loading: Boolean
  },
  data() {
    return {
      srcList: []
    };
  },
  methods: {
    filterHandler(value, row, column) {
      const property = column["property"];
      return row[property] === value;
    },
    reload() {
      this.$emit("loadData");
    },
    preview(url) {
      this.srcList = [url];
    }
  }
};
</script>