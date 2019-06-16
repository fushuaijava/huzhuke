require.config({
	baseUrl: "static/js",
	shim:{
		'md5': ['jquery']
  },
    paths: {
        jquery: 'lib/jquery',
        md5:'lib/jQuery.md5',
    }
});
 
require(['jquery','md5'], function($) {
	var login=function(userid,password){
debugger;
 		var myDate=new Date();
 		var dateString=myDate.getTime();
 		var key="123"
 		var md5String=$.md5(userid + key + dateString);
		 $.ajax({
              type : "POST",  //提交方式
              url :location.protocol+'//'+location.host+'/zuoke/app/login/login',
              data : {
                  "userid" : userid,
                  "password":password,
                  "key":key,
                  "logintime":dateString,
                  "md5key":md5String
              },//数据，这里使用的是Json格式进行传输
              success : function(result) {//返回数据根据结果进行相应的处理
            	  if(result.meta.rescode==0){
            		  alert('登录成功');
            	  }
              }
          });
 	};
 	login('18976575635','148d146af1bbe17b119cf1e6e19b318d')
});