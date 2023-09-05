package onlysolorank.apiserver.config;

import onlysolorank.apiserver.utils.DateToZonedDateTimeConverter;
import onlysolorank.apiserver.utils.ZonedDateTimeToDateConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
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

    @Value("${spring.data.mongodb.uri}") // 이렇게 property 값을 사용하거나 직접 값 지정 가능
    private String mongoUri;
    @Bean
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new DateToZonedDateTimeConverter());
        converters.add(new ZonedDateTimeToDateConverter());
        return new MongoCustomConversions(converters);
    }

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory() {
        return new SimpleMongoClientDatabaseFactory(mongoUri);
    }
    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory, MongoConverter mongoConverter) {
        return new MongoTemplate(mongoDbFactory, mongoConverter);
    }

}
