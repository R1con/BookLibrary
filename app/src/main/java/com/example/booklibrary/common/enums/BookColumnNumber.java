package com.example.booklibrary.common.enums;

public enum BookColumnNumber {
    ID(0),
    TITLE(1),
    AUTHOR(2),
    PAGES(3);

    private final int column;

    private BookColumnNumber(int column) {
        this.column = column;
    }

    public int getColumn() {
        return this.column;
    }
}
