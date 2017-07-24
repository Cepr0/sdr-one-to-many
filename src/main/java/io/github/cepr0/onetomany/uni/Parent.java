package io.github.cepr0.onetomany.model.uni;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.*;

/**
 * @author Cepro
 * @since 2017-07-24
 */
@Entity
public class Parent {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    
    @OneToMany(cascade = {ALL})
    private Set<Child> children = new HashSet<>();
    
    public Parent() {
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
    
    public Set<Child> getChildren() {
        return children;
    }
    
    public void setChildren(Set<Child> children) {
        this.children = children;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parent)) return false;
        
        Parent parent = (Parent) o;
        
        if (getId() != null ? !getId().equals(parent.getId()) : parent.getId() != null) return false;
        if (getName() != null ? !getName().equals(parent.getName()) : parent.getName() != null) return false;
        return getChildren() != null ? getChildren().equals(parent.getChildren()) : parent.getChildren() == null;
    }
    
    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getChildren() != null ? getChildren().hashCode() : 0);
        return result;
    }
    
    
    @Override
    public String toString() {
        return "Parent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
    
    @RepositoryRestResource
    public interface Repo extends JpaRepository<Parent, Long> {
    }
}
