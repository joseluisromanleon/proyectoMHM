package org.iesvdm.mhm.controller;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.mhm.domain.Empleado;
import org.iesvdm.mhm.service.EmpleadoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/v1/api/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {

        this.empleadoService = empleadoService;
    }

    @GetMapping({"","/"})
    public List<Empleado> all() {
        log.info("Accediendo a todas las empleados");
        return this.empleadoService.all();
    }
    @Transactional
    @PostMapping({"","/"})
    public Empleado newEmpleado(@RequestBody Empleado empleado) {
        return this.empleadoService.save(empleado);
    }

    @GetMapping("/{id}")
    public Empleado one(@PathVariable("id") Long id) {
        return this.empleadoService.one(id);
    }

    @PutMapping("/{id}")
    public Empleado replaceEmpleado(@PathVariable("id") Long id, @RequestBody Empleado empleado) {
        return this.empleadoService.replace(id, empleado);
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteEmpleado(@PathVariable("id") Long id) {
        this.empleadoService.delete(id);
    }
}
