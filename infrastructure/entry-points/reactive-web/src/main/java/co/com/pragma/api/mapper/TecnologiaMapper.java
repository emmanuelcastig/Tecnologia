package co.com.pragma.api.mapper;

import co.com.pragma.api.dto.TecnologiaRequest;
import co.com.pragma.api.dto.TecnologiaResponse;
import co.com.pragma.model.tecnologia.Tecnologia;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TecnologiaMapper {
    Tecnologia toDomain(TecnologiaRequest request);
    TecnologiaResponse toResponse(Tecnologia domain);
}
