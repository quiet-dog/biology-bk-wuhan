package com.biology.domain.manage.runTime.command;

import lombok.Data;

@Data
public class UpdateRunTimeCommand extends AddRunTimeCommand {
    private Long runTimeId;
}
