
package com.rimas.explorenepal.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExplorePost {

    @SerializedName("PostList")
    @Expose
    private List<PostList> postList = null;

    public List<PostList> getPostList() {
        return postList;
    }

    public void setPostList(List<PostList> postList) {
        this.postList = postList;
    }

}
