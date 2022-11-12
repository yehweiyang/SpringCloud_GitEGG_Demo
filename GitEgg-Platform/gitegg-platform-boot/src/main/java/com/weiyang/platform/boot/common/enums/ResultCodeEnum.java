package com.weiyang.platform.boot.common.enums;

/**
 * @author GitEgg
 * @ClassName: ResultCodeEnum
 * @Description: 自訂傳回碼列舉
 * @date 2020年09月19日 下午11:49:45
 */
public enum ResultCodeEnum {

    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 系統錯誤
     */
    ERROR(500, "系統錯誤"),

    /**
     * 操作失敗
     */
    FAILED(101, "操作失敗"),

    /**
     * 未登錄/登錄超時
     */
    UNAUTHORIZED(102, "登錄超時"),

    /**
     * 參數錯誤
     */
    PARAM_ERROR(103, "參數錯誤"),

    /**
     * 參數錯誤-已存在
     */
    INVALID_PARAM_EXIST(104, "請求參數已存在"),

    /**
     * 參數錯誤
     */
    INVALID_PARAM_EMPTY(105, "請求參數為空"),

    /**
     * 參數錯誤
     */
    PARAM_TYPE_MISMATCH(106, "參數類型不匹配"),

    /**
     * 參數錯誤
     */
    PARAM_VALID_ERROR(107, "參數校驗失敗"),

    /**
     * 參數錯誤
     */
    ILLEGAL_REQUEST(108, "非法請求"),

    /**
     * 驗證碼錯誤
     */
    INVALID_VCODE(204, "驗證碼錯誤"),

    /**
     * 使用者名稱或密碼錯誤
     */
    INVALID_USERNAME_PASSWORD(205, "帳號或密碼錯誤"),

    /**
     *
     */
    INVALID_RE_PASSWORD(206, "兩次輸入密碼不一致"),

    /**
     * 使用者名稱或密碼錯誤
     */
    INVALID_OLD_PASSWORD(207, "舊密碼錯誤"),

    /**
     * 使用者名稱重複
     */
    USERNAME_ALREADY_IN(208, "使用者名稱已存在"),

    /**
     * 使用者不存在
     */
    INVALID_USERNAME(209, "使用者名稱不存在"),

    /**
     * 角色不存在
     */
    INVALID_ROLE(210, "角色不存在"),

    /**
     * 角色不存在
     */
    ROLE_USED(211, "角色使用中，不可刪除"),

    /**
     * 沒有權限
     */
    NO_PERMISSION(403, "當前使用者無該介面權限");

    public int code;

    public String msg;

    ResultCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}