<!--知识见解详情-->
<template>
  <div class="knowledge-detail-body">
    <el-main>
      <el-row type="flex" class="row-bg" justify="center">
        <!--手机端布局，使用drawer来进行章节选择-->
        <div v-if="$store.state.isMobile">
          <div>
            <div class="show-menu" @click="showDrawerClick">
              章
              <br />节
              <br />选
              <br />择
            </div>
            <el-drawer
              size="80%"
              title="章节选择"
              :visible.sync="drawerShow"
              :direction="drawerDirection"
            >
              <KnowledgeSectionSelect
                :courseView="courseView"
                @getSectionView="getSectionView"
                @getCsdn="getCsdn"
              />
            </el-drawer>
          </div>
          <el-col :span="24">
            <knowledge-section-navigator
              :side="side"
              @getSectionView="getSectionView"
              @getCsdn="getCsdn"
            />
            <knowledge-section
              :sectionView="sectionView"
              :noteViews="noteViews"
              :colParas="colParas"
              @getNoteView="getNoteView"
              @getColParas="getColParas"
            ></knowledge-section>
            <knowledge-section-navigator
              :side="side"
              @getSectionView="getSectionView"
              @getCsdn="getCsdn"
            />
            <knowledge-section-blog :blog="csdn" />
          </el-col>
        </div>
        <!--电脑端布局-->
        <div v-if="!$store.state.isMobile" class="computer-knowledge">
          <div class="computer-select">
            <KnowledgeSectionSelect
              :courseView="courseView"
              @getSectionView="getSectionView"
              @getCsdn="getCsdn"
            />
          </div>
          <div class="computer-content">
            <knowledge-section-navigator
              :side="side"
              @getSectionView="getSectionView"
              @getCsdn="getCsdn"
            />
            <knowledge-section
              :sectionView="sectionView"
              :noteViews="noteViews"
              :colParas="colParas"
              @getNoteView="getNoteView"
              @getColParas="getColParas"
            ></knowledge-section>
            <knowledge-section-navigator
              :side="side"
              @getSectionView="getSectionView"
              @getCsdn="getCsdn"
            />
            <knowledge-section-blog :blog="csdn" />
          </div>
        </div>
      </el-row>
    </el-main>
  </div>
</template>

<script>
import KnowledgeSection from "./KnowledgeSection";
import KnowledgeSectionBlog from "./KnowledgeSectionBlog";
import KnowledgeSectionNavigator from "./KnowledgeSectionNavigator";
import KnowledgeSectionSelect from "./KnowledgeSectionSelect";
import showdown from "showdown";

