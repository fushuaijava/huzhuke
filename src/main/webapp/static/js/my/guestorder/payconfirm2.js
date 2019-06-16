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
			var data={"id":$("#guestorderId").val()};
			
			$('#submitbtn').click(function(){
					$.ajax({
						type : 'POST',
						url : projectpath + "/app/guestorder/prepay",
						data : data,
						success : function(result) {
							// 目前暂且定义添加成功就返回交流中心
							if (result.meta.rescode == 0) {
								//msgbox.alert('做客订单已经创建成功！请您尽快完成支付');
								setTimeout(function() {
									location.href = projectpath
											+ '/static/htm/trip.html';
								}, 2000)
							} else {
								msgbox.alert(result.meta.msg);
							}
						}
					});
				
			});

		});