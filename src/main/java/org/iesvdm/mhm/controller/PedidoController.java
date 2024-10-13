package org.iesvdm.mhm.controller;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.mhm.domain.Pedido;
import org.iesvdm.mhm.service.PedidoService;
import org.iesvdm.mhm.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/v1/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final ProductoService productoService;

    @Autowired
    public PedidoController(PedidoService pedidoService, ProductoService productoService) {
            this.pedidoService = pedidoService;
            this.productoService = productoService;    }


    @PostMapping({"","/"})
    public Pedido newPedido(@RequestBody Pedido pedido) {
        return this.pedidoService.save(pedido);
    }

    @GetMapping("/{id}")
    public Pedido one(@PathVariable("id") Long id) {
        return this.pedidoService.one(id);
    }

    @PutMapping("/{id}")
    public Pedido replacePedido(@PathVariable("id") Long id, @RequestBody Pedido pedido) {
        return this.pedidoService.replace(id, pedido); }

    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletePedido(@PathVariable("id") Long id) {
        this.pedidoService.delete(id);
    }

    @GetMapping(value = {"","/"}, params = {"!page", "!size", "!buscar", "!sort", "!column", "!orden", "!orden_"})
    //opcion2  params={""} no funciona
    public List<Pedido> all() {
        log.info("Accediendo a pedidos sin parametros");
        return this.pedidoService.all();    }



}
