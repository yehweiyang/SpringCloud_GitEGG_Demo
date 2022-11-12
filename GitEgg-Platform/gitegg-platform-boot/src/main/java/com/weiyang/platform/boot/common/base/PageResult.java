package com.weiyang.platform.boot.common.base;

import java.util.List;

import com.weiyang.platform.boot.common.enums.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: PageResult
 * @Description: 通用分頁返回
 * @author GitEgg
 * @date
 * @param <T>
 */
@Data
@ApiModel("通用分頁響應類")
public class PageResult<T> {

    @ApiModelProperty(value = "是否成功", required = true)
    private boolean success;

    @ApiModelProperty(value ="響應程式碼", required = true)
    private int code;

    @ApiModelProperty(value ="提示資訊", required = true)
    private String msg;

    @ApiModelProperty(value ="總數量", required = true)
    private long count;

    @ApiModelProperty(value ="分頁資料")
    private List<T> data;

    public PageResult(long total, List<T> rows) {
        this.count = total;
        this.data = rows;
        this.code = ResultCodeEnum.SUCCESS.code;
        this.msg = ResultCodeEnum.SUCCESS.msg;
    }
}