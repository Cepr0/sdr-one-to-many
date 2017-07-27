package io.github.cepr0.onetomany.uni.repo;

import io.github.cepr0.onetomany.uni.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Cepro
 *         2017-07-27
 */
@RepositoryRestResource
public interface ChildRepo extends JpaRepository<Child, Long> {
}
