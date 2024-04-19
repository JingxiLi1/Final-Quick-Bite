package com.assignment.enums;

/**
 * Payment
 */
public enum PayChannel {
    Online(0, "Credit"),
    CASH(2, "Cash");

    private final Integer channelCode;
    private final String desc;


    PayChannel(Integer channelCode, String desc) {
        this.channelCode = channelCode;
        this.desc = desc;
    }

    public Integer getChannelCode() {
        return channelCode;
    }

    public String getDesc() {
        return desc;
    }

    public static Integer getChannelCodyByDescription(String description) {
        for (PayChannel channel : PayChannel.values()) {
            if (channel.getDesc().equals(description)) {
                return channel.getChannelCode();
            }
        }
        return null;
    }

    public static String getChannelCodyByCode(Integer code) {
        for (PayChannel channel : PayChannel.values()) {
            if (channel.getChannelCode().equals(code)) {
                return channel.getDesc();
            }
        }
        return null;
    }
}
