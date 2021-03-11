package io.bpmnrepo.backend.repository.api.resource;


import io.bpmnrepo.backend.repository.BpmnRepositoryFacade;
import io.bpmnrepo.backend.repository.api.transport.BpmnRepositoryTO;
import io.bpmnrepo.backend.repository.domain.business.BpmnRepositoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bpmnrepo")
public class BpmnRepositoryController {

    private final BpmnRepositoryFacade bpmnRepositoryFacade;


    @PostMapping()
    @Operation(summary = "Create a new Repository")
    public ResponseEntity<Void> createOrUpdateRepository(@RequestBody @Valid final BpmnRepositoryTO repositoryTO){
        bpmnRepositoryFacade.createOrUpdateRepository(repositoryTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    @Operation(summary = "Get all Repositories")
    public ResponseEntity<List<BpmnRepositoryTO>> getAllRepositories(){
        System.out.println("Returning all Repositories assigned to current user (NO_SECURITY_USER)");
        return ResponseEntity.ok().body(this.bpmnRepositoryFacade.getAllRepositories());
    }

    @GetMapping("/{repositoryId}")
    @Operation(summary = "Get a single Repository by providing its ID")
    public ResponseEntity<BpmnRepositoryTO> getSingleRepository(@PathVariable @NotBlank final String repositoryId){
        System.out.println(String.format("Returning single repository with id %s", repositoryId));
        return ResponseEntity.ok().body(this.bpmnRepositoryFacade.getSingleRepository(repositoryId));
    }


    @DeleteMapping("/{repositoryId}")
    @Operation(summary = "Delete a Repository if you own it")
    public ResponseEntity<Void> deleteRepository(@PathVariable("repositoryId") @NotBlank final String repositoryId){
        System.out.println("Deleting Repository with ID " + repositoryId);
        this.bpmnRepositoryFacade.deleteRepository(repositoryId);
        return ResponseEntity.ok().build();
    }
}
