package app.budget.domain.member.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public enum AgeGroup {
    GROUP_10("10대"),
    GROUP_20("20대"),
    GROUP_30("30대"),
    GROUP_40("40대"),
    GROUP_50("50대"),
    GROUP_60("60대"),
    GROUP_60OVER("60대 이상");

    private final String code;

    public static AgeGroup getAgeGroup(String code) {
        switch (code) {
            case "10대":
                return AgeGroup.GROUP_10;
            case "20대":
                return AgeGroup.GROUP_20;
            case "30대":
                return AgeGroup.GROUP_30;
            case "40대":
                return AgeGroup.GROUP_40;
            case "50대":
                return AgeGroup.GROUP_50;
            case "60대":
                return AgeGroup.GROUP_60;

            default:
                return AgeGroup.GROUP_60OVER;
        }
    }
}
