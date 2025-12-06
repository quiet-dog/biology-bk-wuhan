package com.biology.domain.manage.kongTiaoData.command;

import lombok.Data;

@Data
public class AddKongTiaoDataCommand {

    private Boolean isOnline;

    private Long kongTiaoDataId;

    private String deviceSn;

    private Double zhiBanGongKuanYaLiSheDing;

    private Integer pidKongZhiBiLiXiShu;

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

    private String paiFengFaZhiGuanFengFaFanKuan;

}
