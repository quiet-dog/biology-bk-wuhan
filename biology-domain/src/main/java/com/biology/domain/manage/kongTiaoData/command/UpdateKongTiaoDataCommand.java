package com.biology.domain.manage.kongTiaoData.command;

import lombok.Data;

@Data
public class UpdateKongTiaoDataCommand extends AddKongTiaoDataCommand {
    private Long kongTiaoDataId;
}
