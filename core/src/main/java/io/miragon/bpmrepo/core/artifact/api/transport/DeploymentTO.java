package io.miragon.bpmrepo.core.artifact.api.transport;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeploymentTO {

    @NotBlank
    private String id;
    
    @NotBlank
    private String target;

    @NotBlank
    private String user;

    @NotBlank
    private LocalDateTime timestamp;

}