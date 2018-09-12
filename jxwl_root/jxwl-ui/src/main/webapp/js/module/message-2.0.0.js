/**
 * @description: 版本升级,将css和js合在一起使用,
 *               在引入bootstrap之后,直接引入messager之后就直接使用
 *               可以控制按钮颜色和顶部的关闭按钮是否显示
 * @time: 2016-6-7
 * @version 2.0.0
 * @author: 刘明明
 */
;
$(function () {
    window.Message = function () {
        var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
        /*弹出框的html结构*/
        var alr = $(
            ['<div id="messager-dialogue" class="modal fade" style="z-index: 10000">'
                , '<div class="modal-dialog modal-sm">'
                , '<div class="modal-content">'
                , '<div class="modal-header">'
                , '<button type="button" class="close messager-close" data-dismiss="modal"><span aria-hidden="true">×</span>'
                , '<span class="sr-only">Close</span>'
                , '</button>'
                , '<h5 class="modal-title">'
                , '<i class="fa fa-exclamation-circle"></i> [Title]'
                , '</h5>'
                , '</div>'
                , '<div class="modal-body small">'
                , '<div class="row">'
                , '<div class="col-xs-2">[MessagerIcon]</div>'
                , '<div class="col-xs-10 messageText" style="word-break: break-all;line-height: 26px;color: #fff">[Message]</div>'
                , '</div>'
                , '</div>'
                , '<div class="modal-footer" >'
                , '<button type="button" class="btn btn-[OkSkin] ok" data-dismiss="modal">[BtnOk]</button>'
                , '<button type="button" class="btn btn-[CancelSkin] cancel" data-dismiss="modal">[BtnCancel]</button>'
                , '</div>'
                , '</div>'
                , '</div>'
                , '</div>'].join(""));
        var ahtml = alr.html();
        /*alert弹出框*/
        var _alert = function (options) {
            alr.html(ahtml);
            alr.find('.cancel').hide();
            _dialog(options);
            return {
                /*回调函数*/
                on: function (callback) {
                    if (callback && callback instanceof Function) {
                        alr.find('.ok').click(function (e) {
                            e.preventDefault();
                            callback(true, 'ok');
                        });
                        alr.find('.messager-close').click(function (e) {
                            e.preventDefault();
                            callback(false, 'close');
                        });
                    }
                }
            };
        };
        /*confirm弹出框*/
        var _confirm = function (options) {
            alr.html(ahtml);
            alr.find('.cancel').show();
            _dialog(options);
            return {
                on: function (callback) {
                    if (callback && callback instanceof Function) {
                        alr.find('.ok').click(function (e) {
                            e.preventDefault();
                            callback(true, 'ok');
                        });
                        alr.find('.cancel').click(function (e) {
                            e.preventDefault();
                            callback(false, 'cancel');
                        });
                        alr.find('.messager-close').click(function (e) {
                            e.preventDefault();
                            callback(false, 'close');
                        });
                    }
                }
            };
        };
        /*自动消失的alert弹出框*/
        var _show = function (options) {
            var imgUrl = "/GDYY/images/icon/";
            alr.html(ahtml);
            alr.find('.cancel').hide();
            _dialog(options);
            var setTimeoutObj;
            /*判断是否使用默认值，还是用户传入的值*/
            if (options.isHideDate) {
                setTimeoutObj = setTimeout(function () {
                    $("#messager-dialogue").modal("hide");
                }, options.isHideDate);
            } else {
                setTimeoutObj = setTimeout(function () {
                    $("#messager-dialogue").modal("hide");
                }, 3000);
            }
            alr.find('.ok').click(function () {
                clearInterval(setTimeoutObj);
            });
            return {
                on: function (callback) {
                    if (callback && callback instanceof Function) {
                        alr.find('.ok').click(function () {
                            callback(true, 'ok');
                        });
                        alr.find('.messager-close').click(function (e) {
                            e.preventDefault();
                            callback(false, 'close');
                        });
                    }
                }
            };
        };
        var _dialog = function (options) {
            /*默认的参数信息*/
            var ops = {
                Msg: "", /*显示给用户看的内容*/
                iconImg: 'info', /*提示的图标 默认图片是info,只是消息而已*/
                title: "提示", /*提示标题*/
                isModal: false, /*模态状态开关*/
                btnOk: "确定", /*确定按钮的默认文字*/
                btnCancel: "取消", /*取消按钮的默认文字*/
                isHideDate: 3000, /*设置自动消失的时间*/
                okSkin: 'primary', /*确定按钮皮肤*/
                cancelSkin: 'border', /*取消按钮皮肤*/
                isClose: true, /*是否显示左上角关闭按钮*/
                position: {}/*程序控制弹出框位置*/
            };
            /*传入的值，和默认值进行替换*/
            $.extend(ops, options);
            /*替换模板dom结构里面的内容*/
            var html = alr.html().replace(reg, function (node, key) {
                return {
                    Title: ops.title,
                    Message: ops.Msg,
                    BtnOk: ops.btnOk,
                    BtnCancel: ops.btnCancel,
                    OkSkin: ops.okSkin,
                    CancelSkin: ops.cancelSkin,
                    MessagerIcon: '<img class="messagerIcon" style="width:30px;" src="/JXWL/images/icon/' + ops.iconImg + '.png">'
                }[key];
            });
            alr.html(html);
            alr.modal({width: 300, backdrop: 'static'}).css(ops.position);
            /*设置模态状态*/
            if (!ops.isModal) {
                $("body > div.modal-backdrop:last").css("display", "none");
            }
            if (!ops.isClose) {
                $("button[class*='messager-close']").remove();
            }
        };
        /*返回给用户进行方法的调用 	可以看成接口*/
        return {
            alert: _alert,
            confirm: _confirm,
            show: _show
        };
    }();
});