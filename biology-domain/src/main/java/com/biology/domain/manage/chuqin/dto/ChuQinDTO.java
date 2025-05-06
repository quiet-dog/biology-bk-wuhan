package com.biology.domain.manage.chuqin.dto;

import java.time.LocalDateTime;

import com.biology.domain.manage.chuqin.db.ChuQinEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ChuQinDTO {

    @Schema(description = "出勤ID")
    private Long chuQinId;

    @Schema(description = "出勤时间")
    private LocalDateTime chuQinTime;

    @Schema(description = "人员档案ID")
    private Long personnelId;

    public ChuQinDTO(ChuQinEntity entity) {
        if (entity != null) {
            this.chuQinId = entity.getChuQinId();
            this.chuQinTime = entity.getChuQinTime();
            this.personnelId = entity.getPersonnelId();
        }
    }
}
