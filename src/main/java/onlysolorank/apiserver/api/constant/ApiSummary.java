package onlysolorank.apiserver.api.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName    : onlysolorank.apiserver.api.constant
 * fileName       : ApiSummary
 * author         : solmin
 * date           : 1/10/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 1/10/24        solmin       최초 생성
 */
@Getter
@AllArgsConstructor
public class ApiSummary {
    // AssetController
    public static final String GET_LATEST_VERSION = "최신 버전 조회";
    public static final String GET_SKIN_SALES = "스킨 할인 정보 조회";
    public static final String GET_CHAMPION_SALES = "챔피언 할인 정보 조회";
    public static final String GET_ALL_CHAMPIONS = "모든 챔피언 정보 조회";
    public static final String GET_CHAMPION = "특정 챔피언 정보 조회";
    public static final String GET_ROTATIONS = "챔피언 로테이션 정보 조회";

    // ChampionController
    public static final String GET_CHAMPION_STATS = "챔피언 티어 정보 조회";
    public static final String GET_CHAMPION_DETAIL = "챔피언 세부 분석 정보 조회";

    // RankController
    public static final String GET_SUMMONER_RANK = "티어 기준 소환사 랭킹 조회";
    public static final String GET_SPECIALISTS = "챔피언 플레이 기준 소환사 랭킹 조회";

    // SummonerController
    public static final String GET_AUTOCOMPLETE = "소환사 이름 자동완성 결과 조회";
    public static final String GET_SUMMONER = "소환사 단일 정보 조회";
    public static final String GET_SUMMONER_MATCH_INFO = "소환사 전적정보 조회";
    public static final String GET_MATCH_DETAIL = "전적정보 상세 조회";
    public static final String REFRESH_SUMMONER = "소환사 정보 및 전적정보 갱신";
    public static final String GET_CURRENT_GAME = "소환사 인게임 정보 조회";
//    public static final String GET_RECENT_SUMMONERS = "최근 20게임에서 함께 한 소환사 목록 조회하기";
    public static final String GET_CHAMPION_PLAYS = "소환사 전적정보 챔피언별 세부 조회";





}
