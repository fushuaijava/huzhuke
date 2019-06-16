//自定义alert
define(['jquery'],function($){
  //自定义 confirm by@cxw
  var confirm =function (txt,callBack){
    if(!document.getElementById('confirm_box')){
      $('body').append('<div id="confirm_box" class="secWin_con" style="top:0%;"><div class="confirm-header">信息</div><p class="confirm-tips"></p><div class="secWin_btn"><button class="btn-cal btn">取消</button><button class="btn-ok btn">确认</button></div></div>');
    };
    if(!document.getElementById('maskUi')){
      $('body').append('<div id="maskUi" class="secWrap"></div>');
    };
    var $h=$('body').height();
    var toback=false;
    $('#maskUi').height($h);
    $('#maskUi').show();
    //阻止遮罩层冒泡到页面
    $('#maskUi').on('touchstart',function(event){
      event.preventDefault();
    });

    $('#confirm_box .confirm-tips').text(txt);
    var _ml=-($('#confirm_box').outerWidth()/2);
    var _mt=-($('#confirm_box').outerHeight()/2);
    //$('#confirm_box').css({'marginTop':_mt,'marginLeft':_ml});
    $('#confirm_box').css({'marginTop':_mt});
    $('#confirm_box').animate({top:'50%'});
    $('#confirm_box button').click(function(){$('#confirm_box').animate({top:'-100%'},function(){$('#confirm_box').remove();$('#maskUi').hide()});});

    $('#confirm_box .btn-ok').click(function(){callBack()});
    $('#confirm_box .btn-ok').click(function(){toback=false;});
    return toback;
  }

  //类似alert
  var alert = function(txt,type){
    //txt 提示文本，type:只有一个值'todo'，用于需要用户点击确认
    var alertEle;
    if(type!='todo'){
      alertEle='<div class="myAlert" style="position:fixed; left:50%; top:50%; z-index:99999; width:80%; text-align:center; -ms-box-sizing:border-box; -o-box-sizing:border-box; -webkit-box-sizing:border-box; box-sizing:border-box; display:none; line-height:1.5; padding:20px .857em; background:#333;background:rgba(0,0,0,.8); color:#fff; border-radius:.5em;">'+txt+'</div>';
    }else{
      alertEle='<div class="myAlert" style="position:fixed; left:50%; top:50%; z-index:99999; width:80%; text-align:center; -ms-box-sizing:border-box; -o-box-sizing:border-box; -webkit-box-sizing:border-box; box-sizing:border-box; display:none; line-height:1.5; padding:20px .857em; background:#333;background:rgba(0,0,0,.8); color:#fff; border-radius:.5em;">'+txt+'<div class="js_alert_ok" style="padding-top:10px;margin-top:10px; border-top:1px solid #9c9c9c; text-align:center;color:#fff">确定</div></div>';
    }
    $('.myAlert').remove();
    $('body').append(alertEle);
    var _left=$('.myAlert').outerWidth()/2;
    var _top=$('.myAlert').outerHeight()/2;
    $('.myAlert').css({'marginLeft':-_left,'marginTop':-_top});
    if(type!='todo'){
      $('.myAlert').stop(true,true).fadeIn(200).delay(1500).fadeOut(200);
    }else{
      $('.myAlert').stop(true,true).fadeIn(200).delay(3000).fadeOut(200);
    }
    $(document).on('click','.js_alert_ok',function(){
      $('.myAlert').stop(true,true).fadeOut(200);
    });
  }



  return {
    alert:alert,
    confirm:confirm
  }
});
