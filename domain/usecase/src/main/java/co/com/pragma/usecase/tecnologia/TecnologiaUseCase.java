package co.com.pragma.usecase.tecnologia;

import co.com.pragma.model.tecnologia.Tecnologia;
import co.com.pragma.model.tecnologia.gateways.TecnologiaRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TecnologiaUseCase {

    private final TecnologiaRepository tecnologiaRepository;

    public Mono<Void> crearTecnologia(Tecnologia tecnologia) {
        return tecnologiaRepository.buscarTecnologiaPorNombre(tecnologia.getNombre())
                .flatMap(existente -> Mono.<Void>error(
                        new IllegalArgumentException("La tecnología ya existe: " + tecnologia.getNombre())
                ))
                .switchIfEmpty(
                        tecnologiaRepository.crearTecnologia(tecnologia)
                );
    }
}
