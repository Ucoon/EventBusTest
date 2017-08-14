package com.my.eventbustest.qrcode;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

/**
 * Created by ZongJie on 2017/8/14.
 */

public class QRCodeUtils {
    //宽度值，影响中间图片大小
    private static int IMAGE_HALFWIDTH = 50;

    /**
     * 生成二维码
     * @param text 需要生成二维码的文字、网址
     * @param size 需要生成二维码的大小
     * @return
     */
    public static Bitmap createQRCode(String text, int size){
        Hashtable<EncodeHintType, String> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //QR_Code:二维码码制之一
        try {
            BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, size, size, hints);
            int[] pixels = new int[size * size];
            for (int y = 0; y < size; y++){
                for (int x = 0; x < size; x++) {
                    if(bitMatrix.get(x, y)){
                        pixels[y * size + x] = 0xff000000;
                    }else{
                        pixels[y * size + x] = 0xffffffff;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, size, 0, 0, size, size);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成带logo的二维码，logo默认为二维码的1/5
     * @param text 需要生成二维码的文字、网址
     * @param size 需要生成二维码的大小
     * @param bitmap logo
     * @return
     */
    public static Bitmap createQRCodeWithLogo(String text, int size, Bitmap bitmap){
        IMAGE_HALFWIDTH = size/10;
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //设置容错级别
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        try {
            BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, size, size, hints);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            int halfW = width/2;
            int halfH = height/2;

            Matrix m = new Matrix();
            float sx = (float) 2 * IMAGE_HALFWIDTH / bitmap.getWidth();
            float sy = (float) 2 * IMAGE_HALFWIDTH / bitmap.getHeight();
            m.setScale(sx, sy);

            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, false);
            int[] pixels = new int[size * size];
            for (int y = 0; y < size; y++){
                for (int x = 0; x < size; x++) {
                    if(x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
                            && y > halfH - IMAGE_HALFWIDTH && y < halfH + IMAGE_HALFWIDTH){
                        pixels[y * width + x] = bitmap.getPixel(x - halfW + IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);
                    }else{
                        if(bitMatrix.get(x, y)){
                            pixels[y * size + x] = 0xff000000;
                        }else {
                            pixels[y * size + x] = 0xffffffff;
                        }
                    }
                }
            }
            Bitmap bitmapTemp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            bitmapTemp.setPixels(pixels, 0, size, 0, 0, size, size);
            return bitmapTemp;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}
