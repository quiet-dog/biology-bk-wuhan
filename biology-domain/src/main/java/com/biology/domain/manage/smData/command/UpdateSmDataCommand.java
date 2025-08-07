package com.biology.domain.manage.smData.command;

import lombok.Data;

@Data
public class UpdateSmDataCommand extends AddSmDataCommand {
    private Long smDataId;
}
