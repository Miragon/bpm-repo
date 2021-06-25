package io.miragon.bpmrepo.core.diagram.api.resource;

import io.miragon.bpmrepo.core.diagram.api.mapper.DiagramVersionApiMapper;
import io.miragon.bpmrepo.core.diagram.api.transport.DiagramVersionTO;
import io.miragon.bpmrepo.core.diagram.api.transport.DiagramVersionUploadTO;
import io.miragon.bpmrepo.core.diagram.domain.facade.DiagramVersionFacade;
import io.miragon.bpmrepo.core.diagram.domain.model.DiagramVersion;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Slf4j
@RestController
@Transactional
@Validated
@RequiredArgsConstructor
@RequestMapping("api/version")
public class DiagramVersionController {

    private final DiagramVersionFacade diagramVersionFacade;

    private final DiagramVersionApiMapper apiMapper;

    /**
     * Create a new version of the diagram.
     *
     * @param diagramId              Id of the diagram
     * @param diagramVersionUploadTO Update object
     */
    @PostMapping("/{repositoryId}/{diagramId}")
    public ResponseEntity<Void> createOrUpdateVersion(
            @PathVariable @NotBlank final String diagramId,
            @RequestBody @Valid final DiagramVersionUploadTO diagramVersionUploadTO) {
        log.debug("Creating new Version. Savetype: " + diagramVersionUploadTO.getSaveType());
        final String diagramVersionId = this.diagramVersionFacade.createOrUpdateVersion(diagramId, this.apiMapper.mapUploadToModel(diagramVersionUploadTO));
        log.debug(String.format("Current versionId: %s", diagramVersionId));
        return ResponseEntity.ok().build();
    }

    /**
     * Get latest version
     *
     * @param diagramId Id of the diagram
     * @return latest version
     */
    @GetMapping("/{diagramId}/latest")
    @Operation(summary = "Return the latest version of the requested diagram")
    public ResponseEntity<DiagramVersionTO> getLatestVersion(@PathVariable @NotBlank final String diagramId) {
        log.debug("Returning latest version");
        final DiagramVersion latestVersion = this.diagramVersionFacade.getLatestVersion(diagramId);
        return ResponseEntity.ok(this.apiMapper.mapToTO(latestVersion));
    }

    /**
     * Get all versions of the diagram
     *
     * @param diagramId Id of the diagram
     * @return all versions
     */
    @GetMapping("/{diagramId}/all")
    public ResponseEntity<List<DiagramVersionTO>> getAllVersions(@PathVariable @NotBlank final String diagramId) {
        log.debug("Returning all Versions");
        val versions = this.diagramVersionFacade.getAllVersions(diagramId);
        return ResponseEntity.ok(this.apiMapper.mapToTO(versions));
    }

    /**
     * Get a specific version
     *
     * @param diagramId Id of the diagram
     * @param versionId Id of the version
     * @return
     */
    @GetMapping("/{diagramId}/{versionId}")
    public ResponseEntity<DiagramVersionTO> getSingleVersion(
            @PathVariable @NotBlank final String diagramId,
            @PathVariable @NotBlank final String versionId) {
        log.debug("Returning single Version");
        final DiagramVersion version = this.diagramVersionFacade.getVersion(diagramId, versionId);
        return ResponseEntity.ok(this.apiMapper.mapToTO(version));
    }
}
