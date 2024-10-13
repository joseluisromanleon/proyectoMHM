package org.iesvdm.mhm.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "cat_prod")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Categoria {

    @Id
    @Column(name = "id_categoria")
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCategoria;

    @Column(name = "nombre", nullable = false)
    @EqualsAndHashCode.Include
    private String nombre;

    @Column(name = "voltaje_v")
    @EqualsAndHashCode.Include
    private int voltaje;

    @Column(name = "potencia_w")
    @EqualsAndHashCode.Include
    private int potencia;

    @Column(name = "capacidad_l")
    @EqualsAndHashCode.Include
    private String capacidad;

    @Column(name = "alto")
    @EqualsAndHashCode.Include
    private Float alto;

    @Column(name = "ancho")
    @EqualsAndHashCode.Include
    private Float ancho;

    @Column(name = "fondo")
    @EqualsAndHashCode.Include
    private Float fondo;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Producto> productos = new HashSet<>();

}
