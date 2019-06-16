var china = [
 //直辖市
 ['北京'],
 ['上海'],
 ['天津'],
 ['重庆'],
 //华北地区
 ['河北','石家庄','唐山','秦皇岛','邯郸','邢台','保定','张家口','承德','沧州','廊坊','衡水'],
 ['山西','太原','大同','阳泉','长治','晋城','朔州','晋中','运城','忻州','临汾','吕梁'],
 ['内蒙古','呼和浩特','包头','乌海','赤峰','通辽','鄂尔多斯','呼伦贝尔','巴彦淖尔','乌兰察布','兴安','锡林郭勒','阿拉善'],
 //东北地区
 ['辽宁','沈阳','大连','鞍山','抚顺','本溪','丹东','锦州','营口','阜新','辽阳','盘锦','铁岭','朝阳','葫芦岛'],
 ['吉林','长春','吉林','四平','辽源','通化','白山','松原','白城','延边'],
 ['黑龙江','哈尔滨','齐齐哈尔','鸡西','鹤岗','双鸭山','大庆','伊春','佳木斯','七台河','牡丹江','黑河','绥化','大兴安岭'],
 //华东地区
 ['江苏','南京','无锡','徐州','常州','苏州','南通','连云港','淮安','盐城','扬州','镇江','泰州','宿迁'],
 ['浙江','杭州','宁波','温州','嘉兴','湖州','绍兴','金华','衢州','舟山','台州','丽水'],
 ['安徽','合肥','芜湖','蚌埠','淮南','马鞍山','淮北','铜陵','安庆','黄山','滁州','阜阳','宿州','巢湖','六安','亳州','池州','宣城'],
 ['福建','福州','厦门','莆田','三明','泉州','漳州','南平','龙岩','宁德'],
 ['江西','南昌','景德镇','萍乡','九江','新余','鹰潭','赣州','吉安','宜春','抚州','上饶'],
 ['山东','济南','青岛','淄博','枣庄','东营','烟台','潍坊','威海','济宁','泰安','日照','莱芜','临沂','德州','聊城','滨州','菏泽'],
 //中南地区
 ['河南','郑州','开封','洛阳','平顶山','焦作','鹤壁','新乡','安阳','濮阳','许昌','漯河','三门峡','南阳','商丘','信阳','周口','驻马店'],
 ['湖北','武汉','黄石','襄樊','十堰','荆州','宜昌','荆门','鄂州','孝感','咸宁','随州','恩施'],
 ['湖南','长沙','株洲','湘潭','衡阳','邵阳','岳阳','常德','张家界','益阳','郴州','永州','怀化','娄底','湘西'],
 ['广东','广州','深圳','珠海','汕头','韶关','佛山','江门','湛江','茂名','肇庆','惠州','梅州','汕尾','河源','阳江','清远','东莞','中山','潮州','揭阳','云浮'],
 ['广西','南宁','柳州','桂林','梧州','北海','防城港','钦州','贵港','玉林','百色','贺州','河池','来宾','崇左'],
 ['海南','海口','三亚','五指山','琼海','儋州','文昌','万宁','东方','定安','屯昌','澄迈','临高','白沙','昌江','乐东','陵水','保亭','琼中'],
 //西南地区
 ['四川','成都','自贡','攀枝花','泸州','德阳','绵阳','广元','遂宁','内江','乐山','南充','宜宾','广安','达州','眉山','雅安','巴中','资阳',"阿坝","甘孜","凉山"],
 ['贵州','贵阳',"六盘水","遵义","安顺","铜仁","毕节","黔西南","黔东南","黔南"],
 ['云南','昆明','曲靖','玉溪',"保山","昭通","丽江","普洱","临沧","文山","红河","西双版纳","楚雄","大理","德宏","怒江","迪庆"],
 ['西藏',"拉萨","昌都","山南","日喀则","那曲","阿里","林芝"],
 //西北地区
 ['陕西','西安','铜川','宝鸡','咸阳','渭南','延安','汉中','榆林','安康','商洛'],
 ['甘肃',"兰州","嘉峪关","金昌","白银","天水","武威","张掖","平凉","酒泉","庆阳","定西","陇南","临夏","甘南"],
 ['青海',"西宁","海东","海北","黄南","海南","果洛","玉树","海西"],
 ['宁夏','银川',"石嘴山","吴忠","固原","中卫"],
 ['新疆','乌鲁木齐',"克拉玛依","吐鲁番","哈密","和田","阿克苏","喀什","克孜勒苏柯尔克孜","巴音郭楞蒙古","昌吉","博尔塔拉蒙古","伊犁哈萨克","塔城","阿勒泰"],
 //港澳台
 ['香港'],
 ['澳门'],
 ['台湾',"台北","高雄","基隆","台中","台南","新竹","嘉义"]
 ];
