/*
文件上传下载记录列表组件
 */
var fileUploadRecordListTemplate = `
<div>
                    <div>文件上传记录</div>
                    <ul>
                        <template v-for="_file in _files" >
                            <li style="list-style: none">
                                <my_file_upload_record :_file="_file"></my_file_upload_record>
                            </li>
                        </template>
                    </ul>
                </div>
`

var fileUploadRecordListModule = {
    data:function () {
        return{
            _files:[]
        }
    },
    props:[],
    template: fileUploadRecordListTemplate,
    created:function(){
        this.getFiles();
    },
    methods:{
        getFiles:function(){
            this._files = [
                {
                    name:"数据结构",
                    desc:"数据结构的上课课件",
                    status:"正常",
                    uploadDate:'2018-09-15',
                    tags:[
                        {
                            name:"标签一"
                        },
                        {
                            name:"数据"
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
                            name:"标签一"
                        },
                        {
                            name:"数据"
                        }
                    ]
                }
            ]
        }
    },

}

Vue.component("my_file_upload_record_list",fileUploadRecordListModule);