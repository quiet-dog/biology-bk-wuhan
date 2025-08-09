package com.biology.domain.manage.smData.dto;

import java.util.List;

import lombok.Data;

@Data
public class PostRenTiDataDTO {
    private String battery;
    private int co2;
    private String co2_humility;
    private String co2_temp;
    private List<Number> huxi;
    private long sendTime;
    private String sn;
    private double temp;
    private String titai;
    private List<Number> xindian;
    private int xinlv;
    private String xueyang;

    private double parsePercentage(String str) {
        if (str == null)
            return 0;
        try {
            return Double.parseDouble(str.replace("%", "").trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public double getBatteryAsDouble() {
        return parsePercentage(battery);
    }

    public double getXueyangAsDouble() {
        return parsePercentage(xueyang);
    }

    public double getCo2HumilityAsDouble() {
        return parsePercentage(co2_humility);
    }

    public double getCo2TempAsDouble() {
        if (co2_temp == null)
            return 0;
        // 去掉单位，保留正负号
        String temp = co2_temp.trim()
                .replace("°C", "")
                .replace("℃", ""); // 有时候可能是另一种符号
        try {
            return Double.parseDouble(temp);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public double getCo2AsDouble() {
        return (double) co2;
    }

    // 转成double
    public double getXinlvAsDouble() {
        return (double) xinlv;
    }
}
