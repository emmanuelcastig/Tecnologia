package co.com.pragma.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TecnologiaRequest {

    @NotBlank(message = "El nombre es obligatorio y no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria y no puede estar vacía")
    @Size(max = 90, message = "La descripción no puede tener más de 90 caracteres")
    private String descripcion;
}
