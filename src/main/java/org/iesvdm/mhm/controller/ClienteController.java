package org.iesvdm.mhm.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.mhm.domain.Cliente;
import org.iesvdm.mhm.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

    @Slf4j
    @RestController
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping("/v1/api/clientes")
    public class ClienteController {

        /**
         * Rutas Autogeneradas òr Springboot-data
         */
        private final ClienteService clienteService;

        @Autowired
        public ClienteController(ClienteService clienteService) {
            this.clienteService = clienteService;
        }

        @GetMapping(value = {"","/"}, params = {"!nombreCli", "!nombreEmpl", "!page", "!size", "!buscar", "!sort", "!column", "!orden", "!campo", "!valor"})
        public List<Cliente> all() {
            log.info("Accediendo a todas los clientes sin parametros");
            return this.clienteService.all();
        }
        @Transactional
        @PostMapping({"","/"})
        public Cliente newCliente(@RequestBody @Valid Cliente cliente) {
            return this.clienteService.save(cliente);
        }

        @GetMapping("/{id}")
        public Cliente one(@PathVariable("id") Long id) {
            return this.clienteService.one(id);
        }

        @PutMapping("/{id}")
        public Cliente replaceCliente(@PathVariable("id") Long id, @RequestBody @Valid  Cliente cliente) {
            return this.clienteService.replace(id, cliente);
        }

        /**
         * rutas particularizadas
         */


        @GetMapping(value = {"", "/"}, params = {"page", "size", "sort"})
        // para los campos sort separar con coma (id, desc)
        public Page<Cliente> all(Pageable pageable) {
            log.info("Accediendo a clientes paginadas 3 parametros: page size sort");
            return this.clienteService.getAll(pageable);    }

        // Rutas  Para el uso de QueryAutoJPA
        @GetMapping(value = {"","/"}, params = {"nombreCli"})
        public List<Cliente> allClienteNombre(@RequestParam(value = "nombreCli", required = false) String nombreCli) {
            Optional<String> clienteNombreEmpresaOptional = Optional.ofNullable(nombreCli);

            log.info("Accediendo a clientes.nombre contengan nombre: " + nombreCli);
            if (clienteNombreEmpresaOptional.isPresent()) {
                return this.clienteService.clientesNombre(nombreCli);

            }else {
                return this.clienteService.all();
            }
        }

        //Contectando Tablas   (JOIN)
        @GetMapping(value = {"","/"}, params = {"nombreEmpl"})
        public List<Cliente> allEmpleado(@RequestParam(value = "nombreEmpl", required = false) String nombreEmpl) {
            Optional<String> empleadoNombreOptional = Optional.ofNullable(nombreEmpl);
            log.info("Accediendo a clientes con nombre empleado");

            if (empleadoNombreOptional.isPresent()) {
                String nombre = empleadoNombreOptional.get();
                log.info("El empleado con nombre: " + nombre + " existe");

                return this.clienteService.clientesEmpleado(nombreEmpl);
            }else {
                return this.clienteService.all();
            }
        }

        // Añadimos un Empleado al conjunto de Empleados del Cliente
        @PutMapping("/{clienteId}/empleado/{empleadoId}") // Ruta para el endpoint PUT
        public Cliente agregarEmpleadoACliente( @PathVariable Long clienteId, @PathVariable Long empleadoId) {
            return clienteService.addAclienteEmpleado(clienteId, empleadoId);
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
            responseAll = clienteService.procesarOrden2(campo1, sentido1, campo2, sentido2);
            return ResponseEntity.ok(responseAll);

            // Verificar si se recibió una ordenación de un nivel (campo1, sentido1)
        }else if (ordenSplitedRes.length == 2){
            log.info("Orden:" + orden);
            log.info("Orden recibido en el controlador un orden:" + Arrays.toString(ordenSplitedRes) + ordenSplitedRes.length);
            String campo1 = ordenSplitedRes[0];
            String sentido1 = ordenSplitedRes[1];
            responseAll = clienteService.procesarOrden(campo1, sentido1);
            return ResponseEntity.ok(responseAll);
        }
        // Manejar caso de error o formato incorrecto de la solicitud
        else {
            return ResponseEntity.badRequest().build();
        }
    }


    // Ruta Para el uso de QueryAutoJPA
    @GetMapping(value = {"","/"}, params = {"campo", "valor", "page", "size", "sort"})
    public Page<Cliente> all(Pageable pageable,
                              @RequestParam(value = "campo", required = false) String campo,
                              @RequestParam(value = "valor", required = false) String valor)
    {
        log.info("Accediendo a todos  4 atributos: campo page size sort");
        //devolucion al pedido
        return this.clienteService.getAllBuscar(campo, valor , pageable);
    }

/*
    // Ruta Para el Uso de JPQL  // *** no funcionó en insomnia no ordena  REVISAR ***
    @GetMapping(value = {"","/"}, params = {"campo", "subcampo", "valor", "ordenar", "dir"})
    public List<Cliente> all(
            @RequestParam(value = "campo", required = false) String campo,
            @RequestParam(value = "subcampo", required = false) String subcampo,
            @RequestParam(value = "valor", required = false) String valor,
            @RequestParam(value = "ordenar", required = false) String ordenar,
            @RequestParam(value = "dir", required = false) String dir)

    {
        Optional<String> campoOptional = Optional.ofNullable(campo);
        Optional<String> subcampoOptional = Optional.ofNullable(subcampo);
        Optional<String> valorOptional = Optional.ofNullable(valor);
        Optional<String> ordenarOptional = Optional.ofNullable(ordenar);
        Optional<String> dirOptional = Optional.ofNullable(dir);

        log.info("Accediendo a clientes filtrados con  campo {}"
                        + "subcampo {} "
                        + "valor {}"
                        + "ordenar {}",
                campoOptional.orElse("VOID"),
                subcampoOptional.orElse("VOID"),
                valorOptional.orElse("VOID"),
                ordenarOptional.orElse("VOID"));
        return this.clienteService.queryClienteCustomJPQL(campoOptional, subcampoOptional,
                valorOptional, ordenarOptional, dirOptional);

    }*/

    }
