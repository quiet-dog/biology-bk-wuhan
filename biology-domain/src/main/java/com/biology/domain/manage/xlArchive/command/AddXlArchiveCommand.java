package com.biology.domain.manage.xlArchive.command;

import lombok.Data;

@Data
public class AddXlArchiveCommand {

    private Long userId;

    private Long personnelId;

    private String status;
}
