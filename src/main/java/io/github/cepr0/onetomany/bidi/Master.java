package io.github.cepr0.onetomany.bidi;

import io.github.cepr0.onetomany.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static javax.persistence.CascadeType.MERGE;

/**
 * @author Cepro
 * @since 2017-07-23
 */
@NoArgsConstructor
@Setter
@Getter
@ToString(exclude = "slaves")
@Entity
public class Master extends BaseEntity {

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "master", cascade = {MERGE})
    private final List<Slave> slaves = new ArrayList<>();

    public Master(String name, Slave... slaves) {
        this.name = name;
        this.slaves.addAll(asList(slaves));
    }

    public Master setSlaves(List<Slave> slaves) {
        removeSlaves();
        return addSlaves(slaves.toArray(new Slave[0]));
    }

    public Master addSlaves(Slave... slaves) {
        for (Slave slave : slaves) {
            if (slave.getMaster() != this ) {
                slave.setMaster(this);
            }

            if (!this.slaves.contains(slave)) {
                this.slaves.add(slave);
            }
        }
        return this;
    }

    public Master removeSlaves() {
        ArrayList<Slave> tempList = new ArrayList<>(this.slaves);
        tempList.forEach(slave -> slave.setMaster(null));
        this.slaves.clear();
        return this;
    }

    @RepositoryRestResource
    public interface Repo extends JpaRepository<Master, Long> {
    }
}
