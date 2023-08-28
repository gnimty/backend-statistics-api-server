package onlysolorank.apiserver.api.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.Summoner;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * packageName    : onlysolorank.apiserver.api.service.dto
 * fileName       : SummonerRankPageDto
 * author         : solmin
 * date           : 2023/08/15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/08/15        solmin       최초 생성
 * 2023/08/28        solmin       page number와 page 내 총 소환사 수 오류 수정
 */

@Data
@AllArgsConstructor
public class SummonerRankPageDto {
    private List<SummonerRankDto> ranks;
    private Boolean next;
    private Boolean prev;
    private Integer summonersInPage;
    private Integer page;
    private Integer totalSummoners;

    @Builder
    public SummonerRankPageDto(List<SummonerRankDto> summonerRanks, Page<Summoner> summonerPage){
        this.ranks = summonerRanks;
        this.next = summonerPage.hasNext();
        this.prev = summonerPage.hasPrevious();
        this.summonersInPage = summonerRanks.size();
        this.page = summonerPage.getPageable().getPageNumber();
        this.totalSummoners = (int)summonerPage.getTotalElements(); // 소혼사 최대 크기가 integer 범위에서 절대 벗어나지 않기 때문에, 간단하게 형변환 취해서 가져옴
    }
}
