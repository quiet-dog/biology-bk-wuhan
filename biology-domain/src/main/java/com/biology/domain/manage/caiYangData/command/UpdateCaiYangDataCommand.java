package com.biology.domain.manage.caiYangData.command;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class UpdateCaiYangDataCommand extends AddCaiYangDataCommand {
    private Long caiYangDataId;
}
