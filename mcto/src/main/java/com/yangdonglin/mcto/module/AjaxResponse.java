package com.yangdonglin.mcto.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class AjaxResponse implements Serializable {

    /**
     * 请求是否成功
     */
    @JsonProperty("success")
    private Boolean success;

    /**
     * 返回数据, 只有在请求成功时才使用
     */
    @JsonProperty("result")
    private Object result;

    /**
     * true这个操作未被认证或用户未被认证, 默认为false
     */
    @JsonProperty("unauthorizedrequest")
    private Boolean unAuthorizedRequest;

    /**
     * 提供一个URL给服务端，在有需要的时候，把客户端定向到这个URL
     */
    @JsonProperty("targetUrl")
    private String targetUrl;

    /**
     * 错误信息
     */
    @JsonProperty("error")
    private ErrorInfo error;


    @Data
    @Builder
    public static class ErrorInfo implements Serializable{

        //add by yanyongjian
        private final static  Integer ERR_BASE_CODE = 1000;
        /**
         * 数据不存在
         */
        public final static Integer ERR_SQL_NOT_EXITS = (ERR_BASE_CODE + 101);
        /**
         * 新增错误
         */
        public final static Integer ERR_SQL_INSERT = ERR_BASE_CODE + 102;
        /**
         * 更新错误
         */
        public final static Integer ERR_SQL_UPDATE = ERR_BASE_CODE + 103;
        /**
         * 删除错误
         */
        public final static Integer ERR_SQL_DELETE = ERR_BASE_CODE + 104;
        /**
         * 重复错误
         */
        public static Integer ERR_SQL_REPEAT = ERR_BASE_CODE + 105;
        /**
         * 保存错误
         */
        public final static Integer ERR_SQL_SAVE = ERR_BASE_CODE + 106;

        public static Integer ERR_COMMON_TOP_OR_END = ERR_BASE_CODE + 1001;

        public static Integer ERR_FILE_NO_FOUND = ERR_BASE_CODE + 2001;

        private static Map<Integer,String> errMap = new HashMap<Integer,String>();
        static {
            errMap.put(ERR_SQL_NOT_EXITS,"数据不存在");
            errMap.put(ERR_SQL_INSERT,"新增错误");
            errMap.put(ERR_SQL_UPDATE,"更新错误");
            errMap.put(ERR_SQL_DELETE,"删除错误");
            errMap.put(ERR_SQL_REPEAT,"数据已存在");
            errMap.put(ERR_FILE_NO_FOUND,"文件不存在");
            errMap.put(ERR_COMMON_TOP_OR_END,"已经是最顶端或者最低端了");
        }
        /**
         * 错误编码
         */
        @JsonProperty("code")
        private Integer code;

        /**
         * 错误信息
         */
        @JsonProperty("message")
        private String message;

        /**
         * 详细信息
         */
        @JsonProperty("details")
        private String details;

        @JsonProperty("validationerrors")
        private ValidationErrorInfo[] validationErrors;
    }

    @Data
    public static class ValidationErrorInfo implements Serializable{
        @JsonProperty("message")
        private String message;

        @JsonProperty("members")
        private String[] members;
    }

    public static AjaxResponse success() {
        return AjaxResponse.builder().success(Boolean.TRUE).unAuthorizedRequest(Boolean.FALSE).build();
    }

    public static AjaxResponse success(Object data) {
        return AjaxResponse.builder().success(Boolean.TRUE).unAuthorizedRequest(Boolean.FALSE).result(data).build();
    }

    public static AjaxResponse error(ErrorInfo errorInfo, Boolean unAuthorizedRequest) {
        return AjaxResponse.builder().success(Boolean.FALSE).unAuthorizedRequest(unAuthorizedRequest).error(errorInfo).build();
    }

    public static AjaxResponse error(ErrorInfo errorInfo) {
        return error(errorInfo, Boolean.FALSE);
    }

    public static AjaxResponse error(Integer code, String message, String details, Boolean unAuthorizedRequest) {
        ErrorInfo errorInfo = ErrorInfo.builder().code(code).message(message).details(details).build();
        return error(errorInfo, unAuthorizedRequest);
    }

    public static AjaxResponse error(Integer code, String message) {
        return error(code, message, null);
    }

    public static AjaxResponse errorSql(Integer code){
        String message = ErrorInfo.errMap.getOrDefault(code,"未知数据库异常");
        return error(code, message, null);
    }
    public static AjaxResponse error(Integer code){
        String message = ErrorInfo.errMap.getOrDefault(code,"未知异常");
        return error(code, message, null);
    }

    public static AjaxResponse error(Integer code, String message, String details) {
        return error(code, message, details, Boolean.FALSE);
    }

    public static AjaxResponse error(String message) {
        return error(AjaxResponseErrorCode.ERROR.getCode(), message, null);
    }
}
