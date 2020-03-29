package com.sensus.backend.api.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.api.services.youtube.model.Comment;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.sensus.backend.api.models.YoutubeComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.sensus.backend.api.models.YoutubeComment.convertFromCommentThread;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_EMPTY)
public class CommentsResponse {
    private String pageToken;
    private final List<YoutubeComment> youtubeComments = new ArrayList<>();

    public static CommentsResponse createResponse(CommentThreadListResponse commentThreadListResponse, boolean includeReplies) {
        CommentsResponse commentsResponse = new CommentsResponse(commentThreadListResponse.getNextPageToken());
        for(CommentThread commentThread : commentThreadListResponse.getItems())
        {
            commentsResponse.getYoutubeComments().add(convertFromCommentThread(commentThread, includeReplies));
        }
        return commentsResponse;
    }
}