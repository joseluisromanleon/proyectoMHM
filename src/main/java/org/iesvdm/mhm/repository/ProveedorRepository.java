package org.iesvdm.mhm.repository;

import org.iesvdm.mhm.domain.Proveedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    public Page<Proveedor> findByNombreEmpresaContainingIgnoreCaseOrderByNombreEmpresaAsc(String valor, Pageable pageable);

    public Page<Proveedor> findAll(Pageable pageable);

    public List<Proveedor> findProveedorByNombreEmpresaIgnoreCaseOrderByIdAsc(String nombre);

}
