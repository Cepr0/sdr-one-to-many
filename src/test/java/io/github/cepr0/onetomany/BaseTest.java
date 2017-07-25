package io.github.cepr0.onetomany;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public abstract class BaseTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> Set<T> asSet(T... ts) {
        return new HashSet<T>(asList(ts));
    }
}
