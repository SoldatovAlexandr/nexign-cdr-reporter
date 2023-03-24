package edu.asoldatov.nexigncdrreporter.common;

import java.util.Arrays;

public enum CallType {

    OUTGOING("01"),
    INCOMING("02");

    private final String code;

    CallType(String code) {
        this.code = code;
    }

    public static CallType getByCode(String code) {
        return Arrays.stream(values())
                .filter(v -> v.code.equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getCode() {
        return code;
    }
}
