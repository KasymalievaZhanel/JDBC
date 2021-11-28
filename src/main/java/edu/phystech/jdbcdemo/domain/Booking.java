package edu.phystech.jdbcdemo.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class Booking {
    private final String bookRef;
    private final Timestamp bookDate;
    private final BigDecimal totalAmount;
}
