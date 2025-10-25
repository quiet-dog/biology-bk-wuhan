package com.biology.domain.manage.xunJianHistory.command;

import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import lombok.Data;

@Data
public class AddXunJianHistoryCommand {

    private String xunJianLeiXing;

    private Long xunJianId;

    private String status;

    private String xunJianPinLu;

    private String fanWei;

    private List<Integer> timeRange;

    private List<Integer> dayRange;

}
