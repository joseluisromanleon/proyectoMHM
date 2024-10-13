package org.iesvdm.mhm.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.mhm.domain.Cliente;
import org.iesvdm.mhm.domain.Empleado;
import org.iesvdm.mhm.domain.Producto;
import org.iesvdm.mhm.exception.ClienteNotFoundException;
import org.iesvdm.mhm.exception.EmpleadoNotFoundException;
import org.iesvdm.mhm.repository.ClienteRepository;
import org.iesvdm.mhm.repository.EmpleadoRepository;
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
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EmpleadoRepository empleadoRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository,
                          EmpleadoRepository empleadoRepository) {
        this.clienteRepository = clienteRepository;
        this.empleadoRepository = empleadoRepository;
    }

    /**
     * Metodos autoGenerados por Spring
     * @return
     */
    public List<Cliente> all() {
        return this.clienteRepository.findAll();
    }

    public Cliente save(Cliente cliente) {
        return this.clienteRepository.save(cliente);
    }

    public Cliente one(Long id) {
        return this.clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));
    }

    public Cliente replace(Long id, Cliente cliente) {
        return this.clienteRepository.findById(id).map( p -> (id.equals(cliente.getId())  ?
                        this.clienteRepository.save(cliente) : null))
                .orElseThrow(() -> new ClienteNotFoundException(id));
    }

    public void delete(Long id) {
        this.clienteRepository.findById(id).map(p -> {this.clienteRepository.delete(p);
                    return p;})
                .orElseThrow(() -> new ClienteNotFoundException(id));
    }

    /**
     * Metodos Especificos para otras Rutas de Controller
     */

    // ******************   CALCULOS y OPERACIONES SOBRE LA BASE DE DATOS     ******************

    public Page<Cliente> getAll(Pageable pageable){
        return this.clienteRepository.findAll(pageable);
    }

    public Page<Cliente> getAllBuscar(String campo, String valor, Pageable pageable) {

        return campo.equalsIgnoreCase("empleados") ?
                this.clienteRepository.findClienteByEmpleadosIsEmpty(pageable)
                : campo.equalsIgnoreCase("cp") ?
                this.clienteRepository.findClienteByCpContains(valor, pageable)
                : campo.equalsIgnoreCase("pedidos") ?
                this.clienteRepository.findClienteByPedidosIsEmptyOrderByCp(pageable)
                : getAll(pageable);

    }
    public List<Cliente> clientesNombre(String nombreCliente){
        return this.clienteRepository.findClienteByNombreContainingIgnoreCaseOrderByIdDesc(nombreCliente);
    }

    public List<Cliente> clientesEmpleado(String nombreEmpleado){
        return this.clienteRepository.findClientesByNombreEmpleadoContaining(nombreEmpleado);
    }

    // *****************   METODOS  PARA  USOS DE LA CLASE  ***********************
    // Añadir un Empleado a un Cliente Entra en Bucle  ******
    public Cliente addAclienteEmpleado(Long idc, Long ide){
        Cliente cli = clienteRepository.findById(idc)
                .orElseThrow(() -> new ClienteNotFoundException(idc));
        Empleado emp = this.empleadoRepository.findById(ide)
                .orElseThrow(() -> new EmpleadoNotFoundException(ide));
        if(!cli.getEmpleados().contains(emp)) {
            cli.getEmpleados().add(emp);

            clienteRepository.save(cli);  //persistencia a BBDD
            log.info("empleado" + emp + "añadido a cliente " + cli);
        }
        if (!emp.getClientes().contains(cli)) {
            emp.getClientes().add(cli);
            empleadoRepository.save(emp); //persistencia a BDD
            log.info("cliente" + emp + "añadido a empleado " + emp);
        }
            return clienteRepository.findById(idc).get();

    }

    // un orden  y mapeando pageable configurable
    public Map<String, Object> procesarOrden(String campo, String direccion) {

        Pageable pageable = (direccion.equals("desc")) ?
                PageRequest.of(0, 20, Sort.by(campo).descending()) :
                PageRequest.of(0, 20, Sort.by(campo).ascending());

        Page<Cliente> pageAll = this.clienteRepository
                .findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("clientes",pageAll.getContent());
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


        Page<Cliente> pageAll = this.clienteRepository.findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("clientes",pageAll.getContent());
        response.put("currentPage",pageAll.getNumber());
        response.put("totalItems",pageAll.getTotalElements());
        response.put("totalPages",pageAll.getTotalPages());

        return response;
    }



