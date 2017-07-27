package io.github.cepr0.onetomany.uni;

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
 * @since 2017-07-24
 */
@NoArgsConstructor
@Setter
@Getter
@ToString(exclude = "children")
@Entity
public class Parent extends BaseEntity {
    
    @Column(unique = true)
    private String name;
    
    @OneToMany(cascade = {MERGE})
    private final List<Child> children = new ArrayList<>();

    public Parent(String name, Child... children) {
        this.name = name;
        this.children.addAll(asList(children));
    }

}
