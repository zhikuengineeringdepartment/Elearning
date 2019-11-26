<template>
  <el-table ref="filterTable" :data="tableData" stripe style="width: 100%">
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
      <el-table-column prop="url" label="URL" align="center"></el-table-column>
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  props: {
    courses: Array,
    chapters: Array,
    sections: Array
  },
  data() {
    return {
      tableData: [
        {
          name: 1,
          course: 1,
          chapter: 1,
          section: 1,
          url: 1
        }
      ]
    };
  },
  methods: {
    filterHandler(value, row, column) {
      const property = column["property"];
      return row[property] === value;
    }
  }
};
</script>