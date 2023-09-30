package onlysolorank.apiserver.config;

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
import onlysolorank.apiserver.repository.statistics.ChampionStatisticsRepository;
import onlysolorank.apiserver.repository.summoner.SummonerRepository;
import onlysolorank.apiserver.repository.summoner_match.SummonerMatchRepository;
import onlysolorank.apiserver.repository.summoner_play.SummonerPlayRepository;
import onlysolorank.apiserver.repository.team.TeamRepository;
import org.springframework.beans.factory.annotation.Qualifier;
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

import static java.util.Collections.singletonList;

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
        ChampionStatisticsRepository.class,
        ChampionSaleRepository.class,
        SkinSaleRepository.class,
        VersionRepository.class,
        ChampionRepository.class,
        SummonerMatchRepository.class,
        MatchRepository.class,
        TeamRepository.class,
        ParticipantRepository.class,
        SummonerPlayRepository.class,
        SummonerHistoryRepository.class
    },
    mongoTemplateRef = "mongoTemplate")
@EnableConfigurationProperties
public class PrimaryConfig {
    @Bean(name = "primaryProperties")
    @ConfigurationProperties(prefix = "mongodb.primary")
    @Primary
    public MongoProperties primaryProperties() {
        return new MongoProperties();
    }

    @Bean(name = "primaryMongoClient")
    public MongoClient mongoClient(@Qualifier("primaryProperties") MongoProperties mongoProperties) {

        MongoCredential credential = MongoCredential
            .createCredential(mongoProperties.getUsername(), mongoProperties.getAuthenticationDatabase(), mongoProperties.getPassword());

        return MongoClients.create(MongoClientSettings.builder()
            .applyToClusterSettings(builder -> builder
                .hosts(singletonList(new ServerAddress(mongoProperties.getHost(), mongoProperties.getPort()))))
            .credential(credential)
            .build());
    }

    @Primary
    @Bean(name = "primaryMongoDBFactory")
    public MongoDatabaseFactory mongoDatabaseFactory(
        @Qualifier("primaryMongoClient") MongoClient mongoClient,
        @Qualifier("primaryProperties") MongoProperties mongoProperties) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, mongoProperties.getDatabase());
    }

    @Primary
    @Bean(name = "mongoTemplate")
    @Qualifier
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory, MongoConverter mongoConverter) {
        return new MongoTemplate(mongoDatabaseFactory, mongoConverter);
    }
}
