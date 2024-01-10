package onlysolorank.apiserver.config;

import static java.util.Collections.singletonList;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import onlysolorank.apiserver.repository.analysis.ChampionAnalysisRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * packageName    : onlysolorank.apiserver.config
 * fileName       : SecondaryConfig
 * author         : solmin
 * date           : 2023/09/27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/27        solmin       최초 생성
 */

@Configuration
@EnableMongoRepositories(basePackageClasses = {
    ChampionAnalysisRepository.class}, mongoTemplateRef = "secondaryMongoTemplate")
@EnableConfigurationProperties
public class SecondaryConfig {

    @Value("${spring.config.activate.on-profile}")
    private String profile;
    @Bean(name = "secondaryProperties")
    @ConfigurationProperties(prefix = "mongodb.secondary")
    @Qualifier("secondaryProperties")
    public MongoProperties secondaryMongoProperties() {
        return new MongoProperties();
    }

    @Bean(name = "secondaryMongoClient")
    @Qualifier("secondaryMongoClient")
    public MongoClient mongoClient(
        @Qualifier("secondaryProperties") MongoProperties mongoProperties) {
        if (profile.equals("local")){
            return MongoClients.create(mongoProperties.getUri());
        }
        MongoCredential credential = MongoCredential
            .createCredential(mongoProperties.getUsername(),
                mongoProperties.getAuthenticationDatabase(), mongoProperties.getPassword());

        return MongoClients.create(MongoClientSettings.builder()
            .applyToClusterSettings(builder -> builder
                .hosts(singletonList(
                    new ServerAddress(mongoProperties.getHost(), mongoProperties.getPort()))))
            .credential(credential)
            .build());
    }

    @Bean(name = "secondaryMongoDBFactory")
    @Qualifier("secondaryMongoDBFactory")
    public MongoDatabaseFactory mongoDatabaseFactory(
        @Qualifier("secondaryMongoClient") MongoClient mongoClient,
        @Qualifier("secondaryProperties") MongoProperties mongoProperties) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, mongoProperties.getDatabase());
    }

    @Bean(name = "secondaryMongoTemplate")
    @Qualifier("secondaryMongoTemplate")
    public MongoTemplate mongoTemplate(
        @Qualifier("secondaryMongoDBFactory") MongoDatabaseFactory mongoDatabaseFactory,
        MongoConverter mongoConverter) {
        return new MongoTemplate(mongoDatabaseFactory, mongoConverter);
    }

}
