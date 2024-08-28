package site.chatpot.domain.common.exception;

public record ValidationError(
        String field,
        String value,
        String reason
) {

}
