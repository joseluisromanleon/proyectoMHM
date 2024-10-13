/*
package org.iesvdm.mhm;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.iesvdm.mhm.domain.Producto;
import org.iesvdm.mhm.domain.Empleado;
import org.iesvdm.mhm.domain.Pedido;
import org.iesvdm.mhm.repository.EmpleadoRepository;
import org.iesvdm.mhm.repository.ProductoRepository;
import org.iesvdm.mhm.repository.PedidoRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.HashSet;


    @SpringBootTest
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class PedidoEmpleadoTest {

        @Autowired
        PedidoRepository pedidoRepository;

        @Autowired
        EmpleadoRepository empleadoRepository;

        @Autowired
        ProductoRepository productoRepository;

        @PersistenceContext
        EntityManager entityManager;

        @Test
        void contextLoads() {
        }


        @Autowired
        private PlatformTransactionManager transactionManager;
        private TransactionTemplate transactionTemplate;

        @BeforeEach
        public void setUp() {
            transactionTemplate = new TransactionTemplate(transactionManager);
        }

        @Test
        @Order(1)
        void grabarPorPropietarioDeManyToMany() {
            Empleado empleado1 = new Empleado(0, "Empleado1", new HashSet<Pedido>());
            empleadoRepository.save(empleado1);

            Empleado empleado2 = new Empleado(0, "empleado2", new HashSet<Pedido>());
            empleadoRepository.save(empleado2);

            Pedido pedido1 = new Pedido(0, "Pedido1", new HashSet<Producto>(), new HashSet<Empleado>());
            pedidoRepository.save(pedido1);


            Pedido pedido2 = new Pedido(0, "Pedido2", new HashSet<Producto>(), new HashSet<Empleado>());
            pedidoRepository.save(pedido2);

            Empleado empleado3 = new Empleado(0, "Empleado3 - Programando fácil con JPA :P", new HashSet<>());
            empleadoRepository.save(empleado3);
            empleado3.getPedidos().add(pedido1);
            //RECUERDA QUE LA COLECCIÓN DE CATEGORIAS ES UN SET Y NO PUEDE HABER REPETIDOS CON EL
            //MISMO ID por eso se graba cada uno

            pedido1.getEmpleados().add(empleado3);
            pedidoRepository.save(pedido1);

            Empleado Empleado2 = new Empleado(0, "Empleado2 - Programando fácil2 con JPA :P", new HashSet<>());

            Empleado2.getPedidos().add(pedido2);
            empleadoRepository.save(Empleado2);

            pedido2.getEmpleados().add(Empleado2);
            pedidoRepository.save(pedido2);

        }




        @Test
        @Order(2)
        void grabarPedidoQueYaExiste() {

            Pedido pedido3 = new Pedido(0, "EEEH Pedido 3!!!!", new HashSet<>());
            pedidoRepository.save(pedido3);

            Empleado empleado2 = new Empleado(0, "Empleado2 - NO programando tan fácilmente...", new HashSet<Pedido>());
            empleadoRepository.save(empleado2);

            //Si se utlizas un fetch LAZY, mejor estrategia realizar un join fetch en JPQL
            //y cargar en la colección. NOTA: si utilizas EAGER puedes prescindir de join fetch.
                       */
/* List<Pedido> pedidos = entityManager.createQuery(
                       "select t " +
                               "from Pedido t " +
                               "join fetch t.empleados ps " +
                               "where ps.id = :id", Pedido.class)
                                .setParameter("id", Empleado2.getId())
                                .getResultList();

                        Empleado2.setPedido(new HashSet<>(pedidos));
                        UtilJPA.initializeLazyManyToManyByJoinFetch(entityManager,
                                Pedido.class,
                                Producto.class,
                                Empleado2.getId(),
                                Empleado2::setPedido
                        );*//*



            Pedido pedido1 = pedidoRepository.findById(1L).orElse(null);
            //Si se utlizas un fetch LAZY, mejor estrategia realizar un join fetch en JPQL
            //y cargar en la colección. NOTA: si utilizas EAGER puedes prescindir de join fetch.
                       */
