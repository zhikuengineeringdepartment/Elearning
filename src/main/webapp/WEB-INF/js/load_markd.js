/**
 * 依赖
 * ./marked.min.js
 * 使用方法：
 * load_paragraph(data,root_id)
 * @param data: json
 * @param root_id:string, 为已存在的div的id，作为根div
 * 即可渲染组件
 */



/// <reference path="./JQuery3.4.0.js" />
/// <reference path="./wangEditor.min.js" />
// (function(){
//     var script = document.createElement("script");
//     script.type = "text/javascript";
//     script.src = "./js/marked.min.js";
//     document.body.appendChild(script);
// })()


function load_paragraph(json_data, root_id, saveFunc, collectionF, cancelCollectionF) {
    saveFunc = typeof (saveFunc) === 'function' ? saveFunc : new Function();
    collectionF = typeof (collectionF) === 'function' ? collectionF : new Function();
    cancelCollectionF = typeof (cancelCollectionF) === 'function' ? cancelCollectionF : new Function();

    document.getElementById(root_id).innerHTML = ""
    let section_div = document.createElement('div');
    section_div.id = json_data.sid;
    document.getElementById(root_id).append(section_div);
    document.getElementById(root_id).className = "root";
    section_div.innerHTML = marked(json_data.sectionName);
    let knowledges = json_data['knowledgeViews']
    if (knowledges != '') {
        knowledges.map((item, index) => {
            let k_div = document.createElement('div');
            k_div.id = item.kid;
            document.getElementById(root_id).append(k_div);
            k_div.innerHTML = marked(item.knowledgeName);
            p_in_ks = item['paragraphs']
            if (p_in_ks != '') {
                p_in_ks.map((it, ind) => {
                    let p_div = document.createElement('div');
                    p_div.id = it.paragraphSeq;
                    k_div.append(p_div)
                    p_div.innerHTML += marked(it.paragraphContent)


                    /*更改及测试 */
                    p_div.className = "pdiv"; //创建css类

                    let p_divSub1 = document.createElement('div'); //新建子dom节点div（收藏）
                    p_div.append(p_divSub1);
                    p_divSub1.className = "SubpdivC";
                    p_divSub1.id = p_div.id + '_Cbutton';
                    p_divSub1.innerText = "收藏";

                    let p_divSub2 = document.createElement('div'); //新建子dom节点div（笔记）
                    p_div.append(p_divSub2);
                    p_divSub2.className = "SubpdivN";
                    p_divSub2.id = p_div.id + '_Nbutton';
                    p_divSub2.innerText = "笔记";
                    //p_div.dataset.balloonData="收藏|笔记";
                    //console.log(p_div.dataset);

                    //添加点击事件监听

                    p_divSub1.addEventListener("mousedown", function () {
                        //console.log("按下了收藏");
                        var collectionButton = this;
                        let button_text = collectionButton.innerHTML;
                        if (button_text === "收藏") {
                            collectionF(collectionButton.parentElement.id);
                            collectionButton.innerHTML = "取消";
                        } else if (button_text === "取消") {
                            cancelCollectionF(collectionButton.parentElement.id);
                            collectionButton.innerHTML = "收藏";
                        }

                    });

                    $('#' + p_divSub2.id).click(function () {
                        console.log("按下了笔记");
                        console.log(this.parentElement.id);
                        // popLayer.style.display="block";

                        let self = this;
                        //var baseText = null;

                        //console.log(self.parentElement.children[1]);
                        console.log(self.parentElement.id);
                        //let popUp = self.parentElement.children[1];
                        // popUp.style.visibility = "visible";
                        //popUp.style.shadow="10px 10px 5px #888888";
                        //if (baseText == null) baseText = popUp.innerHTML;
                        // if(!document.getElementById(self.parentElement.id+'_notes')){
                        // 	createNote(self.parentElement.id,saveFunc);

                        // 	// document.getElementById(self.parentElement.id+'_notes').focus();
                        // 	}
                        // else{
                        // 	document.getElementById(self.parentElement.id+'_notes').style.visibility="visible";
                        // };
                        if (!document.getElementById(self.parentElement.id + '_editor')) {
                            createNote(self.parentElement.id, saveFunc);
                            this.style.cursor = "not-allowed";
                            this.style.background = "#D3D3D3"
                            // document.getElementById(self.parentElement.id+'_notes').focus();
                        } else {
                            //console.log(this);
                            document.getElementById(self.parentElement.id + '_editor').style.disable = "-webkit-box";
                        };
                    });
                });
            }
        })
    }
};

//load_paragraph(section)
// function collt(self,requestF){
//
// }

[{
    paragraphSeq: 1000101001,
    noteContent: ""
}]
//一个方法
//遍历上面那个数组，对数组的每一个元素执行下面那个方法
function load_array(arr, saveFunc) {
    for (let i = 0; i < arr.length; i++) {
        createNote(arr[i].paragraphSeq, saveFunc, arr[i].noteContent);
    }
    document.activeElement.blur();
}

