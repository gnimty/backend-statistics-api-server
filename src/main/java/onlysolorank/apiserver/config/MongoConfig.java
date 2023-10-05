package onlysolorank.apiserver.config;

import onlysolorank.apiserver.config.converter.*;
import onlysolorank.apiserver.utils.DateToZonedDateTimeConverter;
import onlysolorank.apiserver.utils.ZonedDateTimeToDateConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : onlysolorank.apiserver.config
 * fileName       : MongoConfig.java
 * author         : solmin
 * date           : 2023/08/29
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/08/29        solmin       최초 생성
 * 2023/09/05        solmin       MongoTemplate Configuration 및 Converter 등록
 */

@Configuration
public class MongoConfig {
    @Bean
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new DateToZonedDateTimeConverter());
        converters.add(new ZonedDateTimeToDateConverter());
        converters.add(new StringToTierConverter());
        converters.add(new StringToTierFilterConverter());
        converters.add(new StringToPositionConverter());
        converters.add(new StringToTierStatConverter());
        converters.add(new TierFilterToStringConverter());
        return new MongoCustomConversions(converters);
    }

}
