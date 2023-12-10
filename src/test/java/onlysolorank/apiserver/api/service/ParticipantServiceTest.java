package onlysolorank.apiserver.api.service;

import java.util.List;
import onlysolorank.apiserver.api.service.dto.RecentMemberDto;
import onlysolorank.apiserver.domain.dto.QueueType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * packageName    : onlysolorank.apiserver.api.service
 * fileName       : ParticipantServiceTest
 * author         : solmin
 * date           : 2023/09/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/11        solmin       최초 생성
 */

@ActiveProfiles("test")
@SpringBootTest
class ParticipantServiceTest {

    private final String puuid1 = "kA3ZOxLp3FsEyvO51kpP-rMY0fdNxnhKxDhlnx3yOuZBGWpluRVBgTW2AW4uxRTvUf4-_s3lYNAQ1Q";
    private final String puuid2 = "YWrAhGav1K5GEGerCUE1DtFAPdS4aVE7jOaeMxR3LiWT-XFu0_YsOB6yraq6nFhmXcP4W27lHPD77A";
    @Autowired
    private ParticipantService participantService;

    @Test
    void 최근_20게임의_팀원들_조회() {
        List<RecentMemberDto> distinctTeamMembersExceptMe = participantService.getDistinctTeamMembersByQueueTypeExceptMe(puuid2, QueueType.RANK_SOLO);

        return;
    }
}