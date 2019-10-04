<template>
  <div :id="wangID" class="editor-box"></div>
</template>

<script>
import E from "wangeditor";

export default {
  name: "WangEditor",
  props: {
    wangID: String,
    content: String
  },
  mounted() {
    let _this = this;
    var editor = new E("#" + _this.wangID);
    editor.customConfig.onfocus = function() {
      _this.$emit("editorOnFocus", _this.wangID);
    };
    editor.customConfig.onblur = function(html) {
      _this.$emit("editorOnBlur", _this.wangID, html);
    };
    editor.create();
    editor.txt.html(_this.content);
  }
};
</script>

<style scoped>
.editor-box {
  background: #e1f5fe;
  border: none;
}
</style>