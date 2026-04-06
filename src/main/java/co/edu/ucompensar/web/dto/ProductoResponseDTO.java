package co.edu.ucompensar.web.dto;

import co.edu.ucompensar.web.model.Producto;

public class ProductoResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private String categoria;
    private Boolean activo;

    public static ProductoResponseDTO from (Producto producto){

        ProductoResponseDTO productoResponseDTO = new ProductoResponseDTO();

        productoResponseDTO.id = producto.getId();
        productoResponseDTO.nombre = producto.getNombre();
        productoResponseDTO.descripcion = producto.getDescripcion();
        productoResponseDTO.precio = producto.getPrecio();
        productoResponseDTO.stock = producto.getStock();
        productoResponseDTO.categoria = producto.getCategoria();
        productoResponseDTO.activo = producto.getActivo();

        return productoResponseDTO;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public Integer getStock() {
        return stock;
    }

    public String getCategoria() {
        return categoria;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
