(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["resources"],{1051:function(e,t,o){"use strict";var i=function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",[e.status?o("el-tag",{staticClass:"file-tag"},[e._v("\n    "+e._s(e.status)+"\n  ")]):e._e(),e._l(e.tags,function(t,i,s){return o("div",{key:s},["fid"!==i&&null!==t?o("div",[o("el-tag",{staticClass:"file-tag",attrs:{type:"danger"}},[e._v("\n        "+e._s(t)+"\n      ")])],1):e._e()])})],2)},s=[],l={name:"FileTag",props:["tags","status"]},n=l,r=(o("331c"),o("6691")),c=Object(r["a"])(n,i,s,!1,null,"18222020",null);t["a"]=c.exports},"331c":function(e,t,o){"use strict";var i=o("b22d"),s=o.n(i);s.a},"6f6a":function(e,t,o){},"945a":function(e,t,o){"use strict";var i=o("b09d"),s=o.n(i);s.a},b09d:function(e,t,o){},b22d:function(e,t,o){},f2cc:function(e,t,o){"use strict";var i=o("6f6a"),s=o.n(i);s.a},f9dc:function(e,t,o){"use strict";o.r(t);var i=function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("el-main",{staticClass:"resources"},[o("el-col",{attrs:{span:20}},[o("el-card",{staticClass:"resources-card",attrs:{shadow:"always"}},[o("h1",[e._v("Hello , world")]),o("p",[e._v("每一个不曾起舞的日子都是对生命的浪费")]),o("el-button",{attrs:{type:"primary"},on:{click:e.gotoUpload}},[e._v("上传"),o("i",{staticClass:"el-icon-upload el-icon--right"})])],1)],1),o("el-col",{attrs:{span:20},nativeOn:{scroll:function(t){return e.lazyLoading(t)}}},[o("el-row",[o("div",{staticClass:"resources-select"},[o("el-input",{staticClass:"resources-search",attrs:{placeholder:"搜索"},model:{value:e.fileListForm.keyWord,callback:function(t){e.$set(e.fileListForm,"keyWord",t)},expression:"fileListForm.keyWord"}},[o("el-button",{attrs:{slot:"append",icon:"el-icon-search"},on:{click:e.doSearch},slot:"append"})],1),e.fileListForm.order&&e.$store.state.isMobile?o("div",[o("el-button",{attrs:{icon:"el-icon-sort-down"},on:{click:e.changeOrder}})],1):e._e(),!e.fileListForm.order&&e.$store.state.isMobile?o("div",[o("el-button",{attrs:{icon:"el-icon-sort-up"},on:{click:e.changeOrder}})],1):e._e(),e.fileListForm.order&&!e.$store.state.isMobile?o("div",[o("el-button",{attrs:{icon:"el-icon-sort-down"},on:{click:e.changeOrder}},[e._v("按时间降序")])],1):e._e(),e.fileListForm.order||e.$store.state.isMobile?e._e():o("div",[o("el-button",{attrs:{icon:"el-icon-sort-up"},on:{click:e.changeOrder}},[e._v("按时间升序")])],1)],1)])],1),o("el-col",{staticClass:"file-list-detail",attrs:{span:20}},e._l(e.myFiles,function(e,t){return o("resources-file",{key:t,attrs:{fileItem:e}})}),1)],1)},s=[],l=function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("el-card",{staticClass:"resources-file-item",attrs:{shadow:"hover"}},[o("el-row",[o("el-col",[o("el-row",[e.$store.state.isMobile?o("div",[o("el-col",{attrs:{span:16}},[e._v(e._s(e.fileItem.fileName))]),o("el-col",{attrs:{span:8}},[e._v("下载量:"+e._s(e.fileItem.fileDownloadCount))])],1):o("div",[o("el-col",{attrs:{span:8}},[e._v(e._s(e.fileItem.fileName))]),o("el-col",{attrs:{span:8}},[e._v(e._s(e.fileItem.upper)+"上传于"+e._s(e.fileItem.fileUploadTime))]),o("el-col",{attrs:{span:8}},[e._v("下载量:"+e._s(e.fileItem.fileDownloadCount))])],1)])],1),o("el-col",[o("el-row",{staticClass:"resources-file-footer"},[e.$store.state.isMobile?o("div",[o("el-col",{staticClass:"resources-file-footer-button",attrs:{span:24}},[o("el-button",{attrs:{type:"primary",icon:"el-icon-document",circle:""},on:{click:function(t){return e.handlePreview(e.fileItem.fid)}}}),o("el-button",{attrs:{type:"success",icon:"el-icon-download",circle:""},on:{click:function(t){return e.handleDownload(e.fileItem.fid)}}})],1)],1):o("div",[o("el-col",{attrs:{span:16}},[o("file-tag",{attrs:{tags:e.fileItem.fileKeys}})],1),o("el-col",{staticClass:"resources-file-footer-button",attrs:{span:8}},[o("el-button",{attrs:{type:"primary",icon:"el-icon-document",circle:""},on:{click:function(t){return e.handlePreview(e.fileItem.fid)}}}),o("el-button",{attrs:{type:"success",icon:"el-icon-download",circle:""},on:{click:function(t){return e.handleDownload(e.fileItem.fid)}}})],1)],1)])],1)],1)],1)},n=[],r=o("1051"),c=o("83af"),a={name:"ResourcesFile",components:{FileTag:r["a"]},props:["fileItem"],methods:{handlePreview:function(e){console.log("预览文件"+e),this.$store.commit("setFid",e);var t=Object(c["d"])(window.location.href),o=t.protocol+"//"+t.host;window.open(o+"/preview.html?fid="+e)},handleDownload:function(e){if(Object(c["b"])("token")){console.log("下载文件");var t=Object(c["d"])(window.location.href),o=t.protocol+"//"+t.host;window.open(o+"/file/download?fid="+e+"&uid=0")}else Object(c["f"])("/user/login",this)}}},u=a,d=(o("945a"),o("6691")),f=Object(d["a"])(u,l,n,!1,null,"3530c2b4",null),m=f.exports,p={name:"Resources",components:{ResourcesFile:m},data:function(){return{fileListForm:{keyWord:"",order:!0,fileCourse:"",page:1},myFiles:[]}},methods:{gotoUpload:function(){Object(c["f"])("/resources/upload",this)},changeOrder:function(){this.fileListForm.order=!this.fileListForm.order,this.myFiles.reverse()},doSearch:function(){this.myFiles=[],this.fileListForm.page=1,this.getFileList(this.fileListForm.page)},getFileList:function(e){var t=this;console.log("123"),this.$http.get("file/getFileList",{params:{keyWord:this.fileListForm.keyWord,fileCourse:this.fileListForm.fileCourse,page:e,order:this.fileListForm.order}}).then(function(e){t.myFiles=t.myFiles.concat(e.data.data.files)}).catch(function(e){console.log(e)})},lazyLoading:function(){var e=document.documentElement.scrollTop||document.body.scrollTop,t=document.documentElement.clientHeight,o=document.documentElement.scrollHeight;e+t>=o&&(this.fileListForm.page++,console.log(this.fileListForm.page),this.getFileList(this.fileListForm.page))}},mounted:function(){var e=this;this.getFileList(this.fileListForm.page),document.onscroll=function(){"/resources"===e.$route.path&&e.lazyLoading()}}},h=p,v=(o("f2cc"),Object(d["a"])(h,i,s,!1,null,"16057330",null));t["default"]=v.exports}}]);
//# sourceMappingURL=resources.f2cf6de6.js.map