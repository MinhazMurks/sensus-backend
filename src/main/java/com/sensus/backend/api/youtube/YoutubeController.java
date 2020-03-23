package com.sensus.backend.api.youtube;

import com.sensus.backend.api.models.responses.CommentsResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class YoutubeController {
    private static final String API_VERSION = "v1";
    private static final String API_URL = "/api/" + API_VERSION;

    @GetMapping(API_URL + "/comments")
    public CommentsResponse getComments(@RequestParam String videoId) {

        return null;
    }
}
