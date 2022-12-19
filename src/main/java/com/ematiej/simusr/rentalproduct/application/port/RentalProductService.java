package com.ematiej.simusr.rentalproduct.application.port;

import com.ematiej.simusr.rentalproduct.domain.Motorcycle;

import java.util.List;

public interface RentalProductService {

    List<Motorcycle> getAllMotorcycles();

    void init();
}
