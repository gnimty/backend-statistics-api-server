package onlysolorank.apiserver.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.dto.SummonerDto;
import onlysolorank.apiserver.repository.SummonerRepository;
import org.springframework.stereotype.Service;

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
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SummonerService {

    private final SummonerRepository summonerRepository;
    public List<SummonerDto> getSummonerByInternalName(String keyword){

        String internalName = keywordToInternalName(keyword);

        // internalName으로 찾기
        List<SummonerDto> result = summonerRepository.findTop5ByInternalNameStartsWithOrderByInternalName(internalName)
            .stream().map(summoner -> SummonerDto.builder().summoner(summoner).build()).collect(Collectors.toList());

        return result;
    }


    public String keywordToInternalName(String keyword){
        // 영어, 한글, 숫자를 제외한 모든 문자 및 공백 제거 후 소문자로 변환
        return keyword
            .replaceAll("[^a-zA-Z가-힣0-9]", "")
            .replaceAll("\\s", "")
            .toLowerCase();
    }

}
