package io.github.cepr0.onetomany.bidi;

import io.github.cepr0.onetomany.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Cepro
 * @since 2017-07-23
 */
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity
public class Slave extends BaseEntity {

    @Setter
    @Column(unique = true)
    private String name;
    
    @ManyToOne
    private Master master;

    public Slave(String name, Master master) {
        this.name = name;
        setMaster(master);
    }

    public void setMaster(Master master) {
        if(this.master != null) {
            this.master.getSlaves().remove(this);
        }

        if (master != null && !master.getSlaves().contains(this)) {
            master.getSlaves().add(this);
        }

        this.master = master;
    }

}
