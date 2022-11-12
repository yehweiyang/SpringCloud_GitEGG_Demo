package com.weiyang.gitegg.service.system.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SystemDTO {

    @NotNull
    @Min(value = 10, message = "id必須大於10")
    @Max(value = 150, message = "id必須小於150")
    private Long id;

    @NotNull(message = "名稱不能為空")
    @Size(min = 3, max = 20, message = "名稱長度必須在3-20之間")
    private String name;
}
