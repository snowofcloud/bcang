package com.c503.hthj.asoco.dangerchemical.waste.controller;

import com.c503.hthj.asoco.dangerchemical.waste.base.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

/**
 * @auther xuxq
 * @date 2018/10/16 22:03
 */
@Api(tags = "企业环评信息管理")
@RestController
@RequestMapping("/environmentAssessment")
public class EnterpriseEnvironmentAssessmentController {

    private static Logger log = LoggerFactory.getLogger(EnterpriseEnvironmentAssessmentController.class);

    //@Value("${uploadDir}")
    public final String uploadDir = "D:/data/";

    @ApiOperation("pdf文件上传")
    @RequestMapping(value = "/uploadPDF", method = RequestMethod.POST)
    public JsonResult uploadPDF(@RequestParam(value = "file") MultipartFile file) throws RuntimeException {
        if (file.isEmpty()) {
            return new JsonResult(1,"文件不能为空");
            //return JsonUtil.getFailJsonObject("文件不能为空");
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        log.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        log.info("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String filePath = uploadDir;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            log.info("上传成功后的文件路径未：" + filePath + fileName);
            return new JsonResult(1,"文件上传成功",fileName);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JsonResult(1,"文件上传失败");
    }




}
