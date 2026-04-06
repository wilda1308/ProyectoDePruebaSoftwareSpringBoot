package co.edu.ucompensar.web.service;

import co.edu.ucompensar.web.model.Usuario;

import java.util.List;

public interface UsuarioService {
    // CREATE
    Usuario crear(Usuario usuario);

    // READ - Obtener todos
    List<Usuario> obtenerTodos();

    //READ -Obtener por ID
    Usuario obtenerPorID (Long id);

    // UPDATE
    Usuario actualizar(Long id, Usuario usuario);

    //DELETE
    void eliminar(Long id);


}
