package io.github.cepr0.onetomany.bidi.repo;

import io.github.cepr0.onetomany.bidi.Slave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * @author Cepro
 *         2017-07-27
 */
@RepositoryRestResource
public interface SlaveRepo extends JpaRepository<Slave, Long> {

    @RestResource(exported = false)
    Slave findByName(String name);
}
