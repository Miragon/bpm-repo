package io.miragon.bpmrepo.core.diagram.api.transport;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeploymentTO {

    @NotEmpty
    private String target;

}