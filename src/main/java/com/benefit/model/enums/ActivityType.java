package com.benefit.model.enums;

/**
 * @author Allen
 * @date 2025/6/6 14:24
 */
public enum ActivityType {

    PERCENT_DISCOUNT("百分比折扣"),
    AMOUNT_DISCOUNT("金额折扣"),
    GIFT("买赠活动");

    private final String description;

    ActivityType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static ActivityType fromString(String name) {
        if (name == null) {return null;}
        for (ActivityType type : ActivityType.values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name() + " (" + description + ")";
    }
}
