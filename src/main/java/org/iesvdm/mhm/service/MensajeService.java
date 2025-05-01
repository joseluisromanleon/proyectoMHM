package org.iesvdm.mhm.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.mhm.domain.Mensaje;
import org.iesvdm.mhm.exception.MensajeNotFoundException;
import org.iesvdm.mhm.repository.MensajeRepository;
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
public class MensajeService {

        private final MensajeRepository mensajeRepository;

        // para las consultas dinamicas  8 en una  logica de control para multiconsultas
        @PersistenceContext
        EntityManager em;

        @Autowired
        public MensajeService(MensajeRepository mensajeRepository)
        { this.mensajeRepository = mensajeRepository; }

        public Mensaje one(Long id) {
            return this.mensajeRepository.findById(id)
                    .orElseThrow(() -> new MensajeNotFoundException(id));
        }

        public Mensaje replace(Long id, Mensaje mensaje) {
            return this.mensajeRepository.findById(id).map( c -> (id.equals(mensaje.getId_mensaje()) ?
                            this.mensajeRepository.save(mensaje) : null))
                    .orElseThrow(() -> new MensajeNotFoundException(id));
        }

        public void delete(Long id) {
            log.info("Eliminando mensaje con ID: " + id);
            this.mensajeRepository.findById(id).map(p -> {
                        this.mensajeRepository.delete(p);
                        return p;})
                    .orElseThrow(() -> new MensajeNotFoundException(id));
        }

        @Transactional
        public Mensaje save(Mensaje mensaje) {
            this.mensajeRepository.save(mensaje);
            this.em.refresh(mensaje);

            return mensaje;
        }

    /**
     * // (Trabajar con optional  los nulos)
     * public List<Mensaje> all(String nombre) {
     *     return Optional.ofNullable(nombre)
     *             .filter(n -> !n.trim().isEmpty())
     *             .map(this.mensajeRepository::findMensajeByNombre_empresaContainingIgnoreCase)
     *             .orElseGet(this.mensajeRepository::findAll);
     * }
     *
     */

        public List<Mensaje> all() {
            return this.mensajeRepository.findAll();
        }

        public List<Mensaje> all(String nombre) {
            if (nombre == null || nombre.trim().isEmpty()){
                log.info("El campo 'nombre' resulto estar vacio o nulo,  devuelvo todos");
                return this.mensajeRepository.findAll();
            }
            return this.mensajeRepository.findMensajeByNombreEmpresaContainingIgnoreCase(nombre);
        }

        public Page<Mensaje> getAll(Pageable pageable) {
            return this.mensajeRepository.findAll(pageable);
        }

        // AUTOMATICA PARA DOS CAMPOS CON DIFERENTES DEVOLUCIONES PAGE Y LIST
        // Sin orden y pageable completo
        public Page<Mensaje> getAllBuscar(String campo, String valor, Pageable pageable) {

            return  campo.equalsIgnoreCase("nombre") ?
                    this.mensajeRepository.findByNombreEmpresaContainingIgnoreCaseOrderByNombreEmpresaAsc(valor, pageable)

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

            Page<Mensaje> pageAll = this.mensajeRepository.findAll(pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("mensajes",pageAll.getContent());
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


            Page<Mensaje> pageAll = this.mensajeRepository.findAll(pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("mensajes",pageAll.getContent());
            response.put("currentPage",pageAll.getNumber());
            response.put("totalItems",pageAll.getTotalElements());
            response.put("totalPages",pageAll.getTotalPages());

            return response;
        }

    }


