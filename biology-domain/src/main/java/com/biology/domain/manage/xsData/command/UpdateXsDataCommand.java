package com.biology.domain.manage.xsData.command;

import lombok.Data;

@Data
public class UpdateXsDataCommand extends AddXsDataCommand {
    private Long xsDataId;
}
