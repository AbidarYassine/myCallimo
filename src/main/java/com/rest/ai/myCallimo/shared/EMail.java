package com.rest.ai.myCallimo.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EMail {
    String to;
    String from;
    String subject;
    String content;
    private Map<String, Object> data;
}
