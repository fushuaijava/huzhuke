define(['jquery','plupload','baseurl'],function($,plupload,baseurl){
  var desUpload;
  //console.log(plupload)
  //指定位置的上传
  desUpload = function(ele){
    var $ele=$(ele);

    $ele.each(function(index){
      var $this=$(this);
      $this.attr('id','js_up_input'+index)
      var _id='js_up_input'+index;
      var $list=$this.parent();
      var uploader = new plupload.Uploader({
        browse_button : _id, //触发文件选择对话框的按钮，为那个元素id
        url : baseurl.root+'app/file/houseuploadauto',//服务器端的上传页面地址
        resize: {
        	 width: 400,
        	  height: 300,
        	  crop: true,
        	  quality: 90,
        	  preserve_headers: false
        	}
        //flash_swf_url : 'js/Moxie.swf', //swf文件，当需要使用swf方式进行上传时需要配置该参数
        //silverlight_xap_url : 'js/Moxie.xap' //silverlight文件，当需要使用silverlight方式进行上传时需要配置该参数
      });
      //绑定各种事件，并在事件监听函数中做你想做的事
      uploader.bind('FilesAdded',function(uploader,files){
          //每个事件监听函数都会传入一些很有用的参数，
          //我们可以利用这些参数提供的信息来做比如更新UI，提示上传进度等操作
          var file=files[0] //单张上传
          if($this.find('.file').find('.has-upload-item').length==0){
            $this.find('.file').append('<div class="has-upload-item" id="' + file.id + '"><img src="../images/loading.gif" alt="图片上传中" /><i class="state loading-gif">图片上传中</i></div>');
          }else{
            $this.find('.has-upload-item').attr('id',file.id).find('.state').show();
          }

          //开始上传
          uploader.start();
          console.log('已经开始上传')
      });
      uploader.bind('UploadProgress',function(uploader,file){
          //上传中
          //每个事件监听函数都会传入一些很有用的参数，
          //我们可以利用这些参数提供的信息来做比如更新UI，提示上传进度等操作
      });
      uploader.bind('FileUploaded',function(uploader,file,responseObject){
          //上传完成
          var data=eval('('+responseObject.response+')');
        console.log(baseurl.root+data.compressfilename);
          $( '#'+file.id ).find('img').attr('src',baseurl.root+data.compressfilename).attr({'data-compressfilename':data.compressfilename,'data-filename':data.filename});
          
          //$('#'+file.id.replace('js_up_input',''))
          var pindex=$( '#'+file.id ).parent().data("pindex");
          $("#housemodel").data("picture"+pindex,data.filename);
          $("#housemodel").data("compressImage"+pindex,data.compressfilename);
      });

      //在实例对象上调用init()方法进行初始化
      uploader.init();
    });
  };

  return{
    desUpload:desUpload
  }

});



