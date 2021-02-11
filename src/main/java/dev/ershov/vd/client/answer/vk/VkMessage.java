package dev.ershov.vd.client.answer.vk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class VkMessage {
    private long date;
    @JsonProperty("from_id")
    private int fromId;
    private int id;
    private int out;
    @JsonProperty("peer_id")
    private int peerId;
    private String text;
    @JsonProperty("conversation_message_id")
    private int conversationMessageId;
    private List<Object> attachments;
    private String payload;
}
