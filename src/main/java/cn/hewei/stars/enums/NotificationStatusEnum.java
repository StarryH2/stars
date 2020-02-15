package cn.hewei.stars.enums;

/**
 * @Author 何为
 * @Daet 2020-02-15 19:15
 * @Description
 */
public enum  NotificationStatusEnum {
    UNREAD(0),
    READ(1);

    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
