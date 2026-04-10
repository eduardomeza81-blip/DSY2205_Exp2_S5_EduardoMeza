package com.tienda.usuarios.controller;

import com.tienda.usuarios.model.Usuario;
import com.tienda.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listar();
    }

    @GetMapping("/{id}")
    public Optional<Usuario> obtenerUsuario(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id);
    }

    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioService.guardar(usuario);
    }

    @PutMapping("/{id}")
    public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.actualizar(id, usuario);
    }

    @DeleteMapping("/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return "Usuario eliminado";
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> datos) {
        String correo = datos.get("correo");
        String password = datos.get("password");

        if (correo == null || correo.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new RuntimeException("Correo y contraseña son obligatorios");
        }

        Optional<Usuario> usuario = usuarioService.login(correo, password);

        Map<String, Object> respuesta = new HashMap<>();

        if (usuario.isPresent()) {
            respuesta.put("mensaje", "Login correcto");
            respuesta.put("autenticado", true);
            respuesta.put("id", usuario.get().getId());
            respuesta.put("nombre", usuario.get().getNombre());
            respuesta.put("correo", usuario.get().getCorreo());
            respuesta.put("rol", usuario.get().getRol());
        } else {
            respuesta.put("mensaje", "Credenciales incorrectas");
            respuesta.put("autenticado", false);
        }

        return respuesta;
    }
}