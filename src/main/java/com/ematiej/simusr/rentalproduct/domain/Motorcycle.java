package com.ematiej.simusr.rentalproduct.domain;

import com.ematiej.simusr.jpa.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Motorcycle extends BaseEntity {
    private String brand;
    private String model;
    private BigDecimal price;
    private Boolean available;
}
