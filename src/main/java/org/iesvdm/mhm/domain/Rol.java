package org.iesvdm.mhm.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.Set;

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

    @ManyToMany(mappedBy = "roles", cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
    },fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"roles", "empleados", "pedidos"})         //Rompe el lazo de Serializacion
    @ToStringExclude    //Rompe el lazo de Serializacion
    private Set<Empleado> empleados;


}