function initcitySel(){
	//创建节点
	var citySelhtml='<div class="citySel">'+
	'<div class="citySel_wrap"></div>'+
	'<div class="citySel_con">'+
		'<div class="popHead">所在城市<button class="btn_cancel iconfont icon-icon27"></button></div>'+
		'<div class="citysel_tle"><span class="new_prov"></span><span class="new_city"></span></div>'+
		'<div class="citySel_body">'+
			'<div class="provPicker"><ul class="pickerList"></ul></div>'+
			'<div class="cityPicker"><ul class="pickerList"></ul></div>'+
		'</div>'+
	'</div>'+
	'</div>';
	var citySel = $(citySelhtml);
	$('body').append(citySel)
	for( var a=0; a<china.length; a++){
		$('.provPicker ul').append('<li><span class="vamSpan"></span><i></i></li>')
		$('.provPicker li').eq(a).find('i').text(china[a][0])
	}
		
	//显示
	$('#citychange').live('click',function(){
		$('#citychange em').addClass('blue')
		$('.citySel_wrap').show().siblings('.citySel_con').stop().animate({'bottom':'0'})
		
		//数据
		var old_prov = $('#province').text()
		var old_city = $('#city').text()
		if( old_prov==''){
			$('.new_prov').text('请选择').addClass('blue')
		}else{
			if( old_city=='' ){			
				$('.new_prov').text(old_prov).addClass('blue')
				var liName = $('.provPicker li')
				for( var c=0; c<liName.length; c++ ){
					if( liName.eq(c).text()==old_prov ){
						liName.eq(c).addClass('blue')
					}
				}
				var old_num = $('.provPicker').find('.blue').index()
				var old_h = $('.provPicker').find('.blue').height()
				var old_sclH = old_num * old_h
				$('.cityPicker').css({'left':'100%'}).siblings('.provPicker').css({'left':'0'}).children().scrollTop(old_sclH)							
			}else{			
				$('.new_prov').text(old_prov)
				var liName = $('.provPicker li')
				for( var c=0; c<liName.length; c++ ){
					if( liName.eq(c).text()==old_prov ){
						liName.eq(c).addClass('blue')
					}
				}
				$('.provPicker').css({'left':'-100%'})
				
				var this_eq = $('.provPicker .blue').index()
				var citySum = china[this_eq].length
				fn_cityList(citySum,this_eq)
				$('.cityPicker').css({'left':'0'})
				
				$('.new_city').text(old_city).addClass('blue')
				var liName = $('.cityPicker li')
				for( var c=0; c<liName.length; c++ ){
					if( liName.eq(c).text()==old_city ){
						liName.eq(c).addClass('blue')
					}
				}
				var old_num = $('.cityPicker').find('.blue').index()
				var old_h = $('.cityPicker').find('.blue').height()
				var old_sclH = old_num * old_h
				$('.cityPicker').children().scrollTop(old_sclH)			
				
			}
		}
	})
	
	//隐藏
	$('.btn_cancel,.citySel_wrap').live('click',function() {
		hide_citySel()
	})

	//隐藏
	function hide_citySel(){
		$('.citySel_wrap').hide().siblings('.citySel_con').stop().animate({'bottom':'-50%'}).find('.cityPicker li').remove()
		$('.new_city').text('')
		$('.citySel_con .blue').removeClass('blue')
		$('#citychange em').removeClass('blue')
	}

	//选择省份
	$('.provPicker li').live('click',function(){
		var this_prov = $(this).find('i').text()
		var this_eq = $(this).index()
		$(this).addClass('blue').siblings().removeClass('blue')
		var citySum = china[this_eq].length
		if( citySum==1 ){
			$('#province').text(this_prov).removeClass('blue')
			$('.new_city,#city').text('')
			hide_citySel()
		}else{
			$('.new_prov').text(this_prov).removeClass('blue')
			$('.provPicker').stop().animate({'left':'-100%'},300)
			fn_cityList(citySum,this_eq)		
			$('.new_city').text('请选择').addClass('blue')
			$('.cityPicker').stop().animate({'left':'0'},300)
		}
	})

	//选择城市
	$('.cityPicker li').live('click',function(){
		var this_prov = $('.new_prov').text()
		var this_city = $(this).find('i').text()
		$(this).addClass('blue').siblings().removeClass('blue')
		$('#province').text(this_prov).siblings('#city').text(this_city).removeClass('blue')
		hide_citySel()
	})
	
	$('.new_city').live('click',function(){
		fn_swipe('cityPicker','-100%','new_city')
	})
	$('.new_prov').live('click',function(){
		fn_swipe('provPicker','100%','new_prov')
	})	
	
	//左右滑动事件
	bindEvent('provPicker',touchMoveFunc_l);
	bindEvent('cityPicker',touchMoveFunc_r);
}

