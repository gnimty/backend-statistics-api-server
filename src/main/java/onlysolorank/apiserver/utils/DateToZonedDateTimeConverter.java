package onlysolorank.apiserver.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DateToZonedDateTimeConverter implements Converter<Date, ZonedDateTime> {

    @Override
    public ZonedDateTime convert(Date source) {
        // MongoDB에 UTC 형태로 저장되어 있음
        ZonedDateTime utcDateTime = source.toInstant().atZone(ZoneId.of("UTC"));

        return utcDateTime;
    }
}
