package onlysolorank.apiserver.api.service.dto;

import static java.util.stream.Collectors.groupingBy;
import static onlysolorank.apiserver.utils.CustomFunctions.divideAndReturnDouble;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import onlysolorank.apiserver.api.controller.dto.MatchBriefRes;
import onlysolorank.apiserver.domain.dto.Lane;


@Getter
@Builder
@AllArgsConstructor
public class MatchSummaryDto {
    private Integer plays;
    private Integer wins;
    private Integer defeats;
    private Double winRate;
    private Double avgKda;
    private Boolean isPerfect;
    private List<ChampionSummaryDto> championSummary;
    private Map<Lane, Integer> laneSummary;
    public static MatchSummaryDto from(List<MatchBriefRes> matchDtoList){
        Integer totalKills = 0;
        Integer totalDeaths = 0;
        Integer totalAssists = 0;
        Integer totalPlays = matchDtoList.size();
        Integer totalWins = 0;
        Integer totalDefeats;
        Double totalWinRate;
        Double totalAvgKda;
        Boolean isTotalPerfect= false;

        List<ChampionSummaryDto> championSummary = new ArrayList<>();

        Map<Lane, Integer> laneSummary = new HashMap<>();
        for(Lane l : Lane.getActualLane()){
            laneSummary.put(l, 0);
        }

        List<ParticipantDto> participants = matchDtoList.stream()
            .map(MatchBriefRes::getParticipant).toList();

        for(ParticipantDto p : participants){
            Lane targetLane = p.getLane();

            if(laneSummary.containsKey(targetLane)){
                laneSummary.put(targetLane, laneSummary.get(targetLane)+1);
            }
        }


        Map<Integer, List<ParticipantDto>> participantMap = participants.stream()
            .collect(groupingBy(ParticipantDto::getChampionId));

        List<Map.Entry<Integer, List<ParticipantDto>>> entryList = new ArrayList<>(participantMap.entrySet());

        // 리스트를 크기 기준으로 정렬
        entryList.sort(Comparator.comparingInt(entry -> -1 * entry.getValue().size()));

        int limit = 3;
        // 정렬된 entryList를 사용하여 LinkedHashMap 생성
        for (Map.Entry<Integer, List<ParticipantDto>> entry : entryList) {
            List<ParticipantDto> value = entry.getValue();

            Integer plays = value.size();
            Integer wins = value.stream().filter(ParticipantDto::getWin).toList().size();
            Integer defeats = plays - wins;
            Boolean isPerfect = false;
            Double avgKdaByChampion;
            Double winRate = divideAndReturnDouble(wins, (wins + defeats), 3).get();

            Integer kills = value.stream().map(ParticipantDto::getKill).mapToInt(Integer::intValue).sum();
            Integer deaths = value.stream().map(ParticipantDto::getDeath).mapToInt(Integer::intValue).sum();
            Integer assists = value.stream().map(ParticipantDto::getAssist).mapToInt(Integer::intValue).sum();

            if (deaths.equals(0)) {
                isPerfect = true;
                avgKdaByChampion = null;
            }else{
                avgKdaByChampion = divideAndReturnDouble(kills+assists, deaths, 3).get();
            }

            championSummary.add(ChampionSummaryDto.builder()
                .championId(entry.getKey())
                .avgKda(avgKdaByChampion)
                .plays(plays)
                .wins(wins)
                .defeats(defeats)
                .winRate(winRate)
                .isPerfect(isPerfect)
                .build());

            limit--;

            if (limit==0){
                break;
            }
        }
        totalKills = participants.stream().map(ParticipantDto::getKill).mapToInt(Integer::intValue).sum();
        totalDeaths = participants.stream().map(ParticipantDto::getDeath).mapToInt(Integer::intValue).sum();
        totalAssists = participants.stream().map(ParticipantDto::getAssist).mapToInt(Integer::intValue).sum();
        totalWins = participants.stream().filter(ParticipantDto::getWin).toList().size();

        totalDefeats = totalPlays - totalWins;
        totalWinRate = divideAndReturnDouble(totalWins, (totalWins + totalDefeats), 3).get();


        if (totalDeaths.equals(0)) {
            isTotalPerfect = true;
            totalAvgKda = null;
        }else{
            totalAvgKda = divideAndReturnDouble(totalKills+totalAssists, totalDeaths, 3).get();
        }

        return MatchSummaryDto.builder()
            .avgKda(totalAvgKda)
            .championSummary(championSummary)
            .defeats(totalDefeats)
            .wins(totalWins)
            .plays(totalPlays)
            .isPerfect(isTotalPerfect)
            .winRate(totalWinRate)
            .laneSummary(laneSummary)
            .build();
    }


    @Getter
    @Builder
    @AllArgsConstructor
    public static class ChampionSummaryDto {
        private Integer championId;
        private Integer plays;
        private Integer wins;
        private Integer defeats;
        private Double winRate;
        private Double avgKda;
        private Boolean isPerfect;
    }

}
