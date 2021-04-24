package com.Sparrow.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class imageString {
    private static final String fileformat = "png";
    private static final String fileNameFormat = "yyyy-MM-dd_HH-mm-ss";

    // 根据图片地址将图片转换为字符串类型的数据
    private static BufferedImage getImage(String picture) throws IOException {
        BufferedImage bImage = ImageIO.read(new File(picture));

        return bImage;

    }

// 将BufferImage 转换为字节数组

    public static String imageToString(String picture) throws IOException {
        StringBuffer sb2 = new StringBuffer();

        BufferedImage image1 = getImage(picture);

        byte[] img = getBytes(image1);

        for (int i = 0; i < img.length; i++) {
            if (sb2.length() == 0) {
                sb2.append(img[i]);
            } else {
                sb2.append("," + img[i]);
            }

        }

        return sb2.toString();

    }

// 根据图片地址得到BufferedImage

    private static byte[] getBytes(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", baos);

        return baos.toByteArray();
    }

    public static void stringToImage(String string, String saveDir) {
        if (string.contains(",")) {
// 这里没有自带的那个分割方法，原因是分割速度没有这个快，有人考证在分割字符长度很大的情况下，系统的分割方法容易造成内存溢出。

// 还有subString方法，不知道最新版本的jdk改了源码了么

            String[] imagetemp = split(string, ",");

            byte[] image = new byte[imagetemp.length];

            for (int i = 0; i < imagetemp.length; i++) {
                image[i] = Byte.parseByte(imagetemp[i]);

            }

            saveImage(image, saveDir);

        } else {
// 不能解析格式的字符串

        }

    }

// 将byte[] 转换为BufferedImage

    private static BufferedImage readImage(byte[] bytes) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

        return ImageIO.read(bais);

    }

// 保存图片

    private static String saveImage(byte[] imgages, final String saveDir) {
        try {
            BufferedImage bis = readImage(imgages);

            DateFormat sdf = new SimpleDateFormat(fileNameFormat);

            String fileTime = sdf.format(new Date());

            final String name = fileTime + "_" + "." + fileformat;

            File f = new File(saveDir + name);

            boolean istrue = false;

            if (f.exists()) {
                istrue = ImageIO.write(bis, fileformat, f);

            } else {
                f.mkdirs();

                istrue = ImageIO.write(bis, fileformat, f);

            }

            if (istrue) {
                return name;

            }

        } catch (Exception e) {
        }

        return null;

    }

// 分割字符串

    private static String[] split(String s, String token) {
        if (s == null)

            return null;

        if (token == null || s.length() == 0)

            return new String[]{s};

        int size = 0;

        String[] result = new String[4];

        while (s.length() > 0) {
            int index = s.indexOf(token);

            String splitOne = s;

            if (index > -1) {
                splitOne = s.substring(0, index);

                s = s.substring(index + token.length());

            } else {
                s = "";

            }

            if (size >= result.length) {
                String[] tmp = new String[result.length * 2];

                System.arraycopy(result, 0, tmp, 0, result.length);

                result = tmp;

            }

            if (splitOne.length() > 0) {
                result[size++] = splitOne;

            }

        }

        String[] tmp = result;

        result = new String[size];

        System.arraycopy(tmp, 0, result, 0, size);

        return result;

    }
}
