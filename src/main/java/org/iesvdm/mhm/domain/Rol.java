package org.iesvdm.mhm.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)  //solo los que tienen include
public class Rol {

    @Id
    private Long id_rol;
    private String nombre;

    @OneToMany(mappedBy = "rol")
    private List<Empleado> empleados;



}
