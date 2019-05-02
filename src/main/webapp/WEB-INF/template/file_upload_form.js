/*
文件上传表单组件
 */
var fileUploadFormTemplate = `
<el-main>
<el-row type="flex" justify="center">
            <el-col :span="12" style="border: 1px solid #ebebeb;padding:20px;">
                <el-form ref="form" :model="uploadForm" label-width="80px">
                    <el-form-item label="文件">
                        <el-upload
                                class="upload-demo"
                                action="file/upload"
                                ref="upload"
                                name="multipartFile"
                                :file-list="uploadForm.multipartFile"
                                :limit="3"
                                :on-exceed="handleExceed"
                                :on-change="change_file_list"
                                :on-success="handleSuccess"
                                :on-remove="handleRemove"
                                :auto-upload="false"
                                :with-credentials="true">
                            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                            <div slot="tip" class="el-upload__tip" style="display:inline; margin-left:20px">可上传word,ppt,pdf类型文件，且不超过100M</div>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label="所属课程">
                        <my_course_select v-on:get-course-value="set_course_value"></my_course_select>
                    </el-form-item>
                    <el-form-item label="任课教师">
                        <el-input v-model="uploadForm.fileTeacher"></el-input>
                    </el-form-item>
                    <el-form-item label="标签">
                        <el-tag
                                :key="tag"
                                v-for="tag in uploadForm.file_tags"
                                closable
                                :disable-transitions="false"
                                @close="handleClose(tag)">
                            {{tag}}
                        </el-tag>
                        <el-input
                                class="input-new-tag"
                                v-if="tagVisible"
                                v-model="tagValue"
                                ref="saveTagInput"
                                size="small"
                                @keyup.enter.native="handleInputConfirm"
                                @blur="handleInputConfirm"
                        >
                        </el-input>
                        <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
                        <el-button @click="returnBack">取消</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-main>
`

var fileUploadFormModule = {
    data:function () {
        return{
            tagVisible: false,
            tagValue: '',
            uploadForm: {
                fileCourse:'',
                fileTeacher: '',
                multipartFile:[],
                file_tags: []
            }
        }
    },
    props:[],
    template: fileUploadFormTemplate,
    created:function(){
      this.courses = indexMainModule.courses
    },
    methods:{
        handleExceed(files, fileList) {
            this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
        },
        handleClose(tag) {
            this.uploadForm.file_tags.splice(this.uploadForm.file_tags.indexOf(tag), 1);
        },
        change_file_list(file,flist){
            console.log(this.$refs.upload.uploadFiles)
            this.uploadForm.multipartFile=flist
        },
        set_course_value:function(cid){
            this.uploadForm.fileCourse = cid;
        },
        handleInputConfirm() {
            let tagValue = this.tagValue;
            if (tagValue) {
                this.uploadForm.file_tags.push(tagValue);
            }
            this.tagVisible = false;
            this.tagValue = '';
        },
        showInput() {
            this.tagVisible = true;
            this.$nextTick(_ => {
                this.$refs.saveTagInput.$refs.input.focus();
            });
        },
        handleSuccess(respnose,file,fileList){
            alert(respnose.data.data.message)
        },
        handlePreview(file){
            console.log(file);
        },
        handleRemove(file,fileList){
            console.log(file,fileList)
        },
        submitUpload(){
            var _this = this;
            var form = new FormData();
            form.append("multipartFile", document.getElementsByClassName('el-upload__input')[0].files[0]);
            form.append("uid", 0);
            form.append("fileCourse", this.uploadForm.fileCourse);
            form.append("fileTeacher", this.uploadForm.fileTeacher);
            console.log(this.uploadForm.file_tags)
            for(var i = 1;i<=this.uploadForm.file_tags.length;i++){
                form.append("key"+i,this.uploadForm.file_tags[i-1])
            }
            const instance=axios.create({
                withCredentials: true
            })
            instance.post('file/upload',form).then(res=>{
                if(res.code == 200){
                    alert('提交成功');
                    this.$router.push({ path: "/" });

                }else{
                    alert("请输入完整再提交");
                }
            })
        },
        returnBack(){
          this.$router.go(-1);
        }
    },

}

Vue.component("my_file_upload_form",fileUploadFormModule);