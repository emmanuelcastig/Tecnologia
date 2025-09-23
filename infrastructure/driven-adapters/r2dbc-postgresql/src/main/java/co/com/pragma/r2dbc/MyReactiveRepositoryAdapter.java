package co.com.pragma.r2dbc;

import co.com.pragma.model.tecnologia.Tecnologia;
import co.com.pragma.model.tecnologia.gateways.TecnologiaRepository;
import co.com.pragma.r2dbc.entity.TecnologiaEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class MyReactiveRepositoryAdapter extends ReactiveAdapterOperations<
    Tecnologia,
        TecnologiaEntity,
    Long,
    MyReactiveRepository
> implements TecnologiaRepository {
    public MyReactiveRepositoryAdapter(MyReactiveRepository repository, ObjectMapper mapper) {

        super(repository, mapper, d -> mapper.map(d, Tecnologia.class));
    }

    @Override
    public Mono<Void> crearTecnologia(Tecnologia tecnologia) {
        return repository.save(toData(tecnologia)).then();
    }

    @Override
    public Mono<Tecnologia> buscarTecnologiaPorNombre(String nombre) {
        return repository.findTecnologiaByNombre(nombre)
                .map(this::toEntity);
    }
}
