package com.ematiej.simusr.rentalproduct.database;

import com.ematiej.simusr.rentalproduct.domain.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long> {
}
