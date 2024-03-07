package com.example.insightlogfe.model;

public class HomeFeedModelLinear {

    private int pic_feed_profile ;
    private int pic_feed_main;
    private String text_username;
    private String text_userbio;

    public HomeFeedModelLinear(int pic_feed_profile, int pic_feed_main, String text_username, String text_userbio) {
        this.pic_feed_profile = pic_feed_profile;
        this.pic_feed_main = pic_feed_main;
        this.text_username = text_username;
        this.text_userbio = text_userbio;
    }

    public int getPic_feed_profile() {
        return pic_feed_profile;
    }

    public int getPic_feed_main() {
        return pic_feed_main;
    }

    public String getText_username() {
        return text_username;
    }

    public String getText_userbio() {
        return text_userbio;
    }

    public void setPic_feed_profile(int pic_feed_profile) {
        this.pic_feed_profile = pic_feed_profile;
    }

    public void setPic_feed_main(int pic_feed_main) {
        this.pic_feed_main = pic_feed_main;
    }

    public void setText_username(String text_username) {
        this.text_username = text_username;
    }

    public void setText_userbio(String text_userbio) {
        this.text_userbio = text_userbio;
    }

}
