package io.github.cepr0.onetomany.bidi;

import io.github.cepr0.onetomany.BaseEntity;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

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
@EqualsAndHashCode(callSuper = false)
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
        return addSlaves(slaves);
    }
    
    public Master addSlaves(List<Slave> slaves) {
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

    public Master addSlaves(Slave... slaves) {
        return addSlaves(asList(slaves));
    }
    
    public Master removeSlaves(Slave... slaves) {
        return removeSlaves(asList(slaves));
    }
    
    private Master removeSlaves(List<Slave> slaves) {
        
        ArrayList<Slave> tempList = new ArrayList<>(slaves);
        tempList.retainAll(this.slaves);
        cleanSlaves(tempList);
        return this;
    }
    
    public Master removeSlaves() {
        
        ArrayList<Slave> tempList = new ArrayList<>(this.slaves);
        // We cannot use loop over the 'this.slaves' collection because every invocation of `slave.setMaster(null)
        // leads to removing a `slave` from this collection
        cleanSlaves(tempList);
        return this;
    }
    
    private void cleanSlaves(ArrayList<Slave> tempList) {
        tempList.forEach(slave -> slave.setMaster(null));
        this.slaves.clear();
    }

    @RepositoryRestResource
    public interface Repo extends JpaRepository<Master, Long> {
        @RestResource(exported = false)
        Master findByName(String name);
        
        Long countBySlaves_Master(Master master);
    
        Long countBySlaves_MasterId(Long id);
    }
}
