let sectionTemplate = `
<div id="sectionView_"></div>
`



var sectionModule = {
    data: function () {
        return {

        }
    },
    props:["sectionView"],
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
            })
        }
    },
    methods:{
        handleColParagraph(pid){
            axios.post('paragraph/addColParagraph',{
                uid:0,
                paragraphSeq:pid
            },{
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
            axios.post('paragraph/addNote',{
                uid:0,
                paragraphSeq:pid,
                noteContent:noteContent
            },{
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
            console.log(pid)
        }
    }
}

Vue.component('my_section',sectionModule)


