package cc.phung.graph.repo;

import cc.phung.graph.models.dtos.NodeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NodeRepo extends JpaRepository<NodeDTO, String> {
    Optional<NodeDTO> findByUuid(final String uuid);

    Integer deleteByUuid(final String uuid);
}
