package com.eozyilmaz_stj.gnnyemei;

public class LikeDislike {

    private int like;
    private int dislike;
    private boolean hasLiked = false;
    private boolean hasDisliked = false;

    public boolean getHasLiked() {
        return hasLiked;
    }

    public void setHasLiked(boolean hasLiked) {
        this.hasLiked = hasLiked;
    }

    public boolean getHasDisliked() {
        return hasDisliked;
    }

    public void setHasDisliked(boolean hasDisliked) {
        this.hasDisliked = hasDisliked;
    }

    public LikeDislike() {
    }

    public LikeDislike(int like, int dislike) {
        this.like = like;
        this.dislike = dislike;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }
}
