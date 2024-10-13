package org.iesvdm.mhm.repository;

import org.iesvdm.mhm.domain.DetallePedProd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DetallePedProdRepository extends JpaRepository<DetallePedProd, Long> {
}
