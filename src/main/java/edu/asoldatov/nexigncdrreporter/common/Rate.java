package edu.asoldatov.nexigncdrreporter.common;

import java.util.Arrays;

public enum Rate {
    UNLIMITED("06"),
    MINUTE("03"),
    REGULAR("11");

    private final String index;

    Rate(String index) {
        this.index = index;
    }

    public static Rate getByIndex(String index) {
        return Arrays.stream(values())
                .filter(v -> v.index.equals(index))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getIndex() {
        return index;
    }
}
