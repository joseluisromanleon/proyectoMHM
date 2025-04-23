package org.iesvdm.mhm.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.mhm.domain.Categoria;
import org.iesvdm.mhm.exception.CategoriaNotFoundException;
import org.iesvdm.mhm.repository.CategoriaRepository;
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
public class CategoriaService {

        private final CategoriaRepository categoriaRepository;

        // para las consultas dinamicas  8 en una  logica de control para multiconsultas
        @PersistenceContext
        EntityManager em;

        @Autowired
        public CategoriaService(CategoriaRepository categoriaRepository)
        { this.categoriaRepository = categoriaRepository; }

        public Categoria one(Long id) {
            return this.categoriaRepository.findById(id)
                    .orElseThrow(() -> new CategoriaNotFoundException(id));
        }

        public Categoria replace(Long id, Categoria categoria) {
            return this.categoriaRepository.findById(id).map( c -> (id.equals(categoria.getId()) ?
                            this.categoriaRepository.save(categoria) : null))
                    .orElseThrow(() -> new CategoriaNotFoundException(id));
        }

        public void delete(Long id) {
            log.info("Eliminando categoria con ID: " + id);
            this.categoriaRepository.findById(id).map(p -> {
                        this.categoriaRepository.delete(p);
                        return p;})
                    .orElseThrow(() -> new CategoriaNotFoundException(id));
        }

        @Transactional
        public Categoria save(Categoria categoria) {
            this.categoriaRepository.save(categoria);
            this.em.refresh(categoria);

            return categoria;
        }

        public List<Categoria> all() {
            return this.categoriaRepository.findAll();
        }

        public List<Categoria> all(String nombre) {
            return this.categoriaRepository.findCategoriaByNombreContainingIgnoreCase(nombre);
        }

        public Page<Categoria> getAll(Pageable pageable) {
            return this.categoriaRepository.findAll(pageable);
        }

        // AUTOMATICA PARA DOS CAMPOS CON DIFERENTES DEVOLUCIONES PAGE Y LIST
        // Sin orden y pageable completo
        public Page<Categoria> getAllBuscar(String campo, String valor, Pageable pageable) {

            return  campo.equalsIgnoreCase("nombre") ?
                    this.categoriaRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(valor, pageable)

//                    : campo.equalsIgnoreCase("proveedor") ?
//                    this.categoriaRepository.findCategoriaByProveedorContainingIgnoreCase(valor, pageable)
//                    : campo.equalsIgnoreCase("stock") ?
//                    this.categoriaRepository.findCategoriaByStock(valor, pageable)

                    : this.getAll(pageable);

        }



        // un orden  y mapeando pageable configurable
        public Map<String, Object> procesarOrden(String campo, String direccion) {

            Pageable pageable = (direccion.equals("desc")) ?
                    PageRequest.of(0, 20, Sort.by(campo).descending()) :
                    PageRequest.of(0, 20, Sort.by(campo).ascending());

            Page<Categoria> pageAll = this.categoriaRepository
                    .findAll(pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("categorias",pageAll.getContent());
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


            Page<Categoria> pageAll = this.categoriaRepository.findAll(pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("categorias",pageAll.getContent());
            response.put("currentPage",pageAll.getNumber());
            response.put("totalItems",pageAll.getTotalElements());
            response.put("totalPages",pageAll.getTotalPages());

            return response;
        }

    }
