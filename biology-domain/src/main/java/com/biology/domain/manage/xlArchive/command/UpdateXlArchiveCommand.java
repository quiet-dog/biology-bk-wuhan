package com.biology.domain.manage.xlArchive.command;

import lombok.Data;

@Data
public class UpdateXlArchiveCommand extends AddXlArchiveCommand {
    private Long xlArchiveId;
}
