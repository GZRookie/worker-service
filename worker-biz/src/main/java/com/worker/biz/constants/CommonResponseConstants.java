package com.worker.biz.constants;


import com.worker.common.constant.ResponseStatus;

/**
 * 通用响应常量类
 *
 * @author:
 * @date: 2023-01-13 15:15
 */
public class CommonResponseConstants {
    public static final ResponseStatus CREATED_FAIL = new ResponseStatus(5900, "创建失败");
    public static final ResponseStatus UPDATED_FAIL = new ResponseStatus(5901, "更新失败");
    public static final ResponseStatus DELETED_FAIL = new ResponseStatus(5902, "删除失败");
    public static final ResponseStatus SAVE_FAIL = new ResponseStatus(5903, "保存失败");
    public static final ResponseStatus QUERY_FAIL = new ResponseStatus(5904, "查询失败");
    public static final ResponseStatus ENABLE_FAIL = new ResponseStatus(5905, "启用失败");
    public static final ResponseStatus SECOND_INVOKER_FAIL = new ResponseStatus(5998, "服务调用失败");
    public static final ResponseStatus FILE_UPLOAD_ERROR = new ResponseStatus(5999, "文件上传失败");
    public static final ResponseStatus INVOKE_ERROR = new ResponseStatus(5997, "服务调用异常");
}