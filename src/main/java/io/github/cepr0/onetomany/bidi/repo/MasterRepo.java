package io.github.cepr0.onetomany.bidi.repo;

import io.github.cepr0.onetomany.bidi.Master;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * @author Cepro
 *         2017-07-27
 */
@RepositoryRestResource
public interface MasterRepo extends JpaRepository<Master, Long> {

    @RestResource(exported = false)
    Master findByName(String name);

    Long countBySlaves_Master(Master master);

    Long countBySlaves_MasterId(Long id);
}
