package onlysolorank.apiserver.api.service;

import lombok.RequiredArgsConstructor;
import onlysolorank.apiserver.api.dto.SummonerDto;
import onlysolorank.apiserver.repository.SummonerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
 */
@Service
@RequiredArgsConstructor
public class SummonerService {

    private final SummonerRepository summonerRepository;
    public List<SummonerDto> getSummonerByInternalName(String keyword){

        String internalName = keywordToInternalName(keyword);


        List<SummonerDto> build = List.of
            (SummonerDto.builder().build());

        return build;
    }


    public String keywordToInternalName(String keyword){

        //1. 영어, 한글, 숫자를 제외한 모든 문자 및 공백 제거 후 소문자로 변환
        return keyword
            .replaceAll("[^a-zA-Z가-힣0-9]", "")
            .replaceAll("\\s", "")
            .toLowerCase();
    }

}
