package onlysolorank.apiserver.domain.dto;

import lombok.Getter;

/**
 * packageName    : onlysolorank.apiserver.api.dto
 * fileName       : QueueType
 * author         : solmin
 * date           : 2023/07/31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/31        solmin       최초 생성
 */

@Getter
public enum QueueType {
    RANK_SOLO("개인/2인 랭크 게임", "소환사의 협곡"),
    BLIND("일반 게임", "소환사의 협곡"),
    RANK_FLEX("자유 랭크 게임", "소환사의 협곡"),
    ARAM("칼바람 나락", "칼바람 나락");
    // TODO 추후 추가
//    CUSTOM("사용자 설정 게임", );
    private String value;
    private String map;

    QueueType(String value, String map){
        this.value= value;
        this.map= map;
    }


}
