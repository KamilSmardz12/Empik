package pl.ks.empiktask.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.ks.empiktask.config.IpApiProperties;
import pl.ks.empiktask.dto.IpApiResponse;

@Slf4j
@Service
public class IpService {
    private final IpApiCaller ipApiCaller;
    private final IpApiProperties props;
    private final Cache<String, IpApiResponse> ipCache;

    public IpService(IpApiCaller ipApiCaller, IpApiProperties props) {
        this.ipApiCaller = ipApiCaller;
        this.props = props;
        this.ipCache = Caffeine.newBuilder()
                .expireAfterWrite(props.getTimeout())
                .maximumSize(props.getMaxSize())
                .build();
    }

    public IpApiResponse resolveCountryFromIp(String ip) {
        return ipCache.get(ip, this::resolveWithFallback);
    }

    private IpApiResponse resolveWithFallback(String ip) {
        IpApiResponse response = ipApiCaller.callFirstWorking(ip);

        if (!response.isSuccess()) {
            log.warn("Failed to resolve IP '{}' from any provider. Using fallback.", ip);
            IpApiResponse fallback = new IpApiResponse();
            fallback.setCountry(props.getFallbackCountry());
            fallback.setStatus("fail");
            fallback.setQuery(ip);
            return fallback;
        }

        return response;
    }
}
