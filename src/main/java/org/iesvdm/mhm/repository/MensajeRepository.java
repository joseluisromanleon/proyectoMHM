package org.iesvdm.mhm.repository;

import org.iesvdm.mhm.domain.Mensaje;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

    @Repository
    public interface MensajeRepository extends JpaRepository<Mensaje, Long> {

        public Page<Mensaje> findByNombreEmpresaContainingIgnoreCaseOrderByNombreEmpresaAsc(String valor, Pageable pageable);

        public Page<Mensaje> findAll(Pageable pageable);

        public List<Mensaje> findMensajeByNombreEmpresaContainingIgnoreCase(String nombre);


    }
