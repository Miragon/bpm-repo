package io.miragon.bpmrepo.core.diagram.api.transport;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileTypesTO {

    private String name;

    private String svgIcon;

    private String defaultFile;

    private String defaultPreviewSVG;
}