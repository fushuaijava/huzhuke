var projectpath = location.protocol + '//' + location.host + '/zuoke';
require.config({
	baseUrl : projectpath + "/static/js",
	paths : {
		jquery : 'lib/jquery',
		juicer : 'lib/juicermin',
		dateutil : 'lib/dateutil',
		houseajax : 'my/house/houseajax',
		msgbox : 'module/msgbox'

	}
});
require([ 'jquery', 'houseajax', 'msgbox', 'juicer', 'dateutil' ],
		function($, houseajax, msgbox) {
		
			$('.help .tipTxt .iconfont').click(function(e) {

				$(this).closest('.tipTxt').hide();
			});
			var userproid=houseajax.getParameterByName('userproid');
			$("#userproid").val(userproid);
			if (userproid!=null&&userproid>0) {
				houseajax.userhousedetailajax(houseajax.getParameterByName('id'),'housedetailtpl','housedetaildiv',userproid);
			}else{
				houseajax.housedetailajax(houseajax.getParameterByName('id'),'housedetailtpl','housedetaildiv');

			}
			var calendar = new lCalendar();
			calendar.init({
				'trigger': '#guestStartTime',
				'type': 'date'
			});
			var calendar2 = new lCalendar();
			calendar2.init({
				'trigger': '#guestEndTime',
				'type': 'date'
			});
			var data={"houseId":houseajax.getParameterByName('id'),"guestStartTime":$("#guestStartTime").val(),"guestEndTime":$("#guestEndTime").val()};
			
			/*$('#submitbtn').click(function(){
//				if ($("#hascoin").val()=="yes") {
					$.ajax({
						type : 'POST',
						url : projectpath + "/app/guestorder/create",
						data : data,
						success : function(result) {
							// 目前暂且定义添加成功就返回交流中心
							if (result.meta.rescode == 0) {
								//msgbox.alert('做客订单已经创建成功！请您尽快完成支付');
								setTimeout(function() {
									location.href = projectpath
											+ '/static/htm/payconfirm2.html';
								}, 2000)
							} else {
								msgbox.alert(result.meta.msg);
							}
						}
					});
				}else{
					msgbox.alert('您的金币余额不足，请您充值。');
					setTimeout(function() {
						location.href = projectpath
						+ '/static/htm/recharge2.html';
					}, 2000);
				}
			});*/
			// 详情页面数据获取
			
			
		});