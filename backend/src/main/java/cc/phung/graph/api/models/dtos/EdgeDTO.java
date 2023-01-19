package cc.phung.graph.api.models.dtos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity(name = "Edge")
@Table(name = "edges")
public class EdgeDTO {
    @Id
    private String uuid;

    private String source;

    private String destionantion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EdgeDTO edgeDTO = (EdgeDTO) o;
        return uuid != null && Objects.equals(uuid, edgeDTO.uuid);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
