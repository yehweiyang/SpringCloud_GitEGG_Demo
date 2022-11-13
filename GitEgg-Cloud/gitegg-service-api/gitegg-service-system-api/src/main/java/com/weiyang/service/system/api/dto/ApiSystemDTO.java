package com.weiyang.service.system.api.dto;


import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Data
public class ApiSystemDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -8645428993012216368L;

    @NotNull
    @Min(value = 10, message = "id必須大於10")
    @Max(value = 150, message = "id必須小於150")
    private Long id;

    @NotNull(message = "名稱不能為空")
    @Size(min = 3, max = 20, message = "名稱長度必須在3-20之間")
    private String name;

}
