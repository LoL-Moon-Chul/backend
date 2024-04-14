package com.lolmoonchul.lolmoonchul.post.application.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class FetchPostsResponse {

    List<PostResponse> postResponses;
    long totalPosts;
    int totalPages;

    public FetchPostsResponse(List<PostResponse> postResponses, long totalPosts, int totalPages) {
        this.postResponses = postResponses;
        this.totalPosts = totalPosts;
        this.totalPages = totalPages;
    }
}
