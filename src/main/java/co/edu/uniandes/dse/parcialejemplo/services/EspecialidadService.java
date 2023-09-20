package co.edu.uniandes.dse.parcialejemplo.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.EspecialidadRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EspecialidadService {
    
    @Autowired
    EspecialidadRepository especialidadRepository;


    @Transactional
    public EspecialidadEntity createEspecialidad(EspecialidadEntity especialidad) throws EntityNotFoundException, IllegalOperationException {
        log.info("Comienzo de creación de una especialidad");

        if (especialidad.getDescripcion() == null)
            throw new IllegalOperationException("Descripcion no tiene valor");

        if (especialidad.getDescripcion().strip().length()<10) 
            throw new IllegalOperationException("La descripcion no tiene minimo 10 caracteres");
        
        return especialidadRepository.save(especialidad);
    }
}
