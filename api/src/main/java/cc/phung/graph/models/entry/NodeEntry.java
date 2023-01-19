package cc.phung.graph.models.entry;


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
public class NodeEntry {
    @Id
    private String uuid;
    private String value;
    private int x;
    private int y;
}
