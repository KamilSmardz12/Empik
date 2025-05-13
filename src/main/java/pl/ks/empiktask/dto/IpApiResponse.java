package pl.ks.empiktask.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IpApiResponse {

    private String country;
    private String status;
    private String query;
    private String regionName;
    private String city;

    // ipwho.is specific
    private Boolean success;

    // ipapi.co specific mapping to fit ipwho.is style
    @JsonProperty("ip")
    private void unpackIpAsQuery(String ip) {
        this.query = ip;
    }

    @JsonProperty("error")
    private void handleErrorFromIpWhoIs(Object error) {
        this.success = false;
    }

    public boolean isSuccess() {
        if (success != null) return success;
        return "success".equalsIgnoreCase(status);
    }
}
