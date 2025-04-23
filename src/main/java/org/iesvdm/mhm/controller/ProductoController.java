package org.iesvdm.mhm.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.mhm.domain.Producto;
import org.iesvdm.mhm.service.PedidoService;
import org.iesvdm.mhm.service.ProductoService;
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
    @RequestMapping("/v1/api/productos")
    public class ProductoController {

        private final ProductoService productoService;
        private final PedidoService pedidoService;

        @Autowired
        public ProductoController(ProductoService productoService, PedidoService pedidoService) {
            this.productoService = productoService;
            this.pedidoService = pedidoService;
        }


        @PostMapping({"","/"})
        public Producto newProducto(@RequestBody @Valid  Producto producto) {
            log.info("Creando un Categoria"+ producto.getNombre());
            return this.productoService.save(producto);
        }

        @GetMapping("/{id}")
        public Producto one(@PathVariable("id") Long id) {
            log.info("Buscando un Categoria con id "+id);
            return this.productoService.one(id);
        }

        @PutMapping("/{id}")
        public Producto replaceProducto(@PathVariable("id") Long id, @RequestBody Producto producto) {
            log.info("Actualizando un Categoria con id "+ id);
            return this.productoService.replace(id, producto);
        }

        @DeleteMapping({"{id}","/{id}"})
        public void deleteProducto(@PathVariable("id") Long id) {
            log.info("Eliminando un Categoria con id "+id);
            this.productoService.delete(id);
        }

        @GetMapping(value = {"","/"}, params = {"!nombre", "!page", "!size", "!buscar", "!sort", "!column", "!orden"})
        //******  agregar paremetros que se quieran evitar  *******
        public List<Producto> all() {
            productoService.all();
            log.info("Accediendo a todos sin parametros");
            return this.productoService.all();
        }

        //CON PARAMETROS
        //Buscar un producto por nombre
        @GetMapping(value = {"", "/"}, params = {"nombre"})
        public List<Producto> all(String nombre) {
            log.info("Accediendo a uno por nombre");
            return this.productoService.all(nombre);
        }

        @GetMapping(value = {"", "/"}, params = {"page", "size", "sort"})
        // para los campos sort separar con coma (id, desc)
        public Page<Producto> all(Pageable pageable) {
            productoService.all();
            log.info("Accediendo a todos 3 parametros: page size sort");
            return this.productoService.getAll(pageable);
        }

        // Ruta Para el uso de QueryAutoJPA
        @GetMapping(value = {"","/"}, params = {"campo", "valor", "page", "size", "sort"})
        public Page<Producto> all(Pageable pageable,
                                  @RequestParam(value = "campo", required = false) String campo,
                                    @RequestParam(value = "valor", required = false) String valor)
        {
            log.info("Accediendo a todos  4 atributos: campo page size sort");
            //devolucion al pedido
            return this.productoService.getAllBuscar(campo, valor , pageable);
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
                responseAll = productoService.procesarOrden2(campo1, sentido1, campo2, sentido2);
                return ResponseEntity.ok(responseAll);

                // Verificar si se recibió una ordenación de un nivel (campo1, sentido1)
            }else if (ordenSplitedRes.length == 2){
                log.info("Orden:" + orden);
                log.info("Orden recibido en el controlador un orden:" + Arrays.toString(ordenSplitedRes) + ordenSplitedRes.length);
                String campo1 = ordenSplitedRes[0];
                String sentido1 = ordenSplitedRes[1];
                responseAll = productoService.procesarOrden(campo1, sentido1);
                return ResponseEntity.ok(responseAll);
            }
            // Manejar caso de error o formato incorrecto de la solicitud
            else {
                return ResponseEntity.badRequest().build();
            }
        }

/*

        // ACCEDIENDO A PEDIDO DESDE PRODUCTO CASCADA
        @PostMapping(value ={"/{idPe}/addpel/{idProd}","/{idPe}/addpel/{idProd}/"})
        public Categoria addProductoAPedido(@PathVariable Long idPe, @PathVariable Long idProd) {
            Categoria prod = this.productoService.one(idProd);
            log.info("Añadiendo pedido con id "+ idPe +" al set de producto-pedidos  la producto con id "+idProd );
            return this.pedidoService.addProductoAPedido(idPe, idProd);
        }
*/

/*

        // Metodo de busqueda con array de parametros campos En Estudio
        @GetMapping(value = {"","/"}, params = {"campo", "valor"})
        public ResponseEntity<Map<String,Object>> buscarPorCampos(
                @RequestParam(value = "campo", required = true) String[] campos,
                @RequestParam(value = "valor", required = true) String[] valores
        ) {
            if (campos.length != valores.length) {
                // Manejar caso de error si los arreglos de campos y valores tienen longitudes diferentes
                return ResponseEntity.badRequest().build();
            }

            Map<String, Object> response = new HashMap<>();
            // Construir la consulta de búsqueda dinámicamente
            StringBuilder queryBuilder = new StringBuilder("SELECT p FROM Categoria p WHERE ");
            for (int i = 0; i < campos.length; i++) {
                if (i > 0) {
                    queryBuilder.append(" AND ");
                }
                queryBuilder.append("p.").append(campos[i]).append(" = :valor").append(i);
            }
            String queryString = queryBuilder.toString();

            // Ejecutar la consulta utilizando los parámetros proporcionados
            TypedQuery<Categoria> query = entityManager.createQuery(queryString, Categoria.class);
            for (int i = 0; i < valores.length; i++) {
                query.setParameter("valor" + i, valores[i]);
            }

            List<Categoria> resultados = query.getResultList();
            response.put("resultados", resultados);

            return ResponseEntity.ok(response);
        }

*/

}

