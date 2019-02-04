package cf.nirvandil.cibotmq.service;

import cf.nirvandil.cibotmq.web.dto.BuildResultDTO;

import java.util.Optional;

public interface IBambooClient {
    Optional<BuildResultDTO> getBuildResult(String buildName, Long buildNum);
}
