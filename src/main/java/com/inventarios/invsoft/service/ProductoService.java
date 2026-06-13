package com.inventarios.invsoft.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.inventarios.invsoft.model.Producto;
import com.inventarios.invsoft.repository.ProductoRepository;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;

    ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Producto guardarProducto(Producto producto){
        if(producto.getCodigo()==null || producto.getCodigo().isEmpty()){
            producto.setCodigo("PROD-" + System.currentTimeMillis());
        }else{
            producto.setCodigo(producto.getCodigo().trim().toUpperCase());
        }
        return productoRepository.save(producto);
    }

    public List<Producto> obtenerTodos(){
        return productoRepository.findAll();
    }
    public List<Producto> buscarProductos(String termino){
        if (termino == null || termino.trim().isEmpty()){
            return productoRepository.findAll();
        }
        return productoRepository.findByCodigoContainingIgnoreCaseOrNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(
            termino, termino, termino
    );

    }

    public Optional<Producto> obtenerPorCodigo(String codigo){
        return productoRepository.findByCodigo(codigo);
    }

    public Producto actualizarProducto(String codigo, Producto productoDetalles){
        return productoRepository.findByCodigo(codigo).map(producto -> {
            producto.setNombre(productoDetalles.getNombre());
            producto.setDescripcion(productoDetalles.getDescripcion());
            producto.setStock(productoDetalles.getStock());
            producto.setPrecio(productoDetalles.getPrecio());
            return productoRepository.save(producto);
        }).orElseThrow(() -> new RuntimeException("Producto no encontrado con el codigo: "+ codigo));
    }

    public void eliminarProducto(String codigo){
        Producto producto = productoRepository.findByCodigo(codigo).orElseThrow(() -> new RuntimeException("Producto no encontrado con el codigo "+codigo));
        productoRepository.delete(producto);
    }

}
