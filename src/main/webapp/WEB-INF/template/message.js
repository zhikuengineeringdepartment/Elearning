/*
用户收到的消息组件
 */
var messageTemplate = `
<div style="display: flex">
                            <img :src="msg.toAvatar" width="60px" height="60px">
                            <div style="width: 90%">
                                <div>
                                    <i>{{msg.toUserName}}</i>
                                    <span style="float: right">{{msg.messageDate}}</span>
                                </div>
                                <p style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap; ">{{msg.messageContent}}</p>
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