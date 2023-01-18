package cc.phung.graph.api.models.dtos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

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
