package onlysolorank.apiserver.config;

import static java.util.Collections.singletonList;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import onlysolorank.apiserver.repository.champion.ChampionRepository;
import onlysolorank.apiserver.repository.crawl.ChampionSaleRepository;
import onlysolorank.apiserver.repository.crawl.SkinSaleRepository;
import onlysolorank.apiserver.repository.crawl.VersionRepository;
import onlysolorank.apiserver.repository.history.SummonerHistoryRepository;
import onlysolorank.apiserver.repository.match.MatchRepository;
import onlysolorank.apiserver.repository.participant.ParticipantRepository;
import onlysolorank.apiserver.repository.patch.ChampionPatchRepository;
import onlysolorank.apiserver.repository.season.SeasonRepository;
import onlysolorank.apiserver.repository.summoner.SummonerRepository;
import onlysolorank.apiserver.repository.summoner_match.SummonerMatchRepository;
import onlysolorank.apiserver.repository.summoner_play.SummonerPlayRepository;
import onlysolorank.apiserver.repository.team.TeamRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * packageName    : onlysolorank.apiserver.config
 * fileName       : PrimaryConfig
 * author         : solmin
 * date           : 2023/09/27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/27        solmin       최초 생성
 */

@Configuration
@EnableMongoRepositories(
    basePackageClasses = {
        SummonerRepository.class,
//        ChampionStatisticsRepository.class,
        ChampionSaleRepository.class,
        SkinSaleRepository.class,
        VersionRepository.class,
        ChampionRepository.class,
        SummonerMatchRepository.class,
        MatchRepository.class,
        TeamRepository.class,
        ParticipantRepository.class,
        SummonerPlayRepository.class,
        SummonerHistoryRepository.class,
        SeasonRepository.class,
        ChampionPatchRepository.class
    },
    mongoTemplateRef = "primaryMongoTemplate")
@EnableConfigurationProperties
public class PrimaryConfig {

    @Value("${spring.config.activate.on-profile}")
    private String profile;

    @Bean("primaryProperties")
    @Qualifier("primaryProperties")
    @ConfigurationProperties(prefix = "mongodb.primary")
    @Primary
    public MongoProperties mongoProperties() {
        return new MongoProperties();
    }

    @Bean("primaryMongoClient")
    @Primary
    @Qualifier("primaryMongoClient")
    public MongoClient mongoClient(
        @Qualifier("primaryProperties") MongoProperties mongoProperties) {
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

    @Bean("primaryMongoDBFactory")
    @Qualifier("primaryMongoDBFactory")
    @Primary
    public MongoDatabaseFactory mongoDatabaseFactory(
        @Qualifier("primaryMongoClient") MongoClient mongoClient,
        @Qualifier("primaryProperties") MongoProperties mongoProperties) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, mongoProperties.getDatabase());
    }

    @Bean("primaryMongoTemplate")
    @Qualifier("primaryMongoTemplate")
    @Primary
    public MongoTemplate mongoTemplate(
        @Qualifier("primaryMongoDBFactory") MongoDatabaseFactory mongoDatabaseFactory,
        MongoConverter mongoConverter) {
        return new MongoTemplate(mongoDatabaseFactory, mongoConverter);
    }

}
