define(['jquery','baseurl','lazyload','msgbox','lib/juicer-min'],function($,baseurl,lazyload,msgbox){
	//上传图片路径
	var photopath = function(path) {
		
		return projectpath+path;
	};
	juicer.register('photopath', photopath);
  var bbsPage,init;

  console.log(juicer);
  bbsPage = function(start,limit){
    $.ajax({
      type:'POST',
      url:baseurl.root+'app/invitation/page',
      data:{"start":start,"limit":limit},
      success:function(data){
        //下面使用juicer模板引擎
        if(data.meta.rescode==0 && data.page.content.length>0){
          var data=data.page;
          console.log(data);
          var _timeformat=function(times){
            var _time=new Date(times);
            return _time.getMonth()+1+'-'+_time.getDate()+' '+_time.getHours()+':'+_time.getMinutes();
          }
          juicer.register('time_format', _timeformat); //注册自定义函数
          if(document.getElementById('js_conItem')){
            var tpl=document.getElementById('js_conItem').innerHTML;
            var html = juicer(tpl, data);
            $('.js_conList').append(html);
            //延迟加载图片
            $("img.lazy").lazyload();
            var nextPage=$('.js_conList').parents('body').find('.js_load_more_conList').attr('data-page');
            if(nextPage){
              nextPage=Number(nextPage);
            }else{
              nextPage=0;
            }
            $('.js_conList').parents('body').find('.js_load_more_conList').attr('data-page',nextPage+1)
          }

        }else{
        	if (start>0) {
				msgbox.alert('亲，没有更多数据了，不要再点我了，会疼的！');
			}
        }
        $(".loadmorepage").data("canload","yes");
		$(".waitMore").hide();
      }
    });
  }

  return{
    bbsPage:bbsPage
  }


});
