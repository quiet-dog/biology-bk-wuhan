package com.biology.domain.manage.kongTiaoData.dto;

import java.util.Date;

import com.biology.domain.manage.kongTiaoData.command.AddKongTiaoDataCommand;
import com.biology.domain.manage.kongTiaoData.db.KongTiaoDataEntity;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;

@Data
public class KongTiaoDataDTO {

    private Boolean isOnline;

    private Long kongTiaoDataId;

    private String deviceSn;

    private Double zhiBanGongKuanYaLiSheDing;

    private Double zhiBanGongKuanFengLiangSheDing;

    private Integer fengFaWenDingZhuangTai;

    private Integer faWeiFanKuan;

    private Integer qiangZhiFaWeiSheDing;

    private Integer qiangZhiMoShiKaiGuan;

    private Integer pidKongZhiJiFenXiShu;

    private Integer fengLiangFanKuan;

    private Double fangJianShiJiYaLi;

    private String gongKuangMoShi;

    private Integer shuangGongKuangQieHuanShiJian;

    private Integer fengLiangSheDing;

    private Double yaLiSheDing;

    private String deviceType;

    private String huiFengJiShouZiDong;

    private String huiFengJiGuZhang;

    private String huiFengJiYunXing;

    private String huiFengMiBiKaiGuanKongZhi;

    private String huiFengMiBiGuanDaoWei;

    private String huiFengMiBiKaiDaoWei;

    private String huiFengJiQiTing;

    private String yuanXinFengKouZengJiaXinFengFaKaiGuanKongZhi;

    private String zengJiaXinFengKouXinFengFaKaiGuanKongZhi;

    private String yuanXinFengKouZengJiaXinFengFaGuanDaoWei;

    private String zengJiaXinFengKouXinFengFaGuanDaoWei;

    private String yuanXinFengKouZengJiaXinFengFaKaiDaoWei;

    private String zengJiaXinFengKouXinFengFaKaiDaoWei;

    private String moShiQieHuan;

    private String gongKuangQieHuan;

    private String huiFengJiPinLvFanKuan;

    private Integer pidKongZhiBiLiXiShu;

    private String paiFengFaZhiGuanFengFaFanKuan;

    private Date createTime;

    public KongTiaoDataDTO(KongTiaoDataEntity entity) {
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
    }

    public KongTiaoDataDTO(AddKongTiaoDataCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this);
        }
    }
}
