package cc.phung.graph.models.dtos;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity(name = "Node")
@Table(name = "nodes")
public class NodeDTO {
    @Id
    private String uuid;
    private String value;
    private int x;
    private int y;
}