/* List<Producto> empleados = entityManager.createQuery(
                                "select p " +
                                        "from Producto p " +
                                        "join fetch p.pedidos ts " +
                                        "where ts.id = :id", Producto.class)
                                .setParameter("id", pedido1.getId())
                                .getResultList();
                        pedido1.setProductos(new HashSet<>(empleados));
                        UtilJPA.initializeLazyManyToManyByJoinFetch(entityManager,
                                Producto.class,
                                Pedido.class,
                                pedido1.getId(),
                                pedido1::setProductos
                        );*//*


            empleado2.getPedidos().add(pedido1);
            empleado2.getPedidos().add(pedido3);
            empleadoRepository.save(empleado2);
        }


        @Test
        @Order(3)
        void desasociarPedido() {

            Pedido pedido4 = pedidoRepository.findById(4L).orElse(null);
            //Si se utlizas un fetch LAZY, la mejor estrategia es realizar un join fetch en JPQL
            //y cargar en la colección. NOTA: si utilizas EAGER puedes prescindir de join fetch.
                        */
/*List<Producto> empleados = entityManager.createQuery(
                        "select p " +
                                "from Producto p " +
                                "join fetch p.pedidos ts " +
                                "where ts.id = :id", Producto.class)
                                .setParameter("id", pedido.getId())
                                .getResultList();

                        pedido.setProductos(new HashSet<>(empleados));
                                        UtilJPA.initializeLazyManyToManyByJoinFetch(entityManager,
                                                Producto.class,
                                                Pedido.class,
                                                pedido1.getId(),
                                                pedido1::setProductos
                        );
                        *//*

            ArrayList<Empleado> auxCopyEmpleados = new ArrayList<>(pedido4.getEmpleados());
            auxCopyEmpleados.forEach(Empleado -> {
                Empleado.getPedidos().remove(pedido4);
                pedido4.getEmpleados().remove(Empleado);
            });
            //SE PUEDE DESVINCULAR EL TAG DEL MANYTOMANY DADO QUE
            //NO ES EL PROPIETARIO, EN ESTE CASO EL PROPIETARIO
            //DE LA RELACION ES POST
            pedidoRepository.save(pedido4);
        }

        @Test
        @Order(4)
        void borrarPedido() {

            Pedido pedido4 = pedidoRepository.findById(4L).orElse(null);
            //Si se utlizas un fetch LAZY, mejor estrategia realizar un join fetch en JPQL
            //y cargar en la colección. NOTA: si utilizas EAGER puedes prescindir de join fetch.
       */
/* List<Producto> empleados = entityManager.createQuery(
                        "select p " +
                                "from Producto p " +
                                "join fetch p.pedidos ts " +
                                "where ts.id = :id", Producto.class)
                .setParameter("id", pedido.getId())
                .getResultList();
        pedido.setProductos(new HashSet<>(empleados));
                        UtilJPA.initializeLazyManyToManyByJoinFetch(entityManager,
                                Producto.class,
                                Pedido.class,
                                pedido3.getId(),
                                pedido3::setProductos
                        );*//*



            ArrayList<Empleado> auxCopyEmpleados = new ArrayList<>(pedido4.getEmpleados());
            auxCopyEmpleados.forEach(empleado -> {
                empleado.getPedidos().remove(pedido4);
                empleadoRepository.save(empleado);
                pedido4.getEmpleados().remove(empleado);
            });

            ArrayList<Producto> auxCopyProductos = new ArrayList<>(pedido4.getProductos());
            auxCopyProductos.forEach(cat -> {
                cat.getPedidos().remove(pedido4);
                productoRepository.save(cat);
                pedido4.getProductos().remove(cat);
            });
            pedidoRepository.save(pedido4);

            //SE PUEDE DESVINCULAR EL TAG DEL MANYTOMANY DADO QUE
            //NO ES EL PROPIETARIO, EN ESTE CASO EL PROPIETARIO
            //DE LA RELACION ES POST

            pedidoRepository.delete(pedido4);

        }

    }
    

*/
