package co.com.pragma.api;

import co.com.pragma.api.dto.TecnologiaRequest;
import co.com.pragma.api.mapper.TecnologiaMapper;
import co.com.pragma.usecase.tecnologia.TecnologiaUseCase;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Handler {

    private final TecnologiaUseCase tecnologiaUseCase;
    private final Validator validator;
    private final TransactionalOperator transactionalOperator;
    private final TecnologiaMapper tecnologiaMapper;

    public Mono<ServerResponse> crearTecnologia(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(TecnologiaRequest.class)
                .flatMap(this::validacion)
                .map(tecnologiaMapper::toDomain)
                .as(transactionalOperator::transactional)
                .flatMap(tecnologiaUseCase::crearTecnologia)
                .then(ServerResponse.status(HttpStatus.CREATED).build())
                .onErrorResume(ValidationException.class, e ->
                        ServerResponse.badRequest().bodyValue(e.getMessage()))
                .onErrorResume(IllegalArgumentException.class, e ->
                        ServerResponse.status(HttpStatus.CONFLICT).bodyValue(e.getMessage()));
    }


    public Mono<ServerResponse> listarTecnologias(ServerRequest serverRequest) {
        return tecnologiaUseCase.obtenerTecnologias()
                .map(tecnologiaMapper::toResponse)
                .collectList()
                .flatMap(tecnologias -> {
                    if (tecnologias.isEmpty()) {
                        return ServerResponse.noContent().build();
                    }
                    return ServerResponse.ok().bodyValue(tecnologias);
                });
    }


    public Mono<TecnologiaRequest> validacion(TecnologiaRequest request) {
        Set<ConstraintViolation<TecnologiaRequest>> violaciones = validator.validate(request);
        if (!violaciones.isEmpty()) {
            String errorMessage = violaciones.stream()
                    .map(violation -> violation.getPropertyPath() + ": " +
                            violation.getMessage())
                    .collect(Collectors.joining(", "));
            return Mono.error(new ValidationException(errorMessage));
        }
        return Mono.just(request);
    }
}
