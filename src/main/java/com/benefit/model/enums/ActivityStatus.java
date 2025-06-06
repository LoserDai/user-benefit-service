package com.benefit.model.enums;

/**
 * @author Allen
 * @date 2025/6/6 14:25
 */

public enum ActivityStatus {

    NOT_STARTED("未开始",0),
    ONGOING("进行中",1),
    ENDED("已结束",2),
    CANCELED("已取消",3);

    private final String description;

    private final  int value;

    ActivityStatus(String description, int value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }

    public static ActivityStatus fromString(String name) {
        if (name == null) {return null;}
        for (ActivityStatus status : ActivityStatus.values()) {
            if (status.name().equalsIgnoreCase(name)) {
                return status;
            }
        }
        return null;
    }

    public boolean isFinished() {
        return this == ENDED || this == CANCELED;
    }

    public boolean isRunning() {
        return this == ONGOING;
    }

    @Override
    public String toString() {
        return this.name() + " (" + description + ")";
    }
}
