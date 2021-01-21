package vlaship.price.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vlaship.price.client.CurrencyClient;
import vlaship.price.service.CurrencyService;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

private final CurrencyClient currencyClient;

    @Override
    public BigDecimal getRate() {
        return currencyClient.getRate();
    }
}
