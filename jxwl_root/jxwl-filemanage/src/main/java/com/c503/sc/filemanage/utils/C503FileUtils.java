/**
 * 文件名：FileUtils.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年8月19日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.filemanage.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.c503.sc.utils.common.NumberContant;

/**
 * 〈一句话功能简述〉文件操作：文件夹、文件创建；文件、文件夹删除；文件复制 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2015年8月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class C503FileUtils {
    /**
     * 
     * 〈一句话功能简述〉创建目录 〈功能详细描述〉
     * 
     * @param path 件目录路径
     * @return 返回文件
     * @see [类、类#方法、类#成员]
     */
    public static File createDir(String path) {
        File dirFile = null;
        
        dirFile = new File(path);
        if (!(dirFile.exists()) && !(dirFile.isDirectory())) {
            dirFile.mkdirs();
        }
        
        return dirFile;
    }
    
    /**
     * 〈一句话功能简述〉创建文件 〈功能详细描述〉
     * 
     * @param path 文件路径
     * @return 返回文件
     * @throws Exception 系统异常
     * @see [类#方法]
     */
    public static File createFile(String path)
        throws Exception {
        File file = new File(path);
        file.createNewFile();
        
        return file;
    }
    
    /**
     * 删除文件，可以是单个文件或文件夹
     * 
     * @param filePath 待删除的文件路劲
     * @return 文件删除成功返回true，否则返回false
     * @see [类#方法]
     */
    public static boolean delete(String filePath)
    
    {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
        else {
            if (file.isFile()) {
                return deleteFile(filePath);
            }
            else {
                return deleteDirectory(filePath);
            }
        }
    }
    
    /**
     * 〈一句话功能简述〉 删除单个文件〈功能详细描述〉
     * 
     * @param path 文件路径
     * @return 成功true，反之false
     * @see [类、类#方法、类#成员]
     */
    private static boolean deleteFile(String path) {
        boolean result = false;
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            file.delete();
            result = true;
        }
        
        return result;
    }
    
    /**
     * 〈一句话功能简述〉删除目录（文件夹）以及目录下的文件，只删除文件夹 〈功能详细描述〉
     * 
     * @param dirPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     * @see [类、类#方法、类#成员]
     */
    private static boolean deleteDirectory(String dirPath)
    
    {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符。
        if (!dirPath.endsWith(File.separator)) {
            dirPath = dirPath + File.separator;
        }
        File dirFile = new File(dirPath);
        // 如果dir对应的文件不存在，或者不是一个文件夹，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        boolean successFlag = false;
        // 删除文件夹下所有文件（包括子目录）
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (flag) {
            successFlag = dirFile.delete();
        }
        // 删除当前目录
        return successFlag;
    }
    
    /**
     * 〈一句话功能简述〉复制单个文件， 如果目标文件存在，覆盖。 〈功能详细描述〉
     * 
     * @param srcFileName 待复制的文件名，含路径（如："D:/abc/abc.txt"）
     * @param destFileName 目标文件名，含路劲（如："D:/temp/abc.txt"）
     * @return 拷贝成功true，反之false
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    public static boolean copyFile(String srcFileName, String destFileName)
        throws Exception {
        return copyFile(srcFileName, destFileName, true);
    }
    
    /**
     * 〈一句话功能简述〉复制单个文件 〈功能详细描述〉
     * 
     * @param srcFileName 复制的文件名
     * @param destFileName 目标文件名
     * @param overlay 如果目标文件存在，是否覆盖
     * @return 如果复制成功，则返回true，否则返回false
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    private static boolean copyFile(String srcFileName, String destFileName,
        boolean overlay)
        throws Exception {
        
        boolean successFlag = false;
        // 判断原文件是否存在
        File srcFile = new File(srcFileName);
        if (srcFile.exists() && srcFile.isFile()) {
            File destFile = new File(destFileName);
            if (destFile.exists() && overlay) {
                delete(destFileName);
                successFlag = copyFile(srcFile, destFile);
            }
        }
        return successFlag;
    }
    
    /**
     * 〈一句话功能简述〉文件拷贝
     * 〈功能详细描述〉
     * 
     * @param srcFile 源文件
     * @param destFile 目标文件
     * @return 成功返回true，否则返回false
     * @see [类、类#方法、类#成员]
     */
    private static boolean copyFile(File srcFile, File destFile) {
        // 准备复制文件
        int byteread = 0;
        InputStream in = null;
        OutputStream out = null;
        boolean successFlag = false;
        try {
            // 打开原文件
            in = new FileInputStream(srcFile);
            // 打开连接到目标文件的输出流
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[NumberContant.ONE_ZERO_TWO_FOUR];
            // 一次读取1024个字节，当byteread为-1时表示文件已经读完
            while ((byteread = in.read(buffer)) != -1) {
                // 将读取的字节写入输出流
                out.write(buffer, 0, byteread);
            }
            successFlag = true;
        }
        catch (Exception e) {
            successFlag = false;
        }
        finally {
            // 关闭输入输出流，注意先关闭输出流，再关闭输入流
            if (out != null) {
                closeFileStream(out);
            }
            if (in != null) {
                closeFileStream(in);
            }
        }
        return successFlag;
    }
    
    /**
     * 〈一句话功能简述〉关闭文件流
     * 〈功能详细描述〉
     * 
     * @param ioStream 文件流
     * @see [类、类#方法、类#成员]
     */
    private static void closeFileStream(Closeable ioStream) {
        try {
            ioStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * 〈一句话功能简述〉拷贝文件到指定目录下 〈功能详细描述〉
     * TODO 文件流的关闭方式不正确
     * 
     * @param bytes 字节
     * @param destFilePath 目录路径
     * @return 如果复制成功，则返回true，否则返回false
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    public static boolean copyFile(byte[] bytes, String destFilePath)
        throws Exception {
        OutputStream outs = new FileOutputStream(destFilePath);
        outs.write(bytes);
        
        outs.close();
        return false;
    }
    
    /**
     * 〈一句话功能简述〉文件拷贝
     * 〈功能详细描述〉
     * 
     * @param bytes byte[]
     * @param file File
     * @return true/false
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    public static boolean copyFile(byte[] bytes, File file)
        throws Exception {
        OutputStream outs = null;
        
        try {
            outs = new FileOutputStream(file);
            outs.write(bytes);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (outs != null) {
                closeFileStream(outs);
            }
        }
        return false;
    }
    
    /**
     * 〈一句话功能简述〉 如果文件或者目录的路径不以文件分隔符结尾，自动添加文件分隔符。 〈功能详细描述〉
     * 
     * @param path 文件或者目录的路径
     * @return 返回文件的全路径
     * @see [类#方法]
     */
    public static String addSeparator(String path) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符。
        if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        return path;
    }
    
    /**
     * 〈一句话功能简述〉截取文件名中以第一个"_"开始的字符串 〈功能详细描述〉
     * 
     * @param targetFile 目标文件
     * @return 修改后的返回文件名
     * @see [类、类#方法、类#成员]
     */
    public static String modifyFileName(File targetFile) {
        
        String orgFileName =
            targetFile.getName()
                .substring(targetFile.getName().indexOf("_") + 1);
        return orgFileName;
    }
    
    /**
     * 〈一句话功能简述〉截取文件名后缀 〈功能详细描述〉
     * 
     * @param orgFileName 原始文件名
     * @param ch 单个字符
     * @return 返回文件后缀名
     * @see [类、类#方法、类#成员]
     */
    public static String subString(String orgFileName, String ch) {
        String lastName =
            orgFileName.substring(orgFileName.lastIndexOf(".") + 1);
        
        return lastName;
    }
    
}
