var fileTemplate = `
<el-card shadow="hover" style="margin: 10px 0px">
                    <el-row>
                        <el-col>
                            <el-row>
                                <el-col :span="8">jfile.mingzi</el-col>
                                <el-col :span="8">{{upper}}上传于{{date}}</el-col>
                                <el-col :span="8">下载量{{downloadCount}}</el-col>
                            </el-row>
                        </el-col>
                        <el-col>
                            <el-row>
                                <el-col :span="12">
                                    <el-tag>标签一</el-tag>
                                    <el-tag type="success">标签二</el-tag>
                                    <el-tag type="info">标签三</el-tag>
                                    <el-tag type="warning">标签四</el-tag>
                                    <el-tag type="danger">标签五</el-tag>
                                </el-col>
                                <el-col :span="12" style="display: flex;justify-content: center">
                                    <el-button type="primary" icon="el-icon-document" circle></el-button>
                                    <el-button type="success" icon="el-icon-download" circle></el-button>
                                </el-col>
                            </el-row>
                        </el-col>
                    </el-row>
                </el-card>
`

var fileModule = {
    data:function () {
        return{

        }
    },
    props:["jfile"],
    template: fileTemplate,
    methods:{

    },

}

Vue.component("my_file",fileModule);