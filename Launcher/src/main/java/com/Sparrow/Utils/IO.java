package com.Sparrow.Utils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

public class IO {
    public static String guessCharset(File file) {
        try {
            return guessCharset(new FileInputStream(file), true);
        } catch (FileNotFoundException e) {
            return "UTF-8";
        }
    }

    public static String guessCharset(InputStream inputStream, boolean isAutoClose) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        BufferedInputStream bis = null;
        try {
            boolean checked = false;
            bis = new BufferedInputStream(inputStream);
            bis.mark(50 * 1024 * 1024);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1)
                return charset;

            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE
                    && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF
                    && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8";
                checked = true;
            }
            bis.reset();
            if (!checked) {
                // int len = 0;
                //int loc = 0;

                while ((read = bis.read()) != -1) {
                    //loc++;
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
                            // (0x80
                            // - 0xBF),也可能在GB编码内
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
                // System.out.println( loc + " " + Integer.toHexString( read )
                // );
            }
        } catch (Exception e) {
            return "UTF-8";
        } finally {
            if (isAutoClose) {
                try {
                    bis.close();
                } catch (Exception e) {
                }
            }
        }

        return charset;
    }

    /**
     * 把指定文件或目录转换成指定的编码
     *
     * @param fileName
     *            要转换的文件
     * @param fromCharsetName
     *            源文件的编码
     * @param toCharsetName
     *            要转换的编码
     * @throws Exception
     */
    public static void convert(String fileName, String fromCharsetName,
                               String toCharsetName) throws Exception {
        convert(new File(fileName), fromCharsetName, toCharsetName, null);
    }

    /**
     * 把指定文件或目录转换成指定的编码
     *
     * @param file
     *            要转换的文件或目录
     * @param fromCharsetName
     *            源文件的编码
     * @param toCharsetName
     *            要转换的编码
     * @throws Exception
     */
    public static void convert(File file, String fromCharsetName,
                               String toCharsetName) throws IOException {
        convert(file, fromCharsetName, toCharsetName, null);
    }

    /**
     * 把指定文件或目录转换成指定的编码
     *
     * @param fileName
     *            要转换的文件或目录
     * @param fromCharsetName
     *            源文件的编码
     * @param toCharsetName
     *            要转换的编码
     * @param filter
     *            文件名过滤器
     * @throws Exception
     */
    public static void convert(String fileName, String fromCharsetName,
                               String toCharsetName, FilenameFilter filter) throws IOException {
        convert(new File(fileName), fromCharsetName, toCharsetName, filter);
    }

    /**
     * 把指定文件或目录转换成指定的编码
     *
     * @param file
     *            要转换的文件或目录
     * @param fromCharsetName
     *            源文件的编码
     * @param toCharsetName
     *            要转换的编码
     * @param filter
     *            文件名过滤器
     * @throws Exception
     */
    public static void convert(File file, String fromCharsetName,
                               String toCharsetName, FilenameFilter filter) throws IOException {
        if (file.isDirectory()) {
            File[] fileList = null;
            if (filter == null) {
                fileList = file.listFiles();
            } else {
                fileList = file.listFiles(filter);
            }
            for (File f : fileList) {
                convert(f, fromCharsetName, toCharsetName, filter);
            }
        } else {
            if (filter == null
                    || filter.accept(file.getParentFile(), file.getName())) {
                String fileContent = getFileContentFromCharset(file,
                        fromCharsetName);
                saveFile2Charset(file, toCharsetName, fileContent);
            }
        }
    }

    /**
     * 以指定编码方式读取文件，返回文件内容
     *
     * @param file
     *            要转换的文件
     * @param fromCharsetName
     *            源文件的编码
     * @return
     * @throws Exception
     */
    public static String getFileContentFromCharset(File file,
                                                   String fromCharsetName) throws IOException {
        if (!Charset.isSupported(fromCharsetName)) {
            throw new UnsupportedCharsetException(fromCharsetName);
        }
        InputStream inputStream = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(inputStream,
                fromCharsetName);
        char[] chs = new char[(int) file.length()];
        reader.read(chs);
        String str = new String(chs).trim();
        reader.close();
        return str;
    }

    /**
     * 以指定编码方式写文本文件，存在会覆盖
     *
     * @param file
     *            要写入的文件
     * @param toCharsetName
     *            要转换的编码
     * @param content
     *            文件内容
     * @throws Exception
     */
    public static void saveFile2Charset(File file, String toCharsetName,
                                        String content) throws IOException {
        if (!Charset.isSupported(toCharsetName)) {
            throw new UnsupportedCharsetException(toCharsetName);
        }
        OutputStream outputStream = new FileOutputStream(file);
        OutputStreamWriter outWrite = new OutputStreamWriter(outputStream,
                toCharsetName);
        outWrite.write(content);
        outWrite.close();
    }
}
