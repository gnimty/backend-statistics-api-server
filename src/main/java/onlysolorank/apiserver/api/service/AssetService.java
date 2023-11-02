package onlysolorank.apiserver.api.service;

import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.exception.CustomException;
import onlysolorank.apiserver.api.exception.ErrorCode;
import onlysolorank.apiserver.domain.Champion;
import onlysolorank.apiserver.domain.ChampionCache;
import onlysolorank.apiserver.domain.ChampionSale;
import onlysolorank.apiserver.domain.Rotation;
import onlysolorank.apiserver.domain.SkinSale;
import onlysolorank.apiserver.domain.Version;
import onlysolorank.apiserver.repository.champion.ChampionRepository;
import onlysolorank.apiserver.repository.crawl.ChampionSaleRepository;
import onlysolorank.apiserver.repository.crawl.SkinSaleRepository;
import onlysolorank.apiserver.repository.crawl.VersionRepository;
import org.springframework.stereotype.Service;

/**
 * packageName    : onlysolorank.apiserver.api.service
 * fileName       : AssetService
 * author         : solmin
 * date           : 2023/09/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/13        solmin       최초 생성
 * 2023/10/09        solmin       AssetService로 이름 변경
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class AssetService {

    private final ChampionSaleRepository championSaleRepository;
    private final SkinSaleRepository skinSaleRepository;
    private final VersionRepository versionRepository;
    private final ChampionRepository championRepository;
    private final ChampionCache championCache;

    public Version getLatestVersion() {
        Optional<Version> version = versionRepository.findOneByOrder(0);

        if (version.isPresent()) {
            return version.get();
        }
        return null;
    }

    public List<ChampionSale> getAllChampionSalesInfo() {
        return championSaleRepository.findAll();
    }

    public List<SkinSale> getAllSkinSalesInfo() {
        return skinSaleRepository.findAll();
    }


    public List<Champion> getAllChampions() {
        return championRepository.findAll();
    }

    public Champion getChamiponByChampionId(Long championId) {
        Champion champion = championRepository.findOneByChampionId(championId.toString())
            .orElseThrow(
                () -> new CustomException(ErrorCode.RESULT_NOT_FOUND, "챔피언 정보가 존재하지 않습니다."));

        return champion;
    }

    public List<Rotation> getRotationChampions() {
        List<Rotation> rotationChampions = championRepository.findRotationChampions();
        return rotationChampions;
    }

    @PostConstruct
    public void updateChampionCache(){
        championCache.initOnCrawl(getAllChampions());
    }
}
