package cc.phung.graph.models.entry;


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
public class EdgeEntry {
    @Id
    private String uuid;

    private String sourceId;

    private String destionantionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EdgeEntry edgeEntry = (EdgeEntry) o;
        return uuid != null && Objects.equals(uuid, edgeEntry.uuid);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
