package my.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@NoArgsConstructor
public class Apply {
    @Id
    @GeneratedValue
    private long id;

    @Getter @Setter
    private BigDecimal amount;

    @Getter @Setter
    private Date term;

    @Getter @Setter
    private String personId;

    @Getter @Setter
    private boolean isApproved;

    public Apply(BigDecimal amount, Date term, String personId, boolean isApproved) {
        this.amount = amount;
        this.term = term;
        this.personId = personId;
        this.isApproved = isApproved;
    }
}
