package org.iesvdm.mhm.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.mhm.domain.Producto;
import org.iesvdm.mhm.domain.Pedido;
import org.iesvdm.mhm.exception.ProductoNotFoundException;
import org.iesvdm.mhm.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ProductoService  {

    private final ProductoRepository productoRepository;

    // para las consultas dinamicas  8 en una  logica de control para multiconsultas
    @PersistenceContext
    EntityManager em;

    @Autowired
    public ProductoService(ProductoRepository productoRepository)
    { this.productoRepository = productoRepository; }

    public Producto one(Long id) {
        return this.productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));
    }

    public Producto replace(Long id, Producto producto) {
        return this.productoRepository.findById(id).map( c -> (id.equals(producto.getId()) ?
                        this.productoRepository.save(producto) : null))
                .orElseThrow(() -> new ProductoNotFoundException(id));
    }

    public void delete(Long id) {
        log.info("Eliminando producto con ID: " + id);
        this.productoRepository.findById(id).map(p -> {
                    this.productoRepository.delete(p);
                    return p;})
                .orElseThrow(() -> new ProductoNotFoundException(id));
    }

    @Transactional
    public Producto save(Producto producto) {
        this.productoRepository.save(producto);
        this.em.refresh(producto);

        return producto;
    }

    public List<Producto> all() {
        return this.productoRepository.findAll();
    }

    public List<Producto> all(String nombre) {
        return this.productoRepository.findProductoByNombreIgnoreCase(nombre);
    }

    public Page<Producto> getAll(Pageable pageable) {
        return this.productoRepository.findAll(pageable);
    }

    // AUTOMATICA PARA DOS CAMPOS CON DIFERENTES DEVOLUCIONES PAGE Y LIST
    // Sin orden y pageable completo
    public Page<Producto> getAllBuscar(String campo, String valor, Pageable pageable) {

        return campo.equalsIgnoreCase("categoria") ?
                this.productoRepository.findProductoByCategoriaContainingIgnoreCase(valor, pageable)
                : campo.equalsIgnoreCase("proveedor") ?
                this.productoRepository.findProductoByProveedorContainingIgnoreCase(valor, pageable)
                : campo.equalsIgnoreCase("stock") ?
                this.productoRepository.findProductoByStock(valor, pageable)
                :campo.equalsIgnoreCase("nombre") ?
                this.productoRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(valor, pageable)
                : this.getAll(pageable);

    }



    // un orden  y mapeando pageable configurable
    public Map<String, Object> procesarOrden(String campo, String direccion) {

        Pageable pageable = (direccion.equals("desc")) ?
                PageRequest.of(0, 20, Sort.by(campo).descending()) :
                PageRequest.of(0, 20, Sort.by(campo).ascending());

        Page<Producto> pageAll = this.productoRepository
                .findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("productos",pageAll.getContent());
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


        Page<Producto> pageAll = this.productoRepository.findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("productos",pageAll.getContent());
        response.put("currentPage",pageAll.getNumber());
        response.put("totalItems",pageAll.getTotalElements());
        response.put("totalPages",pageAll.getTotalPages());

        return response;
    }



}
