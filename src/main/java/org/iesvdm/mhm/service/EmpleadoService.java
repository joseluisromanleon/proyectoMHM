package org.iesvdm.mhm.service;

import org.iesvdm.mhm.domain.Empleado;
import org.iesvdm.mhm.exception.EmpleadoNotFoundException;
import org.iesvdm.mhm.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Autowired
    public List<Empleado> all() {
        return this.empleadoRepository.findAll();
    }

    public Empleado save(Empleado empleado) {

        return this.empleadoRepository.save(empleado);
    }

    public Empleado one(Long id) {
        return this.empleadoRepository.findById(id)
                .orElseThrow(() -> new EmpleadoNotFoundException(id));
    }

    public Empleado replace(Long id, Empleado empleado) {

        return this.empleadoRepository.findById(id)
                .map( a -> (id.equals(empleado.getId()) ?
                        this.empleadoRepository.save(empleado) : null))
                .orElseThrow(() -> new EmpleadoNotFoundException(id));

    }

    public void delete(Long id) {
        this.empleadoRepository.findById(id).map(a -> {
                    this.empleadoRepository.delete(a);
                    return a;})
                .orElseThrow(() -> new EmpleadoNotFoundException(id));
    }
}
