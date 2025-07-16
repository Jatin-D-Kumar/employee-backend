package com.jatin.usermodule.payload;

import lombok.*;

import java.time.OffsetDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Standard API Response Wrapper")
public class ApiResponse<T> {

    @Schema(description = "Whether the request was successful", example = "true")
    private boolean success;

    @Schema(description = "Message about the response", example = "User registered successfully")
    private String message;

    @Schema(description = "Actual data being returned")
    private T data;

    @Schema(description = "Time when response was generated", example = "2025-07-14T22:23:00.123Z")
    private OffsetDateTime timestamp;

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(OffsetDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .data(null)
                .timestamp(OffsetDateTime.now())
                .build();
    }
}
