package org.iesvdm.mhm.repository;

import org.iesvdm.mhm.domain.Pedido;
import org.iesvdm.mhm.domain.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {


/*

    // se puede crear la querry como parametro de la notacion y recibimos el dato nombre en parametro
    // 1-  @Query ("SELECT P FROM Pedido P WHERE P.empleado.nombre LIKE %:nombre%")
    // List<Pedido> queryEmpleadoCustomJPQL(@Param("empleado.nombre") String nombre);
    @Query("select P from Pedido as P where P.empleado.nombre like :nombre")
    List<Pedido> queryEmpleadoCustomJPQL(@Param ("nombre") String nombreEmpleado);

    //("SELECT P FROM Pedido P WHERE P.cliente.nombre_empresa LIKE %:nombre%")
    // 2-  pasamos la querry dinamicamente creada
    @Query("select P from Pedido as P where P.cliente.nombre like :nombre")
    List<Pedido> queryClienteCustomJPQL(@Param("nombre") String nombreCliente);



    // Método para consulta SQL nativa
    //(nativeQuery = true, value = "SELECT * FROM Pedido WHERE Pedido.cliente.nombre LIKE CONCAT('%', :nombre, '%')")
    */
/*@Query
    default List<Pedido> queryClienteCustomJPA(@Param("nombre") Optional<String> buscarOptional) {
        return queryProductoCustomJPA(buscarOptional.orElse(null));
    }

    // Método auxiliar para consulta SQL nativa
    @Query(nativeQuery = true, value = "SELECT * FROM Producto WHERE nombre LIKE CONCAT('%', :nombre, '%')")
    List<Pedido> queryProductoCustomJPA(String nombre);*//*



    public Page<Pedido> findPedidoByCliente_NombreOrderByFechaDesc(String nombreCliente, Pageable pageable);
    public Page<Pedido> findPedidoByEmpleado_NombreOrderByFechaDesc(String nombreEmpleado, Pageable pageable);

    public List<Pedido> findPedidoByEmpleado_NombreOrderByClienteAsc(String nombreEmpleado);
    public List<Pedido> findPedidoByEmpleado_NombreOrderByFechaDesc(String nombreEmpresa);

    public Page<Pedido> findPedidoByCliente_Tel_contacto(String nombre, Pageable pageable);

    public Page<Pedido> findPedidoByNombreContainsIgnoreCase(String nombre, Pageable pageable);

    public Page<Pedido> findAll( Pageable pageable);
    public Page<Pedido> findPedidoByCliente_NombreOrderByEmpleado(String nombre,Pageable pageable);
    public Page<Pedido> findPedidoByEmpleado_NombreOrderByCliente(String nombre, Pageable pageable);

*/




}
