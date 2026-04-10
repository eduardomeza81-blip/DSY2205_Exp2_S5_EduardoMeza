package com.tienda.productos.controller;

import com.tienda.productos.model.Producto;
import com.tienda.productos.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.listar();
    }

    @GetMapping("/{id}")
    public Optional<Producto> obtenerProducto(@PathVariable Long id) {
        return productoService.obtenerPorId(id);
    }

    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoService.guardar(producto);
    }

    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        return productoService.actualizar(id, producto);
    }

    @DeleteMapping("/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.eliminar(id);
        return "Producto eliminado";
    }

    @GetMapping("/buscar")
    public List<Producto> buscarPorNombre(@RequestParam String nombre) {
        return productoService.buscarPorNombre(nombre);
    }

    @PostMapping("/comprar/{id}")
    public Map<String, Object> comprarProducto(@PathVariable Long id, @RequestBody Map<String, Integer> datos) {
        Integer cantidad = datos.get("cantidad");

        Producto productoActualizado = productoService.comprarProducto(id, cantidad);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Compra realizada con éxito");
        respuesta.put("producto", productoActualizado.getNombre());
        respuesta.put("stockRestante", productoActualizado.getStock());
        respuesta.put("cantidadComprada", cantidad);

        return respuesta;
    }
}