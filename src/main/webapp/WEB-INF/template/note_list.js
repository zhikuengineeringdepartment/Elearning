/*
文件上传下载记录组件
 */
var noteListTemplate = `
<div>
    <ul>
        <template v-for="(note,index) in note_list" >
            <li style="list-style: none">
                <my_note :note="note" :index="index"></my_note>
            </li>
        </template>
    </ul>
</div>
`

var noteListModule = {
    data:function () {
        return{
            note_list:[

            ]
        }
    },
    props:[],
    template: noteListTemplate,
    created:function(){
        this.getNoteView(10405)
    },
    methods:{
        getNoteView:function (sid) {
            var _this = this;
            axios.post('paragraph/getNoteBySid',{
                uid:0,
                sid:sid
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
                    _this.note_list = res.data.data.noteViews;
                })
                .catch(function(err){
                    console.log(err);
                });
        }

    },

}

Vue.component("my_note_list",noteListModule);