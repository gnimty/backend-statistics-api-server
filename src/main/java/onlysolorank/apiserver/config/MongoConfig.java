package onlysolorank.apiserver.config;

import java.util.ArrayList;
import java.util.List;
import onlysolorank.apiserver.config.converter.StringToPositionConverter;
import onlysolorank.apiserver.config.converter.StringToTierConverter;
import onlysolorank.apiserver.config.converter.StringToTierStatConverter;
import onlysolorank.apiserver.utils.DateToZonedDateTimeConverter;
import onlysolorank.apiserver.utils.ZonedDateTimeToDateConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

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
        converters.add(new StringToPositionConverter());
        converters.add(new StringToTierStatConverter());
        return new MongoCustomConversions(converters);
    }

}
