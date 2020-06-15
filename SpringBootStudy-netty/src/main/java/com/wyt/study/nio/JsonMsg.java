package com.wyt.study.nio;

import lombok.Data;

/**
 * json格式消息实体类
 */
@Data
public class JsonMsg {
    //消息类型
    public Integer msgType;
    //发信人
    public String user;
    //收信人
    public String receiver;
    //消息内容
    public String msg;
    //时间
    public String time;
}
