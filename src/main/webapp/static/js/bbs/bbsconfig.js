var projectpath=location.protocol+'//'+location.host+'/zuoke';
require.config({
  urlArgs: "bust=" + (new Date()).getTime(),
  baseUrl:'../js',
  shim:{
    'plupload':{
      exports: 'plupload'
    },
    'cookie': ['jquery'],
    'lazyload':['jquery']
  },
  paths:{
    jquery: 'lib/jquery',
    lazyload: 'lib/lazyload',
    plupload: 'lib/plupload/plupload.full.min',
    bbsform: 'bbs/bbsform',
    bbslist: 'bbs/bbslist',
    baseurl: 'module/baseurl',
    upload: 'module/upload',
    cookie:'lib/jquery.cookie',
    msgbox:'module/msgbox'
  }
});

require(['jquery','bbslist','baseurl','bbsform','upload','plupload','cookie','msgbox','lazyload'],function($,bbslist,baseurl,bbsform,upload,plupload,cookie,msgbox){
    var userid=$.cookie('userid'),
        password=$.cookie('password');


  //上传图片
  upload.desUpload('.fileWrap');


  //进入列表页load第一页
    bbslist.bbsPage(0,5);

  /*$(document).on('click','.js_load_more_conList', function () {
    var dataPage=$(this).attr('data-page');
    bbslist.bbsPage(dataPage,5);
  });*/
    $('.loadmorepage').click(function(e) {
		if($(".loadmorepage").data("canload")=="yes"){
			$(".waitMore").show();
			var pageid=$(".loadmorepage").data("pageid");
			bbslist.bbsPage(pageid+1,5);
			$(".loadmorepage").data("pageid",pageid+1);
			$(".loadmorepage").data("canload","no");
		}else{
			msgbox.alert('亲，不要点那么快，还没反应过来呢，会疼的！');
		}
    });

  //增加新帖子
  $(document).off('submit','.js_add_commun');
  $(document).on('submit','.js_add_commun',function(event){
    event.preventDefault();
    console.log(userid)
    //先判断是否已经登录
      if((userid!='null'||userid!=undefined)  && (password!='null' && password!=undefined)){
          bbsform.add($(this));
      }else{
          msgbox.alert('请先登录');
          setTimeout(function () {
            location.href=baseurl.root+'/static/htm/loginIn.html';
          },200)
      }
  });

});
