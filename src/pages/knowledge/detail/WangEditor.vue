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
    editor.customConfig.uploadImgShowBase64 = true; // 使用 base64 保存图片
    // editor.customConfig.hasOnBlur = 0;
    editor.customConfig.zIndex = 100;
    editor.customConfig.onfocus = function() {
      _this.$emit("editorOnFocus", _this.wangID);
    };
    editor.customConfig.onblur = function(html) {
      // editor.customConfig.hasOnBlur = 1;
      _this.$emit("editorOnBlur", _this.wangID, html);
    };
    editor.create();
    editor.txt.html(_this.content);
    document.getElementById(
      _this.wangID
    ).children[1].children[0].dataset.placeholder = "My Note:";
    document.activeElement.blur();
  }
};
</script>

<style scoped>
.editor-box {
  background: #e1f5fe;
  border: none;
}
</style>