//创建城市节点
function fn_cityList(part1,part2){
	for( var b=1; b<part1; b++){
		$('.cityPicker ul').append('<li><span class="vamSpan"></span><i></i></li>')
		$('.cityPicker li').eq(b-1).find('i').text(china[part2][b])
	}
}

//省份/城市列表左右滑动
function fn_swipe(part1,part2,part3){
	var _left = parseInt($('.'+part1+'').css('left'))
	if( _left!=0 && $('.new_prov').text()!='' && $('.new_city').text()!='' ){
		$('.'+part1+'').stop().animate({'left':'0'},300).siblings().stop().animate({'left':part2},300)
		$('.'+part3+'').addClass('blue').siblings().removeClass('blue')
		var old_num = $('.'+part1+'').find('.blue').index()
		var old_h = $('.'+part1+'').find('.blue').height()
		var old_sclH = old_num * old_h
		$('.'+part1+'').children().scrollTop(old_sclH)			
	}
}

/*-----------省份城市列表左右滑动--------------*/
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
//	e.preventDefault(); //阻止触摸时浏览器的缩放、滚动条滚动等  
	var touch = e.touches[0]; //获取第一个触点  
	var x = Number(touch.pageX); //页面触点X坐标  
	var y = Number(touch.pageY); //页面触点Y坐标  
	
	var move_x = x - startX;  // <0是往左
	var move_y = y - startY;  // <0是往上
	
	//判断滑动方向
	if ( move_x<0 && move_y<5 && move_y>-5 ) {  //往左滑动 上下滑动小于5  相当于在provPicker页面点击new_city
		fn_swipe('cityPicker','-100%','new_city')
	}
}  
 
//touchmove事件，这个事件无法获取坐标  
function touchMoveFunc_r(e) {  
//	e.preventDefault(); //阻止触摸时浏览器的缩放、滚动条滚动等  
	var touch = e.touches[0]; //获取第一个触点  

	var x = Number(touch.pageX); //页面触点X坐标  
	var y = Number(touch.pageY); //页面触点Y坐标  
	
	var move_x = x - startX;  // <0是往左
	var move_y = y - startY;  // <0是往上
	
	//判断滑动方向
	if ( move_x>0 && move_y<5 && move_y>-5 ) {  //往右滑动 上下滑动小于5  相当于在cityPicker页面点击new_prov
		fn_swipe('provPicker','100%','new_prov')
	} 			
}  

//绑定事件  
function bindEvent(part1,part2) {  
	$('.'+part1+'')[0].addEventListener('touchstart', touchSatrtFunc, false);  
	$('.'+part1+'')[0].addEventListener('touchmove', part2, false);  
}