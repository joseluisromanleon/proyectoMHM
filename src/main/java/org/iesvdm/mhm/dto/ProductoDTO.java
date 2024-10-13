package org.iesvdm.mhm.dto;

import lombok.*;
import org.iesvdm.mhm.domain.Producto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoDTO {


      private Producto producto;
      private int conteoPedido;


}
