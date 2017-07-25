package io.github.cepr0.onetomany.bidi;

import io.github.cepr0.onetomany.BaseEntity;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.*;

/**
 * @author Cepro
 * @since 2017-07-23
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Slave extends BaseEntity {
    
    @Column(unique = true)
    private String name;
    
    @ManyToOne
    private Master master;
    
    @RepositoryRestResource
    public interface Repo extends JpaRepository<Slave, Long> {
    }
}