export default {
  name: "KnowledgeDetail",
  components: {
    KnowledgeSectionSelect,
    KnowledgeSectionNavigator,
    KnowledgeSectionBlog,
    KnowledgeSection
  },
  data: function() {
    return {
      drawerDirection: "ltr",
      drawerShow: false,
      csdn: {},
      courseView: {},
      side: {
        preSection: "",
        nextSection: ""
      },
      sectionView: {},
      noteViews: [],
      colParas: []
    };
  },
  created: function() {
    this.getCourseView(this.$store.state.courseId);
  },
  methods: {
    showDrawerClick: function() {
      this.drawerShow = true;
    },
    // 获取当前章节所在的位置
    // TODO i,j这样的命名要避免，由于目前只是重构，所以沿用了原先的命名
    getSideSectionIndex: function(sid) {
      let i, j;
      let sections = this.courseView.sections;
      for (i = 0; i < sections.length; i++) {
        for (j = 0; j < sections[i].sub.length; j++) {
          if (sections[i].sub[j].sid === sid) {
            return { i: i, j: j };
          }
        }
      }
      return { i: i, j: j };
    },
    //获得当前章节的上下节点
    getSideSection: function(sid) {
      let side = {};
      let sections = this.courseView.sections;
      let sideIndex = this.getSideSectionIndex(sid);
      let i = sideIndex.i;
      let j = sideIndex.j;

      //找前一个
      if (j !== 0) {
        side.preSection = sections[i].sub[j - 1];
      } else {
        if (i !== 0) {
          side.preSection = sections[i - 1].sub[sections[i - 1].sub.length - 1];
        } else {
          side.preSection = "";
        }
      }
      //找后一个
      if (j !== sections[i].sub.length - 1) {
        side.nextSection = sections[i].sub[j + 1];
      } else {
        if (i !== sections.length - 1) {
          side.nextSection = sections[i + 1].sub[0];
        } else {
          side.nextSection = "";
        }
      }
      this.side = side;
    },
    //获得课程目录的请求
    getCourseView: function(cid) {
      const _this = this;

      this.$http
        .get("course/getCourseDetails", {
          params: {
            cid: cid
          }
        })
        .then(function(response) {
          console.log("get course view", response);
          if (response.data.code === 200) {
            _this.courseView = response.data.data.courseView;

            // 转换一下section的结构
            //TODO 后台应该直接从数据库中存储的时候就安排好结构
            let sections = [];
            let tempSections = response.data.data.courseView.sections;
            let index = 0;
            for (let i = 0; i < tempSections.length; i++) {
              let name = tempSections[i].sectionName;
              if (name.substring(0, name.indexOf(" ")) === "#") {
                if (i === 0) {
                  index = 0;
                } else {
                  index++;
                }
                sections[index] = {};
                sections[index].title = name.substring(
                  name.indexOf(" "),
                  name.indexOf("章") + 1
                );
                sections[index].index = index;
                sections[index].sub = [];
                sections[index].sub[0] = tempSections[i];
              } else {
                sections[index].sub[sections[index].sub.length] =
                  tempSections[i];
              }
            }
            _this.courseView.sections = sections;
            if (sections.length > 0) {
              let sid = tempSections[0].sid;
              _this.getSectionView(sid);
              _this.getCsdn(sid);
              _this.$store.commit("setSectionId", sid);
            }
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
    //获得小节内容的请求
    getSectionView: function(sid) {
      const _this = this;

      this.$http
        .get("section/getSection", {
          params: {
            sid: sid
          }
        })
        .then(function(response) {
          console.log("get section view", response);
          if (response.data.code === 200) {
            _this.sectionView = response.data.data.sectionView;
            _this.getSideSection(sid);

            // 将markdown转换为html
            let converter = new showdown.Converter();
            _this.sectionView.sectionNameHtml = converter.makeHtml(
              _this.sectionView.sectionName.toString()
            );
            for (const knowledge of _this.sectionView.knowledgeViews) {
              knowledge.knowledgeNameHtml = converter.makeHtml(
                knowledge.knowledgeName.toString()
              );
              for (const paragraph of knowledge.paragraphs) {
                let paragraphContentHtml = converter.makeHtml(
                  paragraph.paragraphContent.toString()
                );
                if (paragraphContentHtml.indexOf("<pre") !== -1) {
                  paragraphContentHtml = paragraphContentHtml.replace(
                    "<pre",
                    '<pre class="code-content"'
                  );
                  console.log("===", paragraphContentHtml);
                } else if (paragraphContentHtml.indexOf("<img") !== -1) {
                  paragraphContentHtml = paragraphContentHtml.replace(
                    "<img",
                    '<img class="img-content"'
                  );
                  console.log("====", paragraphContentHtml);
                }
                paragraph.paragraphContentHtml = paragraphContentHtml;
              }
            }
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
    // 获取csdn推荐的请求
    getCsdn: function(sid) {
      const _this = this;

      this.$http
        .get("section/getCSDN", {
          params: {
            sid: sid
          }
        })
        .then(function(response) {
          console.log("get csdn", response);
          if (response.data.code === 200) {
            _this.csdn = response.data.data.csdn;
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
    // 获得用户笔记的请求
    getNoteView: function(sid) {
      let _this = this;
      this.$http
        .post(
          "paragraph/getNoteBySid",
          {
            uid: 0,
            sid: sid
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
          console.log("noteview-", res.data.data.noteViews, sid);
          _this.noteViews = res.data.data.noteViews;
        })
        .catch(function(err) {
          console.log(err);
        });
    },
    // 获得用户在某一节下面的所有收藏段落
    getColParas: function(sid) {
      let _this = this;
      this.$http
        .post(
          "paragraph/getColParagraphBySid",
          {
            uid: 0,
            sid: sid
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
          console.log("colParas-", res.data.data.colParagraphList, sid);
          _this.colParas = res.data.data.colParagraphList;
        })
        .catch(function(err) {
          console.log(err);
        });
    }
  }
};
</script>

<style scoped>
.knowledge-detail-body {
  min-height: calc(100vh - 69vmin);
}

.show-menu {
  color: #409eff;
  border: #a7d7f9 1px solid;
  padding: 3vmin 0;
  position: fixed;
  left: 0;
  top: 20vmin;
  width: 6vmin;
}

.computer-knowledge {
  display: flex;
  flex-direction: row;
}

.computer-select {
  width: 25%;
}

.computer-content {
  width: 75%;
}
</style>
