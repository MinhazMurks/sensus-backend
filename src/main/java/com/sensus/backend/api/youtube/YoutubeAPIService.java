package com.sensus.backend.api.youtube;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.sensus.backend.api.youtube.data.CommentPart;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class YoutubeAPIService {
    private final String youtubeDataDeveloperKey;
    private final YouTube youtubeService;

    public YoutubeAPIService(String youtubeDataDeveloperKey, YouTube youtubeService) {
        this.youtubeDataDeveloperKey = youtubeDataDeveloperKey;
        this.youtubeService = youtubeService;
    }

    public CommentThreadListResponse makeRequest(String videoId, List<CommentPart> part) throws Exception {
        // Define and execute the API request
        YouTube.CommentThreads.List request = youtubeService.commentThreads().list(part.stream().map(CommentPart::getParam).collect(Collectors.joining(",")));
        return request.setKey(youtubeDataDeveloperKey)
                .setVideoId(videoId)
                .execute();
    }
}
