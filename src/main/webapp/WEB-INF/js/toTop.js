$(window).scroll(function() {	
	//滚动高度
	var scrTop = $(window).scrollTop();
	
	//窗口高度
	var windowTop = $(window).height();
	var wHeight=600;
	
	//预先将css更改为display:none
	if ((windowTop - wHeight) < scrTop) {
		//滚动高度大于一页
		$(".toTop").fadeIn(50);
	} else {
		//滚动高度小于一页
		$(".toTop").fadeOut(200);
	}
});
