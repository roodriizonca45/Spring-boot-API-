package com.utn.productos.dto;

import com.utn.productos.model.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "DTO para crear o actualizar un producto")
public record ProductoDTO(

        @Schema(description = "Nombre del producto", example = "Zapatillas Runner X")
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
        String nombre,

        @Schema(description = "Descripción del producto", example = "Zapatillas livianas para running")
        @Size(max = 500, message = "La descripción no puede superar 500 caracteres")
        String descripcion,

        @Schema(description = "Precio del producto", example = "19999.99")
        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor o igual a 0.01")
        Double precio,

        @Schema(description = "Stock disponible", example = "15")
        @NotNull(message = "El stock es obligatorio")
        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer stock,

        @Schema(description = "Categoría del producto", example = "DEPORTES")
        @NotNull(message = "La categoría es obligatoria")
        Categoria categoria
) {}
