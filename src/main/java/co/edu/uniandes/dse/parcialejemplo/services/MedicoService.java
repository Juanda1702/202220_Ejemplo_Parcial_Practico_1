package co.edu.uniandes.dse.parcialejemplo.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.MedicoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoService {
    
    @Autowired
    MedicoRepository medicoRepository;

    @Transactional
    public MedicoEntity createMedicos (MedicoEntity medico) throws  IllegalOperationException{
        log.info("Comienza creación de un medico");
        String registroMedico = medico.getRegistroMedico();
        if (registroMedico == null) 
            throw new IllegalOperationException("No tiene registro medico");
        
        if (!registroMedico.strip().startsWith("MG")) 
            throw new IllegalOperationException("El registro no comienza con MG");
        
        log.info("Finaliza creación de un medico");
        return medicoRepository.save(medico);
    }
}
