package com.benefit.model.enums;

/**
 * @author Allen
 * @date 2025/6/6 14:20
 */
public enum Status {
    ACTIVE("激活",1),
    INACTIVE("未激活",0),
    EXPIRED("已过期",2),
    DELETED("已删除",3);

    private final String description;

    private final int value;

    Status(String description,int value) {
        this.description = description;
        this.value = value;
    }

    /**
     * 获取中文描述
     */
    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }

    /**
     * 根据字符串名获取对应的枚举（忽略大小写），找不到则返回 null
     */
    public static Status fromString(String name) {
        if (name == null) return null;
        for (Status status : Status.values()) {
            if (status.name().equalsIgnoreCase(name)) {
                return status;
            }
        }
        return null;
    }

    /**
     * 判断当前状态是否为有效（ACTIVE）
     */
    public boolean isActive() {
        return this == ACTIVE;
    }

    /**
     * 判断是否为已失效状态（EXPIRED 或 DELETED）
     */
    public boolean isInvalid() {
        return this == EXPIRED || this == DELETED;
    }

    @Override
    public String toString() {
        return this.name() + " (" + description + ")";
    }
}
