/*
用户收到的消息组件
 */
var messageTemplate = `
<div style="display: flex">
                            <img :src="msg.user.icon" width="60px" height="60px">
                            <div style="width: 90%">
                                <div>
                                    <i>{{msg.user.name}}</i>
                                    <span style="float: right">{{msg.time}}</span>
                                </div>
                                <p style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap; ">{{msg.content}}</p>
                            </div>
                        </div>
`

var messageModule = {
    data:function () {
        return{
        }
    },
    props:["msg"],
    created:function(){
    },
    template: messageTemplate,
    methods:{

    },

}

Vue.component("my_message",messageModule);