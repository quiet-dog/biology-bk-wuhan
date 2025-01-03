package com.biology.domain.manage.koala.dto.records;

import lombok.Data;

@Data
public class KoalaPageDTO {
    private Integer count;
    private Integer current;
    private Integer total;
    private Integer size;
}
