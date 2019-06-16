package com.zuoke.controller.app.core;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.slf4j.Logger;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.zuoke.common.util.SystemParamUtil;
/**
 * 文件上传，
 * @author fushuai
 * date 2015-12-9
 */
@SuppressWarnings("restriction")
@Controller  
@RequestMapping("file") 
public class UploadController { 
	private Logger logger = LoggerFactory.getLogger(UploadController.class);
	@Autowired
	private  SystemParamUtil systemParam;
	/**
	 * 头像文件上传 
	 * @param file
	 * @param request
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "upload")
    @ResponseBody
    public Map<String, Object> upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request)throws Exception {  
    	Map<String, Object> model = new HashMap<String, Object>();
    	logger.info("进入");
    	if (file.getSize()>5000000) {
    		model.put("error", "maxsize");
    		return  model;
    	}
    	logger.info("进入");
    	String uploaddir=systemParam.getUploaddir();
        String path = request.getSession().getServletContext().getRealPath(uploaddir);  
        String fileName = file.getOriginalFilename(); 
        fileName=fileName.substring(fileName.lastIndexOf("."));
        Random random=new Random();
        fileName = new Date().getTime()+""+random.nextInt(9999)+fileName; 
        System.out.println(path);  
        logger.info("fileName:"+fileName);
        LocalDate localDate=LocalDate.now();
        String dir=localDate.getYear()+"/"+localDate.getMonthValue()+"/"+localDate.getDayOfMonth();
        path=path+"/"+dir;
        File targetFile = new File(path, fileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
        try {  
            file.transferTo(targetFile);  
            try {
				autocut(path+"/"+fileName);
				save(fileName.substring(fileName.lastIndexOf(".")+1),fileName,path,300, 300);
            }  catch (Exception e) {
            	logger.error("保存截图异常",e);
			}
            compressImage(path+"/"+fileName, 10,  1024*1024);
        } catch (Exception e) {  
        	logger.error("打开图片文件异常",e);
        }  
        model.put("filename",uploaddir+"/"+dir+"/"+fileName);
        return  model;
    }
    /**
     * 交流中心文件上传
     * @param file
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "uploadauto")
    @ResponseBody
    public Map<String, Object> uploadauto(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request)throws Exception {  
    	Map<String, Object> model = new HashMap<String, Object>();
    	if (file.getSize()>5000000) {
    		model.put("error", "maxsize");
    		return  model;
    	}
    	String uploaddir=systemParam.getUploaddir();
    	if (StringUtils.isEmpty(uploaddir)) {
    		uploaddir="/upload";
		}
        String path = request.getSession().getServletContext().getRealPath(uploaddir);  
        String fileName = file.getOriginalFilename(); 
        
        fileName=fileName.substring(fileName.lastIndexOf("."));
        Random random=new Random();
        fileName = new Date().getTime()+""+random.nextInt(9999)+fileName; 
        logger.debug("fileName:"+path+fileName);
        LocalDate localDate=LocalDate.now();
        String dir=localDate.getYear()+"/"+localDate.getMonthValue()+"/"+localDate.getDayOfMonth();
        String cutpath=path+"/cut/"+dir;
        String compresspath=path+"/compress/"+dir;
        path=path+"/"+dir;
        File targetFile = new File(path, fileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }
        File cutpathfile = new File(cutpath, fileName);  
        if(!cutpathfile.exists()){  
        	cutpathfile.mkdirs();  
        } 
        File compresspathfile = new File(compresspath);  
        if(!compresspathfile.exists()){  
        	compresspathfile.mkdirs();  
        } 
        try {  
            file.transferTo(targetFile);
//            copyFile(path+"/"+fileName,cutpath+"/"+fileName);
//            copyFile(path+"/"+fileName,compresspath+"/"+fileName);
            try {
				autocut(path+"/"+fileName);
				save(fileName.substring(fileName.lastIndexOf(".")+1),fileName,cutpath,300, 300);
            }  catch (Exception e) {
            	logger.error("保存截图异常",e);
			}
            //复制原图到压缩图
            FileUtils.copyFile(new File(cutpath+"/"+fileName), new File(compresspath+"/"+fileName));
            //压缩图片
            compressImage(compresspath+"/"+fileName, 10,  1024*1024);
        } catch (Exception e) {  
        	logger.error("打开图片文件异常",e);
        }  
        model.put("filename",uploaddir+"/"+dir+"/"+fileName);
        model.put("cutfilename",uploaddir+"/cut/"+dir+"/"+fileName);
        model.put("compressfilename",uploaddir+"/compress/"+dir+"/"+fileName);
        model.put("uploadtime", new Date());
        return  model;
    }
    /**
     * 做客信息图片 上传
     * @param file
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "houseuploadauto")
    @ResponseBody
    public Map<String, Object> houseuploadauto(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request)throws Exception {  
    	Map<String, Object> model = new HashMap<String, Object>();
    	if (file.getSize()>5000000) {
    		model.put("error", "maxsize");
    		return  model;
    	}
    	String uploaddir=systemParam.getUploaddir();
    	if (StringUtils.isEmpty(uploaddir)) {
    		uploaddir="/upload";
		}
        String path = request.getSession().getServletContext().getRealPath(uploaddir);  
        String fileName = file.getOriginalFilename(); 
        
        fileName=fileName.substring(fileName.lastIndexOf("."));
        Random random=new Random();
        fileName = new Date().getTime()+""+random.nextInt(9999)+fileName; 
        logger.debug("fileName:"+path+fileName);
        LocalDate localDate=LocalDate.now();
        String dir=localDate.getYear()+"/"+localDate.getMonthValue()+"/"+localDate.getDayOfMonth();
        String cutpath=path+"/cut/"+dir;
        String compresspath=path+"/compress/"+dir;
        path=path+"/"+dir;
        File targetFile = new File(path, fileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }
        File cutpathfile = new File(cutpath, fileName);  
        if(!cutpathfile.exists()){  
        	cutpathfile.mkdirs();  
        } 
        File compresspathfile = new File(compresspath);  
        if(!compresspathfile.exists()){  
        	compresspathfile.mkdirs();  
        } 
        try {  
            file.transferTo(targetFile);
//            copyFile(path+"/"+fileName,cutpath+"/"+fileName);
//            copyFile(path+"/"+fileName,compresspath+"/"+fileName);
//            try {
//				autocut(path+"/"+fileName);
//				save(fileName.substring(fileName.lastIndexOf(".")+1),fileName,cutpath,300, 300);
//            }  catch (Exception e) {
//            	logger.error("保存截图异常",e);
//			}
            //复制原图到压缩图
            FileUtils.copyFile(new File(path+"/"+fileName), new File(compresspath+"/"+fileName));
            //压缩图片
            compressImage(compresspath+"/"+fileName, 10,  1024*1024);
        } catch (Exception e) {  
        	logger.error("打开图片文件异常",e);
        }  
        model.put("filename",uploaddir+"/"+dir+"/"+fileName);
        //model.put("cutfilename",uploaddir+"/cut/"+dir+"/"+fileName);
        model.put("compressfilename",uploaddir+"/compress/"+dir+"/"+fileName);
        model.put("uploadtime", new Date());
        return  model;
    } 
    @ExceptionHandler(Exception.class)         
    public Map<String, Object> handleException(Exception ex,HttpServletRequest request) {       
         Map<String, Object> model = new HashMap<String, Object>();  
         if (ex instanceof MaxUploadSizeExceededException){  
                        model.put("error", "文件应不大于 "+  
                       getFileKB(((MaxUploadSizeExceededException)ex).getMaxUploadSize()));  
                     } else{  
                        model.put("error", "不知错误: " + ex.getMessage());  
                    } 
         logger.error("文件大小",ex);
         return model;  
    }    
      
    private String getFileKB(long byteFile){  
        if(byteFile==0)  
           return "0KB";  
        long kb=1024;  
        return ""+byteFile/kb+"KB";  
    }  
    @SuppressWarnings("unused")
	private String getFileMB(long byteFile){  
        if(byteFile==0)  
           return "0MB";  
        long mb=1024*1024;  
        return ""+byteFile/mb+"MB";  
    } 
    /** 
     * 对imageFullPath 指定的文件按要求的质量quality进行压缩(gif将不会进行压缩)。quality的范围是(0-100) 
     *  
     * @param imageFullPath 
     *            文件的绝对路径 
     * @param quality 
     *            压缩的质量,范围是(0-100) 
     * @param maxFileSize 
     *            文件超过该大小才进行质量有损压缩,单位:byte 
     * @return 文件大小,单位:byte 
     */  
    public  int compressImage(String imageFullPath, int quality,  
            long maxFileSize) {  
        if (StringUtils.isEmpty(imageFullPath) || quality < 0 || quality > 100) {  
            throw new RuntimeException("invalid parameters, src="  
                    + imageFullPath + ",quality=" + quality);  
        }  
        File img = new File(imageFullPath);  
        if (!img.isFile()) {  
            throw new RuntimeException("file not exists, src=" + imageFullPath);  
        }  
        if (img.length() <= maxFileSize) {  
            return (int) img.length();  
        }  
  
        FileOutputStream out = null;  
        try {  
            RenderedImage rendImage = ImageIO.read(new File(imageFullPath));  
            if (rendImage == null || rendImage.getWidth() <= 0  
                    || rendImage.getHeight() <= 0) {  
                throw new RuntimeException("file is not an image, src="  
                        + imageFullPath);  
            }  
            out = new FileOutputStream(img);  
            BufferedImage tag = new BufferedImage(rendImage.getWidth(),  
                    rendImage.getHeight(), BufferedImage.TYPE_INT_RGB);  
            tag.getGraphics().drawImage((Image) rendImage, 0, 0,  
                    rendImage.getWidth(), rendImage.getHeight(), null);  
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);  
            jep.setQuality(quality / 100f, true);  
            encoder.encode(tag, jep);  
        } catch (Exception e) {  
        	logger.error("打开图片文件",e);
            throw new RuntimeException("compressImage fail, src="  
                    + imageFullPath, e);
        } finally {  
            if (out != null) {  
                try {  
                    out.close();  
                } catch (IOException e) { 
                	logger.error(e.getMessage(),e);
                }  
            }  
        }  
        return (int) new File(imageFullPath).length();  
    }  
    /**  
     * 实现图像的等比缩放  
     * @param source  
     * @param targetW  
     * @param targetH  
     * @return  
     */
	@SuppressWarnings("unused")
	private  BufferedImage resize(BufferedImage source, int targetW,
            int targetH) {
        // targetW，targetH分别表示目标长和宽   
        int type = source.getType();
        BufferedImage target = null;
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        // 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放   
        // 则将下面的if else语句注释即可   
//        if (sx < sy) {
//            sx = sy;
//            targetW = (int) (sx * source.getWidth());
//        } else {
//            sy = sx;
//            targetH = (int) (sy * source.getHeight());
//        }
        if (type == BufferedImage.TYPE_CUSTOM) { // handmade   
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW,
                    targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else
            target = new BufferedImage(targetW, targetH, type);
        Graphics2D g = target.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }
    
    private BufferedImage subImg;
    /**
     * 截图
     * @param srcPath
     * @param startX
     * @param startY
     * @param width
     * @param height
     */
    public void cut(String srcPath,int startX,int startY,int width,int height){
        try {
            BufferedImage bufImg = ImageIO.read(new File(srcPath));
            subImg = bufImg.getSubimage(startX, startY, width, height);
            
        } catch (Exception e) {
            logger.error("切取图片大小",e);
        }
    }
    /**
     * 自动截图
     * @param srcPath
     * @param startX
     * @param startY
     * @param width
     * @param height
     */
    public void autocut(String srcPath){
        try {
        	int startX=0;int startY=0;
            BufferedImage bufImg = ImageIO.read(new File(srcPath));
            int size=0;
            int imgwidth=bufImg.getWidth();
            int imgheight=bufImg.getHeight();
            if (imgwidth<imgheight) {
            	size=imgwidth;
            	startY=(imgheight-imgwidth)/2;
			}else {
				size=imgheight;
				startX=(imgwidth-imgheight)/2;
			}
            subImg = bufImg.getSubimage(startX, startY, size, size);
        } catch (Exception e) {
            logger.error("自动切取图片",e);
        }
    }
    /**
     * 保存截图。
     * @param bufImg
     * @param imgType
     * @param tarPath
     */
    public void save(String imgType,String imgName,String tarPath,int width,int height){
        try {/**压缩图片为指定尺寸*/
            if(subImg.getWidth()!=width || subImg.getHeight()!=height){
                BufferedImage tempImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
                tempImg.getGraphics().drawImage(subImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0,null);
                ImageIO.write(tempImg, imgType, new File(tarPath+"/"+imgName));
            }else{
                ImageIO.write(subImg,imgType,new File(tarPath+"/"+imgName));
            }
        } catch (IOException e) {
            logger.error("压缩图片",e);
        }
    }
    /**   
     * 复制单个文件   
     * @param oldPath String 原文件路径 如：c:/fqf.txt   
     * @param newPath String 复制后路径 如：f:/fqf.txt   
     * @return boolean   
     */    
    public void copyFile(String oldPath, String newPath) {     
      try {     
        @SuppressWarnings("unused")
		int bytesum = 0;     
        int byteread = 0;     
        File oldfile = new File(oldPath);     
        if (oldfile.exists()) { //文件存在时     
          InputStream inStream = new FileInputStream(oldPath); //读入原文件     
          @SuppressWarnings("resource")
		FileOutputStream fs = new FileOutputStream(newPath);     
          byte[] buffer = new byte[1444];     
//          int length;     
          while ( (byteread = inStream.read(buffer)) != -1) {     
            bytesum += byteread; //字节数 文件大小      
            fs.write(buffer, 0, byteread);     
          }     
          inStream.close();     
        }     
      }     
      catch (Exception e) {     
        logger.error("复制单个文件操作出错",e);
      
      }     
      
    } 
}  