package io.github.cepr0.onetomany.uni;

import io.github.cepr0.onetomany.BaseEntity;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.Entity;

/**
 * @author Cepro
 * @since 2017-07-24
 */
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Child extends BaseEntity {

    private String name;

    public Child(String name) {
        this.name = name;
    }

    @RepositoryRestResource
    public interface Repo extends JpaRepository<Child, Long> {
    }
}
