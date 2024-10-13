package org.iesvdm.mhm.repository;

import org.iesvdm.mhm.domain.Cliente;
import org.iesvdm.mhm.domain.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    public List<Cliente> findClienteByNombreContainingIgnoreCaseOrderByIdDesc(String nombreCliente);

    @Query("SELECT DISTINCT c FROM Cliente c JOIN c.empleados e WHERE LOWER(e.nombre) LIKE LOWER(concat('%', :nombreEmpleado, '%'))")
    List<Cliente> findClientesByNombreEmpleadoContaining(@Param("nombreEmpleado") String nombreEmpleado);


    public  Page<Cliente> findClienteByEmpleadosIsEmpty(Pageable pageable);
    public  Page<Cliente> findClienteByCpContains(String valor, Pageable pageable);
    public  Page<Cliente> findClienteByPedidosIsEmptyOrderByCp(Pageable pageable);



/*

    // se puede crear la querry como parametro de la notacion y recibimos el dato nombre en parametro
    // 1-  @Query ("SELECT P FROM liente P WHERE P.empleado.nombre LIKE %:nombre%")
    // List<Cliente> queryEmpleadoCustomJPQL(@Param("empleado.nombre") String nombre);
    @Query("select P from Cliente as P where P.empleado.nombre like :nombre")
    List<Cliente> queryEmpleadoCustomJPQL(@Param ("nombre") String nombreEmpleado);

    //("SELECT P FROM Cliente P WHERE P.cliente.nombre_empresa LIKE %:nombre%")
    // 2-  pasamos la querry dinamicamente creada
    @Query("select P from Cliente as P where P.cliente.nombre like :nombre")
    List<Cliente> queryClienteCustomJPQL(@Param("nombre") String nombreCliente);



    // Método para consulta SQL nativa
    //(nativeQuery = true, value = "SELECT * FROM Cliente WHERE Cliente.cliente.nombre LIKE CONCAT('%', :nombre, '%')")
    */
/*@Query
    default List<Cliente> queryClienteCustomJPA(@Param("nombre") Optional<String> buscarOptional) {
        return queryProductoCustomJPA(buscarOptional.orElse(null));
    }

    // Método auxiliar para consulta SQL nativa
    @Query(nativeQuery = true, value = "SELECT * FROM Producto WHERE nombre LIKE CONCAT('%', :nombre, '%')")
    List<Cliente> queryProductoCustomJPA(String nombre);*//*




    public List<Cliente> findClienteByEmpleado_NombreOrderByClienteAsc(String nombreEmpleado);
    public List<Cliente> findClienteByEmpleado_NombreOrderByFechaDesc(String nombreEmpresa);

    public Page<Cliente> findClienteByCliente_Tel_contacto(String nombre, Pageable pageable);

    public Page<Cliente> findClienteByNombreContainsIgnoreCase(String nombre, Pageable pageable);

    public Page<Cliente> findAll( Pageable pageable);
    public Page<Cliente> findClienteByCliente_NombreOrderByEmpleado(String nombre,Pageable pageable);
    public Page<Cliente> findClienteByEmpleado_NombreOrderByCliente(String nombre, Pageable pageable);

*/

}
