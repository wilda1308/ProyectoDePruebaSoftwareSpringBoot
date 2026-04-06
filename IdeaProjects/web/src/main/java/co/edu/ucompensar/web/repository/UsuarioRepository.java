package co.edu.ucompensar.web.repository;

import co.edu.ucompensar.web.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository <Usuario, Long> {
    // ── Métodos heredados de JpaRepository (automáticos) ──
    //  save(usuario)           → INSERT o UPDATE
    //  findById(id)            → SELECT por ID
    //  findAll()               → SELECT todos
    //  deleteById(id)          → DELETE por ID
    //  existsById(id)          → EXISTS por ID
    //  count()                 → COUNT total
    // ──────────────────────────────────────────────────

    // ── Métodos personalizados (Spring genera la query) ──
}
