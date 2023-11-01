package onlysolorank.apiserver.repository.champion;

import java.util.List;
import onlysolorank.apiserver.domain.Rotation;

/**
 * packageName    : onlysolorank.apiserver.repository.champion
 * fileName       : ChampionRepositoryCustom
 * author         : solmin
 * date           : 10/9/23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 10/9/23        solmin       최초 생성
 */
public interface ChampionRepositoryCustom {

    List<Rotation> findRotationChampions();
}
