package pe.scotiabank.ms.alumnos.service.infrastructure.common.response;

import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class ApiResponseBodyAdvice extends ResponseBodyResultHandler {

    public ApiResponseBodyAdvice(ServerCodecConfigurer serverCodecConfigurer,
                                 RequestedContentTypeResolver resolver) {
        super(serverCodecConfigurer.getWriters(), resolver);
    }

    @Override
    public Mono<Void> handleResult(ServerWebExchange exchange, HandlerResult result) {

        Object returnValue = result.getReturnValue();

        if (returnValue instanceof Mono<?> mono) {
            return super.handleResult(exchange,
                    new HandlerResult(result.getHandler(),
                            mono.map(obj -> {
                                if (obj instanceof ApiErrorResponse) {
                                    return obj;
                                }
                                return new ApiResponse<>(obj);
                            }),
                            result.getReturnTypeSource()));
        }

        if (returnValue instanceof Flux<?> flux) {
            return super.handleResult(exchange,
                    new HandlerResult(result.getHandler(),
                            flux.collectList().map(ApiResponse::new),
                            result.getReturnTypeSource()));
        }

        if (returnValue instanceof ApiErrorResponse) {
            return super.handleResult(exchange, result);
        }

        return super.handleResult(exchange,
                new HandlerResult(result.getHandler(),
                        Mono.just(new ApiResponse<>(returnValue)),
                        result.getReturnTypeSource()));

    }

}
