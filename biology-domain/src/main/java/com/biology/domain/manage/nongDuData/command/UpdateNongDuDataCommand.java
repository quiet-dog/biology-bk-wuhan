package com.biology.domain.manage.nongDuData.command;

import lombok.Data;

@Data
public class UpdateNongDuDataCommand extends AddNongDuDataCommand {
    private Long nongDuDataId;
}
