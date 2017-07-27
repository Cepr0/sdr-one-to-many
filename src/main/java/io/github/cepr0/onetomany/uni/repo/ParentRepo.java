package io.github.cepr0.onetomany.uni.repo;

import io.github.cepr0.onetomany.uni.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Cepro
 *         2017-07-27
 */
@RepositoryRestResource
public interface ParentRepo extends JpaRepository<Parent, Long> {
}
