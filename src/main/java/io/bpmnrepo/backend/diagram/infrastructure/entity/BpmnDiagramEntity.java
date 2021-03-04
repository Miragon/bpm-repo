package io.bpmnrepo.backend.diagram.infrastructure.entity;


import io.bpmnrepo.backend.repository.infrastructure.entity.BpmnRepositoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "bpmnDiagram")
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"bpmn_diagram_id", "bpmn_repository_id"}))
public class BpmnDiagramEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "bpmn_diagram_id", unique = true, nullable = false, updatable = false, length = 36)
    private String bpmnDiagramId;

    @Column(name = "bpmn_repository_id")
    private String bpmnRepositoryId;

    @Column(name = "bpmn_diagram_name")
    private String bpmnDiagramName;

    @Column(name = "bpmd_diagram_description")
    private String bpmnDiagramDescription;

}
