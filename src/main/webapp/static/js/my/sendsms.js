define(['jquery','md5','../module/msgbox'],function ($,md5,msgbox){
	var sendSms=function(userid){
		var myDate=new Date();
		var dateString=myDate.getTime();
		var key="123"
		var md5String=$.md5(userid + key + dateString);
		 $.ajax({
             type : "POST",  //提交方式
             url : "../../app/sms/send",//路径
             data : {
                 "userid" : userid,
                 "password":"1234",
                 "key":key,
                 "logintime":dateString,
                 "md5key":md5String
             },//数据，这里使用的是Json格式进行传输
             success : function(result) {//返回数据根据结果进行相应的处理
               msgbox.alert(result.meta.msg);
             }
         });
	};
	return {sendSms:sendSms}
});
