define(['jquery','baseurl','msgbox'],function($,baseurl,msgbox){
  var add,update;

  //添加交流 bbs帖子
  add = function($form){
      var formDate={};
      var _content=$form.find('[name="content"]').val();
      var _imgele=$form.find('.unloadPt ').find('img');
      for(var i=0;i<_imgele.length;i++){
          (function (i) {
              formDate['bigImage'+(i+1)]=$(_imgele[i]).attr('data-filename');
              formDate['compressImage'+(i+1)]=$(_imgele[i]).attr('data-compressfilename');
          })(i);
      }
      formDate['content']=_content;
      console.log(formDate);
    $.ajax({
      type:'POST',
      url:baseurl.root+'app/invitation/add',
      data:formDate,
      success:function(result){
        //目前暂且定义添加成功就返回交流中心
    	  if (result.meta.rescode == 0) {
				msgbox.alert('贴子已经发布');
				 setTimeout(function () {
			            location.href=baseurl.root+'/static/htm/commun.html';
			          },2000)
			} else {
				msgbox.alert(result.meta.msg);
			}
      }
    });
  }

  update = function(){


  }


  return{
    add:add,
    update:update
  }
});
