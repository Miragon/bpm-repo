package io.miragon.bpmrepo.core.sharing.api.resource;


import io.miragon.bpmrepo.core.sharing.api.transport.ShareWithRepositoryTO;
import io.miragon.bpmrepo.core.sharing.domain.model.ShareWithRepository;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface SharedApiMapper {

    ShareWithRepositoryTO mapToShareRepoTO(final ShareWithRepository shared);

    ShareWithRepository mapToShareRepoModel(final ShareWithRepositoryTO to);

    List<ShareWithRepositoryTO> mapToShareRepoTO(final List<ShareWithRepository> list);

}
