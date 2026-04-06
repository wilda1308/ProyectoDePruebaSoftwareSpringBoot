package co.edu.ucompensar.web.Integration;

import co.edu.ucompensar.web.dto.ProductoRequestDTO;
import co.edu.ucompensar.web.model.Producto;
import co.edu.ucompensar.web.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import co.edu.ucompensar.web.dto.ProductoResponseDTO;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ProductoIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductoRepository productoRepository;


    @BeforeEach
    void setUp(){
        //Limpia la base de datos cada vez que inicie una prueba
        productoRepository.deleteAll();
    }
    /*
            =============================================================================
                                CREATE (POST "/api/productos")
            =============================================================================
    */
    @Test
    public void testCrear_retorna201(){

        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombre("Laptop Dell");
        dto.setPrecio(2500000.0);
        dto.setStock(10);
        dto.setCategoria("Electronica");
        dto.setActivo(true);

        ResponseEntity<ProductoResponseDTO> response = restTemplate.postForEntity("/api/productos",dto,
                ProductoResponseDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertNotNull(response.getBody().getId());

        assertEquals(1L, productoRepository.count());

    }

    @Test
    public void testCrear_persisteEnBD(){

        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombre("Celular OPPO A40");
        dto.setPrecio(1700000.0);
        dto.setStock(45);
        dto.setCategoria("Tecnología");
        dto.setActivo(true);

        restTemplate.postForEntity("/api/productos", dto, ProductoResponseDTO.class);

        assertEquals(1L, productoRepository.count(), "El producto no se guardó en la base de datos.");
    }

    @Test
    public void testCrear_retornaIdGenerado() {

        ProductoRequestDTO dto = new ProductoRequestDTO();

        dto.setNombre("Celular OPPO A40");
        dto.setPrecio(1700000.0);
        dto.setStock(45);
        dto.setCategoria("Tecnología");
        dto.setActivo(true);

        ResponseEntity<ProductoResponseDTO> response = restTemplate.postForEntity("/api/productos", dto,
                ProductoResponseDTO.class);

        assertNotNull(response.getBody().getId());

    }

    @Test
    public void testCrear_productoActivo(){

        ProductoRequestDTO dto = new ProductoRequestDTO();

        dto.setNombre("Celular OPPO A40");
        dto.setPrecio(1700000.0);
        dto.setStock(45);
        dto.setCategoria("Tecnología");
        dto.setActivo(true);

        ResponseEntity<ProductoResponseDTO> response = restTemplate.postForEntity("/api/productos", dto,
                ProductoResponseDTO.class);

        assertEquals(true, response.getBody().getActivo());
    }

    @Test
    public void testCrear_bodyInvalido_retorna400(){

        ProductoRequestDTO dto = new ProductoRequestDTO();

        dto.setNombre("");
        dto.setPrecio(1700000.0);
        dto.setStock(45);
        dto.setCategoria("Tecnología");
        dto.setActivo(true);

        ResponseEntity<ProductoResponseDTO> response = restTemplate.postForEntity("/api/productos", dto,
                ProductoResponseDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
         System.out.println(response.getStatusCode());
    }

     @Test
    public void testCrear_precioNegativo_retorna400(){

        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombre("Lavadora");
        dto.setPrecio(-1000000.0);
        dto.setStock(30);
        dto.setCategoria("Electrodomesticos");
        dto.setActivo(true);

        ResponseEntity<String> response = restTemplate.postForEntity("/api/productos", dto,
                String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        System.out.println(response.getBody() + "\t" +  response.getStatusCode());
     }
    /*
        =============================================================================
                            READ (GET "/api/productos" "/api/productos/{id})
        =============================================================================
    */

    @Test
    public void testListar_bdVacia_retornaListaVacia(){
        productoRepository.deleteAll();

        ResponseEntity<ProductoResponseDTO[]> response = restTemplate.getForEntity("/api/productos",
                ProductoResponseDTO[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response.getBody());

        assertEquals(0, response.getBody().length);

    }

    @Test
    public void testListar_conDatos_retornaLista(){

        ProductoRequestDTO dto1 = new ProductoRequestDTO();
        ProductoRequestDTO dto2 = new ProductoRequestDTO();
        ProductoRequestDTO dto3 = new ProductoRequestDTO();

        dto1.setNombre("Laptop Dell");
        dto1.setPrecio(3500000.0);
        dto1.setStock(100);
        dto1.setActivo(true);

        dto2.setNombre("TV LED 50 pulgadas");
        dto2.setPrecio(1800000.0);
        dto2.setStock(20);
        dto2.setActivo(true);

        dto3.setNombre("Celular OPPO A40");
        dto3.setPrecio(1300000.0);
        dto3.setStock(330);
        dto3.setActivo(true);

        restTemplate.postForEntity("/api/productos", dto1,ProductoResponseDTO.class);
        restTemplate.postForEntity("/api/productos", dto2,ProductoResponseDTO.class);
        restTemplate.postForEntity("/api/productos", dto3,ProductoResponseDTO.class);

        ResponseEntity<ProductoResponseDTO[]> response = restTemplate.getForEntity("/api/productos",
                ProductoResponseDTO[].class);

        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().length);
    }

    @Test
    public void ObtenerPorId_Existe_retorna200(){

        ProductoRequestDTO dto1 = new ProductoRequestDTO();
        ProductoRequestDTO dto2 = new ProductoRequestDTO();

        dto1.setNombre("Laptop Dell");
        dto1.setPrecio(3500000.0);
        dto1.setStock(100);
        dto1.setActivo(true);

        dto2.setNombre("Celular OPPO A40");
        dto2.setPrecio(1300000.0);
        dto2.setStock(330);
        dto2.setActivo(true);

        restTemplate.postForEntity("/api/productos", dto1,ProductoResponseDTO.class);
        ResponseEntity<ProductoResponseDTO> productoResponse = restTemplate.postForEntity("/api/productos", dto2,
                ProductoResponseDTO.class);

        assertNotNull(productoResponse.getBody());

        ResponseEntity<ProductoResponseDTO> response =
                restTemplate.getForEntity("/api/productos/" + productoResponse.getBody().getId(),
                ProductoResponseDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(productoResponse.getBody().getId(), response.getBody().getId());
        assertEquals("Celular OPPO A40", response.getBody().getNombre());
    }

    @Test
    public void testObtenerPorId_noExiste_retorna404(){
        ResponseEntity<String> response = restTemplate.getForEntity("/api/productos/999",
                String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testObtenerPorId_datosCorrectos(){
        ProductoRequestDTO dto = new ProductoRequestDTO();

        dto.setNombre("Celular OPPO A40");
        dto.setPrecio(1250000.0);
        dto.setStock(202);
        dto.setCategoria("Tecnología");
        dto.setActivo(true);

        ResponseEntity<ProductoResponseDTO> productoResponse = restTemplate.postForEntity("/api/productos", dto,
                ProductoResponseDTO.class);

        assertNotNull(productoResponse.getBody());
        ResponseEntity<ProductoResponseDTO> response =
                restTemplate.getForEntity("/api/productos/" + productoResponse.getBody().getId(),
                ProductoResponseDTO.class);

        assertNotNull(response.getBody());
        assertEquals(dto.getNombre(), response.getBody().getNombre());
        assertEquals(dto.getPrecio(), response.getBody().getPrecio());
        assertEquals(dto.getCategoria(), response.getBody().getCategoria());
    }
    /*
        =============================================================================
                            UPDATE (EXCHANGE "/api/productos/{id})
        =============================================================================
    */

    @Test
    public void testActualizar_retorna200(){
        Producto producto = new Producto();

        producto.setNombre("ASUS Vivobook");
        producto.setPrecio(1400000.0);
        producto.setStock(90);
        producto.setActivo(true);

        producto = productoRepository.save(producto);

        ProductoRequestDTO dto =  new ProductoRequestDTO();

        dto.setNombre(producto.getNombre());
        dto.setPrecio(2500000.0);
        dto.setStock(20);
        dto.setCategoria("Tecnología");
        dto.setActivo(producto.getActivo());

        HttpEntity<ProductoRequestDTO> request = new HttpEntity<>(dto);

        ResponseEntity<ProductoResponseDTO> response = restTemplate.exchange("/api/productos/" + producto.getId(),
                HttpMethod.PUT,
                request,ProductoResponseDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

    }

    @Test
    public void testActualizar_persisteEnBD(){
        Producto producto = new Producto();

        producto.setNombre("ASUS Vivobook");
        producto.setPrecio(1400000.0);
        producto.setStock(90);
        producto.setActivo(true);

        producto = productoRepository.save(producto);

        ProductoRequestDTO dto =  new ProductoRequestDTO();

        dto.setNombre(producto.getNombre());
        dto.setPrecio(2500000.0);
        dto.setStock(20);
        dto.setCategoria("Tecnología");
        dto.setActivo(producto.getActivo());

        HttpEntity<ProductoRequestDTO> request = new HttpEntity<>(dto);

        ResponseEntity<ProductoResponseDTO> response = restTemplate.exchange("/api/productos/" + producto.getId(),
                HttpMethod.PUT,
                request,ProductoResponseDTO.class);

        Producto actualizado = productoRepository.findById(producto.getId()).get();

        assertEquals(dto.getPrecio(), actualizado.getPrecio());
        assertEquals(dto.getStock(), actualizado.getStock());
    }

    @Test
    public void testActualizar_noExiste_retorna404(){
        Long idInexistente = 999L;

        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombre("ASUS Vivobook");
        dto.setPrecio(2500000.0);
        dto.setStock(20);
        dto.setCategoria("Tecnología");
        dto.setActivo(true);

        HttpEntity<ProductoRequestDTO> request = new HttpEntity<>(dto);

        ResponseEntity<String> response = restTemplate.exchange("/api/productos/" + idInexistente,
                HttpMethod.PUT, request, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    public void testActualizar_noModificaOtrosRegistros(){
        Producto productoA = new Producto(); //El producto que se va a actualizar
        Producto productoB = new Producto(); //El que no se va a actualizar

        productoA.setNombre("ASUS Vivobook");
        productoA.setPrecio(1400000.0);
        productoA.setStock(90);
        productoA.setActivo(true);

        productoB.setNombre("Lenovo");
        productoB.setPrecio(2000000.0);
        productoB.setStock(500);
        productoB.setActivo(true);

        productoA = productoRepository.save(productoA);
        productoB = productoRepository.save(productoB);

        ProductoRequestDTO dto =  new ProductoRequestDTO();

        dto.setNombre(productoA.getNombre() + " actualizado*");
        dto.setPrecio(2500000.0);
        dto.setStock(20);
        dto.setCategoria("Tecnología");
        dto.setActivo(productoA.getActivo());

        HttpEntity<ProductoRequestDTO> request = new HttpEntity<>(dto);

        ResponseEntity<ProductoResponseDTO> response = restTemplate.exchange("/api/productos/" + productoA.getId(),
                HttpMethod.PUT,
                request,ProductoResponseDTO.class);

        Producto actualizadoA = productoRepository.findById(productoA.getId()).orElseThrow();
        Producto sinActualizacionB = productoRepository.findById(productoB.getId()).orElseThrow();

        assertEquals("ASUS Vivobook actualizado*", actualizadoA.getNombre());
        assertEquals(2500000.0, actualizadoA.getPrecio());

        assertEquals("Lenovo", sinActualizacionB.getNombre());
        assertEquals(2000000.0, sinActualizacionB.getPrecio());

    }

    /*
        =============================================================================
                            DELETE (DELETE "/api/productos/{id})
        =============================================================================
    */

    @Test
    public void testEliminar_retorna204(){
        Producto producto = new Producto();

        producto.setNombre("ASUS Vivobook");
        producto.setPrecio(1400000.0);
        producto.setStock(90);
        producto.setActivo(true);

        productoRepository.save(producto);

        ResponseEntity<Void> response = restTemplate.exchange("/api/productos/" + producto.getId(),HttpMethod.DELETE,null,
                Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    }

    @Test
    public void testEliminar_eliminaDeBD(){
        Producto producto = new Producto();

        producto.setNombre("ASUS Vivobook");
        producto.setPrecio(1400000.0);
        producto.setStock(90);
        producto.setActivo(true);

        productoRepository.save(producto);

        restTemplate.delete("/api/productos/" + producto.getId(),HttpMethod.DELETE);

        assertFalse(productoRepository.existsById(producto.getId()));
    }

    @Test
    public void testEliminar_noExiste_retorna404(){
        ResponseEntity<String> response = restTemplate.exchange("/api/productos/999", HttpMethod.DELETE, null,
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testEliminar_reduceCuenta(){
        Producto productoA = new Producto(); //El producto que se va a actualizar
        Producto productoB = new Producto(); //El que no se va a actualizar

        productoA.setNombre("ASUS Vivobook");
        productoA.setPrecio(1400000.0);
        productoA.setStock(90);
        productoA.setActivo(true);

        productoB.setNombre("Lenovo");
        productoB.setPrecio(2000000.0);
        productoB.setStock(500);
        productoB.setActivo(true);

        productoA = productoRepository.save(productoA);
        productoB = productoRepository.save(productoB);

        long cantidadRegistros = productoRepository.count();

        assertEquals(2, productoRepository.count());

        ResponseEntity<Void> response = restTemplate.exchange("/api/productos/" + productoA.getId(),HttpMethod.DELETE
                ,null,
                Void.class);

        assertEquals(cantidadRegistros-1, productoRepository.count());
    }
    /*
            ============================================================================================================
                                FILTRAR POR CATEGORIA (GET "/api/productos/categoria/{cat}")
            ============================================================================================================
    */
    @Test
    public void testFiltrarPorCategoria_retorna200(){

        Producto p1 = new Producto();
        p1.setNombre("Celular");
        p1.setPrecio(1500000.0);
        p1.setStock(10);
        p1.setCategoria("Electronica");
        p1.setActivo(true);

        Producto p2 = new Producto();
        p2.setNombre("Laptop ASUS Vivobook");
        p2.setPrecio(2300000.0);
        p2.setStock(1000);
        p2.setDescripcion("Tecnología");
        p2.setActivo(true);

        productoRepository.save(p1);
        productoRepository.save(p2);

        ResponseEntity<ProductoResponseDTO[]> response = restTemplate.getForEntity("/api/productos/categoria/" + p2.getCategoria(), ProductoResponseDTO[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testFiltrarPorCategoria_retornaProductosCorrectos(){

        Producto p1 = new Producto();
        p1.setNombre("Laptop");
        p1.setPrecio(2500000.0);
        p1.setStock(10);
        p1.setCategoria("Electronica");
        p1.setActivo(true);

        Producto p3 = new Producto();
        p3.setNombre("Celular");
        p3.setPrecio(1200000.0);
        p3.setStock(20);
        p3.setCategoria("Electronica");
        p3.setActivo(true);

        Producto p2 = new Producto();
        p2.setNombre("Camisa");
        p2.setPrecio(80000.0);
        p2.setStock(50);
        p2.setCategoria("Ropa");
        p2.setActivo(true);

        productoRepository.save(p1);
        productoRepository.save(p2);
        productoRepository.save(p3);

        ResponseEntity<ProductoResponseDTO[]> response = restTemplate.getForEntity("/api/productos/categoria" +
                "/Electronica", ProductoResponseDTO[].class);

        assertNotNull(response.getBody());

        ProductoResponseDTO[] productos = response.getBody();

        for (ProductoResponseDTO p : productos){
            assertEquals("Electronica", p.getCategoria());
        }

        assertEquals(2, response.getBody().length);
    }

    @Test
    public void testFiltrarPorCategoria_categoriaNoExiste_listaVacia(){
        ResponseEntity<ProductoResponseDTO[]> response = restTemplate.getForEntity("/api/productos/categoria/Ropa",
                ProductoResponseDTO[].class);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().length);
    }
    /*
            ============================================================================================================
                                                 MANEJO GLOBAL DE ERRORES
            ============================================================================================================
    */

    @Test
    public void testError404_estructuraJSON(){
        ResponseEntity<String> response = restTemplate.getForEntity("/api/productos/999",
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        String body = response.getBody();

        assertNotNull(body);
        assertTrue(body.contains("status"));
        assertTrue(body.contains("error"));
        assertTrue(body.contains("mensaje"));
    }

    @Test
    public void testError400_validacion_listaErrores(){
        ProductoRequestDTO dto = new ProductoRequestDTO();

        dto.setNombre(""); //Inválido: vacio
        dto.setPrecio(-100.0); //Invalido: No puede ser negativo.
        dto.setStock(20);
        dto.setActivo(true);

        ResponseEntity<String> response = restTemplate.postForEntity("/api/productos", dto, String.class);

        String body = response.getBody();

        assertNotNull(body);
        assertTrue(body.contains("status"));
        assertTrue(body.contains("error"));
        assertTrue(body.contains("mensaje"));
        assertTrue(body.contains("campos"));

        assertTrue(body.contains("nombre"));
        assertTrue(body.contains("precio"));
    }

    @Test
    public void testError400_duplicado_mensajeCorrecto(){
        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombre("iPhone 17 pro");
        dto.setPrecio(50000000.0);
        dto.setStock(900);
        dto.setCategoria("Tecnología");
        dto.setActivo(true);

        restTemplate.postForEntity("/api/productos", dto, ProductoResponseDTO.class);

        ResponseEntity<String> response = restTemplate.postForEntity("/api/productos", dto, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());

        String body = response.getBody();

        assertTrue(response.getBody().contains("Ya existe un producto con este nombre"));
    }

}