package io.github.cepr0.onetomany.model.bidi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Cepro
 * @since 2017-07-23
 */
@Entity
public class Slave {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    
    @ManyToOne
    private Master master;
    
    public Slave() {
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
    
    public Master getMaster() {
        return master;
    }
    
    public void setMaster(Master master) {
        this.master = master;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Slave)) return false;
        
        Slave slave = (Slave) o;
        
        if (getId() != null ? !getId().equals(slave.getId()) : slave.getId() != null) return false;
        if (getName() != null ? !getName().equals(slave.getName()) : slave.getName() != null) return false;
        return getMaster() != null ? getMaster().equals(slave.getMaster()) : slave.getMaster() == null;
    }
    
    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getMaster() != null ? getMaster().hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "Slave{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", master=" + master +
                '}';
    }
    
    @RepositoryRestResource
    public interface Repo extends JpaRepository<Slave, Long> {
    }
}
