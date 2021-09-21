package io.miragon.bpmrepo.core.artifact.domain.service;

import io.miragon.bpmrepo.core.artifact.domain.mapper.DeploymentMapper;
import io.miragon.bpmrepo.core.artifact.domain.model.Artifact;
import io.miragon.bpmrepo.core.artifact.domain.model.ArtifactVersion;
import io.miragon.bpmrepo.core.artifact.domain.model.Deployment;
import io.miragon.bpmrepo.core.artifact.domain.model.NewDeployment;
import io.miragon.bpmrepo.core.artifact.infrastructure.repository.DeploymentJpaRepository;
import io.miragon.bpmrepo.core.artifact.plugin.DeploymentPlugin;
import io.miragon.bpmrepo.core.repository.domain.service.AuthService;
import io.miragon.bpmrepo.core.shared.enums.RoleEnum;
import io.miragon.bpmrepo.core.shared.exception.ObjectNotFoundException;
import io.miragon.bpmrepo.core.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtifactVersionDeploymentService {

    private final ArtifactVersionService artifactVersionService;
    private final ArtifactService artifactService;
    private final AuthService authService;

    private final DeploymentJpaRepository deploymentJpaRepository;
    private final DeploymentMapper deploymentMapper;
    private final DeploymentPlugin deploymentPlugin;

    public ArtifactVersion deploy(final String artifactId, final String versionId, final String target, final User user) {
        log.debug("Persisting deployment of artifact version {} on target {} by user {}", versionId, target, user.getUsername());
        final Artifact artifact = this.artifactService.getArtifactById(artifactId);
        this.authService.checkIfOperationIsAllowed(artifact.getRepositoryId(), RoleEnum.ADMIN);
        final ArtifactVersion version = this.artifactVersionService.getVersion(versionId)
                .orElseThrow(() -> new ObjectNotFoundException("exception.versionNotFound"));
        this.deploymentPlugin.deploy(version.getId(), target);
        version.deploy(target, user.getUsername());
        return this.artifactVersionService.saveToDb(version);
    }

    public List<ArtifactVersion> deployMultiple(final List<NewDeployment> deployments, final User user) {
        log.debug("Persisting deployments of {} versions to target {} by user {}", deployments.size(), deployments.get(0).getTarget(), user.getUsername());
        final List<String> artifactIds = deployments.stream().map(NewDeployment::getArtifactId).collect(Collectors.toList());
        final List<Artifact> artifacts = this.artifactService.getAllArtifactsById(artifactIds);
        artifacts.forEach(artifact -> this.authService.checkIfOperationIsAllowed(artifact.getRepositoryId(), RoleEnum.ADMIN));


        //Stream through all versions that have to be deployed
        final List<ArtifactVersion> updatedVersions = deployments.stream().map(deployment -> {
            final ArtifactVersion version = this.artifactVersionService.getLatestVersion(deployment.getArtifactId());
            //Check if the version is already deployed to the specified target - If true, overwrite the Deployment Object - If false, create a new Deployment Object
            final Optional<Deployment> existingDeployment = this.getExistingDeployment(version, deployment.getTarget());
            if (existingDeployment.isPresent()) {
                version.updateDeployment(existingDeployment.get(), user.getUsername());
            } else {
                version.deploy(deployment.getTarget(), user.getUsername());
            }
            this.deploymentPlugin.deploy(version.getId(), deployment.getTarget());
            return this.artifactVersionService.saveToDb(version);
        }).collect(Collectors.toList());

        return updatedVersions;
    }

    public List<String> getDeploymentTargets() {
        return this.deploymentPlugin.getDeploymentTargets();
    }


    public Optional<Deployment> getExistingDeployment(final ArtifactVersion version, final String target) {
        return version.getDeployments().stream()
                .filter(existingDeployments -> existingDeployments.getTarget().equals(target))
                .findFirst();
    }

}
