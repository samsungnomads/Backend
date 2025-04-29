package com.samsungnomads.wheretogo.domain.station.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LineCode {
    LINE_1("1", "1호선"),
    LINE_2("2", "2호선"),
    LINE_3("3", "3호선"),
    LINE_4("4", "4호선"),
    LINE_5("5", "5호선"),
    LINE_6("6", "6호선"),
    LINE_7("7", "7호선"),
    LINE_8("8", "8호선"),
    LINE_9("9", "9호선"),
    AIRPORT("A1", "공항"),
    GYEONGUI_JUNGANG("K4", "경의중앙"),
    GYEONGCHUN("K2", "경춘"),
    SUIN_BUNDANG("K1", "수인분당"),
    SHIN_BUNDANG("D1", "신분당"),
    GYEONGGANG("K5", "경강"),
    SEOHAE("WS", "서해"),
    INCHEON_1("I1", "인천1"),
    INCHEON_2("I2", "인천2"),
    EVERLINE("E1", "에버라인"),
    UJEONGBU("U1", "의정부"),
    UI("UI", "우이신설"),
    KIMPO_GOLD("G1", "김포골드"),
    GTX_A("A", "GTX-A");

    private final String code;
    private final String name;

}
