package io.github.weightrack.module;

import lombok.Data;

@SuppressWarnings("ALL")
@Data
public class TodaySummaryPanel {
    // 已登记
    int registered;
    // 未过空
    int emptyWeighing;
    // 已过空
    int fullWeighing;

}
