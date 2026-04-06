package co.edu.ucompensar.web.dto;

import co.edu.ucompensar.web.model.Usuario;

public class UsuarioResponseDTO {

    private Long id;
    private String documento;
    private String nombre;
    private String apellido;
    private String email;
    //  <--- Password NO esta aquí por seguridad.

    public static UsuarioResponseDTO from (Usuario usuario){
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();

        usuarioResponseDTO.id = usuario.getId();
        usuarioResponseDTO.documento = usuario.getDocumento();
        usuarioResponseDTO.nombre = usuario.getNombre();
        usuarioResponseDTO.apellido = usuario.getApellido();
        usuarioResponseDTO.email = usuario.getEmail();

        return usuarioResponseDTO;
    }

    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }
}
