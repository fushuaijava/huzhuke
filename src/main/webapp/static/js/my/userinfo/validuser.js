var projectpath = location.protocol + '//' + location.host + '/zuoke';
require.config({
	baseUrl : projectpath + "/static/js",
	shim : {
		'plupload' : {
			exports : 'plupload'
		},
		'cookie' : [ 'jquery' ]
	},
	paths : {
		jquery : 'lib/jquery',
		msgbox : 'module/msgbox',
		juicer : 'lib/juicermin'
	}
})
require([ 'jquery', 'msgbox', 'juicer'  ], function($,msgbox) {
	//上传图片路径
	var photopath = function(path) {
		
		return projectpath+path;
	};
	juicer.register('photopath', photopath);
	// 模板绘制
	var juicerview = function(data, tplId, parentId) {
		var tpl = document.getElementById(tplId).innerHTML;
		var html = juicer(tpl, data);
		$('#' + parentId).append(html);
	};
	$.ajax({
		type : 'POST',
		url : projectpath + '/app/userinfo/page',
		success : function(result) {
			if (result.meta.rescode == 0) {
				juicerview(result.page, 'validusertpl', 'validuserdiv');
				$('.passbtn').click(function() {
					var data={"id":$(this).data("id"),"isValidInfo":$(this).data("state")};
					validInfo(data);
				});
			} else {
				msgbox.alert(result.meta.msg);
			}
		}
	});
	function validInfo(data){
		 
		 $.ajax({
				type : 'POST',
				data:data,
				url : projectpath + '/app/userinfo/valid/userinfo',
				success : function(result) {
					// 目前暂且定义添加成功就返回交流中心
					if (result.meta.rescode == 0) {
						msgbox.alert("处理成功");
					} else {
						msgbox.alert(result.meta.msg);
					}
				}
			});
	}
});