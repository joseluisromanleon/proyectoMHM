package org.iesvdm.mhm.service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.mhm.domain.DetallePedProd;
import org.iesvdm.mhm.exception.DetallePedidoProductoNotFoundException;
import org.iesvdm.mhm.repository.DetallePedProdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;


@Slf4j
@Service
public class DetallePedProdService {
    
    private final DetallePedProdRepository detallePedProdRepository;
    
    // para las consultas dinamicas  8 en una  logica de control para multiconsultas
    @PersistenceContext
    EntityManager em;

    @Autowired
    public DetallePedProdService(DetallePedProdRepository detallePedProdRepository) {
        this.detallePedProdRepository = detallePedProdRepository;
    }

    @Transactional
    public DetallePedProd save(DetallePedProd DetallePedProd) {
        this.detallePedProdRepository.save(DetallePedProd);
        this.em.refresh(DetallePedProd);

        return DetallePedProd;
    }

    public List<DetallePedProd> all() {
        return this.detallePedProdRepository.findAll();
    }

    public DetallePedProd one(Long id) {
        return this.detallePedProdRepository.findById(id)
                .orElseThrow(() -> new DetallePedidoProductoNotFoundException(id));
    }

    public DetallePedProd replace(Long id, DetallePedProd DetallePedProd) {
        return this.detallePedProdRepository.findById(id).map( c -> (id.equals(DetallePedProd.getId())  ?
                        this.detallePedProdRepository.save(DetallePedProd) : null))
                .orElseThrow(() -> new DetallePedidoProductoNotFoundException(id));
    }

    public void delete(Long id) {
        log.info("Eliminando el DetallePedProd con ID: " + id);
        DetallePedProd dpp = this.detallePedProdRepository.findById(id)
                .orElseThrow(() -> new DetallePedidoProductoNotFoundException(id));

        detallePedProdRepository.delete(dpp);
    }





} // LLAVE FINAL




 /*

    public Page<DetallePedProd> getAll(Pageable pageable) {
        Page<DetallePedProd> pageDPP = this.detallePedProdRepository.findAll(pageable);

        return pageDPP;
    }*/
/*
    // AUTOMATICA PARA DOS CAMPOS CON DIFERENTES DEVOLUCIONES PAGE Y LIST
    public Page<DetallePedProd> getAllBuscar(String buscar, Pageable pageable) {
        return this.detallePedProdRepository.findBy(buscar, pageable);
    }

    public Map<String, Object> procesarOrden(String campo, String direccion) {

        Pageable pageable = (direccion.equals("desc")) ?
                PageRequest.of(0, 20, Sort.by(campo).descending()) :
                PageRequest.of(0, 20, Sort.by(campo).ascending());

        Page<DetallePedProd> pageAll = this.detallePedProdRepository.findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("categorias",pageAll.getContent());
        response.put("currentPage",pageAll.getNumber());
        response.put("totalItems",pageAll.getTotalElements());
        response.put("totalPages",pageAll.getTotalPages());

        return response;
    }

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


        Page<DetallePedProd> pageAll = this.detallePedProdRepository.findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("categorias",pageAll.getContent());
        response.put("currentPage",pageAll.getNumber());
        response.put("totalItems",pageAll.getTotalElements());
        response.put("totalPages",pageAll.getTotalPages());


        return response;
    }

    // MANUAL PARA DOS CAMPOS CON DIFERENTES DEVOLUCIONES PAGE Y LIST
    *//*public Page<DetallePedProd> getAllBuscar( String buscar, Pageable pageable) {
        Page<DetallePedProd> pageCat = this.detallePedProdRepository.findAll(pageable);
        List<DetallePedProd> listCat = this.detallePedProdRepository.findByNombreContainsIgnoreCase(buscar);

        // Concatenar las dos listas en un Set
        Set<DetallePedProd> mergedSet = Stream.concat(pageCat.getContent().stream(), listCat.stream())
                .collect(Collectors.toSet());

        // Convertir el HashSet en una lista nuevamente
        List<DetallePedProd> mergedList = mergedSet.stream().collect(Collectors.toList());


        // Crear un nuevo objeto Page a partir de la lista combinada
        Page<DetallePedProd> mergedPage = new PageImpl<>(mergedList, pageable, mergedList.size());

        return mergedPage;
    }*//*






        this.detallePedProdRepository.findById(id).map(c -> {

                    this.detallePedProdRepository.delete(c);
                    return c;})
                .orElseThrow(() -> new DetallePedidoProductoNotFoundException(id));
    }*/
