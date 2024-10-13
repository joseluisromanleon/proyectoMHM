/*
package org.iesvdm.mhm;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.iesvdm.mhm.domain.Cliente;
import org.iesvdm.mhm.domain.Pedido;
import org.iesvdm.mhm.repository.ClienteRepository;
import org.iesvdm.mhm.repository.PedidoRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashSet;

*/
/**
 * TEST ONETOMANY EAGER
 *//*

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PedidoClienteTests {

    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    PedidoRepository pedidoRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private PlatformTransactionManager transactionManager;
    private TransactionTemplate transactionTemplate;
    @BeforeEach
    public void setUp() {
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Test
    @Order(1)
    void grabarMultiplesPedidoCliente() {

        Cliente cliente1 = new Cliente(0, "español", new HashSet<>());
        clienteRepository.save(cliente1);

        Pedido pedido1 = new Pedido(0, "Pedido1", cliente1);
        cliente1.getPedidosCliente().add(pedido1);
        pedidoRepository.save(pedido1);

        Pedido pedido2 = new Pedido(0, "Pedido2", cliente1);
        cliente1.getPedidosCliente().add(pedido2);
        pedidoRepository.save(pedido2);

        Cliente cliente2 = new Cliente(0, "inglés", new HashSet<>());
        clienteRepository.save(cliente2);

        Pedido pedido3 = new Pedido(0, "Pedido3", cliente2);
        cliente2.getPedidosCliente().add(pedido3);
        pedidoRepository.save(pedido3);

    }

    @Test
    @Order(2)
    void actualizarClientePedidoNull() {

        Pedido pedido1 = pedidoRepository.findById(1L).orElse(null);
        pedido1.setCliente(null);
        pedidoRepository.save(pedido1);

    }

    @Test
    @Order(3)
    void actualizarClientePedidoAOtroCliente() {

        Cliente cliente2 = clienteRepository.findById(2L).orElse(null);
        Pedido pedido2 = pedidoRepository.findById(2L).orElse(null);
        pedido2.setCliente(cliente2);
        cliente2.getPedidosCliente().add(pedido2);

        pedidoRepository.save(pedido2);

    }

    @Test
    @Order(4)
    void eliminarPedido() {
        Pedido pedido1 = pedidoRepository.findById(1L).orElse(null);
        pedidoRepository.delete(pedido1);
    }



    @Test
    @Order(5)
    void eliminarPedidoAsociadasACliente() {

        Cliente cliente2 = clienteRepository.findById(2L).orElse(null);

        cliente2.getPedidosCliente().forEach(pedido -> {//pedido.setCliente(null);
            pedidoRepository.delete(pedido);
        });
        //clienteRepository.delete(cliente2);


    }
    @Test
    @Order(6)
    void eliminarPedidoAsociadasAClienteECliente() {


        Cliente cliente1 = clienteRepository.findById(1L).orElse(null);

        cliente1.getPedidosCliente().forEach(pedido -> {//pedido.setCliente(null);
            pedidoRepository.delete(pedido);
        });

        //ESTE 2o FIND HAY QUE HACERLO
        cliente1 = clienteRepository.findById(1L).orElse(null);
        clienteRepository.delete(cliente1);

    }
}
*/
