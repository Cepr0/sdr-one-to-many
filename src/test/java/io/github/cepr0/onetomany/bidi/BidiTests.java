package io.github.cepr0.onetomany.bidi;

import io.github.cepr0.onetomany.BaseTest;
import io.github.cepr0.onetomany.bidi.repo.MasterRepo;
import io.github.cepr0.onetomany.bidi.repo.SlaveRepo;
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
public class BidiTests extends BaseTest {
    
    @Autowired private MasterRepo masterRepo;
    @Autowired private SlaveRepo slaveRepo;
    
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
    public void readTest() throws Exception {
    
        List<Master> masters = masterRepo.findAll();
        assertThat(masters).hasSize(1);
    
        List<Slave> slaves = slaveRepo.findAll();
        assertThat(slaves).hasSize(4);
    
        Master master1 = masterRepo.findByName("master1");
        assertThat(master1).isNotNull();
        assertThat(master1.getName()).isEqualTo("master1");
        assertThat(master1.getSlaves()).hasSize(2);
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
    
        Slave slave1 = slaveRepo.findByName("slave1");
        Slave slave2 = slaveRepo.findByName("slave2");
        Slave slave3 = slaveRepo.findByName("slave3");
        Slave slave4 = slaveRepo.findByName("slave4");
        
        assertThat(slave1.getMaster()).isEqualTo(null);
        assertThat(slave2.getMaster()).isEqualTo(null);
        assertThat(slave3.getMaster().getName()).isEqualTo("master1u");
        assertThat(slave4.getMaster().getName()).isEqualTo("master1u");
    }
    
    @Test
    public void countTest() throws Exception {
        Master master1 = masterRepo.findByName("master1");
        
        Long slavesCount = masterRepo.countBySlaves_Master(master1);
        assertThat(slavesCount).isEqualTo(2L);
    
        slavesCount = masterRepo.countBySlaves_MasterId(master1.getId());
        assertThat(slavesCount).isEqualTo(2L);
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