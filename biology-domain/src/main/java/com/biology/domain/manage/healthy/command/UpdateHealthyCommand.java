package com.biology.domain.manage.healthy.command;

import lombok.Data;

@Data
public class UpdateHealthyCommand extends AddHealthyCommand {
    private Long healthyId;
}