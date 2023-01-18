package cc.phung.graph.api.repo;

import cc.phung.graph.api.models.dtos.NodeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NodeRepo extends JpaRepository<NodeDTO, String> {
    Optional<NodeDTO> findByUuid(final String uuid);
    List<NodeDTO> findAll();
    Integer deleteByUuid(final String uuid);
}
