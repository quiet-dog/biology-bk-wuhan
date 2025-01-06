package com.biology.domain.manage.materials.dto;

import lombok.Data;

@Data
public class NormalDTO {
    // 正常的数量
    private Integer normal;
    // 异常的数量
    private Integer abnormal;
}
