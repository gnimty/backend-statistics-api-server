package onlysolorank.apiserver.repository;

import onlysolorank.apiserver.api.service.dto.ChampionPlaysDto;
import onlysolorank.apiserver.api.service.dto.mostChampionsBySummonerDto;
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
 * 2023/08/10        solmin       totalDeath=0인 경우의 예외 처리
 * 2023/08/16        solmin       findTopChampionStatsByPuuid 추가
 * 2023/08/30        solmin       findChampionPlayInfoByPuuidAndChampionId 추가
 */
public interface ParticipantRepository extends MongoRepository<Participant, String> {
    List<Participant> findByMatchId(String matchId);

    @Query("{'matchId': {$in: ?0}}")
    List<Participant> findByMatchIdInCustom(List<String> matchIds);

    // Most 10 Champion 전적 정보 집계, TODO 이후 챔피언 탭에서 재사용 예정
    @Aggregation(pipeline = {"{ $match: { 'puuid': ?0 } }",
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
            "winRate: {$round: [{$divide:['$totalWin','$totalPlays']}, 2]}" +
//            "avgKda: {$round: [{$divide:[{$add: ['$totalKill','$totalAssist']},'$totalDeath']}, 3]}" +
        "}}",
        "{ $sort: { totalPlays: -1 } }",
        "{ $limit: ?1 }",
        "{$project: { " +
            "_id: 0, " +
            "championId: '$_id.championId', " +
            "championName: '$_id.championName'," +
            "avgCs: 1," +
            "totalPlays: 1," +
            "totalGameDuration: 1," +
            "totalCs: 1," +
            "avgKill: 1," +
            "avgDeath: 1," +
            "avgAssist: 1," +
            "winRate: 1," +
            "totalWin: 1," +
            "totalDefeat: 1," +
            "totalKill: 1," +
            "totalDeath: 1," +
            "totalAssist: 1" +
        "}}"})
    List<ChampionPlaysDto> findTopChampionStatsByPuuid(@Param("puuid") String puuid, @Param("limit") Integer limit);


    /**
     * puuid 리스트를 받아서 각 소환사별로 most N champion list 생성하기
     * 1. puuid in 조건으로 모든 participant 가져오기
     * 2. puuid별로 grouping 하여 챔피언 플레이 횟수를 내림차순으로 가져오기
     *
     * @param summonerIds the summoner ids
     * @param limit       the limit
     * @return the list
     */
    @Aggregation(pipeline = {
        "{$match:{puuid:{$in: ?0}}}",
        "{$group:{ _id: {puuid:$puuid,championId: $championId, }, plays:{$sum: 1}}}",
        "{$project:{ _id:0 , puuid: '$_id.puuid', championId:'$_id.championId', plays:1}}",
        "{$sort:{puuid:1, plays:-1}}",
        "{$group:{_id:$puuid, champions:{$push:{championId:$championId,plays:$plays}}}}",
        "{$project:{_id:0,puuid:$_id,champions:{$slice:[$champions, ?1]}}}",
        "{$project:{_id:0,puuid:1,championIds:'$champions.championId'}}"
        })
    List<mostChampionsBySummonerDto> findTopChampionsForEachSummoner(List<String> summonerIds, int limit);


    @Aggregation(pipeline = {
        "{$match:{puuid:{$in: ?0}, championName: {$eq: ?1}}}",
        "{$group: {" +
            "_id: {puuid:$puuid,championId: $championId, championName: '$championName'}," +
            "plays: {$sum: 1}, " +
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
            "totalAssist: { $sum: '$assists' }}} ",
        "{$sort:{plays:-1}}",
        "{$limit:?2}",
        "{ $addFields: { " +
            "avgCs: {$round: ['$avgCs', 2]}," +
            "avgDeath: {$round: ['$avgDeath', 2]}," +
            "avgAssist: {$round: ['$avgAssist', 2]}," +
            "avgKill: {$round: ['$avgKill', 2]}," +
            "winRate: {$round: [{$divide:['$totalWin','$totalPlays']}, 2]}" +
        "}}",
        "{$project: { " +
            "_id: 0, " +
            "puuid: '$_id.puuid', " +
            "championId: '$_id.championId', " +
            "championName: '$_id.championName'," +
            "avgCs: 1," +
            "totalPlays: 1," +
            "totalGameDuration: 1," +
            "totalCs: 1," +
            "avgKill: 1," +
            "avgDeath: 1," +
            "avgAssist: 1," +
            "winRate: 1," +
            "totalWin: 1," +
            "totalDefeat: 1," +
            "totalKill: 1," +
            "totalDeath: 1," +
            "totalAssist: 1" +
            "}}"
    })
    List<ChampionPlaysDto> findTopChampionStatsByChampionNameAndPuuids(List<String> puuids, String championName, Integer stdPlays);

    @Aggregation(pipeline = {
        "{$match:{puuid:  ?0,championId:  ?1} }",
        "{$group: {" +
            "_id: {championId: '$championId', championName: '$championName'}," +
            "plays: {$sum: 1}, " +
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
            "totalAssist: { $sum: '$assists' }}} ",
        "{ $addFields: { " +
            "avgCs: {$round: ['$avgCs', 2]}," +
            "avgDeath: {$round: ['$avgDeath', 2]}," +
            "avgAssist: {$round: ['$avgAssist', 2]}," +
            "avgKill: {$round: ['$avgKill', 2]}," +
            "winRate: {$round: [{$divide:['$totalWin','$totalPlays']}, 2]}" +
            "}}",
        "{$project: { " +
            "_id: 0, " +
            "puuid: '$_id.puuid', " +
            "championId: '$_id.championId', " +
            "championName: '$_id.championName'," +
            "avgCs: 1," +
            "totalPlays: 1," +
            "totalGameDuration: 1," +
            "totalCs: 1," +
            "avgKill: 1," +
            "avgDeath: 1," +
            "avgAssist: 1," +
            "winRate: 1," +
            "totalWin: 1," +
            "totalDefeat: 1," +
            "totalKill: 1," +
            "totalDeath: 1," +
            "totalAssist: 1" +
            "}}"
    })
    List<ChampionPlaysDto> findChampionPlayInfoByPuuidAndChampionId(String puuid, Long championId);

}
