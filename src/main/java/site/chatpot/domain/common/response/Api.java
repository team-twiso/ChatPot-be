package site.chatpot.domain.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import site.chatpot.domain.common.exception.ErrorCode;

@Getter
@NoArgsConstructor
public class Api<T> {

    private static final String SUCCESS_MESSAGE = "SUCCESS";
    private ResponseHeader header;
    private T result;

    @Builder
    private Api(ResponseHeader header, T result) {
        this.header = header;
        this.result = result;
    }

    public static Api<Void> success() {
        return Api.<Void>builder()
                .header(ResponseHeader.builder()
                                .isSuccessful(true)
                                .resultCode(HttpStatus.OK.value())
                                .resultMessage(SUCCESS_MESSAGE).build())
                .build();
    }

    public static <T> Api<T> successWithData(T data) {
        return Api.<T>builder()
                .header(ResponseHeader.builder()
                                .isSuccessful(true)
                                .resultCode(HttpStatus.OK.value())
                                .resultMessage(SUCCESS_MESSAGE).build())
                .result(data)
                .build();
    }

    public static Api<Void> fail(ErrorCode errorCode) {
        return Api.<Void>builder()
                .header(ResponseHeader.builder()
                                .isSuccessful(false)
                                .resultCode(errorCode.getCode())
                                .resultMessage(errorCode.getMessage()).build())
                .build();
    }

    public static Api<Void> fail(ErrorCode errorCode, String message) {
        return Api.<Void>builder()
                .header(ResponseHeader.builder()
                                .isSuccessful(false)
                                .resultCode(errorCode.getCode())
                                .resultMessage(message).build())
                .build();
    }

    public static <T> Api<T> fail(ErrorCode errorCode, T data) {
        return Api.<T>builder()
                .header(ResponseHeader.builder()
                                .isSuccessful(false)
                                .resultCode(errorCode.getCode())
                                .resultMessage(errorCode.getMessage()).build())
                .result(data)
                .build();
    }
}
