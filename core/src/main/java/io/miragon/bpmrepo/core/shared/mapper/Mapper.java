package io.miragon.bpmrepo.core.shared.mapper;

import io.miragon.bpmrepo.core.repository.infrastructure.entity.AssignmentId;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    //___________________________TO -> Embeddable______________________________-

    public AssignmentId toEmbeddable(final String userId, final String bpmnRepositoryId) {
        if (userId == null || bpmnRepositoryId == null) {
            return null;
        }
        return AssignmentId.builder()
                .userId(userId)
                .bpmnRepositoryId(bpmnRepositoryId)
                .build();
    }
}