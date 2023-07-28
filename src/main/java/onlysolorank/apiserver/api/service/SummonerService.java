package onlysolorank.apiserver.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.dto.SummonerDto;
import onlysolorank.apiserver.repository.SummonerRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName    : onlysolorank.apiserver.api.service
 * fileName       : SummonerService
 * author         : solmin
 * date           : 2023/07/10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/10        solmin       최초 생성
 * 2023/07/24        solmin       internalName으로 SummonerDto 조회하는 기능 구현
 * 2023/07/28        solmin       keywordToInternalName 메소드를 KeywordRequestDto로 이관
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class SummonerService {

    private final SummonerRepository summonerRepository;
    public List<SummonerDto> getSummonerByInternalName(String internalName){

        // internal name으로 찾기
        List<SummonerDto> result = summonerRepository.findTop5ByInternalNameStartsWithOrderByInternalName(internalName)
            .stream().map(summoner -> SummonerDto.builder().summoner(summoner).build()).collect(Collectors.toList());

        return result;
    }

}
