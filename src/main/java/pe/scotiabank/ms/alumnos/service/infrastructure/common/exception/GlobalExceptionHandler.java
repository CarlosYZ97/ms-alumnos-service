package pe.scotiabank.ms.alumnos.service.infrastructure.common.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import pe.scotiabank.ms.alumnos.service.infrastructure.common.response.ApiErrorResponse;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ApiErrorResponse> handleBusinessException(BusinessException ex, ServerWebExchange exchange) {
        return Mono.just(
                new ApiErrorResponse(
                        ApiError.builder()
                                .codigo(ex.getCode())
                                .message(ex.getMessage())
                                .path(exchange.getRequest().getPath().value())
                                .build()
                )
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ApiErrorResponse> handleGenericException(Exception ex, ServerWebExchange exchange) {
        return Mono.just(
                new ApiErrorResponse(
                        ApiError.builder()
                                .message(ex.getMessage())
                                .codigo(ErrorCode.GENERIC_ERROR.getCode())
                                .path(exchange.getRequest().getPath().value())
                                .build()
                )
        );
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ApiErrorResponse> handleValidationException(WebExchangeBindException ex,
                                                            ServerWebExchange exchange) {

        String errorMessages = ex.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .reduce((m1, m2) -> m1 + ", " + m2)
                .orElse(ErrorCode.VALIDATION_ERROR.getDefaultMessage());

        return Mono.just(
                new ApiErrorResponse(
                        ApiError.builder()
                                .codigo(ErrorCode.VALIDATION_ERROR.getCode())
                                .message(errorMessages)
                                .path(exchange.getRequest().getPath().value())
                                .build()
                )
        );
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Mono<ApiErrorResponse> handleOptimisticLock(OptimisticLockingFailureException ex,
                                                       ServerWebExchange exchange) {
        return Mono.just(
                new ApiErrorResponse(
                        ApiError.builder()
                                .codigo(ErrorCode.CONCURRENCY_ERROR.getCode())
                                .message(ErrorCode.CONCURRENCY_ERROR.getDefaultMessage())
                                .path(exchange.getRequest().getPath().value())
                                .build()
                )
        );
    }

}
