package com.biology.domain.manage.moni.command;

import lombok.Data;

@Data
public class UpdateMoniCommand extends AddMoniCommand {
    private Long moniId;
}
