package cc.phung.graph.repo;

import cc.phung.graph.models.entry.NodeEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NodeRepo extends JpaRepository<NodeEntry, String> {
    Optional<NodeEntry> findByUuid(final String uuid);

    Integer deleteByUuid(final String uuid);
}
