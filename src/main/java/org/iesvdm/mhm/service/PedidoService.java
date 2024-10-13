package org.iesvdm.mhm.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.mhm.domain.Pedido;
import org.iesvdm.mhm.domain.Producto;
import org.iesvdm.mhm.exception.PedidoNotFoundException;
import org.iesvdm.mhm.repository.PedidoRepository;
import org.iesvdm.mhm.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    // para las consultas dinamicas  8 en una  logica de control para multiconsultas
    @PersistenceContext
    EntityManager em;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, ProductoRepository productoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

    public List<Pedido> all() {
        return this.pedidoRepository.findAll();
    }

    public Pedido save(Pedido pedido) {
        return this.pedidoRepository.save(pedido);
    }

    public Pedido one(Long id) {
        return this.pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException(id));
    }

    public Pedido replace(Long id, Pedido pedido) {

        return this.pedidoRepository.findById(id)
                .map( p -> (id.equals(pedido.getId()) ?
                        this.pedidoRepository.save(pedido) : null))
                .orElseThrow(() -> new PedidoNotFoundException(id));

    }

    public void delete(Long id) {
        this.pedidoRepository.findById(id).map(p -> {
            this.pedidoRepository.delete(p);
                                                        return p;})
                .orElseThrow(() -> new PedidoNotFoundException(id));
    }

    public Page<Pedido> getAll(Pageable pageable) {
        Page<Pedido> pageCat = this.pedidoRepository.findAll(pageable);

        return pageCat;
    }

}
