package com.samsungnomads.wheretogo.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class KakaoApiResponseDto {

    private Meta meta;
    private List<Document> documents;

    @Getter
    @NoArgsConstructor
    public static class Meta {
        private int totalCount;
        private int pageableCount;
        private boolean isEnd;
        private SameName sameName;
    }

    @Getter
    @NoArgsConstructor
    public static class SameName {
        private List<String> region;
        private String keyword;
        private String selectedRegion;
    }

    @Getter
    @NoArgsConstructor
    public static class Document {

        @JsonProperty("place_name")  // Kakao API에서 'place_name'을 'name' 필드에 매핑
        private String name;

        @JsonProperty("road_address_name")  // 'address_name'을 'address' 필드에 매핑
        private String address;

        @JsonProperty("phone")
        private String phone;

        @JsonProperty("place_url")  // 'place_url'을 'url' 필드에 매핑
        private String url;

        @JsonProperty("category_name")
        private String categoryName;

        @JsonProperty("category_group_name")
        private String categoryGroupName;

        @JsonProperty("x")
        private String longitude;

        @JsonProperty("y")
        private String latitude;

        @JsonProperty("distance")
        private String distance;
    }
}
