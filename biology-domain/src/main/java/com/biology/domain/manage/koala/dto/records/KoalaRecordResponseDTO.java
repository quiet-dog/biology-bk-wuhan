package com.biology.domain.manage.koala.dto.records;

import java.util.List;

import lombok.Data;

@Data
public class KoalaRecordResponseDTO {
    private Integer code;
    private Integer timecost;
    private List<KoalaRecordDTO> data;
    private KoalaPageDTO page;
}
