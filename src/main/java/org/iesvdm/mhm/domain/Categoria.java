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
@Table(name = "categorias")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Categoria {

    @Id
    @Column(name = "id_categoria")
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nombre", nullable = false)
    @EqualsAndHashCode.Include
    private String nombre;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "categoria-productos",
            joinColumns = @JoinColumn(name = "categoria"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private Set<Producto> productos = new HashSet<>();


}
