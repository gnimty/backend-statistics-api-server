package onlysolorank.apiserver.api.service.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * packageName    : onlysolorank.apiserver.api.service.dto
 * fileName       : SpectatorRes
 * author         : solmin
 * date           : 2023/08/30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/08/30        solmin       최초 생성
 */

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class SpectatorV4GetCurrentGameInfo {

    private Long gameId;
    private Long mapId;
    private String gameMode;
    private String gameType;
    private Long gameQueueConfigId;
    private List<CurrentGameParticipant> participants;
    private Long gameStartTime;
    private Long gameLength;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CurrentGameParticipant {

        private Long championId;
        private IngamePerks perks;
        private Long profileIconId;
        private Boolean bot;
        private Long teamId;
        private String summonerName;
        private String summonerId;
        private Long spell1Id;
        private Long spell2Id;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IngamePerks {

        private List<Long> perkIds;
        private Long perkStyle;
        private Long perkSubStyle;
    }
}
