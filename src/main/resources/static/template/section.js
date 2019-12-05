/*
节展示组件
内容主要通过marked.js来渲染
 */
let sectionTemplate = `
<div id="sectionView_"></div>
`



var sectionModule = {
    data: function () {
        return {

        }
    },
    props:["sectionView","noteViews","colParas"],   //传入参数：节视图，笔记记录，收藏记录
    template: sectionTemplate,
    watch: {
        //监听sectionView、noteViews、colParas，改变是重新请求并渲染
        sectionView: function (newSection,oldSection) {
            var _this = this
            load_paragraph(_this.sectionView,"sectionView_",function(pid,noteContent){
                _this.handleNoteParagraph(pid,noteContent)
            },function(pid){
                _this.handleColParagraph(pid)
            },function (pid) {
                _this.handleCancelCol(pid)
            });
        },
        noteViews:function (newNotes,oldNotes) {
            var _this = this;
            load_array(_this.noteViews,function(pid,noteContent){
                _this.handleNoteParagraph(pid,noteContent)
            })
        },
        colParas:function (newcp,oldcp) {
            var _this = this;
            load_colArray(this.colParas);
        }
    },
    methods:{
        //收藏段落
        handleColParagraph(pid){
            var _this = this;
            axios.post('paragraph/addColParagraph',{
                uid:0,
                paragraphSeq:parseInt(pid)
            },{
                transformRequest: [
                    function(data) {
                        let ret = '';
                        for (let it in data) {
                            ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&';
                        }
                        return ret;
                    }
                ],
                withCredentials:true
            })
                .then(function(res){
                    console.log(res);
                })
                .catch(function(err){
                    console.log(err)
                });
        },
        //编辑笔记
        handleNoteParagraph(pid,noteContent){
            axios.post('paragraph/editNote',{
                uid:0,
                paragraphSeq:parseInt(pid),
                noteContent:noteContent
            },{
                transformRequest: [
                    function(data) {
                        let ret = '';
                        for (let it in data) {
                            ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&';
                        }
                        return ret;
                    }
                ],
                withCredentials:true
            })
                .then(function(res){
                    console.log(res);
                })
                .catch(function(err){
                    console.log(err);
                });
        },
        //取消收藏
        handleCancelCol(pid){
            axios.post('paragraph/removeColParagraph',{
                uid:0,
                paragraphSeq:parseInt(pid)
            },{
                transformRequest: [
                    function(data) {
                        let ret = '';
                        for (let it in data) {
                            ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&';
                        }
                        return ret;
                    }
                ],
                withCredentials:true
            })
                .then(function(res){
                    console.log(res);
                })
                .catch(function(err){
                    console.log(err);
                });
        }
    }
}

Vue.component('my_section',sectionModule)


