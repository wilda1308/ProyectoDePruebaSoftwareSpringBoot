package co.edu.ucompensar.web.service;

import co.edu.ucompensar.web.exception.ProductoNotFoundException;
import co.edu.ucompensar.web.model.Producto;
import co.edu.ucompensar.web.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService{

    public ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }


    @Override
    public Producto crear(Producto producto){
        if (productoRepository.existsByNombreIgnoreCase(producto.getNombre())){
            throw new IllegalArgumentException("Ya existe un producto con este nombre");

        }

        if (producto.getNombre() == null ||producto.getNombre().isBlank()){
            throw new IllegalArgumentException("Error: El nombre del producto se requiere");
        } else if (producto.getPrecio() <= 0){
            throw new IllegalArgumentException("Error: El precio no debe ser negativo o no puede ser 0");
        } else if (producto.getStock() < 0) {
            throw new IllegalArgumentException("Error: El stock debe ser positivo.");
        }
        return productoRepository.save(producto);
    }

    @Override
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id).orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con id: " + id));
    }

    @Override
    public Producto actualizar(Long id, Producto producto) {
        Producto existente = obtenerPorId(id);

        existente.setNombre(producto.getNombre());
        existente.setDescripcion(producto.getDescripcion());
        existente.setPrecio(producto.getPrecio());
        existente.setStock(producto.getStock());
        existente.setCategoria(producto.getCategoria());
        existente.setActivo(producto.getActivo());

        return productoRepository.save(existente);

    }

    @Override
    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)){
            throw new ProductoNotFoundException("No se encontró el producto con el id: " + id);
        }
        productoRepository.deleteById(id);
    }

    @Override
    public List<Producto> filtrarPorCategoria(String cat){
        return productoRepository.findByCategoria(cat);
    }
}
