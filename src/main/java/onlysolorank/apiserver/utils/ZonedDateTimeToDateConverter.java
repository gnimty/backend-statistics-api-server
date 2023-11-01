package onlysolorank.apiserver.utils;

import java.time.ZonedDateTime;
import java.util.Date;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ZonedDateTimeToDateConverter implements Converter<ZonedDateTime, Date> {

    @Override
    public Date convert(ZonedDateTime source) {
        return Date.from(source.toInstant());
    }
}
