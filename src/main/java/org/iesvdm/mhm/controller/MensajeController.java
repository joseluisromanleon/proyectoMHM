package org.iesvdm.mhm.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.mhm.domain.Mensaje;
import org.iesvdm.mhm.repository.MensajeRepository;
import org.iesvdm.mhm.service.MensajeService;
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
@RequestMapping("/v1/api/mensajes")

    public class MensajeController {
        private final MensajeRepository mensajeRepository;

        private final MensajeService mensajeService;

        @Autowired
        public MensajeController(MensajeService mensajeService,
                                 MensajeRepository mensajeRepository) {
            this.mensajeService = mensajeService;
            this.mensajeRepository = mensajeRepository;
        }


        @PostMapping({"","/"})
        public Mensaje newMensaje(@RequestBody @Valid Mensaje mensaje) {
            log.info("New mensaje para contacto" + mensaje.getNombreEmpresa());
            return this.mensajeService.save(mensaje);
        }

//        @PostMapping({"/"})
//        public ResponseEntity<Mensaje> crearMensaje(@RequestBody Mensaje mensaje) {
//            mensaje.setEstadoMsje(EstadoMensaje.PENDIENTE);
//            mensaje.setFecha(LocalDateTime.now());
//            Mensaje saved = mensajeRepository.save(mensaje);
//            return ResponseEntity.ok(saved);
//        }


        @GetMapping("/{id}")
        public Mensaje one(@PathVariable("id") Long id) {
            log.info("Buscando Contacto con id "+id);
            return this.mensajeService.one(id);

        }

        @PutMapping("/{id}")
        public Mensaje replaceMensaje(@PathVariable("id") Long id, @RequestBody Mensaje mensaje) {
            log.info("Actualizando Contacto con id "+ id);
            return this.mensajeService.replace(id, mensaje);
        }

        @DeleteMapping({"{id}","/{id}"})
        public void deleteMensaje(@PathVariable("id") Long id) {
            log.info("Eliminando Contacto con id "+id);
            this.mensajeService.delete(id);
        }

        @GetMapping(value = {"","/"}, params = {"!nombre", "!page", "!size", "!buscar", "!sort", "!column", "!orden"})
        //******  agregar paremetros que se quieran evitar  *******

        public List<Mensaje> all() {
            mensajeService.all();
            log.info("Accediendo a todas los Mensajes sin parametros");
            return this.mensajeService.all();
        }

        //CON PARAMETROS
        //Buscar un Contacto por nombre_empresa
        @GetMapping(value = {"", "/"}, params = {"nombre_empresa"})
        public List<Mensaje> all(@RequestParam("nombre_empresa")String nombre) {
            log.info("Accediendo a un mensaje por nombre");
            return this.mensajeService.all(nombre);
        }

        @GetMapping(value = {"", "/"}, params = {"page", "size", "sort"})
        // para los campos sort separar con coma (id, desc)
        public Page<Mensaje> all(Pageable pageable) {
            mensajeService.all();
            log.info("Accediendo a todos 3 parametros: page size sort");
            return this.mensajeService.getAll(pageable);
        }

        // Ruta Para el uso de QueryAutoJPA
        @GetMapping(value = {"","/"}, params = {"campo", "valor", "page", "size", "sort"})
        public Page<Mensaje> all(Pageable pageable,
                                 @RequestParam(value = "campo", required = false) String campo,
                                 @RequestParam(value = "valor", required = false) String valor)
        {
            log.info("Accediendo a todos  4 atributos: campo page size sort");
            //devolucion al pedido
            return this.mensajeService.getAllBuscar(campo, valor , pageable);
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
                responseAll = mensajeService.procesarOrden2(campo1, sentido1, campo2, sentido2);
                return ResponseEntity.ok(responseAll);

                // Verificar si se recibió una ordenación de un nivel (campo1, sentido1)
            }else if (ordenSplitedRes.length == 2){
                log.info("Orden:" + orden);
                log.info("Orden recibido en el controlador un orden:" + Arrays.toString(ordenSplitedRes) + ordenSplitedRes.length);
                String campo1 = ordenSplitedRes[0];
                String sentido1 = ordenSplitedRes[1];
                responseAll = mensajeService.procesarOrden(campo1, sentido1);
                return ResponseEntity.ok(responseAll);
            }
            // Manejar caso de error o formato incorrecto de la solicitud
            else {
                return ResponseEntity.badRequest().build();
            }
        }

    }

