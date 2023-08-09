package onlysolorank.apiserver.repository;

import onlysolorank.apiserver.api.dto.ChampionPlaysBriefDto;
import onlysolorank.apiserver.domain.Participant;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * packageName    : onlysolorank.apiserver.repository
 * fileName       : ParticipantRepository
 * author         : solmin
 * date           : 2023/07/28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/28        solmin       최초 생성
 * 2023/08/09        solmin       findTop10ChampionStatsByPuuid 추가
 */
public interface ParticipantRepository extends MongoRepository<Participant, String> {
    List<Participant> findByMatchId(String matchId);

    @Query("{'matchId': {$in: ?0}}")
    List<Participant> findByMatchIdInCustom(List<String> matchIds);

    // Most 10 Champion 전적 정보 집계, TODO 이후 챔피언 탭에서 재사용 예정
    @Aggregation(pipeline = {"{ $match: { puuid: ?0 } }",
        "{ $group: { " +
            "_id: { championId: '$championId', championName: '$championName' }, " +
            "totalPlays: { $sum: 1 }, " +
            "totalWin: { $sum: { $cond: [{ $eq: ['$win', 'true'] }, 1, 0] } }," +
            "totalDefeat: { $sum: { $cond: [{ $eq: ['$win', 'false'] }, 1, 0] } }," +
            "totalGameDuration: {$sum: '$gameDuration'}," +
            "totalCs: {$sum: '$cs'}," +
            "avgCs: { $avg: '$cs' }, " +
            "avgKill: { $avg: '$kills' }, " +
            "totalKill: { $sum: '$kills' }, " +
            "avgDeath: { $avg: '$deaths' }, " +
            "totalDeath: { $sum: '$deaths' }, " +
            "avgAssist: { $avg: '$assists' }, " +
            "totalAssist: { $sum: '$assists' } " +
        "}} ",
        "{ $addFields: { " +
            "avgCs: {$round: ['$avgCs', 2]}," +
            "avgDeath: {$round: ['$avgDeath', 2]}," +
            "avgAssist: {$round: ['$avgAssist', 2]}," +
            "avgKill: {$round: ['$avgKill', 2]}," +
            "winRate: {$round: [{$divide:['$totalWin','$totalPlays']}, 2]}," +
            "avgKda: {$round: [{$divide:[{$add: ['$totalKill','$totalAssist']},'$totalDeath']}, 3]}" +
        "}}",
        "{ $sort: { totalPlays: -1 } }", "{ $limit: 10 }",
        "{$project: { " +
            "_id: 0, " +
            "championId: '$_id.championId', " +
            "championName: '$_id.championName'," +
            "avgCs: 1," +
            "totalPlays: 1," +
            "totalGameDuration: 1," +
            "totalCs: 1," +
            "avgKda: 1," +
            "avgKill: 1," +
            "avgDeath: 1," +
            "avgAssist: 1," +
            "winRate: 1," +
            "totalWin: 1," +
            "totalDefeat: 1" +
        "}}"})
    List<ChampionPlaysBriefDto> findTop10ChampionStatsByPuuid(@Param("puuid") String puuid);
}
