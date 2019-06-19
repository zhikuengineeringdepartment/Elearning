/*
用户个人信息修改表单组件
 */
var userInfoFormTemplate = `
<el-form ref="form" :model="baseInfo" label-width="80px">
                    <div style="display: flex;align-items: center;margin: 10px;padding: 20px;">
                        <img id="user_avatar" :src="baseInfo.userAvatar" width="160px" height="160px">
                        <el-button id="change_avatar" type="primary" size="small" round style="margin: 20px;" @click="changeAvatar">更换头像</el-button>
                    </div>
                    <div id="pic_cropper_detail" class="details-overlay">
                        <div role="dialog" class="pic_cropper_dialog Box" tabindex="-1">
                            <div class="Box-header">
                                <h4 class="Box-title" >在下方上传并编辑你的头像
                                    <div id="pic_dialog_close" class="pic_dialog_close" @click="closeX">x</div> 
                                </h4>
                                             
                            </div>
                            <div class="flex_form">
                                <label class="pic_upload">
                                    点击以选择图片
                                    <input type="file" accept="image/jpg,image/jpeg,image/png" name="file" id="chooseImg"
                                        class="pic_input" onchange="selectImg(this)">
                                </label>
                                <div id="pic_container" class="container">
                                    <img id="tailoringImg">
                                </div>
                            </div>
                            <div id="sureCut" class="button_bar" @click="sureCuted">
                                <button class="pic_OK">确定并提交</button>
                            </div>

                        </div>
                    </div>
                    <el-form-item label="用户昵称">
                        <el-input v-model="baseInfo.userNick"></el-input>
                    </el-form-item>
                    <el-form-item label="用户邮箱" style="display: flex;align-items: center;margin-left: 0">
                        <div  style="display: flex;align-items: center;margin-left:-80px" >
                            <el-input v-model="baseInfo.userEmail" disabled></el-input>
                            <el-button type="primary" size="small" round disabled style="margin: 20px;">更换邮箱绑定</el-button>
                        </div>
                    </el-form-item>
                    <el-form-item label="手机号">
                        <el-input v-model="baseInfo.userPhone"></el-input>
                    </el-form-item>
                    <!--<el-form-item label="所属专业">-->
                        <!--<el-cascader :options="colleges" :props="college_major" change-on-select></el-cascader>-->
                    <!--</el-form-item>-->
                    <el-form-item label="密码" style="display: flex;align-items: center;">
                        <el-button type="primary" size="small" disabled round style="margin: -80px;">修改密码</el-button>
                    </el-form-item>
                </el-form>
`

var userInfoFormModule = {
    data:function () {
        return{
            baseInfo:{},
            colleges:[],
            college_major:{
                value: 'id',
                label: 'name',
                children: '_majors'
            }
        }
    },
    props:[],
    template: userInfoFormTemplate,
    created:function(){
        this.getUserInfo();
        this.getColleges();
    },
    mounted:function(){
        init_cut()
    },
    methods:{
        getUserInfo:function(){
            var _this = this;
            axios.get('user/getBaseInfo',{
                params:{
                    uid:0
                }
            })
                .then(function(response){
                    _this.baseInfo = response.data.data.baseInfo;
                })
                .then(function () {
                    if(root.login){
                        _this.getNoteView(sid);
                    }
                })
                .catch(function(err){
                    console.log(err);
                });
        },
        getColleges:function(){
            this.colleges = [
                {
                    id:1,
                    name:'软件学院',
                    _majors:[
                        {
                            id:2,
                            name:"软件工程"
                        },
                        {
                            name:"数媒"
                        }
                    ]
                }
            ]
        },
        changeAvatar(){
            console.log(111)
            $(".pic_cropper_dialog")[0].style.display = "block";
            $(".details-overlay").addClass('details-overlay-dark');
        },
        sureCuted:function () {
            if ($("#tailoringImg").attr("src") == null) {
                return false;
            } else {
                var cas = $('#tailoringImg').cropper('getCroppedCanvas');// 获取被裁剪后的canvas
                var base64 = cas.toDataURL('image/jpeg'); // 转换为base64

                this.baseInfo.userAvatar = base64;
                // $("#user_avatar").prop("src", base64);// 显示图片
                // uploadFile(encodeURIComponent(base64))//编码后上传服务器
                console.log(base64);
                this.modifyAvatar(base64)
                closeTailor();// 关闭裁剪框

            }
        },
        closeTailor() {
            //document.getElementById("pic_cropper_detail").open="";
            $(".pic_cropper_dialog")[0].style.display = "none";
            $(".details-overlay").removeClass('details-overlay-dark');
        },
        closeX:function () {
            closeTailor();
        },
        modifyAvatar:function (avatar) {
            var _this = this;
            axios.post('user/modifyAvatar',{
                uid:0,
                avatar:avatar
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
                    localStorage['user_icon'] = avatar;
                    console.log(res);
                })
                .catch(function(err){
                    console.log(err);
                });
        }
    },

}

Vue.component("my_baseInfo_form",userInfoFormModule);