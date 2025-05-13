package pl.ks.empiktask.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class HttpRequestUtil {
    private static final String LOCALHOST = "127.0.0.1";

    private HttpRequestUtil() {
    }

    public static Optional<String> getClientIp(HttpServletRequest request) {
        return Optional.ofNullable(request.getRemoteAddr())
                .filter(ip -> !ip.isBlank() && !LOCALHOST.equals(ip))
                .map(ip -> {
                    log.debug("Using remoteAddr as client IP: {}", ip);
                    return ip;
                })
                .or(() -> getFromHeaders(request).map(ip -> {
                    log.debug("Using X-Forwarded-For as client IP: {}", ip);
                    return ip;
                }));
    }

    private static Optional<String> getFromHeaders(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("X-Forwarded-For"))
                .map(header -> header.split(",")[0].trim())
                .filter(ip -> !ip.isBlank())
                .map(ip -> {
                    log.debug("X-Forwarded-For header: {}", ip);
                    return ip;
                });
    }
}
