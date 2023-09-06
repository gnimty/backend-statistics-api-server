package onlysolorank.apiserver.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.exception.CustomException;
import onlysolorank.apiserver.api.exception.ErrorCode;
import onlysolorank.apiserver.domain.SummonerHistory;
import onlysolorank.apiserver.repository.history.SummonerHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * packageName    : onlysolorank.apiserver.api.service
 * fileName       : SummonerHistoryService
 * author         : solmin
 * date           : 2023/09/05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/05        solmin       최초 생성
 */

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class SummonerHistoryService {
    private final SummonerHistoryRepository summonerHistoryRepository;

    public SummonerHistory getSummonerHistoryByPuuid(String puuid){
        return summonerHistoryRepository.findByPuuid(puuid)
            .orElseThrow(()-> new CustomException(ErrorCode.RESULT_NOT_FOUND));
    }
}
