/**
 * 依赖
 * ./marked.min.js
 * 使用方法：
 * load_paragraph(data,root_id)
 * @param data: json
 * @param root_id:string, 为已存在的div的id，作为根div
 * 即可渲染组件
 */




// (function(){
//     var script = document.createElement("script");
//     script.type = "text/javascript";
//     script.src = "./js/marked.min.js";
//     document.body.appendChild(script);
// })()


function load_paragraph(json_data, root_id ,saveFunc,collectionF,cancelCollectionF) {
	saveFunc = typeof(saveFunc)==='function'?saveFunc:new Function();
	collectionF = typeof(collectionF)==='function'?collectionF:new Function();
	cancelCollectionF = typeof(cancelCollectionF)==='function'?cancelCollectionF:new Function();

	document.getElementById(root_id).innerHTML = ""
	let section_div = document.createElement('div');
	section_div.id = json_data.sid;
	document.getElementById(root_id).append(section_div);
	document.getElementById(root_id).className="root";
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
					p_divSub1.innerText = "收藏";

					let p_divSub2 = document.createElement('div'); //新建子dom节点div（笔记）
					p_div.append(p_divSub2);
					p_divSub2.className = "SubpdivN";
					p_divSub2.id=p_div.id+'_Nbutton';
					p_divSub2.innerText = "笔记";
					//p_div.dataset.balloonData="收藏|笔记";
					//console.log(p_div.dataset);

					//添加点击事件监听
					
					p_divSub1.addEventListener("mousedown", function() {
						//console.log("按下了收藏");						
						var collectionButton=this;
						let button_text=collectionButton.innerHTML;
						if(button_text==="收藏"){							
							collectionF(collectionButton.parentElement.id);
							collectionButton.innerHTML="取消";
						}
						else if(button_text==="取消"){
							//TODO调用取消收藏的函数
							cancelCollectionF(collectionButton.parentElement.id);						
							collectionButton.innerHTML="收藏";
						}						
					
					});

                    $('#'+ p_divSub2.id).click(function(){
						console.log("按下了笔记");
						console.log(this.parentElement.id);
						// popLayer.style.display="block";
						
						var self = this;
						//var baseText = null;
						
						//console.log(self.parentElement.children[1]);
						console.log(self.parentElement.id);
						//let popUp = self.parentElement.children[1];
						// popUp.style.visibility = "visible";
						//popUp.style.shadow="10px 10px 5px #888888";
						//if (baseText == null) baseText = popUp.innerHTML;
						if(!document.getElementById(self.parentElement.id+'_notes')){							
							createNote(self.parentElement.id,saveFunc);	
							// document.getElementById(self.parentElement.id+'_notes').focus();						
							}	
						else{
							document.getElementById(self.parentElement.id+'_notes').style.visibility="visible";
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
	paragraphSeq:1000101001,
	noteContent:""
}]
//TODO
//一个方法
//遍历上面那个数组，对数组的每一个元素执行下面那个方法
function load_array(arr,saveFunc){
	for(let i=0;i<arr.length;i++){
		arr[i].createNote(arr[i].paragraphSeq,saveFunc,arr[i].noteContent);
	}
}

function createNote(id,saveF,content) {
	$('#'+id).after('<textarea class="input_notes" id='+id+'_notes></textarea>');
	
	    //('#'+self.parentElement.id).after(inputNote);
	$('#'+id+'_notes').after('<button class="savebutton" id='+id+'_savebutton>保存</button>');	
    //let isfocus=false;
	
// 	function losefocus(){
// 		
// 	
// 	}
    document.getElementById(id+'_notes').focus();
	document.getElementById(id+'_notes').style.background="#E1F5FE";
	document.getElementById(id+'_savebutton').style.visibility="visible";
	//console.log(id+'_notes');
	//按下保存按钮
	document.getElementById(id+'_savebutton').addEventListener('mousedown',function(event){
		saveF(id,content);
		this.style.visibility='hidden';
	});
	
	document.getElementById(id+'_notes').addEventListener('blur',function(event){
		
		console.log("你失焦了");
		console.log(this.id);
		//alert("你失焦了");
		//失焦后自动保存
		this.style.background="#ffffff";
		saveF(id,content);
		if(this.value===''){
			//this.style.visibility="hidden";
			$(this).remove();
			//console.log("已删");
			$('#'+id+'_savebutton').remove();
		}
		else{
			document.getElementById(id+'_savebutton').style.visibility="hidden";
			this.style.height="20px";
			this.style.background="#EEEEEE";
		}
		// document.getElementById(id+'_savebutton').style.visibility="hidden";
		//console.log(self.parentElement);			    								
	});
    
	
// 	document.getElementById(id+'_notes').addEventListener('focus',function(){
// 		//this.contentEditable=true;
// 		console.log("focus1");
// 	    this.style.background="#E1F5FE";
// 		document.getElementById(id+'_savebutton').style.visibility="visible";
// 	});
	$('#'+id+'_notes').focus(function(){
		console.log("focus2");
	    document.getElementById(id+'_notes').style.background="#E1F5FE";
		document.getElementById(id+'_savebutton').style.visibility="visible";		
	});
	
	
	//如果参数数目为3
	if (arguments.length==3){
		//input value =content
		document.getElementById(id+'_notes').value=content;
	}else{
		
	}
}
