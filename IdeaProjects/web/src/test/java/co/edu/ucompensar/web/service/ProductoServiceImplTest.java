package co.edu.ucompensar.web.service;

import co.edu.ucompensar.web.exception.ProductoNotFoundException;
import co.edu.ucompensar.web.model.Producto;
import co.edu.ucompensar.web.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceImplTest {

    @Mock
    private ProductoRepository repo;

    @InjectMocks
    private ProductoServiceImpl service;

    @Captor
    private ArgumentCaptor<Producto> captor;

    private Producto producto;

    @BeforeEach
    public void setUp(){
        producto = new Producto();
    }

    @Test
    public void testCrear_exitoso(){

        producto.setId(1L);
        producto.setNombre("Laptop Dell");
        producto.setPrecio(2500000.0);
        producto.setStock(10);
        producto.setCategoria("Electronica");
        producto.setActivo(true);

        when(repo.save(any(Producto.class))).thenReturn(producto);
        Producto resultado = service.crear(producto); //Llamamos la clase que permite crear el producto.
        assertNotNull(resultado); //Validamos que el producto no sea nulo.
        assertEquals(1L, resultado.getId()); //Validamos el id del producto sea igual a la expectativa.

    }

    @Test
    public void testCrear_nombreNulo(){

        producto.setNombre(""); //Cambiamos el valor a nulo o comillas sin contenido.

        assertNotNull(producto); //Válidamos que el producto no sea nulo
        assertThrows(IllegalArgumentException.class, () -> service.crear(producto),"Error: El nombre del producto se requiere");
        //Verificamos que lance una excepción al crear el producto.

    }

    @Test
    public void testCrear_precioNegativo(){
        producto.setPrecio(-3000000.0); //Cambiamos el precio a negativo para hacer validaciones y lanzar excepcion.
        assertNotNull(producto); //Válidamos que el producto no sea nulo
        assertThrows(IllegalArgumentException.class, () -> service.crear(producto), "Error: El precio no debe ser negativo o no puede ser 0");
        //Verificamos que lance una excepción al crear el producto, respectivamente del precio.
    }

    @Test
    public void testCrear_stockNegativo(){

        producto.setStock(-1); //Cambiamos el stock para hacer validaciones y lanzar una excepción de que no debe ser menor a 0

        assertNotNull(producto);
        assertThrows(IllegalArgumentException.class, ()-> service.crear(producto), "Error: El stock debe ser positivo.");
        //Verificamos que lance una excepción al crear el producto, si el stock es menor a 0.
    }

    @Test
    public void testCrear_verificaSave(){
        repo.save(producto);
        verify(repo,times(1)).save(any(Producto.class));
    }

    @Test
    public void testCrear_precioExacto(){
        producto.setId(1L);
        producto.setNombre("Laptop Dell");
        producto.setPrecio(15000000.0);
        producto.setStock(10);
        producto.setCategoria("Electronica");
        producto.setActivo(true);

        when(repo.save(any(Producto.class))).thenReturn(producto);
        Producto resultado = service.crear(producto);
        assertEquals(15000000.0, resultado.getPrecio());
    }

    @Test
    public void testObtenerPorId_existe(){

        producto.setId(1L);
        producto.setNombre("Laptop Dell");
        producto.setPrecio(2500000.0);
        producto.setStock(10);
        producto.setCategoria("Electronica");
        producto.setActivo(true);

        when(repo.findById(1L)).thenReturn(Optional.of(producto));
        Producto resultado = service.obtenerPorId(1L);
        assertEquals(producto.getNombre(), resultado.getNombre());
    }

    @Test
    public void testObtenerPorId_noExiste(){

        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ProductoNotFoundException.class, () -> service.obtenerPorId(99L));

    }

    @Test
    public void testObtenerPorId_mensajeError(){
        try{
            when(repo.findById(99L)).thenReturn(Optional.empty());
            service.obtenerPorId(99L);
        }catch(ProductoNotFoundException pnfe){
            assertTrue(pnfe.getMessage().contains("Producto no encontrado con id: 99"));
        }
    }

    @Test
    public void testObtenerPorId_verificaFindById(){
        repo.findById(1L);
        verify(repo, times(1)).findById(1L);
    }

    @Test
    public void testObtenerTodos_listaConElementos(){

        Producto p1 = new Producto();
        Producto p2 = new Producto();
        Producto p3 = new Producto();

        List<Producto> lista = Arrays.asList(p1, p2, p3);

        when(repo.findAll()).thenReturn(lista);

        List<Producto> resultado = service.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(3, resultado.size());

    }

    @Test
    public void testObtenerTodos_listaVacia(){
        when(repo.findAll()).thenReturn(Collections.emptyList());

        List<Producto> resultado = service.obtenerTodos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void testObtenerTodos_verificarFindAll(){
        repo.findAll();
        verify(repo, times(1)).findAll();
    }

    @Test
    public void testActualizar_exitoso() {
        Producto productoV = new Producto();
        productoV.setId(1L);
        productoV.setNombre("Laptop vieja Dell");
        productoV.setPrecio(1500000.0);
        productoV.setStock(10);
        productoV.setCategoria("Electronica");
        productoV.setActivo(true);

        Producto productoN = new Producto();

        productoN.setNombre("Laptop nueva Dell");
        productoN.setPrecio(2500000.0);
        productoN.setStock(30);
        productoN.setCategoria("Tecnología");

        when(repo.findById(1L)).thenReturn(Optional.of(productoV));
        when(repo.save(any(Producto.class))).thenReturn(productoV);

        Producto resultado = service.actualizar(1L, productoN);

        assertNotNull(resultado);
        assertEquals("Laptop nueva Dell", resultado.getNombre());
        assertEquals(30, resultado.getStock());
        assertEquals(2500000.0, resultado.getPrecio());
        assertEquals("Tecnología", resultado.getCategoria());
    }

    @Test
    public void testActualizar_noExiste(){

        producto.setNombre("Laptop nueva Dell");
        producto.setPrecio(2500000.0);
        producto.setStock(30);
        producto.setCategoria("Tecnología");

        when(repo.findById(99L)).thenReturn(Optional.empty());
        //Buscamos para luego guardar, como reacciona que no encontro el producto
        //Lanza la excepción y verificamos la excepcion sea la misma.
        assertThrows(ProductoNotFoundException.class, () -> service.actualizar(99L, producto));

    }

    @Test
    public void testActualizar_cambiaNombre(){
        Producto productoV = new Producto();
        productoV.setId(1L);
        productoV.setNombre("Laptop vieja Dell");

        Producto productoN = new Producto();
        productoN.setNombre("Laptop nueva Dell");

        when(repo.findById(1L)).thenReturn(Optional.of(productoV));
        when(repo.save(any(Producto.class))).thenReturn(productoV);

        Producto product = service.obtenerPorId(1L);
        assertEquals("Laptop vieja Dell", product.getNombre());

        Producto resultado = service.actualizar(1L, productoN);
        assertEquals("Laptop nueva Dell", resultado.getNombre());
    }

    @Test
    public void testActualizar_verificaSave(){
        Producto nuevo = new Producto();
        nuevo.setNombre("Nuevo producto");
        nuevo.setPrecio(40000.0);

        when(repo.findById(1L)).thenReturn(Optional.of(producto));
        when(repo.save(any(Producto.class))).thenReturn(producto);

        service.actualizar(1L, nuevo);

        verify(repo).save(captor.capture());

        Producto guardado = captor.getValue();

        System.out.println("Producto guardado: " + guardado.getNombre());
    }

    @Test
    public void testEliminar_exitoso(){
        when(repo.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> service.eliminar(1L));
        //Valida que al eliminar el producto no lanza excepción.
    }

    @Test
    public void testEliminar_noExiste(){
        when(repo.existsById(99L)).thenReturn(false);

        assertThrows(ProductoNotFoundException.class, () -> service.eliminar(99L), "No se encontró el producto con el id: " + producto.getId());
        //Valida que el metodo de eliminar lanza un error.
    }

    @Test
    public void testEliminar_verificaDeleteById(){
        repo.deleteById(1L);
        verify(repo,times(1)).deleteById(1L);
    }

    @Test
    public void testaEliminar_noLlamaDelete(){
        verify(repo, never()).deleteById(any());
    }
}
