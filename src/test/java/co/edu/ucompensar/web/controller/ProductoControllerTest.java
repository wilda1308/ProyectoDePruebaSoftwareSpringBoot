package co.edu.ucompensar.web.controller;

import co.edu.ucompensar.web.exception.ProductoNotFoundException;
import co.edu.ucompensar.web.model.Producto;
import co.edu.ucompensar.web.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductoController.class)

public class ProductoControllerTest {

    @MockBean
    private ProductoService service;

    private Producto producto;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPostProducto_retorna201() throws Exception{

        producto = new Producto();

        producto.setId(1L);
        producto.setNombre("Laptop Dell");
        producto.setDescripcion("Aparato electronico");
        producto.setPrecio(2500000.0);
        producto.setStock(10);
        producto.setCategoria("Electronica");
        producto.setActivo(true);

        when(service.crear(any(Producto.class))).thenReturn(producto);

        String json = "{\"nombre\": \"Laptop Dell\", \"descripcion\": \"Aparato electronico\", \"precio\": 2500000.0, \"stock\": 10, \"categoria\": \"Electronica\", \"activo\": true}";

        mockMvc.perform(post("/api/productos").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetTodos_retorna200() throws Exception{

        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Laptop Dell");
        producto.setDescripcion("Aparato electronico");
        producto.setPrecio(2500000.0);
        producto.setStock(10);
        producto.setCategoria("Electronica");
        producto.setActivo(true);

        when(service.obtenerTodos()).thenReturn(List.of(producto));

        mockMvc.perform(get("/api/productos")).andExpect(status().isOk());
    }

    @Test
    public void testGetPorId_noExiste_retorna404() throws Exception{
        when(service.obtenerPorId(999L)).thenThrow(new ProductoNotFoundException("Producto no encontrado"));

        mockMvc.perform(get("/api/productos/999")).andExpect(status().isNotFound());
    }

    @Test
    public void testPostProducto_bodyInvalido_retorna400() throws Exception{
        String json = "{\"nombre\": \"\", \"descripcion\": \"Aparato electronico\", \"precio\": 2500000.0, \"stock\": 10, \"categoria\": \"Electronica\", \"activo\": true}";

        mockMvc.perform(post("/api/productos").contentType(MediaType.APPLICATION_JSON).content(json)).
                andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    public void testDeleteProducto_retorna204() throws Exception{

        doNothing().when(service).eliminar(99L);

        mockMvc.perform(delete("/api/productos/99")).
                andExpect(status().isNoContent());
    }

}
