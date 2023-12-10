package onlysolorank.apiserver.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.api.service.dto.ChampionDto;

/**
 * packageName    : onlysolorank.apiserver.api.controller.dto
 * fileName       : ChampionRes
 * author         : solmin
 * date           : 11/6/23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 11/6/23        solmin       최초 생성
 */

@Data
public class ChampionRes {

    // 복수 챔피언
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ChampionDto> champions;

    // 단일 챔피언
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ChampionDto champion;

    private ChampionRes(List<ChampionDto> champions) {
        this.champions = champions;
    }

    private ChampionRes(ChampionDto champion) {
        this.champion = champion;
    }


    // 두 멤버 변수가 양립하는 경우는 없으므로 custom한 Builder 메소드로 분리한 후 생성자를 닫아버리기
    @Builder(builderMethodName = "multiBuilder")
    public static ChampionRes createChampion(List<ChampionDto> champions) {
        return new ChampionRes(champions);
    }

    @Builder(builderMethodName = "singleBuilder")
    public static ChampionRes createChampion(ChampionDto champion) {
        return new ChampionRes(champion);
    }

//    public ChampionRes(List<ChampionDto> champions, ChampionDto champion) {
//        if (champions != null && champion != null) {
//            throw new IllegalArgumentException("champion과 champions 중 하나는 설정되어야 합니다.");
//        }
//        this.champions = champions;
//        this.champion = champion;
//    }
//

//    @NoArgsConstructor
//    public static class Builder {
//        private List<ChampionDto> champions;
//        private ChampionDto champion;
//
//        public Builder champions(List<ChampionDto> champions) {
//            this.champions = champions;
//            this.champion = null;
//            return this;
//        }
//
//        public Builder champion(ChampionDto champion) {
//            this.champion = champion;
//            this.champions = null;
//            return this;
//        }
//
//        public ChampionRes build() {
//            return new ChampionRes(this.champions, this.champion);
//        }
//    }
//
//    public static Builder builder() {
//        return new Builder();
//    }

}
