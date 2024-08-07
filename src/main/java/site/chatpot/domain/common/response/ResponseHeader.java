package site.chatpot.domain.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseHeader {
    private boolean isSuccessful;
    private int resultCode;
    private String resultMessage;

    @Builder
    public ResponseHeader(boolean isSuccessful, int resultCode, String resultMessage) {
        this.isSuccessful = isSuccessful;
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public boolean getIsSuccessful() {
        return isSuccessful;
    }
}
