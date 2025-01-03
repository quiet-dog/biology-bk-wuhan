package com.biology.domain.manage.sop.command;

import lombok.Data;

@Data
public class UpdateSopCommand extends AddSopCommand {
    private Long sopId;
}
