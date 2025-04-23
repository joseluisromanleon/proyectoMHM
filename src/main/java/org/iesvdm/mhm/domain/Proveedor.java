package org.iesvdm.mhm.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "proveedores")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Proveedor {


    @Id
    @Column(name = "id_proveedor")
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "empresa", nullable = false)
    @EqualsAndHashCode.Include
    private String nombreEmpresa;
    @Column(name = "tel_empresa", nullable = false)
    @EqualsAndHashCode.Include
    private String telefonoEmpresa;
    @Column(name = "mail_empresa", nullable = false)
    @EqualsAndHashCode.Include
    private String emailEmpresa;
    @Column(name = "contacto", nullable = false)
    @EqualsAndHashCode.Include
    private String nombreContacto;
    @Column(name = "tel_contacto", nullable = false)
    @EqualsAndHashCode.Include
    private String telefonoContacto;
    @Column(name = "mail_contacto", nullable = false)
    @EqualsAndHashCode.Include
    private String emailContacto;
    @Column(name = "cif", nullable = false)
    @EqualsAndHashCode.Include
    private String CIF;
    @Column(name = "direccion", nullable = false)
    @EqualsAndHashCode.Include
    private String Direccion;


    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonBackReference
    @ToStringExclude
    private Set<Producto> detalles = new HashSet<>();



}
