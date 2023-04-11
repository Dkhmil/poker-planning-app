package com.khmil.management.enums;

public enum UserStoryStatus {
    PENDING("Pending"),
    VOTING("Voting"),
    VOTED("Voted");

    private final String displayName;

    private UserStoryStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
