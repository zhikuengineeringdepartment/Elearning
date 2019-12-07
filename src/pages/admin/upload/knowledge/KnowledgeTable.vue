<!--已上传知识见解表格-->
<template>
  <el-table ref="filterTable" :data="tableData" stripe style="width: 100%" height="550">
    <el-table-column label="已上传知识见解" align="center">
      <el-table-column
        prop="course"
        label="课程"
        align="center"
        width="250"
        :filters="courses"
        :filter-method="filterHandler"
      ></el-table-column>
      <el-table-column prop="chapter" label="已上传章" align="center" width="150">
        <template slot-scope="scope">
          <span>第{{scope.row.chapter}}章</span>
        </template>
      </el-table-column>
      <el-table-column prop="section" label="已上传节" align="center">
        <template slot-scope="scope">
          <el-tag
            v-for="section in scope.row.sections"
            :key="section"
            disable-transitions
            style="margin:5px 5px;"
          >
            <span v-if="section == 0">概述</span>
            <span v-else>{{section}}</span>
          </el-tag>
        </template>
      </el-table-column>
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  props: {
    tableData: Array,
    courses: Array
  },
  data() {
    return {};
  },
  methods: {
    filterHandler(value, row, column) {
      const property = column["property"];
      return row[property] === value;
    }
  }
};
</script>