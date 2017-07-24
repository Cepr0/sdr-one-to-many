package io.github.cepr0.onetomany.model.uni;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Cepro
 * @since 2017-07-24
 */
@Entity
public class Child {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    
    public Child() {
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Child)) return false;
        
        Child child = (Child) o;
        
        if (getId() != null ? !getId().equals(child.getId()) : child.getId() != null) return false;
        return getName() != null ? getName().equals(child.getName()) : child.getName() == null;
    }
    
    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "Child{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
    
    @RepositoryRestResource
    public interface Repo extends JpaRepository<Child, Long> {
    }
}
