package co.edu.ucompensar.web.service;

import co.edu.ucompensar.web.dto.ProductoResponseDTO;
import co.edu.ucompensar.web.model.Producto;
import java.util.List;

public interface ProductoService {

    Producto crear(Producto producto);

    List<Producto> obtenerTodos();

    Producto obtenerPorId(Long id);

    Producto actualizar(Long id, Producto producto);

    void eliminar(Long id);

    List<Producto> filtrarPorCategoria(String cat);
}
