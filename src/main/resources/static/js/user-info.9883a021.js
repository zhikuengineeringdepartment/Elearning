(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["user-info"],{"323e":function(t,e,s){"use strict";s.r(e);var a=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("el-form",{ref:"form",attrs:{model:t.baseInfo}},[s("div",{staticClass:"user-image"},[s("img",{attrs:{id:"user_avatar",src:t.baseInfo.userAvatar}}),s("el-button",{staticClass:"user-image-button",attrs:{type:"primary",disabled:""}},[t._v("更换头像")]),s("el-button",{staticClass:"user-image-button",attrs:{type:"primary"},on:{click:t.signOut}},[t._v("退出登陆")]),t.isAdmin("tokena")?s("el-button",{staticClass:"user-image-button",attrs:{type:"primary"},on:{click:t.jumpToAdmin}},[t._v("管理")]):t._e()],1),s("div",{staticClass:"user-info-form-item"},[s("el-input",{staticClass:"user-info-form-input",model:{value:t.baseInfo.userNick,callback:function(e){t.$set(t.baseInfo,"userNick",e)},expression:"baseInfo.userNick"}}),s("el-button",{staticClass:"user-info-form-button",attrs:{type:"primary",disabled:""}},[t._v("修改昵称")])],1),s("div",{staticClass:"user-info-form-item"},[s("el-input",{staticClass:"user-info-form-input",attrs:{disabled:""},model:{value:t.baseInfo.userEmail,callback:function(e){t.$set(t.baseInfo,"userEmail",e)},expression:"baseInfo.userEmail"}}),s("el-button",{staticClass:"user-info-form-button",attrs:{type:"primary",disabled:""}},[t._v("修改邮箱")])],1),s("div",{staticClass:"user-info-form-item"},[s("el-input",{staticClass:"user-info-form-input",model:{value:t.baseInfo.userPhone,callback:function(e){t.$set(t.baseInfo,"userPhone",e)},expression:"baseInfo.userPhone"}}),s("el-button",{staticClass:"user-info-form-button",attrs:{type:"primary",disabled:""}},[t._v("修改电话")])],1)])},n=[],i=s("83af"),o={name:"UserInfo",data:function(){return{baseInfo:{}}},mounted:function(){this.getUserInfo()},methods:{getUserInfo:function(){var t=this;t.$http.get("/user/getBaseInfo",{params:{uid:0}}).then(function(e){200===e.data.code?(console.log(e.data),t.baseInfo=e.data.data.baseInfo):t.$message({showClose:!0,message:e.data.message,type:"error"})}).catch(function(t){console.log(t)})},signOut:function(){Object(i["a"])("token"),Object(i["a"])("tokena"),this.$emit("singOut"),Object(i["f"])("/user/login",this)},jumpToAdmin:function(){Object(i["f"])("/admin/upload/image",this)},isAdmin:function(t){return Object(i["b"])(t)}}},r=o,u=(s("61bb"),s("6691")),c=Object(u["a"])(r,a,n,!1,null,"0d0bad02",null);e["default"]=c.exports},"61bb":function(t,e,s){"use strict";var a=s("cb6c"),n=s.n(a);n.a},cb6c:function(t,e,s){}}]);
//# sourceMappingURL=user-info.9883a021.js.map