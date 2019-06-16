var projectpath = location.protocol + '//' + location.host + '/zuoke';
var housesaveurl = projectpath + '/app/house/add';
require.config({
	urlArgs : "bust=" + (new Date()).getTime(),
	baseUrl : projectpath + '/static/js',
	shim : {
		'plupload' : {
			exports : 'plupload'
		},
		'cookie' : [ 'jquery' ]
	},
	paths : {
		jquery : 'lib/jquery',
		plupload : 'lib/plupload/plupload.full.min',
		// houseform: 'my/house/houseform',
		baseurl : 'module/baseurl',
		upload : 'module/uploadhouse',
		cookie : 'lib/jquery.cookie',
		juicer : 'lib/juicermin',
		//cityselect:'lib/cityselect',
		msgbox : 'module/msgbox',
		citySel:'my/citySel'
	}
});

require(
		[ 'jquery', 'baseurl', 'upload', 'plupload', 'cookie','msgbox', 'juicer','citySel'],
		function($, baseurl, upload, plupload, cookie, msgbox,citySel) {
			
			initcitySel();
			
			//光标出现在文字最后
			$('.perInfoEditLi input').live('click',function(){
				var myVal = $(this).val();
				$(this).val('').focus().val(myVal);
			});

			function trimStr(str){return str.replace(/(^\s*)|(\s*$)/g,"");}
			// 上传图片路径
			var photopath = function(path) {

				return projectpath + path;
			};
			juicer.register('photopath', photopath);
			var juicerview = function(data, tplId, parentId) {
				var tpl = document.getElementById(tplId).innerHTML;
				var html = juicer(tpl, data);
				$('#' + parentId).append(html);
			};
			var userid = $.cookie('userid'), password = $.cookie('password');
			
			$.ajax({
						type : 'POST',
						url : projectpath + '/app/house/myhome',
						success : function(result) {
							// 目前暂且定义添加成功就返回交流中心
							// juicerview(result, 'houseedittpl',
							// 'houseEditdiv');
							upload.desUpload('.fileWrap');
							var house = result.house;
							if (result.meta.rescode == 0 && house != null) {
								$("#housemodel").data("id", house.id);
								$("#housemodel").data("picture1",
										house.picture1);
								$("#housemodel").data("picture2",
										house.picture2);
								$("#housemodel").data("picture3",
										house.picture3);
								$("#housemodel").data("picture4",
										house.picture4);
								$("#housemodel").data("picture5",
										house.picture5);
								$("#housemodel").data("compressImage1",
										house.compressImage1);
								$("#housemodel").data("compressImage2",
										house.compressImage2);
								$("#housemodel").data("compressImage3",
										house.compressImage3);
								$("#housemodel").data("compressImage4",
										house.compressImage4);
								$("#housemodel").data("compressImage5",
										house.compressImage5);
								$("#housemodel").data("canlive", house.canlive);
								$("#housemodel").data("province",
										house.province);
								$("#housemodel").data("city",
										house.city);
								$("p").css("color", "red");
								// $("#compressImage1").attr('src',
								// house.compressImage1==null?null:projectpath+house.compressImage1);
								if (house.compressImage1 != null
										&& house.compressImage1 != "") {

									document.getElementById('compressImage1').style.backgroundImage = "url("
											+ projectpath
											+ house.compressImage1 + ")";
								}
								if (house.compressImage2 != null
										&& house.compressImage2 != "") {

									document.getElementById('compressImage2').style.backgroundImage = "url("
											+ projectpath
											+ house.compressImage2 + ")";
								}
								if (house.compressImage3 != null
										&& house.compressImage3 != "") {

									document.getElementById('compressImage3').style.backgroundImage = "url("
											+ projectpath
											+ house.compressImage3 + ")";
								}
								if (house.compressImage4 != null
										&& house.compressImage1 != "") {

									document.getElementById('compressImage4').style.backgroundImage = "url("
											+ projectpath
											+ house.compressImage4 + ")";
								}
								if (house.compressImage5 != null
										&& house.compressImage5 != "") {

									document.getElementById('compressImage5').style.backgroundImage = "url("
											+ projectpath
											+ house.compressImage1 + ")";
								}
								$("#smalldes").val(house.smalldes);
								$("#nearby").val(house.nearby);
								$("#cate").val(house.cate);
								$("#province").html(house.province);
								$("#city").html(house.city);
								$("#address").val(house.address);
								if (house.canlive == 1) {

									$("#canlivetxt").html("是");
								} else {
									$("#canlivetxt").html("否 ");
								}
								$("#minCoin").val(house.minCoin);
								$("#adviseCoin").val(house.adviseCoin);
								housesaveurl = projectpath
										+ '/app/house/update';
							} else {
								
								//msgbox.alert(result.meta.msg);
							}
						}
					});
			// 上传图片

			console.log(111)
			function saveHouse() {
				var canlivetxt=trimStr($("#canlivetxt").html());
				 if (canlivetxt=="是") {
					 $("#housemodel").data("canlive",1)
				}else{
					$("#housemodel").data("canlive",0)
				}
				var data = {
					"id" : $("#housemodel").data("id"),
					"picture1" : $("#housemodel").data("picture1"),
					"picture2" : $("#housemodel").data("picture2"),
					"picture3" : $("#housemodel").data("picture3"),
					"picture4" : $("#housemodel").data("picture4"),
					"picture5" : $("#housemodel").data("picture5"),
					"compressImage1" : $("#housemodel").data("compressImage1"),
					"compressImage2" : $("#housemodel").data("compressImage2"),
					"compressImage3" : $("#housemodel").data("compressImage3"),
					"compressImage4" : $("#housemodel").data("compressImage4"),
					"compressImage5" : $("#housemodel").data("compressImage5"),
					"canlive" : $("#housemodel").data("canlive"),
					"province" : trimStr($("#province").html()),
					"smalldes" : $("#smalldes").val(),
					"nearby" : $("#nearby").val(),
					"cate" : $("#cate").val(),
					"city" :trimStr($("#city").html()),
					"address" : $("#address").val(),
					"minCoin" : $("#minCoin").val(),
					"adviseCoin" : $("#adviseCoin").val()
				};
				debugger;

				/*
				 * for ( var key in housemodeljson) { try { if (key !=
				 * "__proto__") {
				 * 
				 * var value = eval("housemodeljson['" + key + "']"); data['"' +
				 * key + '"'] = value; } alert(key + "_" + value); } catch (e) { } }
				 */
				console.log(data);
				console.log(projectpath);
				/*
				 * var url=projectpath+'/app/house/update'; if(isadd){
				 * url=projectpath+'/app/house/add'; }
				 */
				// debugger;
				$.ajax({
					type : 'POST',
					url : housesaveurl,
					data : data,
					success : function(result) {
						// 目前暂且定义添加成功就返回交流中心
						if (result.meta.rescode == 0) {
							msgbox.alert('做客邀请已经发布');
							setTimeout(function() {
								location.href = projectpath
										+ '/static/htm/beGuest.html';
							}, 2000)
						} else {
							msgbox.alert(result.meta.msg);
						}
					}
				});

			}

			// 增加新帖子
			$("#savebtn").click(function(e) {
				saveHouse();
			});
						
			//地市选择
/*			$("#citychange").click(function(e) {
				BindProvince();
				$('#procitydiv').show();
			});
			$("#ddlProvince").change(function(e) {
				selectMoreCity(this);
				var provinceval= this.options[this.selectedIndex].value;
				$('#province').html(provinceval);
				$("#housemodel").data("province",provinceval);
			});
			$("#ddlProvince").change(function(e) {
				selectMoreCity(this);
				var provinceval= this.options[this.selectedIndex].value;
				$('#province').html(provinceval);
				$("#housemodel").data("province",provinceval);
			});
			$("#ddlCity").change(function(e) {
				selectMoreCity(this);
				var cityval= this.options[this.selectedIndex].value;
				$('#city').html(cityval);
				$("#housemodel").data("city",cityval);
			});*/
			
			// 选择 是否可借住
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
			chooseOne('myHotelLi', 'myHotel');
			
		});