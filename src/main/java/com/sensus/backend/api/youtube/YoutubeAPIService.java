package com.sensus.backend.api.youtube;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.google.common.base.Charsets;
import com.sensus.backend.api.youtube.data.CommentPart;
import jdk.nashorn.internal.runtime.options.Option;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class YoutubeAPIService {
    private final String youtubeDataDeveloperKey;
    private final YouTube youtubeService;

    public YoutubeAPIService(String youtubeDataDeveloperKey, YouTube youtubeService) {
        this.youtubeDataDeveloperKey = youtubeDataDeveloperKey;
        this.youtubeService = youtubeService;
    }

    public CommentThreadListResponse makeRequest(String url, List<CommentPart> part) throws Exception {
        YouTube.CommentThreads.List request = youtubeService.commentThreads().list(part.stream().map(CommentPart::getParam).collect(Collectors.joining(",")));

        return request.setKey(youtubeDataDeveloperKey)
                .setVideoId(parseVideoId(url))
                .execute();
    }

    public CommentThreadListResponse makeRequest(String url, List<CommentPart> part, String pageToken) throws Exception {
        YouTube.CommentThreads.List request = youtubeService.commentThreads().list(part.stream().map(CommentPart::getParam).collect(Collectors.joining(",")));

        return request.setKey(youtubeDataDeveloperKey)
                .setVideoId(parseVideoId(url))
                .setPageToken(pageToken)
                .execute();
    }

    private static String parseVideoId(String url) throws URISyntaxException, MalformedURLException {
        return URLEncodedUtils.parse(new URI(url), Charsets.UTF_8.name())
                .stream().filter(nameValuePair -> nameValuePair.getName().equals("v"))
                .findFirst()
                .orElseThrow(MalformedURLException::new)
                .getValue();
    }
}
