package com.biology.domain.manage.materials.command;

import lombok.Data;

@Data
public class UpdateMaterialsCommand extends AddMaterialsCommand {
    private Long materialsId;
}
