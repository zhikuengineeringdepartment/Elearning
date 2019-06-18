let sectionTemplate = `
<div id="sectionView_"></div>
`



var sectionModule = {
    data: function () {
        return {

        }
    },
    props:["sectionView","noteViews","colParas"],
    template: sectionTemplate,
    watch: {
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
        handleColParagraph(pid){
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
                    console.log(err);
                });
        },
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


