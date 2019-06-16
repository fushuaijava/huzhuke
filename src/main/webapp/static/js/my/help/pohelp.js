var projectpath=location.protocol+'//'+location.host+'/zuoke';
require.config({
	baseUrl: projectpath+"/static/js",
    paths : {
        jquery: 'lib/jquery',
        msgbox:'module/msgbox'
    }
});
require(['jquery','msgbox'], function($,msgbox) {
	
	//光标出现在文字最后
	$('.perInfoEditLi input').live('click',function(){
		var myVal = $(this).val();
		$(this).val('').focus().val(myVal);
	});

	$('.blueBtn').click(function(){
		var helpInfo=$('#helpInfo').val();
		var place=$('#place').val();
		var mobile=$('#mobile').val();
		var coin=$('#coin').val();
		add(helpInfo,place,mobile,coin);
	});	
	var add=function(helpInfo,place,mobile,coin){
		 $.ajax({  
            type : "POST",  //提交方式  
            url : projectpath+"/app/helpinfo/add",//路径 
            data : {  
                "helpInfo" : helpInfo,
                "place":place,
                "mobile":mobile,
                "coin":coin
            },//数据，这里使用的是Json格式进行传输  
            success : function(data) {//返回数据根据结果进行相应的处理  
            	if(data.meta.rescode==0){
            		msgbox.alert('求助消息已经发布，请耐心等待哦！');
            		setTimeout(function () {
			            location.href=projectpath+'/static/htm/help.html';
			          },2000)
            	}else{
            		msgbox.alert(data.meta.msg);
            	}
            }  
        });
	};
});	