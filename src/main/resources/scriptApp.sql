create database mhm2;
create table cliente
(
    id_cliente      bigint auto_increment
        primary key,
    ccc_empresa     varchar(255) null,
    direccion       varchar(255) null,
    email_contacto  varchar(255) null,
    email_empresa   varchar(255) null,
    fecha_alta      datetime(6)  null,
    nombre          varchar(255) null,
    nombre_contacto varchar(255) null,
    observaciones   varchar(255) null,
    tel_contacto    varchar(255) null,
    tel_empresa     varchar(255) null
);

create table empleado
(
    id_empleado  bigint auto_increment
        primary key,
    apellidos    varchar(255) null,
    ccc_empleado varchar(255) null,
    direccion    varchar(255) null,
    fecha_alta   datetime(6)  null,
    nombre       varchar(255) null,
    telefono     varchar(255) null
);

create table empl_clientes
(
    id_empleado bigint not null,
    id_cliente  bigint not null,
    primary key (id_empleado, id_cliente),
    constraint FK4ru7lxp007nvua1iv9qdbauqt
        foreign key (id_cliente) references cliente (id_cliente),
    constraint FKiec25773a75qpx4xq97ebr1iv
        foreign key (id_empleado) references empleado (id_empleado)
);

create table pedido
(
    id_pedido         bigint auto_increment
        primary key,
    enviado           bit          null,
    factura_          varchar(255) null,
    facturado         bit          null,
    fecha_factura     datetime(6)  null,
    fecha_pedido      datetime(6)  null,
    importe_total     double       not null,
    observaciones     varchar(255) null,
    unidades_vendidas int          not null,
    id_cliente        bigint       null,
    id_empleado       bigint       null,
    constraint FK9y4jnyp1hxqa386cnly0ay9uw
        foreign key (id_cliente) references cliente (id_cliente),
    constraint FKqbl4adl78gxdv0wbx3nq03awv
        foreign key (id_empleado) references empleado (id_empleado)
);

create table producto
(
    id_producto       bigint auto_increment
        primary key,
    categoria         varchar(255) null,
    descatalogado     bit          not null,
    nombre            varchar(255) null,
    precio_compra     double       not null,
    precio_venta      double       not null,
    proveedor         varchar(255) null,
    stock             bigint       not null,
    stock_minimo      bigint       not null,
    ultima_compra     datetime(6)  null,
    unidades_servidas bigint       not null
);

create table detalle_pedido_producto
(
    id_detalle       bigint auto_increment
        primary key,
    base             double not null,
    dtos             double not null,
    facturado        bit    not null,
    iva              double not null,
    numero_productos int    not null,
    preciou          double not null,
    rec_equi         double not null,
    subtotal         double not null,
    total            double not null,
    id_pedido        bigint null,
    id_producto      bigint null,
    constraint FKc63ip4mgbkguuwpc7ohw0depf
        foreign key (id_pedido) references pedido (id_pedido),
    constraint FKcnj9f9jll1bfojwkv6qc9mciq
        foreign key (id_producto) references producto (id_producto)
);

