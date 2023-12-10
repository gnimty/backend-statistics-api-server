package onlysolorank.apiserver.domain.dto;

import java.util.Arrays;
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
    RANK_SOLO("개인/2인 랭크 게임", "소환사의 협곡", 420 ),
    BLIND("일반 게임", "소환사의 협곡", 430),
    RANK_FLEX("자유 랭크 게임", "소환사의 협곡", 440),
    ARAM("칼바람 나락", "칼바람 나락", 450),
    ALL("상관없음", "상관없음", null );

    // TODO 추후 추가

    private final String value;
    private final String map;
    private final Integer queueId;

    QueueType(String value, String map, Integer queueId) {
        this.value = value;
        this.map = map;
        this.queueId = queueId;
    }

    public static QueueType[] getActualQueueType() {
        QueueType[] targets = {RANK_SOLO, RANK_FLEX, ARAM, BLIND};
        return targets;
    }

    public static Boolean isActualQueueType(QueueType queueType){
        if (Arrays.asList(getActualQueueType()).contains(queueType)){
            return true;
        }
        return false;
    }

    public static QueueType getByQueueId(Integer queueId) {
        for (QueueType queueType : values()) {
            if (queueType.queueId.equals(queueId)) {
                return queueType;
            }
        }
        // Handle the case when the queueId is not found
        throw new IllegalArgumentException("No QueueType found for queueId: " + queueId);
    }

}
