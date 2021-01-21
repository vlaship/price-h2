package vlaship.price.client;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrencyClient {

    public static final String EMPTY_BODY = "empty response body";
    private final RestTemplate restTemplate;

    @Value("${currency.base-url}")
    private String baseUrl;

    public BigDecimal getRate() {

        final var responseEntity = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                JsonNode.class
        );

        final var curOfficialRate = Optional.ofNullable(responseEntity.getBody())
                .orElseThrow(() -> new IllegalStateException(EMPTY_BODY))
                .get("Cur_OfficialRate").asText();
        final var bigDecimal = new BigDecimal(curOfficialRate);

        return bigDecimal;
    }
}
