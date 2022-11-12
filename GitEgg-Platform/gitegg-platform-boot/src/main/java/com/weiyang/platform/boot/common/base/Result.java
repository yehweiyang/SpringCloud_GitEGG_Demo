package com.weiyang.platform.boot.common.base;

import com.weiyang.platform.boot.common.enums.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName: Result
 * @Description: 自訂通用響應類
 * @author GitEgg
 * @date 2020年09月19日 下午9:24:50
 */
@ApiModel(description = "通用響應類")
@Getter
@ToString
public class Result<T> {

    @ApiModelProperty(value = "是否成功", required = true)
    private boolean success;

    @ApiModelProperty(value ="響應程式碼", required = true)
    private int code;

    @ApiModelProperty(value ="提示資訊", required = true)
    private String msg;

    @ApiModelProperty(value ="響應資料")
    private T data;

    /**
     *
     * @param code
     * @param data
     * @param msg
     */
    private Result(int code, T data, String msg) {
        this.success = ResultCodeEnum.SUCCESS.code == code;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     *
     * @param resultCodeEnum
     */
    private Result(ResultCodeEnum resultCodeEnum ) {
        this(resultCodeEnum.code, null, resultCodeEnum.msg);
    }

    /**
     *
     * @param resultCodeEnum
     * @param msg
     */
    private Result(ResultCodeEnum resultCodeEnum , String msg) {
        this(resultCodeEnum, null, msg);
    }

    /**
     *
     * @param resultCodeEnum
     * @param data
     */
    private Result(ResultCodeEnum resultCodeEnum , T data) {
        this(resultCodeEnum, data, resultCodeEnum.msg);
    }

    /**
     *
     * @param resultCodeEnum
     * @param data
     * @param msg
     */
    private Result(ResultCodeEnum resultCodeEnum , T data, String msg) {
        this(resultCodeEnum.code, data, msg);
    }

    /**
     *
     *
     * @param data 資料
     * @param <T>  T 響應資料
     * @
     */
    public static <T> Result<T> data(T data) {
        return data(data, ResultCodeEnum.SUCCESS.msg);
    }

    /**
     *
     *
     * @param data 資料
     * @param msg  消息
     * @param <T>  T 響應資料
     * @
     */
    public static <T> Result<T> data(T data, String msg) {
        return data(ResultCodeEnum.SUCCESS.code, data, msg);
    }

    /**
     *
     *
     * @param code 狀態碼
     * @param data 資料
     * @param msg  消息
     * @param <T>  T 響應資料
     * @
     */
    public static <T> Result<T> data(int code, T data, String msg) {
        return new Result<>(code, data, msg);
    }

    /**
     * 返回Result
     *
     * @param
     * @param <T>  T 響應資料
     * @返回Result
     */
    public static <T> Result<T> success() {
        return new Result<>(ResultCodeEnum.SUCCESS);
    }

    /**
     * 返回Result
     *
     * @param msg 消息
     * @param <T> T 響應資料
     * @返回Result
     */
    public static <T> Result<T> success(String msg) {
        return new Result<>(ResultCodeEnum.SUCCESS, msg);
    }

    /**
     * 返回Result
     *
     * @param
     * @param <T>  T 響應資料
     * @返回Result
     */
    public static <T> Result<T> success(ResultCodeEnum resultCodeEnum ) {
        return new Result<>(resultCodeEnum);
    }

    /**
     * 返回Result
     *
     * @param
     * @param msg   提示資訊
     * @param <T>  T 響應資料
     * @返回Result
     */
    public static <T> Result<T> success(ResultCodeEnum resultCodeEnum , String msg) {
        return new Result<>(resultCodeEnum, msg);
    }

    /**
     * 返回Result
     *
     * @param <T> T 響應資料
     * @返回Result
     */
    public static <T> Result<T> error() {
        return new Result<>(ResultCodeEnum.ERROR, ResultCodeEnum.ERROR.msg);
    }

    /**
     * 返回Result
     *
     * @param msg 消息
     * @param <T> T 響應資料
     * @返回Result
     */
    public static <T> Result<T> error(String msg) {
        return new Result<>(ResultCodeEnum.ERROR, msg);
    }


    /**
     * 返回Result
     *
     * @param code 狀態碼
     * @param msg  消息
     * @param <T>  T 響應資料
     * @返回Result
     */
    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, null, msg);
    }

    /**
     * 返回Result
     *
     * @param
     * @param <T>  T 響應資料
     * @返回Result
     */
    public static <T> Result<T> error(ResultCodeEnum resultCodeEnum ) {
        return new Result<>(resultCodeEnum);
    }

    /**
     * 返回Result
     *
     * @param
     * @param msg   提示資訊
     * @param <T>  T 響應資料
     * @返回Result
     */
    public static <T> Result<T> error(ResultCodeEnum resultCodeEnum , String msg) {
        return new Result<>(resultCodeEnum, msg);
    }

    /**
     *
     * @param <T>
     * @param flag
     * @return
     */
    public static <T> Result<T> result(boolean flag) {
        return flag ? Result.success("操作成功") : Result.error("操作失敗");
    }

}