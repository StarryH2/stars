package cn.hewei.stars.exception;

/**
 * @Author 何为
 * @Daet 2020-02-12 0:31
 * @Description
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"你找的问题不存在，要不换个试试？"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"当前操作需要登陆...请登陆后重试"),
    SYS_ERROR(2004,"服务器宕机了...要不稍等再试试!!!"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在..."),
    COMMENT_NOT_FOUND(2006,"你回复的评论不存在..."),
    COMMENT_IS_EMPTY(2007,"输入内容不能为空..."),
    READ_NOTIFICATION_FAIL(2008,"兄弟你这是读别人的信息呢？？？"),
    NOTIFICATION_NOT_FOUND(2009,"消息莫非是不翼而飞了？"),
    FILE_UPLOAD_FAIL(2010,"图片上传消失");



    private Integer code;
    private String massage;


    CustomizeErrorCode(Integer code, String massage) {
        this.massage = massage;
        this.code = code;
    }

    @Override
    public String getMassage() {
        return massage;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
