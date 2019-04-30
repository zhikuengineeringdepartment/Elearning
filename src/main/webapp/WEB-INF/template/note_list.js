/*
文件上传下载记录组件
 */
var noteListTemplate = `
<div>
    <ul>
        <template v-for="note in note_list" >
            <li style="list-style: none">
                <my_note :note="note"></my_note>
            </li>
        </template>
    </ul>
</div>
`

var noteListModule = {
    data:function () {
        return{
            note_list:[
                {

                }
            ]
        }
    },
    props:[],
    template: noteListTemplate,
    methods:{
    },

}

Vue.component("my_note_list",noteListModule);