package com.youhujia.solar.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shawn on 12/05/2017.
 */
public class QRCodeUtils {
    public static final String JPG_FORMAT = "jpg";
    public static final String PNG_FORMAT = "png";
    public static final String JPEG_FORMAT = "jpeg";
    public static final String DEFAULT_FORMAT = JPG_FORMAT;
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    private static String IMAGE_FORMAT = DEFAULT_FORMAT;

    private QRCodeUtils() {
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }


    public static BitMatrix getBitMatrix(String content, int width, int height, String format) throws WriterException {
        if (format != null && !"".equals(format)) {
            IMAGE_FORMAT = format;
        }
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        return bitMatrix;
    }


    /**
     * 将生成的二维码写入流
     *
     * @param matrix {@See getBitMatrix(String content, int width, int height)}
     * @param stream 要写入的流
     * @throws IOException
     */
    public static void writeToStream(BitMatrix matrix, OutputStream stream)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, IMAGE_FORMAT, stream)) {
            throw new IOException("Could not write an image of format " + IMAGE_FORMAT);
        }
    }

    /**
     * 得到二维码图片流的base64加密字符串
     *
     * @param matrix {@See getBitMatrix(String content, int width, int height)}
     * @return
     * @throws IOException
     */
    public static String getBase64ImageStream(BitMatrix matrix) throws IOException {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        writeToStream(matrix, bao);
        return new BASE64Encoder().encode(bao.toByteArray());
    }

    public static String getBase64ImagePre() {
        StringBuilder sb = new StringBuilder("data:image/");
        sb.append(IMAGE_FORMAT).append(";base64,");
        return sb.toString();
    }

    /**
     * 入口方法，得到img src的base64加密属性
     *
     * @param content 二维码内容
     * @param width   二维码宽度
     * @param height  二维码高度
     * @param format  二维码图片格式
     * @return
     * @throws IOException
     * @throws WriterException
     */
    public static String getImageBase64Src(String content, int width, int height, String format) throws IOException, WriterException {
        BitMatrix matrix = getBitMatrix(content, width, height, format);
        String src = getBase64ImagePre() + getBase64ImageStream(matrix);
        return src.replace("\n", "").replace("\r", "");
    }
}
