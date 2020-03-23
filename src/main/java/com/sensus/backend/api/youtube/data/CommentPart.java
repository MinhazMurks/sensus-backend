package com.sensus.backend.api.youtube.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum CommentPart {
    ID("id"),
    REPLIES("replies"),
    SNIPPET("snippet");

    String param;
}
