package org.iesvdm.mhm.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.mhm.domain.Proveedor;
import org.iesvdm.mhm.exception.ProveedorNotFoundException;
import org.iesvdm.mhm.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ProveedorService {


    private final ProveedorRepository proveedorRepository;

    // para las consultas dinamicas  8 en una  logica de control para multiconsultas
    @PersistenceContext
    EntityManager em;

    @Autowired
    public ProveedorService(ProveedorRepository proveedorRepository)
    { this.proveedorRepository = proveedorRepository; }

    public Proveedor one(Long id) {
        return this.proveedorRepository.findById(id)
                .orElseThrow(() -> new ProveedorNotFoundException(id));
    }

    public Proveedor replace(Long id, Proveedor proveedor) {
        return this.proveedorRepository.findById(id).map( c -> (id.equals(proveedor.getId()) ?
                        this.proveedorRepository.save(proveedor) : null))
                .orElseThrow(() -> new ProveedorNotFoundException(id));
    }

    public void delete(Long id) {
        log.info("Eliminando proveedor con ID: " + id);
        this.proveedorRepository.findById(id).map(p -> {
                    this.proveedorRepository.delete(p);
                    return p;})
                .orElseThrow(() -> new ProveedorNotFoundException(id));
    }

    @Transactional
    public Proveedor save(Proveedor proveedor) {
        this.proveedorRepository.save(proveedor);
        this.em.refresh(proveedor);

        return proveedor;
    }

    public List<Proveedor> all() {
        return this.proveedorRepository.findAll();
    }

    public List<Proveedor> all(String nombre) {
        return this.proveedorRepository.findProveedorByNombreEmpresaIgnoreCaseOrderByIdAsc(nombre);
    }

    public Page<Proveedor> getAll(Pageable pageable) {
        return this.proveedorRepository.findAll(pageable);
    }

    // AUTOMATICA PARA DOS CAMPOS CON DIFERENTES DEVOLUCIONES PAGE Y LIST
    // Sin orden y pageable completo
    public Page<Proveedor> getAllBuscar(String campo, String valor, Pageable pageable) {

        return  campo.equalsIgnoreCase("empresa") ?
                this.proveedorRepository.findByNombreEmpresaContainingIgnoreCaseOrderByNombreEmpresaAsc(valor, pageable)

//                    : campo.equalsIgnoreCase("proveedor") ?
//                    this.proveedorRepository.findProveedorByProveedorContainingIgnoreCase(valor, pageable)
//                    : campo.equalsIgnoreCase("stock") ?
//                    this.proveedorRepository.findProveedorByStock(valor, pageable)

                : this.getAll(pageable);

    }



    // un orden  y mapeando pageable configurable
    public Map<String, Object> procesarOrden(String campo, String direccion) {

        Pageable pageable = (direccion.equals("desc")) ?
                PageRequest.of(0, 20, Sort.by(campo).descending()) :
                PageRequest.of(0, 20, Sort.by(campo).ascending());

        Page<Proveedor> pageAll = this.proveedorRepository
                .findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("proveedores",pageAll.getContent());
        response.put("currentPage",pageAll.getNumber());
        response.put("totalItems",pageAll.getTotalElements());
        response.put("totalPages",pageAll.getTotalPages());

        return response;
    }

    // dos orden  y pageable configurable
    public Map<String, Object> procesarOrden2(String campo1, String direccion1, String campo2, String direccion2) {

        Sort sort = Sort.by(
                Sort.Order.by(campo1).with(
                        direccion1.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC
                ),
                Sort.Order.by(campo2).with(
                        direccion2.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC
                )
        );

        Pageable pageable = PageRequest.of(0, 20,sort);


        Page<Proveedor> pageAll = this.proveedorRepository.findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("proveedores",pageAll.getContent());
        response.put("currentPage",pageAll.getNumber());
        response.put("totalItems",pageAll.getTotalElements());
        response.put("totalPages",pageAll.getTotalPages());

        return response;
    }
}
