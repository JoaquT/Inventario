package com.inventarios.invsoft.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inventarios.invsoft.model.Producto;
import com.inventarios.invsoft.service.ProductoService;

@Controller
@RequestMapping("/productos")
public class ProductoViewController {

    private final  ProductoService productoService;

    ProductoViewController(ProductoService productoService) {
        this.productoService = productoService;
    }
    @GetMapping
    public String listarProductos(@RequestParam(value = "buscar", required = false) String buscar, Model model) {
    List<Producto> productos;
    
    if (buscar != null && !buscar.trim().isEmpty()) {
        productos = productoService.buscarProductos(buscar);
        model.addAttribute("buscar", buscar); 
    } else {
        productos = productoService.obtenerTodos();
    }
    
    model.addAttribute("productos", productos);
    return "productos/lista";
}

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        return "productos/formulario";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute("producto") Producto producto) {
        productoService.guardarProducto(producto);
        return "redirect:/productos"; 
    }

    @GetMapping("/editar/{codigo}")
    public String mostrarFormularioEditar(@PathVariable String codigo, Model model) {
        return productoService.obtenerPorCodigo(codigo)
                .map(producto -> {
                    model.addAttribute("producto", producto);
                    return "productos/formulario";
                }).orElse("redirect:/productos");
    }

    @GetMapping("/eliminar/{codigo}")
    public String eliminarProducto(@PathVariable String codigo) {
        productoService.eliminarProducto(codigo);
        return "redirect:/productos";
    }

}
