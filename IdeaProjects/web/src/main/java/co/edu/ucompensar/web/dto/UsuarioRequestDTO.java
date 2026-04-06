package co.edu.ucompensar.web.dto;

import jakarta.validation.constraints.*;

public class UsuarioRequestDTO {

    @NotBlank(message = "El documento es obligatorio")
    @Size(max = 20, message = "El documento no puede tener más de 20 cáracteres")
    private String documento;

    @NotBlank
    @Size(max = 20, message = "Los nombres no pueden tener más de 100 cáracteres")
    private String nombre;

    @NotBlank
    @Size(max = 20, message = "Los apellidos no puede tener más de 100 cáracteres")
    private String apellido;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de Email inválido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 20, message = "La contraseña debe contar con mínimo 6 a 20 caracteres.")
    private String password;

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
