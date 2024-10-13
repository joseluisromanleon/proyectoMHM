package org.iesvdm.mhm.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.mhm.domain.Proveedor;
import org.iesvdm.mhm.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/v1/api/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    @Autowired
    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }


    @PostMapping({"","/"})
    public Proveedor newProveedor(@RequestBody @Valid Proveedor proveedor) {
        log.info("Creando una Proveedor"+ proveedor.getNombreEmpresa());
        return this.proveedorService.save(proveedor);
    }

    @GetMapping("/{id}")
    public Proveedor one(@PathVariable("id") Long id) {
        log.info("Buscando Proveedor con id "+id);
        return this.proveedorService.one(id);
    }

    @PutMapping("/{id}")
    public Proveedor replaceProveedor(@PathVariable("id") Long id, @RequestBody Proveedor proveedor) {
        log.info("Actualizando Proveedor con id "+ id);
        return this.proveedorService.replace(id, proveedor);
    }

    @DeleteMapping({"{id}","/{id}"})
    public void deleteProveedor(@PathVariable("id") Long id) {
        log.info("Eliminando Categoría con id "+id);
        this.proveedorService.delete(id);
    }

    @GetMapping(value = {"","/"}, params = {"!empresa", "!page", "!size", "!buscar", "!sort", "!column", "!orden"})
    //******  agregar paremetros que se quieran evitar  *******
    public List<Proveedor> all() {
        proveedorService.all();
        log.info("Accediendo a todas las categorías sin parametros");
        return this.proveedorService.all();
    }

    //CON PARAMETROS
    //Buscar una Categoría por nombre
    @GetMapping(value = {"", "/"}, params = {"empresa"})
    public List<Proveedor> all(String nombre) {
        log.info("Accediendo a una Categoría por nombre");
        return this.proveedorService.all(nombre);
    }

    @GetMapping(value = {"", "/"}, params = {"page", "size", "sort"})
    // para los campos sort separar con coma (id, desc)
    public Page<Proveedor> all(Pageable pageable) {
        proveedorService.all();
        log.info("Accediendo a todos 3 parametros: page size sort");
        return this.proveedorService.getAll(pageable);
    }

    // Ruta Para el uso de QueryAutoJPA
    @GetMapping(value = {"","/"}, params = {"campo", "valor", "page", "size", "sort"})
    public Page<Proveedor> all(Pageable pageable,
                               @RequestParam(value = "campo", required = false) String campo,
                               @RequestParam(value = "valor", required = false) String valor)
    {
        log.info("Accediendo a todos  4 atributos: campo page size sort");
        //devolucion al pedido
        return this.proveedorService.getAllBuscar(campo, valor , pageable);
    }


    // Metodo de ordenacion con array de parametros orden
    @GetMapping(value = {"","/"}, params = {"orden"})
    public ResponseEntity<Map<String,Object>> allOrden(
            @RequestParam(value = "orden", required = false, defaultValue = "id, asc" ) String[] orden
    ){
        for (String elemento : orden){
            log.info("Orden recibido en el controlador: " + elemento);
        }
        Map<String, Object> responseAll = null;
        String[] ordenSplited_0 = orden[0].split(",");
        String[] ordenSplited_1 = orden[1].split(",");
        // Calcular la longitud total del nuevo array
        int totalLength = ordenSplited_0.length + ordenSplited_1.length;
        // Crear un nuevo array con la longitud total
        String[] ordenSplitedRes = new String[totalLength];
        // Copiar los elementos del primer array
        System.arraycopy(ordenSplited_0, 0, ordenSplitedRes, 0, ordenSplited_0.length);
        // Copiar los elementos del segundo array
        System.arraycopy(ordenSplited_1, 0, ordenSplitedRes, ordenSplited_0.length, ordenSplited_1.length);
        // Verificar si se recibió una ordenación de dos niveles (campo, sentido, campo2, sentido2)
        if (ordenSplitedRes.length == 4) {
            log.info("Orden:" + orden);
            log.info("Orden recibido en el controlador dos orden:" + Arrays.toString(orden)+ ordenSplitedRes.length);
            String campo1 = ordenSplitedRes[0];
            String sentido1 = ordenSplitedRes[1];
            String campo2 = ordenSplitedRes[2];
            String sentido2 = ordenSplitedRes[3];
            responseAll = proveedorService.procesarOrden2(campo1, sentido1, campo2, sentido2);
            return ResponseEntity.ok(responseAll);

            // Verificar si se recibió una ordenación de un nivel (campo1, sentido1)
        }else if (ordenSplitedRes.length == 2){
            log.info("Orden:" + orden);
            log.info("Orden recibido en el controlador un orden:" + Arrays.toString(ordenSplitedRes) + ordenSplitedRes.length);
            String campo1 = ordenSplitedRes[0];
            String sentido1 = ordenSplitedRes[1];
            responseAll = proveedorService.procesarOrden(campo1, sentido1);
            return ResponseEntity.ok(responseAll);
        }
        // Manejar caso de error o formato incorrecto de la solicitud
        else {
            return ResponseEntity.badRequest().build();
        }
    }

}