/*

    // ******************   CALCULOS SOBRE LA BASE DE DATOS     ******************

    public DetallePedProd addDetallePedProdADetallePedidoProducto(Long idPel, Long idCat){
        DetallePedProd p = detallePedProdService.one(idPel);
        DetallePedProd c = one(idCat);
        if(!this.one(idCat).getDetallePedProds().contains(p)){
            c.getDetallePedProds().add(p);
            save(c);
            detallePedProdsPorDetallePedidoProductos();
        }

        return c;
    }
    public int numDetallePedProdsPorDetallePedidoProducto(Long id) {
        DetallePedProd cat = one(id);
        int cont = cat.getDetallePedProds().size();
        if  (cont > 0) {
            cat.setNumPelis(cont);
        }else{
            cat.setNumPelis(0);
            cont = 0;
        }
        return cont;
    }


    public void detallePedProdsPorDetallePedidoProductos() {
        List<DetallePedProd> cats = detallePedProdRepository.findAll();
        int cont = 0;
        for (DetallePedProd cat : cats) {
            cont = (cat.getDetallePedProds().size());
            if (cont >0) {
                cat.setNumPelis(cont);
            }else{
                cat.setNumPelis(0);
            }
        }
    }


    // *****************    TRABAJANDO CON LA BASE DE DATOS     *******************

    //BLOQUE METHOD @Query JPQL CON OBJETOS DE ENTIDADES JPA
    //Notación para asociar peticiones JPQL o SQL a un método pasando parámetros por orden de entrada
    // de la firma del método o parametrizados con nombre
    public List<DetallePedProd> queryDetallePedidoProductoCustomJPQL(Optional<String> buscarOptional, Optional<String>  ordenarOptional) {
        String queryBodyString = "select C from DetallePedProd as C";  //cuerpo repetitivo
        if (buscarOptional.isPresent()){
            queryBodyString += " where C.nombre like :nombre";
        }
        if (ordenarOptional.isPresent()){
            if(buscarOptional.isPresent() && "asc".equalsIgnoreCase(buscarOptional.get())){
                queryBodyString += " order by C.nombre ASC";
            }else if(buscarOptional.isPresent() && "desc".equalsIgnoreCase(buscarOptional.get())) {
                queryBodyString += " order by C.nombre desc";
            }
        }
        Query query = em.createQuery(queryBodyString.toString());
        if (buscarOptional.isPresent()){
            query.setParameter("nombre", "%"+buscarOptional.get()+"%");
        }
        return query.getResultList();
    }



    //BLOQUE DE MÉTODOS @QUERY CON "SQL" NATIVO BASADO EN LAS TABLAS.
    //@Query nativeQuery = true, es decir, SQL:
    // Se Parametrizan con el nombre del parámetro:  (%:nombre%)
    public List<DetallePedProd> queryDetallePedidoProductoCustomJPA(Optional<String> buscarOptional, Optional<String>  ordenarOptional ) {
        String queryBodyString = "select * from DetallePedProd";
        if (buscarOptional.isPresent()){
            queryBodyString += "where nombre like :nombre";
        }
        if (ordenarOptional.isPresent()){
            if(buscarOptional.isPresent() && "asc".equalsIgnoreCase(buscarOptional.get())){
                queryBodyString += "order by nombre ASC";
            }else if(buscarOptional.isPresent() && "desc".equalsIgnoreCase(buscarOptional.get())) {
                queryBodyString += "order by nombre desc";
            }
        }
        Query query = em.createNativeQuery(queryBodyString.toString(), DetallePedProd.class);
        if (buscarOptional.isPresent()){
            query.setParameter("nombre", "%"+buscarOptional.get()+"%");
        }

        return query.getResultList();
    }

    //Bloque con Query de JPA auto
    public List<DetallePedProd> findByNombreContainsIgnoreCaseOrderByNombre(String nombre, String orden){
        List<DetallePedProd> lista = null;
        if(orden.equalsIgnoreCase("asc")){
            lista = detallePedProdRepository.findByNombreContainsIgnoreCaseOrderByNombreAsc(nombre);

        }else if(orden.equalsIgnoreCase("desc")){
            lista = detallePedProdRepository.findByNombreContainsIgnoreCaseOrderByNombreDesc(nombre);
        }
        return lista;
    }

    public List<DetallePedProd> findByNombreContainsIgnoreCase(String nombre){
        List<DetallePedProd> lista = null;
        return detallePedProdRepository.findByNombreContainsIgnoreCase(nombre);

    }

*/


