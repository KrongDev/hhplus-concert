package com.hhplusconcert.common.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KafkaEvent {
    //
    private String id;
    private String message;

    public void validate() {
        //
        Assert.hasText(id, "id is required");
        Assert.hasText(message, "message is required");
    }

}
