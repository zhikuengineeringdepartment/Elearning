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
                                ref="upload"
                                action="uploadRequest"
                                multiple="false"
                                data="uploadForm"
                                accept="docx||pdf"
                                :on-preview="handlePreview"
                                :on-remove="handleRemove"
                                :file-list="fileList"
                                :auto-upload="false">
                            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                            <div slot="tip" class="el-upload__tip" style="display:inline; margin-left:20px">可上传word,ppt,pdf类型文件，且不超过100M</div>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label="所属课程">
                        <el-cascader :options="courses" change-on-select></el-cascader>
                    </el-form-item>
                    <el-form-item label="任课教师">
                        <el-input v-model="uploadForm.teacherName"></el-input>
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
                                v-if="inputVisible"
                                v-model="inputValue"
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
                        <el-button>取消</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-main>
`

var fileUploadFormModule = {
    data:function () {
        return{
            courses:[],
            inputVisible: false,
            inputValue: '',
            fileList:[],
            uploadForm: {
                teacherName: '',
                file_tags: []
            }
        }
    },
    props:[],
    template: fileUploadFormTemplate,
    methods:{
        handleClose(tag) {
            this.uploadForm.file_tags.splice(this.uploadForm.file_tags.indexOf(tag), 1);
        },
        handleInputConfirm() {
            let inputValue = this.inputValue;
            if (inputValue) {
                this.uploadForm.file_tags.push(inputValue);
            }
            this.inputVisible = false;
            this.inputValue = '';
        },
        showInput() {
            this.inputVisible = true;
            this.$nextTick(_ => {
                this.$refs.saveTagInput.$refs.input.focus();
            });
        },
        handlePreview(file){
            console.log(file);
        },
        handleRemove(file,fileList){
            console.log(file,fileList)
        },
        submitUpload(){
            console.log(this.uploadForm);
        }
    },

}

Vue.component("my_file_upload_form",fileUploadFormModule);