/*




    // *****************    TRABAJANDO CON LA BASE DE DATOS     *******************

    //BLOQUE METHOD @Query JPQL CON OBJETOS DE ENTIDADES JPA
    //Notación para asociar peticiones JPQL o SQL a un método pasando parámetros por orden de entrada
    // de la firma del método o parametrizados con nombre

    public List<Cliente> queryEmpleadoCustomJPQL(Optional<String> nombreEmpleadoOptional, Optional<String>  ordenarOptional) {
        String queryBodyString = "select P from Cliente as P";  //cuerpo repetitivo
        if (nombreEmpleadoOptional.isPresent()){
            queryBodyString += " where P.empleado.nombre like :" + nombreEmpleadoOptional.get();
        }
        if (ordenarOptional.isPresent() && nombreEmpleadoOptional.isPresent()){
            if("asc".equalsIgnoreCase(ordenarOptional.get())){
                queryBodyString += " order by P.empleado.nombre ASC";
            }else if("desc".equalsIgnoreCase(nombreEmpleadoOptional.get())) {
                queryBodyString += " order by P.empleado.nombre desc";
            }
        }
        Query query = em.createQuery(queryBodyString.toString());
        if (nombreEmpleadoOptional.isPresent()){
            query.setParameter("nombre", "%"+nombreEmpleadoOptional.get()+"%");
        }
        return query.getResultList();
    }

    public List<Cliente> queryClienteCustomJPQL(Optional<String> nombreClienteOptional, Optional<String>  ordenarOptional) {
        String queryBodyString = "select P from Cliente as P";  //cuerpo repetitivo
        if (nombreClienteOptional.isPresent()){
            queryBodyString += " where P.cliente.nombre like :nombre";
        }
        if (ordenarOptional.isPresent() && nombreClienteOptional.isPresent()){
            if("asc".equalsIgnoreCase(ordenarOptional.get())){
                queryBodyString += " order by P.cliente.nombre ASC";
            } else if("desc".equalsIgnoreCase(ordenarOptional.get())) {
                queryBodyString += " order by P.cliente.nombre desc";
            }
        }
        Query query = em.createQuery(queryBodyString);

        if (nombreClienteOptional.isPresent()){
            query.setParameter("nombre", "%"+nombreClienteOptional.get()+"%");
        }
        return query.getResultList();
    }


    public List<Cliente> queryclienteCustomJPQL(Optional<String> campoOptional,Optional<String> subcampoOptional,
                                              Optional<String> valorOptional, Optional<String>  ordenarOptional,
                                              Optional<String> dirOptional)
    {
        String queryBodyString = "select P from Cliente as P";  //cuerpo repetitivo

        if (campoOptional.isPresent()){
            queryBodyString += " where P." + campoOptional.get() ;
        }
        if (subcampoOptional.isPresent()){
            queryBodyString += "." + subcampoOptional.get();
        }
        if (valorOptional.isPresent()){
            queryBodyString += " like :" + valorOptional.get();
        }
        if (ordenarOptional.isPresent() && "asc".equalsIgnoreCase(dirOptional.get())){
            queryBodyString += " order by P."+ordenarOptional.get() + " ASC";
            }else if(ordenarOptional.isPresent() && "desc".equalsIgnoreCase(dirOptional.get())) {
                queryBodyString += " order by P."+ordenarOptional+"DESC";
            }else{
            queryBodyString = "select P from Cliente as P" ;

        }
        Query query = em.createQuery(queryBodyString.toString());
        if (campoOptional.isPresent()){
            query.setParameter("nombre", "%"+campoOptional.get()+"%");
        }
        return this.clienteRepository.queryClienteCustomJPQL(query.toString());
    }

    //BLOQUE DE MÉTODOS @QUERY CON "SQL" NATIVO BASADO EN LAS TABLAS.
    //@Query nativeQuery = true, es decir, SQL:
    // Se Parametrizan con el nombre del parámetro:  (%:nombre%)
    public List<Producto> queryProductoCustomJPA(Optional<String> buscarOptional,Optional<String>  ordenarOptional ) {
        String queryBodyString = "select * from producto";
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
        Query query = em.createNativeQuery(queryBodyString.toString(),Producto.class);
        if (buscarOptional.isPresent()){
            query.setParameter("nombre", "%"+buscarOptional.get()+"%");
        }

        return query.getResultList();
    }

    //Bloque con Query de JPA auto
    public List<Cliente> findclienteByEmpleadoNombre(String nombreEmpleado, String orden){
        return (orden.equalsIgnoreCase("asc"))?
            clienteRepository.findclienteByEmpleado_NombreOrderByClienteAsc(nombreEmpleado)
            : clienteRepository.findclienteByEmpleado_NombreOrderByFechaDesc(nombreEmpleado);
    }


    public Page<Cliente> findclienteByClienteNombre(String nombreCliente, Pageable pageable){
        return  clienteRepository.findclienteByCliente_NombreOrderByEmpleado(nombreCliente, pageable);
    }

    public Page<Cliente> findclienteByEmpleadoNombre(String nombreEmpleado, Pageable pageable){
        return  clienteRepository.findclienteByEmpleado_NombreOrderByCliente(nombreEmpleado, pageable);
    }





*/
    /*
    public Cliente addAclienteProducto(Long idped, Long idprod){
        Cliente ped = one(idped);
        Producto prod = this.productoRepository.findById(idprod).get();
        if(!prod.getclientes().contains(ped)){
            ped.getProductos().add(prod);
            save(ped);
            numProductosPorcliente(idped);
            log.info("producto" + prod + "añadido a cliente " + ped );
        }

        return one(idped);
    }

    public int numProductosPorcliente(Long idcliente) {
        Cliente ped = one(idcliente);
        int cont = ped.getProductos().size();
        if  (cont > 0) {
            ped.setUnidadesVendidas(cont);
        }else{
            ped.setUnidadesVendidas(0);
            cont = 0;
        }
        return cont;
    }

*/

}
