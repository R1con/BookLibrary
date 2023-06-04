package com.example.booklibrary.common.enums;

public enum BookColumnName {
    ID("id"),
    TITLE("title"),
    AUTHOR("author"),
    PAGES("pages");

    private final String name;

    private BookColumnName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
