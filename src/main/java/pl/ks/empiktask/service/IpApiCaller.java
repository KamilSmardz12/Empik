package pl.ks.empiktask.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.ks.empiktask.dto.IpApiResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class IpApiCaller {

    private final WebClient webClient;

    public IpApiResponse callFirstWorking(String ip) {
        Mono<IpApiResponse> ipwhois = callIpWhoIsAsync(ip);
        Mono<IpApiResponse> ipapico = callIpApiCoAsync(ip);

        return Mono.firstWithValue(ipwhois, ipapico)
                .timeout(Duration.ofSeconds(3))
                .onErrorReturn(failedResponse(ip))
                .block();
    }

    private Mono<IpApiResponse> callIpWhoIsAsync(String ip) {
        String url = "https://ipwho.is/" + ip;
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(IpApiResponse.class)
                .filter(IpApiResponse::isSuccess)
                .doOnNext(res -> log.debug("ipwho.is response for {}: {}", ip, res))
                .doOnError(ex -> log.warn("ipwho.is failed for '{}': {}", ip, ex.getMessage()))
                .onErrorResume(ex -> Mono.empty());
    }

    private Mono<IpApiResponse> callIpApiCoAsync(String ip) {
        String url = "https://ipapi.co/" + ip + "/json/";
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(IpApiResponse.class)
                .filter(IpApiResponse::isSuccess)
                .doOnNext(res -> log.debug("ipapi.co response for {}: {}", ip, res))
                .doOnError(ex -> log.warn("ipapi.co failed for '{}': {}", ip, ex.getMessage()))
                .onErrorResume(ex -> Mono.empty());
    }

    private IpApiResponse failedResponse(String ip) {
        IpApiResponse fail = new IpApiResponse();
        fail.setSuccess(false);
        fail.setStatus("fail");
        fail.setQuery(ip);
        fail.setCountry(null);
        return fail;
    }
}
