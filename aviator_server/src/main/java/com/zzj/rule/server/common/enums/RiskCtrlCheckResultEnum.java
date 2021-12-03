package com.zzj.rule.server.common.enums;

import java.util.Objects;

public enum RiskCtrlCheckResultEnum {
    NO_RISK(-1, "无任何风险"),
    ONLY_HAS_INFO(0, "无风险-但有信息级别提示"),
    ONLY_HAS_WEAK(9, "最多只有弱风险"),
    HAS_STRONG(99, "存在强风险"),
    ;

    private Integer level;
    private String desc;

    public Integer getLevel() {
        return level;
    }

    public String getDesc() {
        return desc;
    }

    RiskCtrlCheckResultEnum(Integer level, String desc) {
        this.level = level;
        this.desc = desc;
    }

    public static RiskCtrlCheckResultEnum newLevel(RiskCtrlCheckResultEnum oldResult, int newLevel) {
        RiskCtrlCheckResultEnum newResult = NO_RISK;
        if (newLevel > ONLY_HAS_WEAK.getLevel()) { // > 9 : strong
            newResult = HAS_STRONG;
        } else if (newLevel > ONLY_HAS_INFO.getLevel()) { // > 0 : weak
            newResult = ONLY_HAS_WEAK;
        } else if (newLevel > NO_RISK.getLevel()) { // > -1 : info
            newResult = ONLY_HAS_INFO;
        }

        if (newResult.getLevel() > oldResult.getLevel()) return newResult;
        return oldResult;
    }

    public static RiskCtrlCheckResultEnum getById(Integer level) {
        if (level == null) return null;
        for (RiskCtrlCheckResultEnum type : RiskCtrlCheckResultEnum.values()) {
            if (Objects.equals(type.getLevel(), level)) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return level + "|" + desc;
    }
}