package my.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by a on 03.08.17.
 */
@Data
@AllArgsConstructor
public class CountryBan {
    private String country;
    private String message;
}
