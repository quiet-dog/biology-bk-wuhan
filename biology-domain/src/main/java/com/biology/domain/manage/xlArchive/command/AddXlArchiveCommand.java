package com.biology.domain.manage.xlArchive.command;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class AddXlArchiveCommand {

    private Long userId;

    private Long personnelId;

    private String status;
}
