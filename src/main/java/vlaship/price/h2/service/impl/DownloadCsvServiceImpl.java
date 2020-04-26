package vlaship.price.h2.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vlaship.price.h2.exception.DownloadException;
import vlaship.price.h2.service.DownloadCsvService;

import java.net.CookieManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DownloadCsvServiceImpl implements DownloadCsvService {

    @Value("${download.query.value.login}")
    private String queryValueLogin;
    @Value("${download.query.value.password}")
    private String queryValuePassword;
    @Value("${download.query.param.login}")
    private String queryParamLogin;
    @Value("${download.query.param.password}")
    private String queryParamPassword;
    @Value("${download.url.auth}")
    private String urlAuth;
    @Value("${download.url.file}")
    private String urlFile;

    @Override
    public List<String> download() {
        try {
            final var httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .cookieHandler(new CookieManager())
                    .build();

            log.info("logging ...");

            httpClient.send(
                    HttpRequest.newBuilder()
                            .uri(URI.create(urlAuth + "?" + queryParamLogin + "=" + queryValueLogin + "&" + queryParamPassword + "=" + queryValuePassword))
                            .build(),
                    HttpResponse.BodyHandlers.discarding()
            );

            log.info("Starting download...");

            final var send = httpClient.send(
                    HttpRequest.newBuilder().uri(URI.create(urlFile)).build(),
                    HttpResponse.BodyHandlers.ofLines()
            );

            log.info("Done with download...");

            return send.body().collect(Collectors.toList());

        } catch (Exception ex) {
            log.error("{}", ExceptionUtils.getRootCause(ex).getClass().getSimpleName(), ex);
            throw new DownloadException("Error while downloading");
        }
    }
}
