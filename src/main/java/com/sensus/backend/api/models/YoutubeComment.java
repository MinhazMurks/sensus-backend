package com.sensus.backend.api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.api.services.youtube.model.Comment;
import com.google.api.services.youtube.model.CommentSnippet;
import com.google.api.services.youtube.model.CommentThread;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data
@AllArgsConstructor
@JsonInclude(NON_EMPTY)
public class YoutubeComment {
    @NonNull
    private String commentId;
    @NonNull
    private String commentText;
    @NonNull
    private Long likeCount;
    @NonNull
    private String author;
    @NonNull
    private String authorChannelUrl;
    @NonNull
    private String authorImageUrl;
    @NonNull
    private String videoId;
    @NonNull
    private Boolean isTopLevelComment;
    @NonNull
    private List<YoutubeComment> replies;

    public static YoutubeComment convertFromCommentThread(CommentThread commentThread, boolean includeReplies) {
        CommentSnippet commentSnippet = commentThread.getSnippet().getTopLevelComment().getSnippet();

        YoutubeComment youtubeComment = new YoutubeComment(
                commentThread.getId(),
                commentSnippet.getTextOriginal(),
                commentSnippet.getLikeCount(),
                commentSnippet.getAuthorDisplayName(),
                commentSnippet.getAuthorChannelUrl(),
                commentSnippet.getAuthorProfileImageUrl(),
                commentSnippet.getVideoId(),
                true,
                new ArrayList<YoutubeComment>()
        );

        if(commentThread.getReplies() != null && !commentThread.getReplies().getComments().isEmpty() && includeReplies) {
            youtubeComment.setReplies(new ArrayList<YoutubeComment>());
            for(Comment replyComment : commentThread.getReplies().getComments())
            {
                CommentSnippet replyCommentSnippet = replyComment.getSnippet();
                YoutubeComment reply = new YoutubeComment(
                        replyComment.getId(),
                        replyCommentSnippet.getTextOriginal(),
                        replyCommentSnippet.getLikeCount(),
                        replyCommentSnippet.getAuthorDisplayName(),
                        replyCommentSnippet.getAuthorChannelUrl(),
                        replyCommentSnippet.getAuthorProfileImageUrl(),
                        replyCommentSnippet.getVideoId(),
                        false,
                        Collections.emptyList()
                        );
                youtubeComment.getReplies().add(reply);
            }
        }
        return youtubeComment;
    }
}
