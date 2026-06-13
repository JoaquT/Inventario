package com.inventarios.invsoft.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventarios.invsoft.model.Producto;
import com.inventarios.invsoft.service.ProductoService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoService productoService;

    ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto){
        return new ResponseEntity<>(productoService.guardarProducto(producto),HttpStatus.CREATED);
    }
    @GetMapping
    public List<Producto> listarTodos(){
        return productoService.obtenerTodos();
    }
    @GetMapping("/{codigo}")
    public ResponseEntity<Producto> obtenerPorCodigo(@PathVariable String codigo){
        return productoService.obtenerPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{codigo}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable String codigo,@RequestBody Producto producto){
        try{
            return ResponseEntity.ok(productoService.actualizarProducto(codigo, producto));
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
    
}   
