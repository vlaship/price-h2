package vlaship.price.h2.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import vlaship.price.h2.exception.DownloadException;
import vlaship.price.h2.service.DownloadCsvService;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.List;

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
    @Value("${download.query.param.others}")
    private String queryParamOthers;
    @Value("${download.url.auth}")
    private String urlAuth;
    @Value("${download.url.file}")
    private String urlFile;

    @Override
    public List<String> download() {
        try {
            final var httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .cookieHandler(buildCookieManager())
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .sslContext(buildSslContext())
                    .sslParameters(buildSslParameters())
                    .build();

            log.info("logging in...");

            httpClient.send(
                    HttpRequest.newBuilder()
                            .uri(URI.create(urlAuth))
                            .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                            .POST(HttpRequest.BodyPublishers.ofString(buildBody()))
                            .build(),
                    HttpResponse.BodyHandlers.discarding()
            );

            log.info("Starting download...");

            final var send = httpClient.send(
                    HttpRequest.newBuilder()
                            .uri(URI.create(urlFile))
                            .header("Referer", urlFile)
                            .build(),
                    HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)
            );

            log.info("Done with download...");
            return List.of(send.body().split("\n"));

        } catch (Exception ex) {
            log.error("{}", ExceptionUtils.getRootCause(ex).getClass().getSimpleName(), ex);
            throw new DownloadException("Error while downloading");
        }
    }

    private SSLContext buildSslContext() {
        final var trustAllCerts = new TrustManager[]{new X509TrustManager() {

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
        };

        try {
            final var sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            return sc;
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }

    private SSLParameters buildSslParameters() {
        final var sslParams = new SSLParameters();
        sslParams.setEndpointIdentificationAlgorithm("");
        return sslParams;
    }

    private String buildBody() {
        return queryParamLogin + "=" + queryValueLogin
                + "&" + queryParamPassword + "=" + queryValuePassword
                + queryParamOthers;
    }

    private CookieManager buildCookieManager() {
        final var cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
        return cookieManager;
    }

}
