package com.biology.domain.manage.moni.command;

import java.util.List;

import lombok.Data;

@Data
public class DeleteMoniCommand {
    private List<Long> moniIds;
}
