package io.github.cepr0.onetomany.bidi;

import io.github.cepr0.onetomany.BaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * @author Cepro
 * @since 2017-07-25
 */
public class BidiTest extends BaseTest {
    
    @Autowired private Master.Repo masterRepo;
    @Autowired private Slave.Repo slaveRepo;
    
    @Before
    public void setUp() throws Exception {
    
        Master master = masterRepo.save(new Master("master1"));
        slaveRepo.save(asList(
                new Slave("slave1", master),
                new Slave("slave2", master),
                new Slave("slave3", null),
                new Slave("slave4", null)));
    }
    
    @Test
    public void read() throws Exception {
    
        List<Master> masters = masterRepo.findAll();
        assertThat(masters).hasSize(1);
    
        List<Slave> slaves = slaveRepo.findAll();
        assertThat(slaves).hasSize(4);
    }
    
    @Test
    public void updateMasterTest() throws Exception {
    
        updateMaster();
    
        List<Master> masters = masterRepo.findAll();
        Master updatedMaster = masters.get(0);
        assertThat(updatedMaster.getName()).isEqualTo("master1u");
    
        List<Slave> updatedSlaves = updatedMaster.getSlaves();
        assertThat(updatedSlaves).hasSize(2);
    
        List<String> slaveNames = updatedSlaves.stream().map(Slave::getName).collect(Collectors.toList());
        assertThat(slaveNames).containsOnly("slave3", "slave4");
    
        Slave slave1 = slaveRepo.findOne(1L);
        Slave slave2 = slaveRepo.findOne(2L);
        Slave slave3 = slaveRepo.findOne(3L);
        Slave slave4 = slaveRepo.findOne(4L);
        
        assertThat(slave1.getMaster()).isEqualTo(null);
        assertThat(slave2.getMaster()).isEqualTo(null);
        assertThat(slave3.getMaster().getId()).isEqualTo(1L);
        assertThat(slave4.getMaster().getId()).isEqualTo(1L);
    }
    
    private void updateMaster() {
        List<Master> masters = masterRepo.findAll();
        List<Slave> slaves = slaveRepo.findAll();
        
        Master master1 = masters.get(0);

        master1.setName("master1u");
        master1.removeSlaves().addSlaves(slaves.get(2), slaves.get(3));

        masterRepo.saveAndFlush(master1);
    }
}