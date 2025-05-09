package com.worker.biz.constants.file;

import com.worker.common.constant.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * 文件接口响应码表
 *
 *  @author
 * @date: 2024/1/9 17:41
 */
@Getter
@Setter
public class FileResponseStatus {

    public static final ResponseStatus APK_FORMAT_IS_ERROR = new ResponseStatus(6900, "非APK格式，请上传正确文件");
    public static final ResponseStatus IMAGE_FORMAT_IS_ERROR = new ResponseStatus(6901, "非图片文件，请上传正确文件");
}
