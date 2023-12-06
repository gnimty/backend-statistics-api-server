package onlysolorank.apiserver.api.service;

import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.controller.dto.VersionRes;
import onlysolorank.apiserver.api.exception.CustomException;
import onlysolorank.apiserver.api.exception.ErrorCode;
import onlysolorank.apiserver.api.service.dto.ChampionDto;
import onlysolorank.apiserver.api.service.dto.ChampionSaleDto;
import onlysolorank.apiserver.api.service.dto.SkinSaleDto;
import onlysolorank.apiserver.domain.Champion;
import onlysolorank.apiserver.domain.ChampionCache;
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

    public VersionRes getLatestVersion() {
        Optional<Version> version = getVersion();

        if (version.isPresent()) {
            return VersionRes.from(version.get());
        }

        return null;
    }

    public String getLatestVersionString() {
        Optional<Version> version = getVersion();

        if (version.isPresent()) {
            return version.get().getVersion();
        }

        return null;
    }

    private Optional<Version> getVersion() {
        Optional<Version> version = versionRepository.findOneByOrder(0);
        return version;
    }

    public List<ChampionSaleDto> getAllChampionSalesInfo() {
        return championSaleRepository.findAll().stream()
            .map(championSale -> ChampionSaleDto.from(championSale))
            .toList();
    }

    public List<SkinSaleDto> getAllSkinSalesInfo() {
        return skinSaleRepository.findAll().stream()
            .map(skinSale -> SkinSaleDto.fromSkinSale(skinSale))
            .toList();
    }

    public List<ChampionDto> getAllChampions() {
        return championRepository.findAll().stream()
            .map(champion -> ChampionDto.from(champion))
            .toList();
    }

    public ChampionDto getChampion(Long championId) {
        Champion champion = championRepository.findOneByChampionId(championId.toString())
            .orElseThrow(() -> new CustomException(ErrorCode.RESULT_NOT_FOUND, "챔피언 정보가 존재하지 않습니다."));

        return ChampionDto.from(champion);
    }

    public List<ChampionDto> getRotationChampions() {
        return championRepository.findRotationChampions().stream()
            .map(rotation -> ChampionDto.from(rotation))
            .toList();
    }

    @PostConstruct
    public void updateChampionCache() {
        championCache.initOnCrawl(getAllChampions());
    }
}
