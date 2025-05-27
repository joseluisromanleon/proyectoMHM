package org.iesvdm.mhm.repository;

import org.iesvdm.mhm.domain.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    public Page<Categoria> findByNombreContainingIgnoreCaseOrderByNombreAsc(String valor, Pageable pageable);

    public Page<Categoria> findAll(Pageable pageable);

    public List<Categoria> findCategoriaByNombreContainingIgnoreCase(String nombre);

    public Page<Categoria> findCategoriaByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    //public Page<Categoria> findCategoriaByStock(String valor, Pageable pageable);
}
