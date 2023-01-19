package cc.phung.graph.api.repo;

import cc.phung.graph.api.models.dtos.EdgeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EdgeRepo extends JpaRepository<EdgeDTO, String> {
    Optional<EdgeDTO> findByUuid(final String uuid);
    List<EdgeDTO> findAll();
    Integer deleteByUuid(final String uuid);
    List<EdgeDTO> findBySourceOrDestionantion(final String sourecId, final String destionationId);

    boolean existsBySourceOrDestionantion(final String sourecId, final String destionationId);

    void deleteByUuidIn(List<String> uuid);
}
