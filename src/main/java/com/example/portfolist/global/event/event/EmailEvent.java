package com.example.portfolist.global.event.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailEvent {

    private final String toEmail;
    private final String title;
    private final String content;

}
