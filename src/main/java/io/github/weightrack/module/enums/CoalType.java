package io.github.weightrack.module.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum CoalType {

    FOUR_ONE_WASHED_COAL("4-1水洗煤"),
    FOUR_TWO_WASHED_COAL("4-2水洗煤"),
    FOUR_TWO_RAW_COAL("4-2原煤"),
    FIVE_ONE_WASHED_COAL("5-1水洗煤"),
    FIVE_ONE_RAW_COAL("5-1原煤"),
    SIX_ONE_WASHED_COAL("6-1水洗煤"),
    SIX_ONE_RAW_COAL("6-1原煤");
    private final String coalTypeDescription;

    public static boolean isValidCoalType(String coalTypeDescription) {
        for (CoalType coalType : CoalType.values()) {
            if (coalType.getCoalTypeDescription().equals(coalTypeDescription)) {
                return true;
            }
        }
        return false;
    }
}
