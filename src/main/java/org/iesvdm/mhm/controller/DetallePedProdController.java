package org.iesvdm.mhm.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.mhm.domain.DetallePedProd;
import org.iesvdm.mhm.service.DetallePedProdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/v1/api/detalles_pedido_producto")
public class DetallePedProdController {

    /**
     * Rutas Autogeneradas òr Springboot-data
     */

    private final DetallePedProdService detallePedProdService;

    @Autowired
    public DetallePedProdController(DetallePedProdService detallePedProdService) {
        this.detallePedProdService = detallePedProdService;
    }

    @GetMapping(value = {"","/"}, params = {"!nPed", "!nDetalle", "!page", "!size", "!buscar", "!sort", "!column", "!orden", "!campo", "!valor"})
    public List<DetallePedProd> all() {
        log.info("Accediendo a todas los clientes");
        return this.detallePedProdService.all();
    }
    @Transactional
    @PostMapping({"","/"})
    public DetallePedProd newCliente(@RequestBody @Valid DetallePedProd detallePedProdService) {
        return this.detallePedProdService.save(detallePedProdService);
    }

    @GetMapping("/{id}")
    public DetallePedProd one(@PathVariable("id") Long id) {
        return this.detallePedProdService.one(id);
    }

    @PutMapping("/{id}")
    public DetallePedProd replaceCliente(@PathVariable("id") Long id, @RequestBody @Valid  DetallePedProd detallePedProd) {
        return this.detallePedProdService.replace(id, detallePedProd);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void DetallePedProd(@PathVariable("id") Long id) {
        this.detallePedProdService.delete(id);
    }
}
