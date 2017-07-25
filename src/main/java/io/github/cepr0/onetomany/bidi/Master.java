package io.github.cepr0.onetomany.bidi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.MERGE;

/**
 * @author Cepro
 * @since 2017-07-23
 */
@Entity
public class Master {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy = "master", cascade = {MERGE})
    private List<Slave> slaves = new ArrayList<>();
    
    public void setSlaves(List<Slave> slaves) {
        slaves.forEach(slave -> slave.setMaster(this)); // link new slaves to this master
        this.slaves.forEach(slave -> slave.setMaster(null)); // unlink prev slaves
        this.slaves.clear();
        this.slaves.addAll(slaves);
    }
    
    public Master() {
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<Slave> getSlaves() {
        return slaves;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Master)) return false;
        
        Master master = (Master) o;
        
        if (getId() != null ? !getId().equals(master.getId()) : master.getId() != null) return false;
        if (getName() != null ? !getName().equals(master.getName()) : master.getName() != null) return false;
        return getSlaves() != null ? getSlaves().equals(master.getSlaves()) : master.getSlaves() == null;
    }
    
    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getSlaves() != null ? getSlaves().hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "Master{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
    
    @RepositoryRestResource
    public interface Repo extends JpaRepository<Master, Long> {
    }
}
