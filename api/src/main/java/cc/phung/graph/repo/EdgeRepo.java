package cc.phung.graph.repo;

import cc.phung.graph.models.entry.EdgeEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EdgeRepo extends JpaRepository<EdgeEntry, String> {

    Integer deleteByUuid(final String uuid);
    List<EdgeEntry> findBySourceIdOrDestionantionId(final String sourecId, final String destionationId);

    boolean existsBySourceIdAndDestionantionId(final String sourecId, final String destionationId);

    void deleteByUuidIn(List<String> uuid);
}
