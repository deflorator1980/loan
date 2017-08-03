package my.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by a on 03.08.17.
 */
@Data
public class Apply {
    private String id;
    private BigDecimal amount;
    private Long term;
    private String name;
    private String surname;
}