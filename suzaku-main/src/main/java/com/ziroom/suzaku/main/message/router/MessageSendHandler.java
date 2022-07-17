package com.ziroom.suzaku.main.message.router;

/**
 * @author xuzeyu
 */
public interface MessageSendHandler {

    Integer support();

    void sendMessage(String data);
}
