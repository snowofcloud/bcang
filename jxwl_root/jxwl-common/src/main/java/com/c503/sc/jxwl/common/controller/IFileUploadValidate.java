/**
 * 文件名：IFileUploadValidate.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年8月14日 n
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.common.controller;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * 〈一句话功能简述〉文件上传验证 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2015年8月25日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IFileUploadValidate {
    /**
     * 〈一句话功能简述〉文件上传验证接口 〈功能详细描述〉
     * 
     * @param file 上传的文件
     * @param map 用来装验证信息
     * @return 验证成功返回false
     * @see [类、类#方法、类#成员]
     */
    boolean validFile(MultipartFile file, Map<String, Object> map);
}
