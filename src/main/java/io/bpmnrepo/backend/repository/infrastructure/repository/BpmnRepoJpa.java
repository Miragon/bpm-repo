package io.bpmnrepo.backend.repository.infrastructure.repository;

import io.bpmnrepo.backend.repository.infrastructure.entity.BpmnRepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;


//@Repository
public interface BpmnRepoJpa extends JpaRepository<BpmnRepositoryEntity, String>{
    List<BpmnRepositoryEntity> findAllByBpmnRepositoryNameIsNot(String bpmnRepositoryName);

    List<BpmnRepositoryEntity> findAllByBpmnRepositoryId(String bpmnRepositoryId);


    @Transactional
    void deleteBpmnRepositoryEntityByBpmnRepositoryId(String bpmnRepositoryId);

    BpmnRepositoryEntity findByBpmnRepositoryId(String bpmnRepositoryId);
}
