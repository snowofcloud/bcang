/**
 * @description: 第三方机构管理
 * @author: 刘明明
 * @time: 2016-6-27
 */

var thirdOrg = (function ($) {

    //全局区域
    var $searchBtn = $('#searchBtn');
    var $enteringBtn = $('#enteringBtn');
    var $batchDeleteBtn = $('#batchDeleteBtn');
    var $thirdOrgTable = $("#thirdOrgTable");
    //弹框区域
    var $saveOrUpdatePanel = $("#saveOrUpdatePanel");
    var $saveOrUpdateForm = $("#saveOrUpdateForm");
    var $organType = $("#organType");
    //搜索区域
    var $orgTypeSearch = $("#orgType-search");
    var $orgNameSearch = $("#orgName-search");
    //临时变量
    var flag, dicObj;

    //请求路径
    var urlPath = {
        findByPageUrl: $.backPath + "/thridPartyOrg/findByPage",
        saveUrl: $.backPath + "/thridPartyOrg/save",
        updateUrl: $.backPath + "/thridPartyOrg/update/",
        deleteUrl: $.backPath + "/thridPartyOrg/delete/",
        findByIdUrl: $.backPath + "/thridPartyOrg/findById/",
        findDictByCodeUrl: $.backPath + "/thridPartyOrg/findDictByCode/100001"
    };

    //数据初始化
    var _init = function () {
        dicObj = $.getData(urlPath.findDictByCodeUrl);
        $organType.settingsOptions(dicObj);
        $orgTypeSearch.settingsOptions(dicObj, true);
        _initTabel();
        _bindMethods();
    };

    //表格初始化
    var _initTabel = function () {
        $thirdOrgTable.jqGrid({
            url: urlPath.findByPageUrl,
            mtype: "POST",
            datatype: "JSON",
            colNames: ["机构名称", "机构类型", "地址", "编号", "邮编", "电话", "专业范围", "操作"],
            colModel: [
                {name: "organName", index: "1", align: "center", width: "30px", sortable: false},
                {
                    name: "organType", index: "2", align: "center", width: "30px", sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        return $.getDicNameByCode(cellValue, dicObj);
                    }
                },
                {name: "address", index: "3", align: "center", width: "30px", sortable: false},
                {name: "organNo", index: "4", align: "center", width: "30px", sortable: false},
                {name: "postCode", index: "5", align: "center", width: "30px", sortable: false},
                {name: "telephone", index: "6", align: "center", width: "30px", sortable: false},
                {name: "professionScope", index: "7", align: "center", width: "30px", sortable: false},
                {
                    name: "handle", index: "8", align: "center", width: "40px", sortable: false,
                    formatter: function (cellValue, options, rowObject) {
                        var handlerTemp = '<p class="jqgrid-handle-p">' +
                            '<label class="jqgrid-handle-text delete-link" onclick="thirdOrg.deleteHandle(\'' + rowObject.id + '\')">删除</label>' +
                            '&nbsp;&nbsp;&nbsp;' +
                            '<label class="jqgrid-handle-text update-link" onclick="thirdOrg.updateShowPanel(\'' + rowObject.id + '\')">修改</label>' +
                            '</p>';
                        return handlerTemp;
                    }
                }
            ],
            loadonce: false,
            viewrecords: true,
            autowidth: true,
            height: true,
            multiselect: true,
            multiboxonly: true,
            rowNum: 10,
            rowList: [5, 10, 15],
            pager: "#thirdOrgPager"
        }).resizeTableWidth();
    };

    //绑定方法
    var _bindMethods = function () {
        $searchBtn.click(_searchHandle);
        $enteringBtn.click(_enterShowPanel);
        $batchDeleteBtn.click(_moreDelete);
        $saveOrUpdateForm.html5Validate(function () {
            _submitHandle(flag);
            return false;
        });
    };

    //搜素
    var _searchHandle = function () {
        $thirdOrgTable.jqGrid('setGridParam', {
            url: urlPath.findByPageUrl,
            postData: {"organName": $orgNameSearch.val(), "organType": $orgTypeSearch.val()},
            page: 1
        }).trigger("reloadGrid");
    };

    //新增弹框
    var _enterShowPanel = function () {
        flag = "";
        $saveOrUpdatePanel.showPanelModel('新增第三方机构名称');
    };

    //修改弹框
    var _updateShowPanel = function (ids) {
        flag = ids;
        var updateObj = $.getData(urlPath.findByIdUrl + ids);
        $saveOrUpdatePanel.showPanelModel('修改第三方机构名称').setFormSingleObj(updateObj);
    };

    //编辑和修改操作
    var _submitHandle = function (flag) {
        var formData = $saveOrUpdateForm.serialize();
        $.ajax({
            url: flag ? urlPath.updateUrl + flag : urlPath.saveUrl,
            type: 'POST',
            async: false,
            data: formData
        }).done(function (json) {
            if (json.code === 1) {
                $saveOrUpdatePanel.closePanelModel();
                $thirdOrgTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
            } else {
                $.validateTip(json);
            }
        });
    };

    //单个删除操作
    var _deleteHandle = function (ids) {
        Message.confirm({
            Msg: $.msg.sureDelete,
            okSkin: 'danger',
            iconImg: 'question',
            isModal: true
        }).on(function (flag) {
            if (flag) {
                $.ajax({
                    url: urlPath.deleteUrl + ids,
                    type: 'delete',
                    async: false
                }).done(function (json) {
                    if (json.code === 1) {
                        $thirdOrgTable.jqGrid('setGridParam', {postData: {}, page: 1}).trigger("reloadGrid");
                    } else {
                        $.validateTip(json);
                    }
                });
            }
        });
    };

    //批量
    var _moreDelete = function () {
        var ids = $thirdOrgTable.jqGrid('getGridParam', 'selarrrow');
        var len = ids.length;
        if (len <= 0) {
            Message.alert({Msg: $.msg.noSelectedDelete, isModal: false});
        } else {
            _deleteHandle(ids);
        }
    };

    //初始化
    _init();

    //公有方法
    return {
        deleteHandle: _deleteHandle,
        updateShowPanel: _updateShowPanel
    };

})(jQuery);