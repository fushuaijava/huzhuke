define(['jquery','baseurl','module/msgbox','cookie','userlogin','lib/juicer-min'],function($,baseurl,msgbox,cookie,userlogin){
  var out,userTpl,init;

  out=function(){
    $.ajax({
      type:'POST',
      url:baseurl.root+'app/login/loginout',
      success:function(data){
        if(data.meta.rescode==0){
          msgbox.alert('已退出');
			$('.pageIndex').stop().animate({'left':'0'},200).find('.header,.footer,.indexWrap').stop().animate({'left':'0'},200);
			$('.pageLeft').stop().animate({'left':'-75%'},200);
			$('.indexWrap').hide();
          $.cookie('userid',null);
          $.cookie('password',null);
          userlogin.loginTpl(data);
        }else{
          msgbox.alert(data.meta.msg);
        }
      }

    });
  }

  init = function(){
    $(document).on('click','.js_loginOut',function(){
      msgbox.confirm('确定退出吗',function(){
        out();
      });
    });
  }

  return{
    init:init
  }
});
