package cc.phung.graph.models.dtos;

import cc.phung.graph.models.entry.EdgeEntry;
import cc.phung.graph.models.entry.NodeEntry;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GraphDTO {
    public List<EdgeEntry> edges;
    public List<NodeEntry> nodes;
}
