package com.onezero.ozerp.appbase.dto;

public enum SocialProvider {

    FACEBOOK("facebook"), TWITTER("twitter"), LINKEDIN("linkedin"), GOOGLE("google"), GITHUB("github"), LOCAL("local");

    private String providerType;

    SocialProvider(final String providerType) {
        this.providerType = providerType;
    }

    public String getProviderType() {
        return providerType;
    }
}