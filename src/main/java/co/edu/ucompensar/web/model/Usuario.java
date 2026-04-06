package co.edu.ucompensar.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("documento")
    @Column(name = "documento", length = 20, nullable = false, unique = true)
    private String documento;

    @JsonProperty("nombre")
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @JsonProperty("apellido")
    @Column(name = "apellido", length = 100, nullable = false)
    private String apellido;

    @JsonProperty("email")
    @Column(name = "email", length = 150, nullable = false, unique = true)
    private String email;

    @JsonProperty("password")
    @Column(name = "password", length = 200, nullable = false)
    private String password;

    public Usuario() {
    }

    public Usuario(Long id, String documento, String nombre, String apellido, String email, String password) {
        this.id = id;
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
