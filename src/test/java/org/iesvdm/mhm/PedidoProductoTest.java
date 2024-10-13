/*
package org.iesvdm.mhm;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.iesvdm.mhm.domain.*;
import org.iesvdm.mhm.repository.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.HashSet;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PedidoProductoTest {

        @Autowired
        PedidoRepository pedidoRepository;

        @Autowired
        ClienteRepository clienteRepository;

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
                Cliente cliente1 = new Cliente(0, "Espaniol", new HashSet<Pedido>());
                clienteRepository.save(cliente1);

                Cliente cliente2 = new Cliente(0, "Ingles", new HashSet<Pedido>());
                clienteRepository.save(cliente2);

                Pedido pedido1 = new Pedido(0, "Programación", new HashSet<Producto>(), cliente1);
                pedidoRepository.save(pedido1);


                Pedido pedido2 = new Pedido(0, "Base de datos", new HashSet<Producto>(), cliente2);
                pedidoRepository.save(pedido2);

                Producto producto1 = new Producto(0, "Producto1 - Programando fácil con JPA :P", new HashSet<>());

                producto1.getPedidos().add(pedido1);
                //RECUERDA QUE LA COLECCIÓN DE CATEGORIAS ES UN SET Y NO PUEDE HABER REPETIDOS CON EL
                //MISMO ID por eso se graba cada uno
                productoRepository.save(producto1);

                pedido1.getProductos().add(producto1);
                pedidoRepository.save(pedido1);

                Producto producto2 = new Producto(0, "Producto2 - Programando fácil2 con JPA :P", new HashSet<>());

                producto2.getPedidos().add(pedido2);
                productoRepository.save(producto1);

                pedido2.getProductos().add(producto2);
                pedidoRepository.save(pedido2);

        }




                @Test
                @Order(2)
                void grabarPedidoQueYaExiste() {

                        Pedido pedido3 = new Pedido(0, "EEEH Pedido 3!!!!", new HashSet<>());
                        pedidoRepository.save(pedido3);

                        Producto producto2 = new Producto(0, "Producto2 - NO programando tan fácilmente...", new HashSet<>());
                        productoRepository.save(producto2);

                        //Si se utlizas un fetch LAZY, mejor estrategia realizar un join fetch en JPQL
                        //y cargar en la colección. NOTA: si utilizas EAGER puedes prescindir de join fetch.
                       */
/* List<Pedido> pedidos = entityManager.createQuery(
                       "select t " +
                               "from Pedido t " +
                               "join fetch t.productos ps " +
                               "where ps.id = :id", Pedido.class)
                                .setParameter("id", producto2.getId())
                                .getResultList();

                        producto2.setPedido(new HashSet<>(pedidos));
                        UtilJPA.initializeLazyManyToManyByJoinFetch(entityManager,
                                Pedido.class,
                                Producto.class,
                                producto2.getId(),
                                producto2::setPedido
                        );*//*



                        Pedido pedido1 = pedidoRepository.findById(1L).orElse(null);
                      //Si se utlizas un fetch LAZY, mejor estrategia realizar un join fetch en JPQL
                      //y cargar en la colección. NOTA: si utilizas EAGER puedes prescindir de join fetch.
                       */
/* List<Producto> productos = entityManager.createQuery(
                                "select p " +
                                        "from Producto p " +
                                        "join fetch p.pedidos ts " +
                                        "where ts.id = :id", Producto.class)
                                .setParameter("id", pedido1.getId())
                                .getResultList();
                        pedido1.setProductos(new HashSet<>(productos));
                        UtilJPA.initializeLazyManyToManyByJoinFetch(entityManager,
                                Producto.class,
                                Pedido.class,
                                pedido1.getId(),
                                pedido1::setProductos
                        );*//*


                        producto2.getPedidos().add(pedido1);
                        producto2.getPedidos().add(pedido3);
                        productoRepository.save(producto2);
                }


                @Test
                @Order(3)
                void desasociarPedido() {

                        Pedido pedido3 = pedidoRepository.findById(3L).orElse(null);
                        //Si se utlizas un fetch LAZY, la mejor estrategia es realizar un join fetch en JPQL
                        //y cargar en la colección. NOTA: si utilizas EAGER puedes prescindir de join fetch.
                        */
/*List<Producto> productos = entityManager.createQuery(
                        "select p " +
                                "from Producto p " +
                                "join fetch p.pedidos ts " +
                                "where ts.id = :id", Producto.class)
                                .setParameter("id", pedido.getId())
                                .getResultList();

                        pedido.setProductos(new HashSet<>(productos));
                                        UtilJPA.initializeLazyManyToManyByJoinFetch(entityManager,
                                                Producto.class,
                                                Pedido.class,
                                                pedido1.getId(),
                                                pedido1::setProductos
                        );
                        *//*

                        ArrayList<Producto> auxCopyProductos = new ArrayList<>(pedido3.getProductos());
                        auxCopyProductos.forEach(producto -> {
                                producto.getPedidos().remove(pedido3);
                                pedido3.getProductos().remove(producto);
                        });
                        //SE PUEDE DESVINCULAR EL TAG DEL MANYTOMANY DADO QUE
                        //NO ES EL PROPIETARIO, EN ESTE CASO EL PROPIETARIO
                        //DE LA RELACION ES POST
                        pedidoRepository.save(pedido3);
                }

                @Test
                @Order(4)
                void borrarPedido() {

                        Pedido pedido3 = pedidoRepository.findById(3L).orElse(null);
                        //Si se utlizas un fetch LAZY, mejor estrategia realizar un join fetch en JPQL
                        //y cargar en la colección. NOTA: si utilizas EAGER puedes prescindir de join fetch.
       */
/* List<Producto> productos = entityManager.createQuery(
                        "select p " +
                                "from Producto p " +
                                "join fetch p.pedidos ts " +
                                "where ts.id = :id", Producto.class)
                .setParameter("id", pedido.getId())
                .getResultList();
        pedido.setProductos(new HashSet<>(productos));
                        UtilJPA.initializeLazyManyToManyByJoinFetch(entityManager,
                                Producto.class,
                                Pedido.class,
                                pedido3.getId(),
                                pedido3::setProductos
                        );*//*



                        ArrayList<Producto> auxCopyProductos = new ArrayList<>(pedido3.getProductos());
                        auxCopyProductos.forEach(producto -> {
                                producto.getPedidos().remove(pedido3);
                                pedido3.getProductos().remove(producto);
                        });
                        //SE PUEDE DESVINCULAR EL TAG DEL MANYTOMANY DADO QUE
                        //NO ES EL PROPIETARIO, EN ESTE CASO EL PROPIETARIO
                        //DE LA RELACION ES POST
                        pedidoRepository.delete(pedido3);

                }

}



*/
