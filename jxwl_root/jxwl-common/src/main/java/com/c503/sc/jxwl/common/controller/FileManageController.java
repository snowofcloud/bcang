// /**
// * 文件名：ThridPartyOrgController.java
// * 版权： 航天恒星科技有限公司
// * 描述：〈描述〉
// * 修改时间：2016-6-22
// * 修改内容：〈修改内容〉
// */
// package com.c503.sc.jxwl.common.controller;
//
// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
//
// import javax.annotation.Resource;
//
// import org.springframework.context.annotation.Scope;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.ResponseBody;
// import org.springframework.web.multipart.MultipartFile;
//
// import com.alibaba.fastjson.JSONObject;
// import com.c503.sc.base.common.NumberContant;
// import com.c503.sc.base.entity.Page;
// import com.c503.sc.base.service.IBaseService;
// import com.c503.sc.filemanage.bean.FileInfoEntity;
// import com.c503.sc.filemanage.service.IFileManageService;
// import com.c503.sc.jxwl.common.constant.CommonConstant;
// import com.c503.sc.log.ControlLogModel;
// import com.c503.sc.log.LoggingManager;
// import com.c503.sc.log.resource.ResourceManager;
// import com.c503.sc.utils.common.SystemContants;
// import com.c503.sc.utils.response.ResultJQGrid;
// import com.c503.sc.utils.response.ResultMessage;
//
// /**
// * 〈一句话功能简述〉文件管理action
// * 〈功能详细描述〉
// *
// * @author zz
// * @version [版本号, 2016-6-22]
// * @see [相关类/方法]
// * @since [产品/模块版本]
// */
// @Controller
// @Scope(value = "prototype")
// @RequestMapping(value = "/file")
// public class FileManageController extends ResultController implements
// IFileUploadValidate {
// /** 终端注册业务接口 */
// @Resource
// private IFileManageService fileManageService;
//
// /** 日志器 */
// private static final LoggingManager LOGGER =
// LoggingManager.getLogger(FileManageController.class);
//
// /**
// * 〈一句话功能简述〉分页查询第三方机构入口
// * 〈功能详细描述〉
// *
// * @param page 前台数据-页数
// * @param rows 前台数据-每一页显示的行数
// * @param fileName fileName
// * @return 返回查询的所有记录（分页数据）
// * @throws Exception 数据库异常
// * @see [类、类#方法、类#成员]
// */
// @RequestMapping(value = "/findByPage", method = RequestMethod.GET)
// @ResponseBody
// public Object findByPage(Integer page, Integer rows, String fileName)
// throws Exception {
// LOGGER.debug(SystemContants.DEBUG_START, fileName);
// Map<String, Object> map = new HashMap<String, Object>();
// map.put("fileName", fileName);
//
// Page pageEntity = new Page();
// pageEntity.setCurrentPage(page);
// pageEntity.setPageSize(rows);
// map.put("page", pageEntity);
//
// map.put("remove", SystemContants.ON);
//
// List<FileInfoEntity> list = this.fileManageService.findByParams(map);
// setJQGrid(list,
// pageEntity.getTotalCount(),
// page,
// rows,
// CommonConstant.FIND_SUC_OPTION);
// LOGGER.info(CommonConstant.FIND_SUC_OPTION, list);
// LOGGER.debug(SystemContants.DEBUG_END);
//
// return (ResultJQGrid) sendMessage();
// }
//
// /**
// * 〈一句话功能简述〉上传附件 〈功能详细描述〉
// *
// * @param fileCode fileCode
// * @return success :{"code":1,msg:"上传成功",data:null}
// * exception:{"code":3,"msg":"xxx",data:null}
// * @throws Exception 自定义异常 312410001
// * @see [类、类#方法、类#成员]
// */
// @SuppressWarnings("unchecked")
// @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
// @ResponseBody
// public ResultMessage uploadFile(String fileCode)
// throws Exception {
// // 记录程序进入方法调试日志
// LOGGER.debug(SystemContants.DEBUG_START);
//
// Object obj =
// this.uploadFiles(this.getFileUploadService(), getRequest());
// if (obj instanceof HashMap) {
// // 设置响应消息
// sendData(obj, CommonConstant.FORMVALID_FAIL_OPTION);
// // 记录操作失败信息（存入文件）
// LOGGER.info(CommonConstant.FORMVALID_FAIL_OPTION, obj);
// // 保存操作日志 记录操作失败（存入数据库）
// controlLog(ControlLogModel.CONTROL_RESULT_FAIL,
// CommonConstant.FORMVALID_FAIL_OPTION,
// obj).setErrorMessage(CommonConstant.FORMVALID_FAIL_OPTION)
// .recordLog();
// }
// else {
// List<FileInfoEntity> fileInfoes = (List<FileInfoEntity>) obj;
// JSONObject json = new JSONObject();
// List<String> ids = new ArrayList<String>();
// List<String> names = new ArrayList<String>();
//
// if (0 < fileInfoes.size()) {
// for (FileInfoEntity fie : fileInfoes) {
// fie.setFileCode(fileCode);
// // fie.setFileMappingPath(fileMappingPath);
// fie = this.fileManageService.saveFileAndInfo(fie);
// ids.add(fie.getId());
// names.add(fie.getOrgFileName());
// }
// json.put("ids", ids);
// json.put("names", names);
// }
// // 设置响应消息
// sendData(json, CommonConstant.UPLOAD_SUC_OPTION);
// // 记录操作成功信息
// LOGGER.info(CommonConstant.UPLOAD_SUC_OPTION, json);
// // 保存操作日志 记录操作成功
// controlLog(ControlLogModel.CONTROL_RESULT_SUCCESS,
// CommonConstant.UPLOAD_SUC_OPTION,
// json).recordLog();
// LOGGER.debug(SystemContants.DEBUG_END);
// }
//
// return sendMessage();
// }
//
// /** {@inheritDoc} */
// @Override
// public boolean validFile(MultipartFile file, Map<String, Object> map) {
// // file.getSize()>50M
// boolean isValid = false;
// if (file.getSize() > NumberContant.FIFTY_MILLION) {
// // 文件的大小 不对。
// String msg =
// ResourceManager.getMessage("accidentProgressForm.fileSize");
// map.put("fileSize", msg);
// isValid = true;
// }
//
// String fileName = file.getOriginalFilename();
// String fileType =
// fileName.substring(fileName.lastIndexOf("."), fileName.length());
// boolean typeControl =
// ".docx".equalsIgnoreCase(fileType)
// || ".doc".equalsIgnoreCase(fileType) || ".pdf".equals(fileType)
// || ".xlsx".equalsIgnoreCase(fileType)
// || ".xls".equalsIgnoreCase(fileType)
// || ".png".equalsIgnoreCase(fileType)
// || ".jpg".equalsIgnoreCase(fileType)
// || ".jpeg".equalsIgnoreCase(fileType);
// // TODO 上传支持的文件类型 通过系统配置指定
// if (!typeControl) {
// // 文件的类型 格式不对。
// String msg =
// ResourceManager.getMessage("accidentProgressForm.fileType");
// map.put("fileType", msg);
// isValid = true;
// }
// int fileNameSize =
// fileName.substring(0, fileName.lastIndexOf(".") - 1).length();
// if (fileNameSize > NumberContant.THIRTY) {
// String msg = ResourceManager.getMessage("caseForm.fileNameSize");
// map.put("fileNameSize", msg);
// isValid = true;
// }
//
// return isValid;
// }
//
// /**
// * 〈一句话功能简述〉实现文件上传验证的接口 〈功能详细描述〉
// *
// * @return 当前对象作为实现类
// * @see [类、类#方法、类#成员]
// */
// private IFileUploadValidate getFileUploadService() {
// return this;
// }
//
// @Override
// protected <T> IBaseService<T> getBaseService() {
// return null;
// }
//
// @Override
// protected Object show() {
// return null;
// }
//
// @Override
// protected LoggingManager logger() {
// return null;
// }
//
// }
