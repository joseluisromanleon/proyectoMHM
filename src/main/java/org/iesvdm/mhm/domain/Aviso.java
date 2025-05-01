package org.iesvdm.mhm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;


    @Data
    @Entity
    @Table(name="Aviso")
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)

    public class Aviso {

        @Id
        @Column(name="id_aviso")
        @EqualsAndHashCode.Include
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @Size(min = 1, max = 1)   // ojo no sabemos que guarda si el, id  o podriamos guardar el objeto y como
        @Column(name = "cliente_id", length = 1)
        private Long cliente_id;

        @Size(min = 1, max = 1)
        @Column(name = "empleado_id", length = 1)
        private Long empleado_id;

        @Column(name = "fecha_aviso")
        @JsonFormat(pattern = "yyyy-MM-dd-HH:mm:ss",  shape = JsonFormat.Shape.STRING)
        private Date fechaAviso;

        @Size(min = 1, max = 256)
        @Column(name = "observaciones", length = 256)
        private String observaciones;

        @Column(name = "ultima_actualizacion")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  shape = JsonFormat.Shape.STRING)
        private Date ultimaActualizacion;

        //Constructores `para tests
        public Aviso(int id, Cliente cliente, Empleado empleado) {
            this.id = id;
            this.cliente_id = getCliente_id();
            this.empleado_id = getEmpleado_id();
            this.observaciones = getObservaciones();
        }


}
