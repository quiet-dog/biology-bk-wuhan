package com.biology.domain.manage.xlFangAn.command;

import java.util.List;

import lombok.Data;

@Data
public class AddXlFangAnCommand {
    private String name;

    private List<String> shiJuanTypes;

    private List<Integer> userIds;

    private Long pingGuTime;
}
