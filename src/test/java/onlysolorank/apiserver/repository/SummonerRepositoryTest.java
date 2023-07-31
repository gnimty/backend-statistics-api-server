package onlysolorank.apiserver.repository;

import onlysolorank.apiserver.domain.Summoner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * packageName    : onlysolorank.apiserver.repository
 * fileName       : SummonerRepositoryTest
 * author         : solmin
 * date           : 2023/07/07
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/07        solmin       최초 생성
 * 2023/07/24        solmin       internalName test 추가
 */


@ActiveProfiles("test")
@SpringBootTest
class SummonerRepositoryTest {

    @Autowired
    private SummonerRepository summonerRepository;

    @Test
    public void getAllUserInternalNameTest(){
        List<Summoner> allSummoners = summonerRepository.findAll();

        allSummoners.forEach(summoner -> {
            System.out.printf("%s | %s\n", summoner.getName(), summoner.getInternalName());
        });
    }

    @Test
    public void getTop20UserTest(){
        Page<Summoner> allSummoners = summonerRepository.findAll(PageRequest.of(0, 20));

        allSummoners.forEach(summoner -> {
            System.out.println("summoner = " + summoner);
        });
    }
}