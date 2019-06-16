package com.zuoke.common.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Repository;
@Repository
public class MD5Util {
    public static String MD5(String str){  
        String pwd = null;  
        try {  
            // 生成一个MD5加密计算摘要  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            // 计算md5函数  
            md.update(str.getBytes());  
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符  
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值  
            pwd = new BigInteger(1, md.digest()).toString(16);  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        return pwd;  
    }
    public static void main(String[] args) {
        System.out.println(MD5Util.MD5("2YvBhFHviNBJSCS9X3tguqiTeUwB61e970ba516ebceb376552b5197b33b9201814636317761021"));
        System.out.println(MD5Util.MD5("18789142117"+"123"+"1456372670047"));
    }
}
