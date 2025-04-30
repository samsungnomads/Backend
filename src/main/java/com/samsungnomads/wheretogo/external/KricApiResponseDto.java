package com.samsungnomads.wheretogo.external;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class KricApiResponseDto {

    private Header header;
    private List<StationInfo> body;

    @Getter
    @NoArgsConstructor
    public static class Header {
        private Integer resultCnt;
        private String resultCode;
        private String resultMsg;
    }

    @Getter
    @NoArgsConstructor
    public static class StationInfo {
        private String mreaWideCd;
        private String routCd;
        private String routNm;
        private Integer stinConsOrdr;
        private String railOprIsttCd;
        private String lnCd;
        private String stinCd;
        private String stinNm;
    }
}
