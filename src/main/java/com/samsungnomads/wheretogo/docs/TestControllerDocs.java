package com.samsungnomads.wheretogo.docs;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Test", description = "테스트 API")
public interface TestControllerDocs {

    @Operation(summary = "Test API", description = "Test API 입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "서버 정상",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(example = "Test Endpoint"))
            )
    })
    String test();
}
