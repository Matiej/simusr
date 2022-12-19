package com.ematiej.simusr.rentalproduct.application;

import com.ematiej.simusr.rentalproduct.application.port.RentalProductService;
import com.ematiej.simusr.rentalproduct.database.MotorcycleRepository;
import com.ematiej.simusr.rentalproduct.domain.Motorcycle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalProductServiceImpl implements RentalProductService {
    private final MotorcycleRepository repository;

    @Override
    public List<Motorcycle> getAllMotorcycles() {
        return repository.findAll();
    }

    @Override
    public void init() {
        Motorcycle m1 = new Motorcycle("SUZUKI", "DL650", new BigDecimal("100.00"), true);
        Motorcycle m2 = new Motorcycle("HONDA", "CBR600", new BigDecimal("110.00"), true);
        Motorcycle m3 = new Motorcycle("BMW", "GS1250", new BigDecimal("400.00"), false);
        Motorcycle m4 = new Motorcycle("YAMAHA", "FJR1300", new BigDecimal("410.00"), true);
        repository.saveAll(List.of(m1,m2,m3,m4));
    }
}
