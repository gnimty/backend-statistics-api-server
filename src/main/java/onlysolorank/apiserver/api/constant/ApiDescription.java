package onlysolorank.apiserver.api.constant;

/**
 * packageName    : onlysolorank.apiserver.api.constant
 * fileName       : ApiDescription
 * author         : solmin
 * date           : 1/10/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 1/10/24        solmin       최초 생성
 */
public class ApiDescription {

    // AssetController
    public static final String GET_LATEST_VERSION = """
        현재 리그오브레전드 패치 기준 버전 정보를 조회하는 API입니다.
        		
        https://ddragon.leagueoflegends.com/api/versions.json로부터 받은 버전 리스트의 가장 첫 번째 요소를 서버 내부에 저장하여 
        배포 서버 내의 다른 기능을 수행할 수 있는 의존 버전을 받아오고 이를 전달합니다.
        """;

    public static final String GET_SKIN_SALES = """
        스킨 할인 정보를 조회하는 API입니다.
        """;
    public static final String GET_CHAMPION_SALES = """
        챔피언 할인 정보를 조회하는 API입니다.
        """;
    public static final String GET_ALL_CHAMPIONS = """
        현재 패치 버전에 존재하는 모든 챔피언 ID, 챔피언 영문 이름, 챔피언 한글 이름을 조회하는 API입니다.
        """;
    public static final String GET_CHAMPION = """
        현재 패치 버전에 존재하는 챔피언 중 champion_id에 해당하는 챔피언 영문 이름 및 한글 이름을 조회하는 API입니다.
        """;
    public static final String GET_ROTATIONS = """
        로테이션 챔피언 목록을 조회하는 API입니다.
        """;


    // ChampionController
    public static final String GET_CHAMPION_STATS = """
        조건에 해당하는 챔피언 티어 및 통계 정보를 보여줍니다.
                
        챔피언 티어 및 통계 정보 업데이트는 하루에 한 번씩 이루어집니다.
                
        티어 정보 산정은 emerald 이상 매치 정보를 기준으로 이루어집니다.
        """;
    public static final String GET_CHAMPION_DETAIL = """
        조건에 맞는 챔피언 빌드 및 카운터 정보를 보여줍니다.
                
        챔피언 분석 정보 업데이트는 하루에 한 번씩 이루어집니다.
        """;

    // RankController
    public static final String GET_SUMMONER_RANK = """
        소환사의 랭크 티어를 기준으로 랭킹 정보를 보여주는 API입니다.
                
        Page 형태로 제공되며 1페이지당 100명의 소환사 정보를 얻을 수 있습니다.
                
        약 2시간에 한 번씩 갱신됩니다.
        """;
    public static final String GET_SPECIALISTS = """
        특정 챔피언을 최소 50번 이상 플레이한 소환사를 플레이수 내림차순으로 장인 랭킹 정보를 보여주는 API입니다.
                
        champion_name은 영문 챔피언 이름으로 입력해야 합니다.
        """;

    // SummonerController
    public static final String GET_AUTOCOMPLETE = """
        메인 화면 검색 엔진에서 소환사 이름을 입력하면 서버에 저장된 소환사의 이름과 패턴이 일치하는 소환사 정보를 사전 순으로 최대 5명까지 보여줍니다.
                
        keyword 검색어는 [summonerName]#[tagLine] 형태 또는 [summonerName]의 형식을 따라야 하며
        [summonerName]#[tagLine or 공백]을 summonerTagName으로 지칭합니다.
                
        또한 소환사명의 영어, 한글, 숫자를 제외한 모든 문자를 제거하여 얻은 문자열인 internalName과, tagLine을 lstrip() 및 lowerCase()로 적용한 internalTagLine을 
        
        ‘#’으로 합친 문자열을 internalTagName으로 지칭하며 검색 결과 반영 시 위 internalTagName을 LIKE% 조건으로 검색합니다.
                
        ex) keyword : “ hId@#eo  nb#%@” → internalTagName: hideonb
        ex) keyword : “ hId@#eo  nb#ush%@ -Kr1 ” → internalTagName: hideonbush#kr1
                
        keyword 검색어를 통해 얻는 결과값인 internalTagName은 case-insensitive하며 internalName은 공백을 무시합니다. 
        
        반대로 tagName은 맨 처음 공백 외의 공백을 무시하지 않습니다.
                
        keyword는 영어, 한글, 숫자를 제외한 문자를 모두 제거한 글자 수 기준으로 1자 이상 전송해야 합니다.
        
        cf) Path variable로 internalTagName을 받을 시 '#' 문자열을 정상적으로 받아올 수 없어 '-' 구분자 사용
        """;
    public static final String GET_SUMMONER = """
        summoner_tag_name을 변환한 internalTagName과 정확히 일치하는 단일 소환사 정보를 보내 줍니다.
        """;
    public static final String GET_SUMMONER_MATCH_INFO = """
        summoner_tag_name의 후처리 결과인 internalTagName이 일치하는 소환사 정보를 검색하여
        소환사 및 소환사 랭크 정보 / 모스트10 챔피언 플레이 정보 / 매치 데이터(최대 30개) 정보를 넘겨줍니다.
                
        매치데이터는 한국 서버 솔로랭크 / 자유 랭크 / 칼바람 나락 / 빠른 대전에 해당하는 전적만 수집합니다.\s
                
        internalTagName에 대한 세부 정보는 @소환사 이름 자동완성 결과 조회를 참고하시기 바랍니다.
        """;
    public static final String GET_MATCH_DETAIL = """
        {match_id}에 해당하는 전적의 상세 정보를 조회합니다.
        """;
    public static final String REFRESH_SUMMONER = """
        puuid에 해당하는 소환사의 전적 정보를 갱신하는 요청입니다. puuid는 소환사 전적정보 조회 / 소환사 전적정보 챔피언별 세부 조회 API 에서 얻을 수 있습니다. 갱신 이후 요청에서는 최신화된 소환서 정보 / 소환사 전적정보를 얻을 수 있습니다.
                
        전적 정보가 갱신된 후 다시 전적정보를 조회하는 API를 호출해야 하며
        puuid와 일치하는 소환사 정보가 존재하지 않을 경우 NotFoundError를 반환합니다.
                
        특정 소환사 정보를 갱신한 지 2분이 지나지 않은 상태에서 위 API를 재호출하게 되면 TooManyRequests를 반환합니다. 이는 전적정보 조회 시  renewableAt 필드에 종속됩니다.
                
        Riot API Rate Limit에 따라 다소 지연시간이 발생할 수 있습니다.
        """;
    public static final String GET_CURRENT_GAME = """
        summoner_tag_name의 후처리 결과인 internalTagName이 일치하는 소환사 정보를 검색하여
        해당 소환사가 현재 시즌에 진행한 모든 챔피언 플레이 정보를 보여줍니다.
                
        internalTagName에 대한 세부 정보는 @소환사 이름 자동완성 결과 조회를 참고하시기 바랍니다.
        """;
    public static final String GET_CHAMPION_PLAYS = """
        summoner_tag_name의 후처리 결과인 internalTagName이 일치하는 소환사 정보를 검색하여
        해당 소환사가 현재 시즌에 진행한 모든 챔피언 플레이 정보를 보여줍니다.
                
        internalTagName에 대한 세부 정보는 @소환사 이름 자동완성 결과 조회를 참고하시기 바랍니다.
        """;
}
