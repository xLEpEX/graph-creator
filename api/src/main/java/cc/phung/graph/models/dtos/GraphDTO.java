package cc.phung.graph.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GraphDTO {
    public List<EdgeDTO> edges;
    public List<NodeDTO> nodes;
}
