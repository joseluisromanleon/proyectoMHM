package org.iesvdm.mhm.repository;

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
public interface ProductoRepository extends JpaRepository<Producto, Long> {


    public Page<Producto> findProductoByCategoriaContainingIgnoreCase(String valor, Pageable pageable);
    public Page<Producto> findProductoByProveedorContainingIgnoreCase(String valor, Pageable pageable);
    public Page<Producto> findProductoByStock(String valor, Pageable pageable);
    public Page<Producto> findByNombreContainingIgnoreCaseOrderByNombreAsc(String valor, Pageable pageable);

    public Page<Producto> findAll(Pageable pageable);

    public List<Producto> findProductoByNombreIgnoreCase(String nombre);

    /// ******************************

    //public Page<Producto> findByProveedorContainsIgnoreCaseAndCategoriaContainingIgnoreCase(String proveedor, String categoria, Pageable pageable);

/* public List<Producto> findAll();

    public List<Producto> findByNombreContainsIgnoreCaseOrderByNombreAsc(String nombre);

    public List<Producto> findByNombreContainsIgnoreCaseOrderByNombreDesc(String nombre);

    public List<Producto> findByNombreContainsIgnoreCase(String nombre);

    public Page<Producto> findByNombreContainsIgnoreCase(String nombre, Pageable pageable);

 */


}
