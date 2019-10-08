<!--知识见解详情-段落组件-->
<!--TODO 收藏和笔记功能暂时注释掉了，因为没有想好如何做移动端适配-->
<template>
  <div class="section">
    <div class="section-name" v-html="sectionView.sectionNameHtml"></div>
    <div v-for="knowledge in sectionView.knowledgeViews" :key="knowledge.kid">
      <div class="section-knowledge-name" v-html="knowledge.knowledgeNameHtml"></div>
      <div v-for="paragraph in knowledge.paragraphs" :key="paragraph.pid">
        <div
          class="section-knowledge-paragraph"
          @mouseenter="mouseenter(paragraph.pid)"
          @mouseleave="currentPid = -1"
        >
          <div
            class="section-knowledge-paragraph-content"
            :style="{'padding':($store.state.isMobile ? '7vmin 0':'3vmin 0')}"
            v-html="paragraph.paragraphContentHtml"
          ></div>
          <div class="button-group" v-if="currentPid === paragraph.pid">
            <el-button
              type="primary"
              size="mini"
              :class="['button',$store.state.isMobile?'button-mobile':'']"
              @click="handleCollected($event, paragraph.paragraphSeq)"
            >
              <span :ref="'collected'+paragraph.pid">收藏</span>
            </el-button>
            <el-button
              type="primary"
              size="mini"
              :class="['button',$store.state.isMobile?'button-mobile':'']"
              @click="handleNote(paragraph.paragraphSeq)"
            >笔记</el-button>
          </div>
        </div>
        <wang-editor
          :wangID="'wang'+paragraph.paragraphSeq"
          :content="note[paragraph.paragraphSeq]"
          @editorOnBlur="editorOnBlur"
          @editorOnFocus="editorOnFocus"
          v-if="noteId.includes(paragraph.paragraphSeq) || wangEditorIDs.includes('wang'+paragraph.paragraphSeq)"
        ></wang-editor>
      </div>
    </div>
  </div>
</template>

<script>
import wangEditor from "./WangEditor";

