/**
 * 文件名：CompressFileUtil.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年8月19日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.filemanage.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * 〈一句话功能简述〉压缩指定的文件或目录 解压指定的压缩文件(仅限ZIP格式) 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2015年8月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CompressFileUtil {
    /**
     * 〈一句话功能简述〉将指定文件或者指定目录下的所有文件压缩并生成指定路径的压缩文件. 如果压缩文件的路径或父路径不存在, 将会自动创建.
     * 〈功能详细描述〉
     * 
     * @param srcFile 将要进行压缩的文件或目录
     * @param destFile 最终生成的压缩文件的路径
     * @throws IOException IO异常
     * @see [类、类#方法、类#成员]
     */
    public static void zipFile(String srcFile, String destFile)
        throws IOException {
        zipFile(new File(srcFile), new File(destFile));
    }
    
    /**
     * 〈一句话功能简述〉将指定文件或目录下的所有文件压缩并生成指定路径的压缩文件. 如果压缩文件的路径或父路径不存在, 将会自动创建. 〈功能详细描述〉
     * 
     * @param srcFile 将要进行压缩的文件或目录
     * @param destFile 最终生成的压缩文件的路径
     * @throws IOException IO异常
     * @see [类、类#方法、类#成员]
     */
    private static void zipFile(File srcFile, File destFile)
        throws IOException {
        zipFile(srcFile, FileUtils.openOutputStream(destFile));
    }
    
    /**
     * 〈一句话功能简述〉将指定文件或目录下的所有文件压缩并将流写入指定的输出流中. 〈功能详细描述〉
     * 
     * @param srcFile 将要进行压缩的目录
     * @param outputStream 用于接收压缩产生的文件流的输出流
     * @see [类、类#方法、类#成员]
     */
    private static void zipFile(File srcFile, OutputStream outputStream) {
        zipFile(srcFile, new ZipOutputStream(outputStream));
    }
    
    /**
     * 〈一句话功能简述〉将指定目录下的所有文件压缩并将流写入指定的ZIP输出流中. 〈功能详细描述〉
     * 
     * @param srcFile 将要进行压缩的目录
     * @param zipOut 用于接收压缩产生的文件流的ZIP输出流
     * @see [类、类#方法、类#成员]
     */
    private static void zipFile(File srcFile, ZipOutputStream zipOut) {
        try {
            doZipFile(srcFile, zipOut);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            IOUtils.closeQuietly(zipOut);
        }
    }
    
    /**
     * 〈一句话功能简述〉压缩文件或目录到指定ZipOutputStream 〈功能详细描述〉
     * 
     * @param srcFile 指定文件或者目录
     * @param zipOut 指定ZipOutputStream输出流
     * @throws IOException IO异常
     * @see [类、类#方法、类#成员]
     */
    private static void doZipFile(File srcFile, ZipOutputStream zipOut)
        throws IOException {
        if (srcFile.isFile()) {
            zipOut.putNextEntry(new ZipEntry(srcFile.getName()));
            InputStream is = FileUtils.openInputStream(srcFile);
            try {
                IOUtils.copy(is, zipOut);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                IOUtils.closeQuietly(is);
            }
            return;
        }
        for (File file : srcFile.listFiles()) {
            String entryName = file.getName();
            
            if (file.isDirectory()) {
                entryName += File.separator;
                zipOut.putNextEntry(new ZipEntry(entryName));
            }
            doZipFile(file, zipOut);
        }
    }
    
}
