package cc.phung.graph.models.dtos;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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

    private String sourceId;

    private String destionantionId;

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
