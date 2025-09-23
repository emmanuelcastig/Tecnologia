package co.com.pragma.model.tecnologia.gateways;

import co.com.pragma.model.tecnologia.Tecnologia;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TecnologiaRepository {
    Mono<Void> crearTecnologia(Tecnologia tecnologia);
    Mono<Tecnologia> buscarTecnologiaPorNombre(String nombre);
    Flux<Tecnologia> obtenerTecnologias();
}
