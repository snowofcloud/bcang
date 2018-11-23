package com.xly.spring.boot.mybatisplus.server.controller;

import com.xly.spring.boot.mybatisplus.server.base.model.JsonResult;
import com.xly.spring.boot.mybatisplus.server.base.model.PageInfo;
import com.xly.spring.boot.mybatisplus.server.entity.Enterprise;
import com.xly.spring.boot.mybatisplus.server.service.EnterpriseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.xly.spring.boot.mybatisplus.server.base.model.JsonResult.State.SUCCESS;

/**
 * @auther xuxq
 * @date 2018/11/23 14:31
 */
@Api(tags = "企业基本信息")
@RestController
@RequestMapping("api/enterprise")
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;

    @ApiOperation("获取企业信息")
    @GetMapping("/queryAllEnterprise")
    public JsonResult queryAll(@ApiParam(name = "pageIndex", value = "当前页码")
                               @RequestParam(name = "pageIndex", required = false, defaultValue = "1") int pageIndex,
                               @ApiParam(name = "pageSize", value = "当页显示数量")
                               @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize){
        PageInfo<Enterprise> pageInfo = enterpriseService.queryAll(pageIndex,pageSize);
        return new JsonResult(SUCCESS,pageInfo);

    }



}
