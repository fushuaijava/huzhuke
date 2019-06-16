var projectpath=location.protocol+'//'+location.host+'/zuoke';
require.config({
	baseUrl: "../js",
    paths : {
        jquery: 'lib/jquery',
        juicer:'lib/juicermin',
        msgbox:'module/msgbox'
    }
})
require(['jquery','msgbox','juicer'], function($,msgbox) {
	
	// 模板绘制
	var juicerview = function(data, tplId, parentId) {
		var tpl = document.getElementById(tplId).innerHTML;
		var html = juicer(tpl, data);
		$('#' + parentId).append(html);
	};
	var coinpagelist = function(pageid, tplId, parentId) {
		var myDate = new Date();
		var dateString = myDate.getTime();
		$.ajax({
			type : "POST", // 提交方式
			url : projectpath + "/app/product/page/1",// 路径
			data : {
				"start" : pageid
			},// 数据，这里使用的是Json格式进行传输
			success : function(result) {// 返回数据根据结果进行相应的处理
				juicerview(result.page, tplId, parentId);
				$('.rechargeGold ul li').first().addClass('current');
				$('.rechargeGold p span').html(result.page.content[0].nowMoney+'元');
				rechargeinit();
			}
		});
	};
	var vippagelist = function(pageid, tplId, parentId) {
		var myDate = new Date();
		var dateString = myDate.getTime();
		$.ajax({
			type : "POST", // 提交方式
			url : projectpath + "/app/product/page/2",// 路径
			data : {
				"start" : pageid
			},// 数据，这里使用的是Json格式进行传输
			success : function(result) {// 返回数据根据结果进行相应的处理
				juicerview(result.page, tplId, parentId);
				rechargeinit();
			}
		});
	};
	coinpagelist(0,'cointpl','coinparentul');
	vippagelist(0,'viptpl','vipparentul');
	//金币充值数据
	/*var aGoldNum = ['500','600','800','1000','1500','2000','2500','3000'];
	var aGoldCost = ['200','300','400','500','600','700','800','900'];
	for(var i=0; i<aGoldNum.length; i++){
		var goldNumLi = $('<li willCost="'+aGoldCost[i]+'"><span>'+aGoldNum[i]+'币</span></li>');		//把充值价格存在自定义的属性willCost内
		$('.rechargeGold ul').append(goldNumLi);
	};
	$('.rechargeGold ul li').first().addClass('current');
	$('.rechargeGold p span').html(aGoldCost[0]+'元');*/
	
	/*//VIP充值数据
	var aVIPNum = ['1月','2月','3月','4月','5月','6月','7月','8月'];
	var aVIPCost = ['300','400','500','600','700','800','900','1000'];
	for(var i=0; i<aVIPNum.length; i++){
		var vipNumLi = $('<li willCost="'+aVIPCost[i]+'"><span>'+aVIPNum[i]+'</span></li>');
		$('.rechargeVIP ul').append(vipNumLi);
	};*/

	//选择充值
	function rechargeinit(){
		$('.selRight li').on('click',function(){
			$(this).addClass('current').siblings().removeClass('current').closest('.rechargeM').siblings('.rechargeM').find('li').removeClass('current');
			$(this).closest('.rechargeM').find('.opSelted').removeClass('dnIm').closest('.rechargeM').siblings('.rechargeM').find('.opSelted').addClass('dnIm');
			var myCost = $(this).attr('willCost')+'元';
			$(this).closest('.rechargeM').find('p span').addClass('red').html(myCost).closest('.rechargeM').siblings('.rechargeM').find('p span').removeClass('red').html('0元');;
		});
		$('.paybtn').on('click',function(){
			var currentselect=$('.selRight .current');
			var data={'productId':currentselect.data('id'),'orderType':currentselect.data('productType')}
			createorder(data,1);
		});
		$('.paybtn2').on('click',function(){
			var currentselect=$('.selRight .current');
			var data={'productId':currentselect.data('id'),'orderType':currentselect.data('productType')}
			createorder(data,2);
		});
	}
	function createorder(data,paytype) {
		$.ajax({
			type : "POST", // 提交方式
			url : projectpath + "/app/order/add",// 路径
			data : data,// 数据，这里使用的是Json格式进行传输
			success : function(result) {// 返回数据根据结果进行相应的处理
				if(result.meta.rescode==0){
					$('#orderid').val(result.tOrder.id);
					document.myForm.action = projectpath + "/app/pay/pay";
					if(paytype==2){
						document.myForm.action = projectpath + "/app/wxpay/pay";
					}
					document.myForm.submit();
				}else{
					msgbox.alert(result.meta.msg);
				}
			}
		});
	}
	
});	