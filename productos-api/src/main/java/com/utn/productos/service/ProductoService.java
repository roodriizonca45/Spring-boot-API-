package com.utn.productos.service;

import com.utn.productos.dto.ProductoDTO;
import com.utn.productos.dto.ProductoResponseDTO;
import com.utn.productos.exception.ProductoNotFoundException;
import com.utn.productos.model.Categoria;
import com.utn.productos.model.Producto;
import com.utn.productos.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public ProductoResponseDTO crearProducto(ProductoDTO dto) {
        Producto p = toEntity(dto);
        Producto saved = productoRepository.save(p);
        return toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerTodos() {
        return productoRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    @Transactional(readOnly = true)
    public ProductoResponseDTO obtenerPorId(Long id) {
        Producto p = productoRepository.findById(id).orElseThrow(() -> new ProductoNotFoundException(id));
        return toResponseDTO(p);
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerPorCategoria(Categoria categoria) {
        return productoRepository.findByCategoria(categoria).stream().map(this::toResponseDTO).toList();
    }

    public ProductoResponseDTO actualizarProducto(Long id, ProductoDTO dto) {
        Producto p = productoRepository.findById(id).orElseThrow(() -> new ProductoNotFoundException(id));
        p.setNombre(dto.nombre());
        p.setDescripcion(dto.descripcion());
        p.setPrecio(dto.precio());
        p.setStock(dto.stock());
        p.setCategoria(dto.categoria());
        Producto saved = productoRepository.save(p);
        return toResponseDTO(saved);
    }

    public ProductoResponseDTO actualizarStock(Long id, Integer nuevoStock) {
        Producto p = productoRepository.findById(id).orElseThrow(() -> new ProductoNotFoundException(id));
        p.setStock(nuevoStock);
        Producto saved = productoRepository.save(p);
        return toResponseDTO(saved);
    }

    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ProductoNotFoundException(id);
        }
        productoRepository.deleteById(id);
    }

    // Mapping helpers
    private Producto toEntity(ProductoDTO dto) {
        return Producto.builder()
                .nombre(dto.nombre())
                .descripcion(dto.descripcion())
                .precio(dto.precio())
                .stock(dto.stock())
                .categoria(dto.categoria())
                .build();
    }

    private ProductoResponseDTO toResponseDTO(Producto p) {
        return new ProductoResponseDTO(
                p.getId(),
                p.getNombre(),
                p.getDescripcion(),
                p.getPrecio(),
                p.getStock(),
                p.getCategoria()
        );
    }
}
