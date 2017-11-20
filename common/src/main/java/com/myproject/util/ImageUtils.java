package com.myproject.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by Administrator on 2016-7-2.
 */
public class ImageUtils {
    static BASE64Encoder encoder = new BASE64Encoder();
    static BASE64Decoder decoder = new BASE64Decoder();

    public static void main(String[] args) {
        System.out.println(getImageBinary("d://timg.jpg"));
//        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));

//        String a ="";
        try{
//            StringBuffer sb = new StringBuffer(buf.readLine());
//            base64StringToImage(getImageBinary("d://timg.jpg"),"d://QQ.bmp",200,180);
//            base64StringToImage(sb,"d://QQ.bmp",200,180);
//            System.out.print(a);
        }catch(Exception e)
        {
            System.out.println(e);
            System.exit(0);
        }


    }

    public static String getImageBinary(String path){
//        // TODO: 2016-7-2 回头再细调类型 路径
//        File f = new File("c://20090709442.jpg");
        File f = new File(path);
        BufferedImage bi;
        try {
            bi = ImageIO.read(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);
            byte[] bytes = baos.toByteArray();

            return encoder.encodeBuffer(bytes).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void base64StringToImage(String base64String,String savePath,int photoHeadWidth,int photoHeadHigh){
        try {
            byte[] bytes1 = decoder.decodeBuffer(base64String);

            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            BufferedImage bi1 =ImageIO.read(bais);
            //File w2 = new File(savePath);//可以是jpg,png,gif格式
            //File w2 = new File("c://QQ.bmp");//可以是jpg,png,gif格式
            //ImageIO.write(bi1, "jpg", w2);//不管输出什么格式图片，此处不需改动
            compressImage(bi1, savePath, photoHeadWidth, photoHeadHigh);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void base64StringToImage(StringBuffer sb,String savePath,int photoHeadWidth,int photoHeadHigh) throws Exception{
        try {
            byte[] bytes1 = decoder.decodeBuffer(sb.toString());

            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            BufferedImage bi1 =ImageIO.read(bais);
            //File w2 = new File(savePath);//可以是jpg,png,gif格式
            //File w2 = new File("c://QQ.bmp");//可以是jpg,png,gif格式
            //ImageIO.write(bi1, "jpg", w2);//不管输出什么格式图片，此处不需改动
            compressImage(bi1, savePath, photoHeadWidth, photoHeadHigh);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }
    /** 
     * * 将图片按照指定的图片尺寸压缩 * * @param srcImgPath :源图片路径 * @param outImgPath * 
     * :输出的压缩图片的路径 * @param new_w * :压缩后的图片宽 * @param new_h * :压缩后的图片高 
     */  
    public static void compressImage(BufferedImage bi1, String outImgPath,  
            int new_w, int new_h) {  
        disposeImage(bi1, outImgPath, new_w, new_h);  
    }  
    
    
    /** 
     * * 指定长或者宽的最大值来压缩图片 * * @param srcImgPath * :源图片路径 * @param outImgPath * 
     * :输出的压缩图片的路径 * @param maxLength * :长或者宽的最大值 
     */ 
    public static void compressImage(BufferedImage bi1, String outImgPath,  
            int maxLength) {  
        // 得到图片  
        BufferedImage src = bi1;
        if (null != src) {  
            int old_w = src.getWidth();  
            // 得到源图宽  
            int old_h = src.getHeight();  
            // 得到源图长  
            int new_w = 0;  
            // 新图的宽  
            int new_h = 0;  
            // 新图的长  
            // 根据图片尺寸压缩比得到新图的尺寸  
            if (old_w > old_h) {  
                // 图片要缩放的比例  
                new_w = maxLength;  
                new_h = (int) Math.round(old_h * ((float) maxLength / old_w));  
            } else {  
                new_w = (int) Math.round(old_w * ((float) maxLength / old_h));  
                new_h = maxLength;  
            }  
            disposeImage(src, outImgPath, new_w, new_h);  
        }  
    }
  
    /** * 处理图片 * * @param src * @param outImgPath * @param new_w * @param new_h */  
    private synchronized static void disposeImage(BufferedImage src,  
            String outImgPath, int new_w, int new_h) {  
        // 得到图片  
        int old_w = src.getWidth();  
        // 得到源图宽  
        int old_h = src.getHeight();  
        // 得到源图长  
        BufferedImage newImg = null;  
        // 判断输入图片的类型  
        switch (src.getType()) {  
        case 13:  
            // png,gifnewImg = new BufferedImage(new_w, new_h,  
            // BufferedImage.TYPE_4BYTE_ABGR);  
            break;  
        default:  
            newImg = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);  
            break;  
        }  
        Graphics2D g = newImg.createGraphics();  
        // 从原图上取颜色绘制新图  
        g.drawImage(src, 0, 0, old_w, old_h, null);  
        g.dispose();  
        // 根据图片尺寸压缩比得到新图的尺寸  
        newImg.getGraphics().drawImage(  
                src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0,  
                null);  
        // 调用方法输出图片文件  
        OutImage(outImgPath, newImg);  
    }  
  
    /** 
     * * 将图片文件输出到指定的路径，并可设定压缩质量 * * @param outImgPath * @param newImg * @param 
     * per 
     */  
    private static void OutImage(String outImgPath, BufferedImage newImg) {  
        // 判断输出的文件夹路径是否存在，不存在则创建  
        File file = new File(outImgPath);  
        if (!file.getParentFile().exists()) {  
            file.getParentFile().mkdirs();  
        }// 输出到文件流  
        try {  
            /*ImageIO.write(newImg, outImgPath.substring(outImgPath  
                    .lastIndexOf(".") + 1), new File(outImgPath)); */
        	ImageIO.write(newImg, "jpg", file);
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}
