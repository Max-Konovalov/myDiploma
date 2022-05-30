package ru.mkonovalov.jurdoc.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<R> {
    private R result;
    private int status;
    private boolean ok;
    private String message;

    public static <R> ApiResponse<R> of(R result, int status, String message) {
        return new ApiResponse<>(
                result,
                status,
                status >= 200 && status < 400,
                message
        );
    }

    public static <R> ApiResponse<R> success(R result) {
        if (result instanceof MessageResponse) {
            return ApiResponse.of(null, 200, ((MessageResponse) result).getMessage());
        }

        return ApiResponse.of(result, 200,  "ok");
    }

    public static ApiResponse<Void> success() {
        return ApiResponse.success(null);
    }
}
