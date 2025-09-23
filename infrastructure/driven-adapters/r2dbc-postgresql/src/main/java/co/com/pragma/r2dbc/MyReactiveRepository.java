package co.com.pragma.r2dbc;

import co.com.pragma.r2dbc.entity.TecnologiaEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface MyReactiveRepository extends ReactiveCrudRepository<TecnologiaEntity, Long>, ReactiveQueryByExampleExecutor<TecnologiaEntity> {
    public Mono<TecnologiaEntity> findTecnologiaByNombre(String nombre);

}
