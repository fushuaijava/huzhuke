require.config({
	baseUrl: "../js",
    paths : {
        jquery: 'lib/jquery',
		SuperSlide:'lib/TouchSlide',
    }
})
require(['jquery','SuperSlide'], function($,SuperSlide) {
	
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
	

});	