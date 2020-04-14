<template>
  <el-col :span="14">
    <el-table :data="tableData" max-height="500">
      <el-table-column label="文章上传记录" align="center">
        <!-- aid '专栏文章id', article_title 专栏文章标题 article_url '专栏文章URL', article_pic_url '专栏文章展示图片URL', special_column_id '对应专栏类别的外键id', create_time '创建时间', update_time '更新时间', is_delete '是否删除', -->
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="table-expand">
              <el-form-item label="文章id">
                <span>{{ props.row.aid }}</span>
              </el-form-item>
              <el-form-item label="文章标题">
                <span>{{ props.row.articleTitle }}</span>
              </el-form-item>
              <el-form-item label="所属板块">
                <span>{{ props.row.specialColumnName }}</span>
              </el-form-item>
              <el-form-item label="文章URL">
                <a target="_blank" :href="props.row.articleUrl">点此跳转</a>
              </el-form-item>
              <!-- <el-form-item label="文章描述">
                <span>{{ props.row.id }}</span>
              </el-form-item>-->
              <el-form-item label="上传时间">
                <span>{{ props.row.createTime }}</span>
              </el-form-item>
              <el-form-item label="更新时间">
                <span>{{ props.row.updateTime }}</span>
              </el-form-item>
              <el-form-item label="图片展示">
                <el-image
                  style="width: 100px; height: 100px"
                  :src="props.row.articlePicUrl"
                  :preview-src-list="srcList"
                ></el-image>
              </el-form-item>
              <el-form-item label="状态">
                <span>{{ props.row.statue }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column prop="aid" label="文章id" align="center" width="80"></el-table-column>
        <el-table-column prop="articleTitle" label="文章标题" align="center" width="250"></el-table-column>
        <el-table-column
          prop="specialColumnName"
          label="所属板块"
          align="center"
          :filters="categoryFilArr"
          :filter-method="filterCategory"
          filter-placement="bottom-end"
        ></el-table-column>
        <!-- <el-table-column prop="url" label="上传时间" align="center"></el-table-column>
        <el-table-column prop="url" label="更新时间" align="center"></el-table-column>-->
        <el-table-column prop="updateTime" label="最后更改时间" align="center"></el-table-column>
        <el-table-column
          prop="status"
          label="状态"
          align="center"
          :filters="[{ text: '已删除', value: '已删除' }, { text: '正常', value: '正常' }]"
          :filter-method="filterStatue"
          filter-placement="bottom-end"
        ></el-table-column>
      </el-table-column>
    </el-table>
  </el-col>
</template>

<style>
.table-expand {
  font-size: 0;
}
.table-expand label {
  width: 90px;
  color: #99a9bf;
}
.table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>

<script>
export default {
  props: {
    tableData: Array,
    categoryData: Array
  },
  data() {
    return {
      categoryFilArr: []
    };
  },
  created() {
    this.categoryFilter();
  },
  methods: {
    categoryFilter: function() {
      let _this = this;
      let categoryData = _this.categoryData;
      for (let i = 0; i < categoryData.length; i++) {
        if (categoryData[i].isDelete === 0) {
          let filterObj = {};
          filterObj.text = categoryData[i].specialcName;
          filterObj.value = categoryData[i].specialcName;
          // console.log(filterObj)
          _this.categoryFilArr.push(filterObj);
        }
      }
    },
    filterCategory: function(value, row) {
      //   console.log(row.specialColumnName)
      return row.specialColumnName === value;
    },
    filterStatue: function(value, row) {
      return row.status === value;
    }
  }
};
</script>