package org.iesvdm.mhm.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.mhm.domain.Categoria;
import org.iesvdm.mhm.service.CategoriaService;
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
@RequestMapping("/v1/api/categorias")
public class CategoriaController {


        private final CategoriaService categoriaService;

        @Autowired
        public CategoriaController(CategoriaService categoriaService) {
            this.categoriaService = categoriaService;
        }


        @PostMapping({"","/"})
        public Categoria newCategoria(@RequestBody @Valid Categoria categoria) {
            log.info("Creando una Categoría"+ categoria.getNombre());
            return this.categoriaService.save(categoria);
        }

        @GetMapping("/{id}")
        public Categoria one(@PathVariable("id") Long id) {
            log.info("Buscando Categoría con id "+id);
            return this.categoriaService.one(id);
        }

        @PutMapping("/{id}")
        public Categoria replaceCategoria(@PathVariable("id") Long id, @RequestBody Categoria categoria) {
            log.info("Actualizando Categoría con id "+ id);
            return this.categoriaService.replace(id, categoria);
        }

        @DeleteMapping({"{id}","/{id}"})
        public void deleteCategoria(@PathVariable("id") Long id) {
            log.info("Eliminando Categoría con id "+id);
            this.categoriaService.delete(id);
        }

        //@GetMapping(value = {"","/"}, params = {"!nombre", "!page", "!size", "!buscar", "!sort", "!column", "!orden"})

        @GetMapping(value = {"","/"})
        public List<Categoria> all() {
            categoriaService.all();
            log.info("Accediendo a todas las categorías sin parametros");
            return this.categoriaService.all();
        }

        //CON PARAMETROS
        //Buscar una Categoría por nombre
        @GetMapping(value = {"", "/"}, params = {"nombre"})
        public List<Categoria> all(String nombre) {
            log.info("Accediendo a una Categoría por nombre");
            return this.categoriaService.all(nombre);
        }

        @GetMapping(value = {"", "/"}, params = {"page", "size", "sort"})
        // para los campos sort separar con coma (id, desc)
        public Page<Categoria> all(Pageable pageable) {
            categoriaService.all();
            log.info("Accediendo a todos 3 parametros: page size sort");
            return this.categoriaService.getAll(pageable);
        }

        // Ruta Para el uso de QueryAutoJPA
        @GetMapping(value = {"","/"}, params = {"campo", "valor", "page", "size", "sort"})
        public Page<Categoria> all(Pageable pageable,
                                  @RequestParam(value = "campo", required = false) String campo,
                                  @RequestParam(value = "valor", required = false) String valor)
        {
            log.info("Accediendo a todos  4 atributos: campo page size sort");
            //devolucion al pedido
            return this.categoriaService.getAllBuscar(campo, valor , pageable);
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
                responseAll = categoriaService.procesarOrden2(campo1, sentido1, campo2, sentido2);
                return ResponseEntity.ok(responseAll);

                // Verificar si se recibió una ordenación de un nivel (campo1, sentido1)
            }else if (ordenSplitedRes.length == 2){
                log.info("Orden:" + orden);
                log.info("Orden recibido en el controlador un orden:" + Arrays.toString(ordenSplitedRes) + ordenSplitedRes.length);
                String campo1 = ordenSplitedRes[0];
                String sentido1 = ordenSplitedRes[1];
                responseAll = categoriaService.procesarOrden(campo1, sentido1);
                return ResponseEntity.ok(responseAll);
            }
            // Manejar caso de error o formato incorrecto de la solicitud
            else {
                return ResponseEntity.badRequest().build();
            }
        }
}
