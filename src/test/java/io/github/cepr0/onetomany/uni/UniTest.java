package io.github.cepr0.onetomany.uni;

import io.github.cepr0.onetomany.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * @author Cepro
 *         2017-07-25
 */
public class UniTest extends BaseTest {

    @Autowired private Parent.Repo parentRepo;
    @Autowired private Child.Repo childRepo;

    @Test
    public void updateParent() throws Exception {

        childRepo.save(asList(
                new Child("child1"),
                new Child("child2"),
                new Child("child3"),
                new Child("child4")));
        childRepo.flush();

        List<Child> children = childRepo.findAll();
        assertThat(children).hasSize(4);

        parentRepo.saveAndFlush(new Parent("parent1", children.get(0), children.get(1)));

        List<Parent> parents = parentRepo.findAll();
        assertThat(parents).hasSize(1);

        Parent parent1 = parents.get(0);
        parent1.setName("parent1u");
        List<Child> parent1Children = parent1.getChildren();
        parent1Children.clear();
        parent1Children.addAll(asList(children.get(2), children.get(3)));
        parentRepo.saveAndFlush(parent1);

        parents = parentRepo.findAll();
        Parent updatedParent = parents.get(0);

        assertThat(updatedParent.getName()).isEqualTo("parent1u");

        Collection<Child> updatedChildren = updatedParent.getChildren();
        assertThat(updatedChildren).hasSize(2);

        List<String> childNames = updatedChildren.stream().map(Child::getName).collect(Collectors.toList());
        assertThat(childNames).containsOnly("child3", "child4");
    }
}