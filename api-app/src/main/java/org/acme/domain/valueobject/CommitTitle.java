package org.acme.domain.valueobject;

public class CommitTitle {
    private final String title;

    public CommitTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        this.title = title;
    }

    public String getValue() {
        return title;
    }
}