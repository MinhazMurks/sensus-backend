package com.sensus.backend.api.youtube;

import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.sensus.backend.api.models.YoutubeComment;
import com.sensus.backend.api.models.responses.CommentsResponse;
import com.sensus.backend.api.youtube.data.CommentPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.sensus.backend.api.models.YoutubeComment.convertFromCommentThread;

@RestController
public class YoutubeController {
    private static final String API_VERSION = "v1";
    private static final String API_URL = "/api/" + API_VERSION;

    @Autowired
    YoutubeAPIService youtubeAPIService;

    @GetMapping(API_URL + "/comments")
    public List<YoutubeComment> getComments(@RequestParam String videoUrl) throws Exception {
        String videoId = videoUrl;
        ArrayList<YoutubeComment> youtubeComments = new ArrayList<>();

        for(CommentThread commentThread : youtubeAPIService.makeRequest(videoId, Arrays.asList(CommentPart.ID, CommentPart.REPLIES, CommentPart.SNIPPET)).getItems()) {
            youtubeComments.add(convertFromCommentThread(commentThread));
        }

        return youtubeComments;
    }
}