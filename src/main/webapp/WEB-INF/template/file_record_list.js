/*
文件上传下载记录列表组件
 */
var fileRecordListTemplate = `
<div>
                    <ul>
                        <template v-for="upload_file in upload_files" >
                            <li style="list-style: none">
                                <my_file_record :file_record="upload_file" :is_upload_page="is_upload_page"></my_file_record>
                            </li>
                        </template>
                    </ul>
                </div>
`

var fileRecordListModule = {
    data:function () {
        return{
            upload_files:[]
        }
    },
    props:["is_upload_page"],
    template: fileRecordListTemplate,
    created:function(){
        this.getFiles();
    },
    methods:{
        getFiles:function(){
            this.upload_files = [
                {
                    name:"数据结构",
                    desc:"数据结构的上课课件",
                    status:"正常",
                    uploadDate:'2018-09-15',
                    tags:[
                        {
                            tagName:"标签一"
                        },
                        {
                            tagName:"数据"
                        }
                    ]
                },
                {
                    name:"数据结构",
                    desc:"数据结构的上课课件",
                    status:"正常",
                    uploadDate:'2018-09-15',
                    tags:[
                        {
                            tagName:"标签一"
                        },
                        {
                            tagName:"数据"
                        }
                    ]
                }
            ]
        }
    },

}

Vue.component("my_file_record_list",fileRecordListModule);