function createNote(id, saveF, content) {
    //$('#'+id).after('<textarea class="input_notes" id='+id+'_notes></textarea>');
    $('#' + id).after('<div id=' + id + '_editor class=notes_editor></div>');
    $('#' + id + '_editor').append('<div id=_' + id + '_editor_bar class="editor_bar"></div>');
    $('#_' + id + '_editor_bar').after
    ('<div id=_' + id + '_notes class="input_notes" title="点击这里写笔记&#10;再次点击其他位置可自动保存&#10;或者隐藏笔记区"></div>');
    // console.log(id);
    // console.log(document.getElementById(id+'_editor'));
    //$('#'+id+'_editor_bar').after('<div id='+id+'_notes></div>');
    //console.log(document.getElementById(id+'_notes'));
    var E = window.wangEditor;
    var editor = new E('#_' + id + '_editor_bar', '#_' + id + '_notes');
    editor.customConfig.uploadImgShowBase64 = true // 使用 base64 保存图片
    editor.customConfig.hasOnBlur = 0;
    /**失焦的事件
     * wangEditor自带的onblur只是手动获取焦点的情况有效
     * js原生的'blur'可以,但是会在点击菜单时候也失焦
     */
    editor.customConfig.onblur = function () {
        editor.customConfig.hasOnBlur = 1;
        let html = editor.txt.html();
        console.log('onblur', html)
        if (html === '<p><br></p>') {
            // console.log("空的");
            // console.log(document.getElementById(id+'_editor'));

            //为空时候也要保存一次，不然会显示上一次保存的内容
            saveF(id, editor.txt.html());

            $('#' + id + '_editor').remove();
            // $('#_' + id + '_save_button').remove();
            document.getElementById(id + '_Nbutton').style.cursor = "pointer";
            document.getElementById(id + '_Nbutton').style.background = "#BFEFFF";

        }
        else {
            //自动保存
            saveF(id, editor.txt.html());
            // document.getElementById('_' + id + '_save_button').style.visibility = "hidden";
            //this.style.height="20px";
            //console.log("我失焦了，要变色");
            document.getElementById('_' + id + '_notes').style.background = "#EEEEEE";
            document.getElementById(id + '_editor').children[0].style.display = "none";
        }
    }
    //获取到焦点时
    editor.customConfig.onfocus = function () {
        //console.log("焦点在这");
        document.getElementById('_' + id + '_notes').style.background = "#E1F5FE";
        // document.getElementById('_' + id + '_save_button').style.visibility = "visible";
        document.getElementById(id + '_editor').children[0].style.display = "-webkit-box";
    }

    editor.create();
    //editor.$textElem[0].attr("placeholder","在此处输入");
    document.activeElement.blur();

    document.getElementById('_' + id + '_notes').children[0].dataset.placeholder="My Note:";

    //console.log(document.activeElement);

    // 执行代码
    //$(document).on('click', function (event) {
    //let isInEditor=$.contains(document.getElementById(id+'_editor'),$(e.target));
    //let isInEditor=document.getElementById(id+'_editor').contains($(e.target));

    // document.getElementById('_' + id + '_notes').children[0].addEventListener('blur', function (event) {
    //     console.log('失焦');
    // 	let div_editor = document.getElementById(id + '_editor');
    // 	let x = event.clientX;
    // 	let y = event.clientY;
    // 	console.log(document.getElementById(id + '_editor'));
    // 	let divx1 = div_editor.offsetLeft;
    // 	let divy1 = div_editor.offsetTop;
    // 	let divx2 = div_editor.offsetLeft + div_editor.offsetWidth;
    // 	let divy2 = div_editor.offsetTop + div_editor.offsetHeight;
    // 	console.log(x);
    // 	//如果不在该区域
    // 	// if (x < divx1 || x > divx2 || y < divy1 || y > divy2) {
    // 		let html = editor.txt.html();
    // 		console.log('onblur', html)
    // 		if (html === '<p><br></p>') {
    // 			// console.log("空的");
    // 			console.log(document.getElementById(id + '_editor'));

    // 			//为空时候也要保存一次，不然会显示上一次保存的内容
    // 			saveF(id, editor.txt.html());

    // 			$('#' + id + '_editor').remove();
    // 			$('#_' + id + '_save_button').remove();
    // 			document.getElementById(id + '_Nbutton').style.cursor = "pointer";
    // 			document.getElementById(id + '_Nbutton').style.background = "#BFEFFF";

    // 		} else {
    // 			//自动保存
    // 			saveF(id, editor.txt.html());
    // 			document.getElementById('_' + id + '_save_button').style.visibility = "hidden";
    // 			//this.style.height="20px";
    // 			console.log("我失焦了，要变色");
    // 			document.getElementById('_' + id + '_notes').style.background = "#EEEEEE";
    // 			document.getElementById(id + '_editor').children[0].style.display = "none";
    // 		}
    // 	//}

    // });


    //});

    // 	document.addEventListener('mousedown',function(event){
    // 		document.getElementById(id+'_notes').addEventListener('blur',function(){
    // 		let div_editor = document.getElementById(id + '_editor');
    // 		let x = event.clientX;
    // 		let y = event.clientY;
    // 		console.log(document.getElementById(id + '_editor'));
    // 		let divx1 = div_editor.offsetLeft;
    // 		let divy1 = div_editor.offsetTop;
    // 		let divx2 = div_editor.offsetLeft + div_editor.offsetWidth;
    // 		let divy2 = div_editor.offsetTop + div_editor.offsetHeight;
    // 		let notInEditor=x < divx1 || x > divx2 || y < divy1 || y > divy2;
    // 		console.log(x);
    // 		//不在该区域内
    // 		if (notInEditor) {
    // 			if (editor.txt.html() === '<p><br></p>') {
    // 				// console.log("空的");
    // 				console.log(document.getElementById(id + '_editor'));

    // 				//为空时候也要保存一次，不然会显示上一次保存的内容
    // 				saveF(id, editor.txt.html());

    // 				$('#' + id + '_editor').remove();
    // 				$('#_' + id + '_save_button').remove();
    // 				document.getElementById(id + '_Nbutton').style.cursor = "pointer";
    // 				document.getElementById(id + '_Nbutton').style.background = "#BFEFFF";

    // 			} else {
    // 				//自动保存
    // 				saveF(id, editor.txt.html());
    // 				document.getElementById('_' + id + '_save_button').style.visibility = "hidden";
    // 				//this.style.height="20px";
    // 				console.log("我失焦了，要变色");
    // 				document.getElementById('_' + id + '_notes').style.background = "#EEEEEE";
    // 				document.getElementById(id + '_editor').children[0].style.display = "none";
    // 			}
    // 		}
    // 	});
    // });





    //保存按钮
    // $('#_' + id + '_notes').after('<button class="save_button" id=_' + id + '_save_button>保存</button>');
    //document.getElementById(id+'_notes').focus();
    document.getElementById('_' + id + '_notes').style.background = "#E1F5FE";
    // document.getElementById('_' + id + '_save_button').style.visibility = "visible";
    //console.log('_'+id+'_notes');

    /** 按下保存按钮,在按下按钮的时候就已经失焦了，所以没必要在其中添加save了
     *  现在这个按钮挺鸡肋的
     */
    // document.getElementById('_' + id + '_save_button').addEventListener('mousedown', function (event) {
    //     //if (editor.customConfig.hasOnBlur !== 0) {
    //     //saveF(id, editor.txt.html());
    //     this.style.visibility = 'hidden';
    //
    //     // event.preventDefault();
    //     //Event 接口的 preventDefault()方法，告诉user agent：如果此事件没有被显式处理，那么它默认的动作也不要做（因为默认是要做的）
    //     if (editor.txt.html() !== '<p><br></p>') {
    //
    //         console.log("save");
    //         //editor.selection.collapseRange();
    //         document.getElementById('_' + id + '_notes').style.background = "#EEEEEE";
    //         document.getElementById(id + '_editor').children[0].style.display = "none";
    //     } else {
    //         //笔记为空，移除笔记区
    //
    //
    //         console.log("save");
    //         $('#' + id + '_editor').remove();
    //         $('#_' + id + '_save_button').remove();
    //         //console.log(document.getElementById(id+'_Nbutton'));
    //         document.getElementById(id + '_Nbutton').style.cursor = "pointer";
    //         document.getElementById(id + '_Nbutton').style.background = "#BFEFFF";
    //     }
    //     // }
    //     // else{
    //
    //     // }
    //
    // });



    // document.getElementById(id+'_notes').addEventListener('blur',function(event){

    // 	console.log("你失焦了");
    // 	console.log(this.id);

    // 	this.style.background="#ffffff";
    // 	saveF(id,content);
    // 	if(this.value===''){
    // 		$(this).remove();
    // 		console.log("已删");
    // 		$('#'+id+'_save_button').remove();
    // 	}
    // 	else{
    // 		document.getElementById(id+'_save_button').style.visibility="hidden";
    // 		this.style.height="20px";
    // 		this.style.background="#EEEEEE";
    // 	}
    // });


    // 	document.getElementById(id+'_notes').addEventListener('focus',function(){
    // 		console.log("focus1");
    // 	    this.style.background="#E1F5FE";
    // 		document.getElementById(id+'_save_button').style.visibility="visible";
    // 	});
    // $('#'+id+'_notes').focus(function(){
    // 	console.log("focus2");
    //     document.getElementById(id+'_notes').style.background="#E1F5FE";
    // 	document.getElementById(id+'_save_button').style.visibility="visible";
    // });


    //如果参数数目为3
    if (arguments.length == 3) {
        //input value =content
        //document.getElementById('_'+id+'_notes').value=content;

        if (content != '<p><br></p>') {
            editor.txt.html(content);
            document.getElementById('_' + id + '_notes').style.background = "#EEEEEE";
            document.getElementById(id + '_editor').children[0].style.display = "none";
        }
        // $('body')[0].focus();

    } else {

    }
}