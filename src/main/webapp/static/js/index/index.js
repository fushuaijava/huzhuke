require.config({
	baseUrl: "../js",
	shim:{
		'validate': ['jquery'],
		'cookie': ['jquery'],
		'md5': ['jquery']
	},
    paths : {
        jquery: 'lib/jquery',
		SuperSlide:'lib/TouchSlide',
        md5: 'lib/jQuery.md5',
        validate: 'lib/jquery.validate.min',
        cookie: 'lib/jquery.cookie',
        userlogin: 'index/userlogin',
        baseurl: 'module/baseurl',
        loginout: 'index/loginout'
    }
})
require(['jquery','baseurl','SuperSlide','userlogin','loginout','md5','validate','cookie'], function($,baseurl,SuperSlide,userlogin,loginout,md5,validate,cookie) {

	//页面载入后用户自动根据cookie登录获取用户信息
	userlogin.init();
	//退出登录
	loginout.init();

	//banner 轮播图
	function banner_wh(){
		$('.banner .bd a').width($('.banner').outerWidth()).height($('.banner').outerHeight())
	}
	banner_wh()
	
	TouchSlide({
		slideCell:"#banner",
		titCell:".hd ul",
		mainCell:".bd ul",
		effect:"leftLoop",
		autoPlay:true,//自动播放
		autoPage:true, //自动分页
		delayTime:500, //切换效果持续时间
		interTime:2500, //自动运行间隔
	});
	
	
	//banner图片大小控制
	function imgSize(pTag){
		pTag.each(function(){
			var wW = $(this).outerWidth()
			var wH = $(this).outerHeight()
			var t = $(this).children('img')
			var iW = t.width()
			var iH = t.height()
			var rateW = wW/iW
			var rateH = wH/iH
			var rateM = Math.max(rateW,rateH)
			var mT = -(iH*rateM-wH)/2
			var mL = -(iW*rateM-wW)/2
			
			t.width(iW*rateM).height(iH*rateM).css({'marginTop':mT,'marginLeft':mL})
		})
	};
	imgSize($('.banner .bd a'));
	
	$(window).resize(function(){
		banner_wh()
		imgSize($('.banner .bd a'))
	});

	//侧栏
	function open_slide(){
		$('.pageIndex').stop().animate({'left':'75%'},200).find('.header,.footer,.indexWrap').stop().animate({'left':'75%'},200);
		$('.pageLeft').stop().animate({'left':'0'},200);
		$('.indexWrap').show();	
	};
	function close_slide(){
		$('.pageIndex').stop().animate({'left':'0'},200).find('.header,.footer,.indexWrap').stop().animate({'left':'0'},200);
		$('.pageLeft').stop().animate({'left':'-75%'},200);
		$('.indexWrap').hide();
	};
	$('#portrait').live('click',function(){
		open_slide()
	})
	$('.indexWrap').click(function(){
		close_slide()
	});
	
	//滑动关闭左栏
	//全局变量，触摸开始位置  
	var startX = 0, startY = 0;  
	
	//touchstart事件  
	function touchSatrtFunc(e) {  
		var touch = e.touches[0]; //获取第一个触点  
		var x = Number(touch.pageX); //页面触点X坐标  
		var y = Number(touch.pageY); //页面触点Y坐标  
		//记录触点初始位置  
		startX = x;
		startY = y;  
	}
	 
	//touchmove事件，这个事件无法获取坐标  
	function touchMoveFunc_l(e) {  
		e.preventDefault(); //阻止触摸时浏览器的缩放、滚动条滚动等  
		var touch = e.touches[0]; //获取第一个触点  
		var x = Number(touch.pageX); //页面触点X坐标  
		var y = Number(touch.pageY); //页面触点Y坐标  
		var move_x = x - startX;  // <0是往左
		var move_y = y - startY;  // <0是往上
		
		//判断滑动方向
		if ( move_x<0 && move_y<5 && move_y>-5 ) {  //往左滑动 上下滑动小于5 
			close_slide()
		}
	}  
	 	
	//绑定事件  
	function bindEvent(part1,part2) {  
		$('.'+part1+'')[0].addEventListener('touchstart', touchSatrtFunc, false);  
		$('.'+part1+'')[0].addEventListener('touchmove', part2, false); 
	}	
	bindEvent('indexWrap',touchMoveFunc_l)


	//加载失败提示
	function erMsg(){
		$('.error').show();
		setTimeout(function(){
			$('.error').hide();
		},1000);
	};

/*	if( Number($('#noreadmsg').html())<=0 ||  $('#noreadmsg').html()==''){
		$('#noreadmsg').hide()
	}else{
		$('#noreadmsg').show()
	}
*/	
});
