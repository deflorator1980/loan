package my.model;

import lombok.Data;

/**
 * Created by a on 03.08.17.
 */
@Data
public class IpApi {
    private String as;
    private String city;
    private String country;
    private String countryCode;
    private String isp;
    private Double lat;
    private Double lon;
    private String org;
    private String query;
    private String region;
    private String regionName;
    private String status;
    private String timezone;
    private String zip;
}