/*
文件上传下载记录列表组件
 */
var fileRecordListTemplate = `
<div>
                    <ul>
                        <template v-if="is_upload_page === 'true'">
                            <template v-for="record in file_upload_records" >
                                <li style="list-style: none">
                                    <my_file_upload_record :file_upload_record="record"></my_file_upload_record>
                                </li>
                            </template>
                        </template>
                        <template v-else>
                            <template v-for="record in file_download_records" >
                                <li style="list-style: none">
                                    <my_file_download_record :file_download_record="record"></my_file_download_record>
                                </li>
                            </template>
                        </template>
                    </ul>
                </div>
`

var fileRecordListModule = {
    data:function () {
        return{
            page:1,
            file_upload_records:[],
            file_download_records:[]
        }
    },
    props:["is_upload_page"],
    template: fileRecordListTemplate,
    created:function(){
        this.getFiles();
    },
    watch:{
        '$route' (to) {
            console.log(to.params)
            if(to.params.is_upload_page === 'true'){
                this.getUploadRecords();
            }else{
                this.getDownloadRecords();
            }
        }
    },
    methods:{
        getFiles:function(){
            if(this.is_upload_page === 'true'){
                this.getUploadRecords();
            }else{
                this.getDownloadRecords();
            }
        },
        getUploadRecords:function(){
            var _this = this;
            axios.get("user/getUploadRecords",{
                params:{
                    uid:0,
                    page:this.page
                },
                withCredentials:true
            })
                .then(function (response) {
                    _this.file_upload_records = response.data.data.fileUploadRecords;
                    for(var i=0;i<_this.file_upload_records.length;i++){
                        _this.file_upload_records[i].fileUploadTime = getFormatDate(response.data.data.fileUploadRecords[i].fileUploadTime);
                        if(response.data.data.fileUploadRecords[i].fileStatus == 'n'){
                            _this.file_upload_records[i].fileStatus = '正常'
                        }else if(response.data.data.fileUploadRecords[i].fileStatus == 'f'){
                            _this.file_upload_records[i].fileStatus = '封禁'
                        }else if(response.data.data.fileUploadRecords[i].fileStatus == 'u'){
                            _this.file_upload_records[i].fileStatus = '待审核'
                        }else{
                            _this.file_upload_records[i].fileStatus = '异常'
                        }
                    }
                })
                .catch(function(err){
                    console.log(err);
                });
        },
        getDownloadRecords:function(){
            var _this = this;
            axios.get("user/getDownloadRecords",{
                params:{
                    uid:0,
                    page:this.page
                },
                withCredentials:true
            })
                .then(function (response) {
                    _this.file_download_records = response.data.data.fileDownloadRecords;
                    for(var i=0;i<_this.file_download_records.length;i++){
                        _this.file_download_records[i].fopDate = getFormatDate(response.data.data.fileDownloadRecords[i].fopDate);
                        if(response.data.data.fileDownloadRecords[i].fileView.fileStatus === 'n'){
                            _this.file_download_records[i].fileStatus = '正常'
                        }else if(response.data.data.fileDownloadRecords[i].fileView.fileStatus === 'f'){
                            _this.file_download_records[i].fileStatus = '封禁'
                        }else{
                            _this.file_download_records[i].fileStatus = '异常'
                        }
                    }
                })
                .catch(function(err){
                    console.log(err);
                });
        }
    },

}

Vue.component("my_file_record_list",fileRecordListModule);