/*
function picUpload(ele,flag,callback){
 var backFun=null;
 callback?backFun=callback:backFun=null;
 var $ele=$(ele);
 $ele.each(function(index){
   if($(this).find('.js_webupload').length==0){
     $(this).append('<div class="js_webupload webupload-cover" id="js_up_input'+index+'"></div>');
   }else{
     $(this).find('.js_webupload').attr('id','js_up_input'+index)
   }
 });

 $('.js_webupload').each(function(){
   var _id=$(this).attr('id');
   var $list=$(this).parent();
    //上传配置
   var uploader = new plupload.Uploader({
     runtimes : 'html5,flash,silverlight,html4',
     browse_button : _id, // you can pass an id...
     //container: document.getElementById('upload-pick'), // ... or DOM Element itself
     url : hostBaseUrl+'/wx/file/uploadauto/',
     flash_swf_url : hostBaseUrl+'/public/js/plupload/Moxie.swf',
     //silverlight_xap_url : '../js/Moxie.xap',

     filters : {
       max_file_size : '5mb',
       mime_types: [
         {title : "Image files", extensions : "jpg,gif,png"}
         //{title : "Zip files", extensions : "zip"}
       ]
     },

     init: {
       PostInit: function() {
       },

       FilesAdded: function(up, files) {
         plupload.each(files, function(file) {
           if( $list.find('.has-upload-item').length==0){
             $list.prepend( '<div class="has-upload-item" id="' + file.id + '">' +
               '<img src="" />'+
               '<i class="state loading-gif"></i>' +
               '</div>' );
             $list.append('<i class="icon-close"></i>');
           }else{
             $list.find('.has-upload-item').attr('id',file.id).find('.state').show();
             if($list.find('.icon-close').length==0){
               $list.append('<i class="icon-close"></i>');
             }
           }
           $list.find('.photo-upload-place').hide();
         });
           //开始上传
           uploader.start();
         },

         UploadProgress: function(up, file) {
           //上传中
           //$(file.id).find('.status').addClass('.icon-ok').delay(500).removeClass('.icon-ok').hide();
           //console.log(file.percent);
         },
         FileUploaded:function(uploader,file,responseObject){
          //上传完成后，将取得的图片3种大小格式插入数据库，获取当前图片ID。
          var data=eval('('+responseObject.response+')');
          //下面是上传完成后的回调函数
          var thisFather=$( '#'+file.id ).parents(ele);
          if(thisFather.find('.imgForm').length==0){
            thisFather.append('<form class="js_upload_getid imgForm" action="'+hostBaseUrl+'/wx/wxphoto/add" method="post"><input type="hidden" name="url" /><input type="hidden" name="compressurl" /><input type="hidden" name="cuturl" /></form>')

          }
          var form=thisFather.find('form');
          var fomrUrl=form.attr('action');
          var url=data.filename;
          var cuturl=data.cutfilename;
          var compressurl=data.compressfilename;
            //console.log(url+'~~~~'+cuturl+'~~~~~AAA'+compressurl)
            var time=myFormatDate(new Date(data.uploadtime),'nyns');
            thisFather.find('.icon-close').hide();
            thisFather.find('.del-upload').show();
            $( '#'+file.id ).addClass('upload-state-done');
            $( '#'+file.id ).find('img').attr('src',hostBaseUrl+cuturl);
            $( '#'+file.id ).find('.state').hide();
            thisFather.find('[name="url"]').val(url);
            thisFather.find('[name="cuturl"]').val(cuturl);
            thisFather.find('[name="compressurl"]').val(compressurl);
            thisFather.find('.js_upload_time').text(time).show();
              thisFather.find('.js_del_img').show();  //显示删除操作块
              thisFather.find('.js_show_del').show(); //显示删除按钮
              if(flag==true){
                 //将图片url传到后台，获取id
                 $.ajax({
                   type:'POST',
                   url:fomrUrl,
                   data:form.serialize(),
                   dataType:'json',
                   success:function(d){
                     thisFather.attr('data-id',d.wxPhoto.id);
                     //下面是回调函数
                     backFun=eval('('+backFun+')');
                     //console.log(backFun);
                     if(backFun!=null){
                      backFun(thisFather);
                    }
                  }
                });
               }else{
               //下面是回调函数
               backFun=eval('('+backFun+')');
               //console.log(backFun);
               if(backFun!=null){
                backFun(thisFather);
              }
            }



          },

          Error: function(up, err) {
           console.log(document.createTextNode("\nError #" + err.code + ": " + err.message));
         }
       }
     });
    //初始化
    uploader.init();
    //取消上传
    $(document).on('tap', '.upload-item .icon-close', function() {
     var toremove;
     var id=$(this).parents('.upload-item').find('.has-upload-item').attr('id');
     for(var i in uploader.files){
       if(uploader.files[i].id === id){
         toremove = i;
       }
     }
     uploader.files.splice(toremove, 1);
      uploader.refresh(); //好像没生效，这应该用于刷新上传队列
      uploader.stop();//所以只能设置下停止，来阻止当前图片上传.
      $(this).hide();
      $(this).parents('.upload-item').find('.state').hide();
      my_alert('上传已取消');
    });

 });
}
 */
