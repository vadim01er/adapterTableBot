package dev.ershov.vd.client.answer.tg;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TgResponse {
    @JsonProperty("update_id")
    private int updateId;
    private Message message;


    @Data
    public class Message {
        @JsonProperty("message_id")
        private int messageId;
        private Chat chat;
        private String text;
        @Data
        public class Chat {
            private int id;
        }
    }
}
