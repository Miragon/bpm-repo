package io.bpmnrepo.backend.diagram.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Starred {
    private String bpmnDiagramId;
    private String userId;

}