package co.edu.uniandes.dse.parcialejemplo.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class EspecialidadEntity extends BaseEntity{
    
    private String nombre;
    private String descripcion;

    @PodamExclude
    @ManyToMany
    private List<MedicoEntity> medicos = new ArrayList<>();

}
