// 选择文件触发事件  
function selectImg(file) {
    //文件为空，返回  
    if (!file.files || !file.files[0]) {
        return;
    }
    $(".tailoring-container").toggle();
    var reader = new FileReader();
    reader.onload = function (e) {
        var replaceSrc = e.target.result;
        let imgSize = file.files[0].size;
        if (imgSize > 1024 * 1024) {
            alert("请更换一个小于1M的图片");
        }
        else {
            // 更换cropper的图片
            //加载图片获取图片真实宽度和高度
            var image = new Image();
            image.onload = function () {
                let width = image.width;
                let height = image.height;
                //alert(width + '======' + height + "=====" + f.size);
                // let whRatio = height / width;
                // console.log(whRatio);
                // console.log(document.getElementById('pic_container').style.width);
                // let containerWidth = 27.8;
                // if (containerWidth * whRatio > 56) {
                //     document.getElementById('pic_container').style.height = "56vh";
                // }
                // else {
                //     document.getElementById('pic_container').style.height = 27.8 * whRatio + "vh";
                // }
            };
            image.src = replaceSrc;
            $('#tailoringImg').cropper('replace', replaceSrc, false);// 默认false，适应高度，不失真
            //$('#tailoringImg').cropper('replace', replaceSrc, true);
        }

    }
    reader.readAsDataURL(file.files[0]);
}
function init_cut(){
    // cropper图片裁剪
    $('#tailoringImg').cropper({
        preview: '.previewImg',// 预览视图
        guides: false, // 裁剪框的虚线(九宫格)
        autoCropArea: 0.8, // 0-1之间的数值，定义自动剪裁区域的大小，默认0.8
        movable: false, // 是否允许移动图片
        dragCrop: false, // 是否允许移除当前的剪裁框，并通过拖动来新建一个剪裁框区域
        resizable: true, // 是否允许改变裁剪框的大小
        zoomable: true, // 是否允许缩放图片大小
        mouseWheelZoom: false, // 是否允许通过鼠标滚轮来缩放图片
        touchDragZoom: true, // 是否允许通过触摸移动来缩放图片
        rotatable: false, // 是否允许旋转图片
        aspectRatio: 1 / 1,//裁剪框比例 1：1
        background:false,
        // modal:false,
        // viewMode: 3,//显示
        viewMode: 1,
        crop: function (e) {
            // 输出结果数据裁剪图像。
        }
    });


// // 确定按钮点击事件
//     $("#sureCut").on("click", function () {
//         if ($("#tailoringImg").attr("src") == null) {
//             return false;
//         } else {
//             var cas = $('#tailoringImg').cropper('getCroppedCanvas');// 获取被裁剪后的canvas
//             var base64 = cas.toDataURL('image/jpeg'); // 转换为base64
//
//             $("#user_avatar").prop("src", base64);// 显示图片
//             // uploadFile(encodeURIComponent(base64))//编码后上传服务器
//             closeTailor();// 关闭裁剪框
//
//         }
//     });
//

}



// 关闭裁剪框  
function closeTailor() {
    //document.getElementById("pic_cropper_detail").open="";
    $(".pic_cropper_dialog")[0].style.display = "none";
    $(".details-overlay").removeClass('details-overlay-dark');
}
// 点击以显示对话框
// $("#change_avatar").on('click', function () {
//     console.log(111)
//     $(".pic_cropper_dialog")[0].style.display = "block";
//     $(".details-overlay").addClass('details-overlay-dark');
// })
// function changeAvatar(){
//     console.log(111)
//     $(".pic_cropper_dialog")[0].style.display = "block";
//     $(".details-overlay").addClass('details-overlay-dark');
// }
//点击x关闭对话框
// $(".pic_dialog_close").on('click', function () {
//     closeTailor();
// })


