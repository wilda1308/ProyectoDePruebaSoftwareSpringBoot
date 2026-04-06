package co.edu.ucompensar.web.controller;

import co.edu.ucompensar.web.exception.GlobalExceptionHandler;
import co.edu.ucompensar.web.exception.ProductoNotFoundException;
import co.edu.ucompensar.web.model.Producto;
import co.edu.ucompensar.web.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")

public class ProductoController {
    private final ProductoService service;

    public ProductoController(ProductoService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Producto> crear(@Valid @RequestBody Producto producto){
        Producto creado = service.crear(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listar(){
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Long id){
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @ExceptionHandler(ProductoNotFoundException.class)
    public ResponseEntity<GlobalExceptionHandler> manejoError(ProductoNotFoundException pnfe){

        GlobalExceptionHandler productoNoEncontrado = new GlobalExceptionHandler(HttpStatus.NOT_FOUND.value(),
                "ERROR: !Producto No Encontrado!", pnfe.getMessage());


        return ResponseEntity.status(productoNoEncontrado.getStatus()).body(productoNoEncontrado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(
            @PathVariable Long id,
            @RequestBody Producto producto) {

        return ResponseEntity.ok(service.actualizar(id, producto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Producto> eliminar(@PathVariable Long id){
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categoria/{cat}")
    public ResponseEntity<List<Producto>> listarPorCategoria(@PathVariable String cat){
        return ResponseEntity.ok(service.filtrarPorCategoria(cat));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalExceptionHandler> manejarValidacionesDeEntradas(MethodArgumentNotValidException manve){
        List<String> erroresValidacion =
                manve.getBindingResult().getFieldErrors().stream().map(error-> error.getField() + ": " + error.getDefaultMessage()).toList();

        GlobalExceptionHandler response = new GlobalExceptionHandler(HttpStatus.BAD_REQUEST.value(), "Bad Request",
                "Error de validación", erroresValidacion);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public  ResponseEntity<GlobalExceptionHandler> manejarDuplicados(IllegalArgumentException iae){
        GlobalExceptionHandler error = new GlobalExceptionHandler(HttpStatus.BAD_REQUEST.value(), "Bad Request",
                iae.getMessage(), null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
