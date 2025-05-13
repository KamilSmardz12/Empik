package pl.ks.empiktask.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ip.api")
public class IpApiProperties {
    private String url;
    private Duration timeout;
    private int maxSize;
    private String fallbackCountry;
}
