package com.c503.hthj.asoco.dangerchemical.waste.zoo.son;

import java.io.File;

/**
 * @auther xuxq
 * @date 2019/3/27 14:53
 */
public class DeleteFilesUtils {

    public static void main(String[] args) {
        String dir = "K:/dfgh";
        //https://www.cnblogs.com/esther-qing/p/6145336.html
        DeleteFilesUtils.deleteDirectory(dir);
    }


    public static boolean deleteDirectory(String dir){
        //判断文件分隔符
        //如果dir文件结尾不是以分隔符结尾的，自动添加文件分割符。
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;

        //创建一个文件对象
        File file = new File(dir);

        if ((!file.exists())||(!file.isDirectory()))//如果文件不存在，或者不是一个文件夹，则返回false
            return false;

        //加标识
        boolean flag = true;
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {

            //删除文件--单个文件
            if (files[i].isFile()) {
                flag = DeleteFilesUtils.deteleFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            //删除文件夹里的内容
            if (files[i].isDirectory()) {
                flag = DeleteFilesUtils.deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }

        if (!flag) {
            System.out.println("删除目录失败");
            return false;
        }

        //删除当前目录
        if (file.delete()) {
            System.out.println("删除目录" + dir  +"成功!");
            return true;
        } else
            return false;
    }

    //删除单个文件
    public static boolean deteleFile(String fileName) {
        File file = new File(fileName);
        if ((file.exists())&&(file.isFile())) {//文件存在且是一个文件
            if (file.delete()) {
                System.out.println("删除单个文件："+ fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败："+ fileName + "不存在！");
            return false;
        }
    }

    public static boolean detele(String fileName){
        File file = new File(fileName);
        if (!file.exists())
            return false;
        else {
            if (file.isDirectory())
                return deleteDirectory(fileName);
            else
                return deteleFile(fileName);
        }
    }


}
