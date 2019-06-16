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
		plupload : 'lib/plupload/plupload.full.min',
		baseurl : 'module/baseurl',
		upload : 'module/upload',
		cookie : 'lib/jquery.cookie',
		msgbox : 'module/msgbox',
		//cityselect:'lib/cityselect',
		juicer : 'lib/juicermin',
		citySel:'my/citySel'
	}
})
require([ 'jquery', 'baseurl', 'upload', 'plupload', 'cookie',
		'msgbox', 'juicer','citySel'  ], function($, baseurl, upload, plupload,
		cookie, msgbox,citySel) {
	initcitySel();

	//光标出现在文字最后
	$('.perInfoEditLi input').live('click',function(){
		var myVal = $(this).val();
		$(this).val('').focus().val(myVal);
	});

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
	// 选择 性别
	function chooseOne(part1, part2) {
		$('.' + part1 + '').click(function() {
			$(this).find('.' + part2 + '').addClass('blue');
			$('.secWrap').show().siblings('.selOp').slideDown();
		});
		$('.selOp li').click(function() {
			var myOp = $(this).text();
			if (myOp !== '取消') {
				$('.' + part2 + '').text(myOp).removeClass('blue');
				$('.secWrap').hide().siblings('.selOp').slideUp();
			}
			;
		});
		$('.twoSel li').click(function() {
			var myOp = $(this).text();
			if (myOp == '取消') {
				$('.' + part2 + '').removeClass('blue');
				$('.secWrap').hide().siblings().slideUp();
			}
			;
		});
		$('.secWrap').click(function() {
			$('.' + part2 + '').removeClass('blue');
			$('.secWrap').hide().siblings().slideUp();
		});
	}

	$.ajax({
		type : 'POST',
		url : projectpath + '/app/userinfo/myinfo',
		success : function(result) {
			if (result.meta.rescode == 0) {
				juicerview(result, 'userinfotpl', 'userinfodiv');
				// 上传图片
				upload.desUpload('.fileWrap');
				// 性别
				chooseOne('sexEd', 'mySex');
				$('#uisubmitbtn').click(function() {
					submituser();
				});
				//initcityclick();
			} else {
				msgbox.alert(result.meta.msg);
			}
		}
	});
	function trimStr(str){return str.replace(/(^\s*)|(\s*$)/g,"");}
	function submituser(){
		 var headimg=$("#headimgdiv").find('.has-upload-item').find('img');
		 var headimgpath=headimg.data("compressfilename");
		 var mysex=trimStr($("#mysex").text());
		 var nickname=trimStr($("#nickname").val());
		 var gender=1;
		 if(mysex=="女"){
			 gender=2;
		 }	 
		 $.ajax({
				type : 'POST',
				data:{"id":$("#userid").val(),"headimg":headimgpath,"gender":gender,"nickname":nickname,"province":trimStr($("#province").html()),"city":trimStr($("#city").html())},
				url : projectpath + '/app/user/update',
				success : function(result) {
					// 目前暂且定义添加成功就返回交流中心
					if (result.meta.rescode == 0) {
						if ($("#hasvalidinfo").val()=="yes") {
							
						}else{
							msgbox.alert("信息修改成功");
							setTimeout(function() {
								location.href = projectpath
										+ '/static/htm/loginIn.html';
							}, 2000);
						}
					} else {
						msgbox.alert(result.meta.msg);
					}
				}
			});
		 if ($("#hasvalidinfo").val()=="yes") {
			 var cardImg1=$("#cardImg1").find('img').data("filename");
			 var cardImg2=$("#cardImg2").find('img').data("filename");
			 var data={"cardImg1":cardImg1,"cardImg2":cardImg2,"name":$("#name").val(),"idcard":$("#idcard").val(),"email":$("#email").val()};
			 if($("#tUserinfoid")!=null){
				 
				 data["id"] = $("#tUserinfoid").val();
			 }
			 $.ajax({
					type : 'POST',
					data:data,
					url : projectpath + '/app/userinfo/save',
					success : function(result) {
						// 目前暂且定义添加成功就返回首页
						if (result.meta.rescode == 0) {
							msgbox.alert(result.meta.msg);
						} else {
							msgbox.alert(result.meta.msg);
						}
						setTimeout(function() {
							location.href = projectpath
							+ '/static/htm/loginIn.html';
						}, 2000);
					}
				});
		}
	}
	
	//地市选择
	/*function initcityclick(){
		$("#citychange").click(function(e) {
			BindProvince();
			$('#procitydiv').show();
		});
		
		$("#ddlProvince").change(function(e) {
			selectMoreCity(this);
			var provinceval= this.options[this.selectedIndex].value;
			$('#province').html(provinceval);
		});
		$("#ddlCity").change(function(e) {
			selectMoreCity(this);
			var cityval= this.options[this.selectedIndex].value;
			$('#city').html(cityval);
		});
	}*/
	// 选择 是否可以借宿
	// chooseOne('myHotelLi','myHotel');

	// 删除 重新上传 图片
	/*
	 * $('.unloadPt img').live('click',function(){
	 * $('.secWrap').show().siblings('.reloadPt').slideDown(); });
	 */
});