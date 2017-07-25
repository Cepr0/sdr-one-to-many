package io.github.cepr0.onetomany.bidi;

import io.github.cepr0.onetomany.BaseEntity;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static javax.persistence.CascadeType.MERGE;

/**
 * @author Cepro
 * @since 2017-07-23
 */
@NoArgsConstructor
@AllArgsConstructor
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
        List<Slave> slaveList = asList(slaves);
        // slaveList.forEach(slave -> slave.setMaster(this));
        this.slaves.addAll(slaveList);
    }
    
    // public void setSlaves(List<Slave> slaves) {
    //     slaves.forEach(slave -> slave.setMaster(this)); // link new slaves to this master
    //     this.slaves.forEach(slave -> slave.setMaster(null)); // unlink prev slaves
    //     this.slaves.clear();
    //     this.slaves.addAll(slaves);
    // }
    
    @PrePersist
    @PreUpdate
    private void preSave() {
        slaves.forEach(slave -> slave.setMaster(this));
    }
    
    @RepositoryRestResource
    public interface Repo extends JpaRepository<Master, Long> {
    }
}