export default {
  name: "KnowledgeSection",
  props: ["sectionView", "noteViews", "colParas"],
  components: { wangEditor },
  data: function() {
    return {
      currentPid: -1,
      wangEditorIDs: [],
      noteId: [],
      note: {},
      sectionId: this.$store.state.sectionId
    };
  },
  created() {
    this.$emit("getColParas", this.$store.state.sectionId);
    this.$emit("getNoteView", this.$store.state.sectionId);
  },
  updated() {
    this.sectionId = this.$store.state.sectionId;
  },
  watch: {
    noteViews() {
      for (let i of this.noteViews) {
        this.noteId.push(i.colParagraphView.paragraphSeq);
        this.note[i.colParagraphView.paragraphSeq] = i.noteContent;
      }
    },
    sectionId() {
      this.$emit("getColParas", this.$store.state.sectionId);
      this.$emit("getNoteView", this.$store.state.sectionId);
    }
  },
  methods: {
    mouseenter(pid) {
      this.currentPid = pid;
      let id = "collected" + pid;
      let colParasString = "";
      if (this.colParas) {
        for (let i of this.colParas) {
          colParasString += i.colpPara + "&";
        }

        if (colParasString.includes(pid)) {
          this.$nextTick(() => {
            this.$refs[id][0].innerHTML = "取消";
          });
        } else {
          this.$nextTick(() => {
            this.$refs[id][0].innerHTML = "收藏";
          });
        }
      }
    },
    //点击收藏/取消按钮
    handleCollected(e, paragraphSeq) {
      if (e.target.innerHTML.includes("收藏")) {
        this.addColParagraph(paragraphSeq);
        e.target.innerText = "取消";
      } else if (e.target.innerHTML.includes("取消")) {
        this.removeColParagraph(paragraphSeq);
        e.target.innerText = "收藏";
      }
    },
    //点击笔记按钮
    handleNote(paragraphSeq) {
      let id = "wang" + paragraphSeq;
      if (this.wangEditorIDs.includes(id)) {
        this.wangEditorIDs.splice(this.wangEditorIDs.indexOf(id), 1);
      } else {
        this.wangEditorIDs.push(id);
      }
    },
    //wangEditie聚焦
    editorOnFocus(wangID) {
      document.getElementById(wangID).style.background = "#e1f5fe";
      document.getElementById(wangID).children[0].style.display = "flex";
    },
    //wangEditor失焦,修改笔记
    editorOnBlur(wangID, html) {
      console.log("onblur");
      document.getElementById(wangID).style.background = "#eee";
      document.getElementById(wangID).children[0].style.display = "none";
      let paragraphSeq = wangID.slice(4);
      //如果内容为空，隐藏编辑器
      if (html === "<p><br></p>") {
        this.wangEditorIDs.splice(this.wangEditorIDs.indexOf(wangID), 1);
        document.getElementById(wangID).style.display = "none";
      }
      this.$http
        .post(
          "paragraph/editNote",
          {
            uid: 0,
            paragraphSeq: paragraphSeq,
            noteContent: html
          },
          {
            transformRequest: [
              function(data) {
                let ret = "";
                for (let it in data) {
                  ret +=
                    encodeURIComponent(it) +
                    "=" +
                    encodeURIComponent(data[it]) +
                    "&";
                }
                return ret;
              }
            ],
            withCredentials: true
          }
        )
        .then(function(res) {
          console.log(res.data);
        })
        .catch(function(err) {
          console.log(err);
        });
    },
    //收藏段落
    addColParagraph(paragraphSeq) {
      let _this = this;
      this.$http
        .post(
          "paragraph/addColParagraph",
          {
            uid: 0,
            paragraphSeq: paragraphSeq
          },
          {
            transformRequest: [
              function(data) {
                let ret = "";
                for (let it in data) {
                  ret +=
                    encodeURIComponent(it) +
                    "=" +
                    encodeURIComponent(data[it]) +
                    "&";
                }
                return ret;
              }
            ],
            withCredentials: true
          }
        )
        .then(function(res) {
          console.log(res.data);
          if (res.data.code === 200) {
            _this.$message({
              showClose: true,
              message: "收藏成功",
              type: "success",
              duration: 1000 * 3
            });
          } else if (res.data.code === 500) {
            _this.$message({
              showClose: true,
              message: res.data.message,
              type: "warning",
              duration: 1000 * 3
            });
          }
        })
        .catch(function(err) {
          console.log(err);
        });
    },
    //取消收藏
    removeColParagraph(paragraphSeq) {
      let _this = this;
      this.$http
        .post(
          "paragraph/removeColParagraph",
          {
            uid: 0,
            paragraphSeq: paragraphSeq
          },
          {
            transformRequest: [
              function(data) {
                let ret = "";
                for (let it in data) {
                  ret +=
                    encodeURIComponent(it) +
                    "=" +
                    encodeURIComponent(data[it]) +
                    "&";
                }
                return ret;
              }
            ],
            withCredentials: true
          }
        )
        .then(function(res) {
          console.log(res.data);
          if (res.data.code === 200) {
            _this.$message({
              showClose: true,
              message: "取消收藏成功",
              type: "success",
              duration: 1000 * 3
            });
          } else if (res.data.code === 500) {
            _this.$message({
              showClose: true,
              message: res.data.message,
              type: "warning",
              duration: 1000 * 3
            });
          }
        })
        .catch(function(err) {
          console.log(err);
        });
    }
  }
};
</script>

<style lang="less">
.section {
  text-align: left;
  margin: 1vmin 5vmin 0 5vmin;
  padding: 3vmin;
  border: 1px solid #a7d7f9;
}

.section-name {
  padding: 2vmin 0;
  font-size: 3.5vmin;
}

.section-knowledge-name {
  padding: 2vmin 0;
  font-size: 3vmin;
}

.section-knowledge-paragraph {
  display: flex;
  flex-direction: row;
}

.section-knowledge-paragraph:hover {
  background-color: #e3f3f7;
}

.section-knowledge-paragraph-content {
  padding: 3vmin 0;
  font-size: 2vmin;

  pre {
    overflow: scroll;
    width: 50vw;
  }

  img {
    width: 50vw;
    margin: 1vw;
  }
}

.button-group {
  display: inherit;
  position: absolute;
  right: 8vmin;
  // float: right;
  // width: 15vmin;
  // position: absolute;
  // left: 90vmin;
  // display: flex;
  // flex-direction: row;
}

.button {
  // background-color: #a3f3f7;
  // width: 5vmin;
  // margin: 0 1vmin;
  // text-align: center;
  // height: 2.5vmin;
  padding: 6px 15px;
  cursor: pointer;
}

.button-mobile {
  padding: 4px 10px;
  // height: 5vmin;
  // width: 10vmin;
}

//wangEditor样式
.w-e-text-container {
  height: auto !important;
  border: none !important;
}

.w-e-text-container div::before {
  color: lightgrey;
  content: attr(data-placeholder);
  font-size: 2vmin;
}

.w-e-toolbar {
  border: none !important;
  background: #e1f5fe !important;
  flex-wrap: wrap !important;
}
</style>
