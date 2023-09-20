package co.edu.uniandes.dse.parcialejemplo.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.EspecialidadRepository;
import co.edu.uniandes.dse.parcialejemplo.repositories.MedicoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoEspecialidadService {
    
    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    EspecialidadRepository especialidadRepository;


    @Transactional
    public EspecialidadEntity addEspecialidad(Long medicoId, Long especialidadId) throws EntityNotFoundException{
        log.info("Inicio de proceso de adicion de una especialidad a un medico con id = {0}", medicoId);
        Optional<EspecialidadEntity> especialidadEntity = especialidadRepository.findById(especialidadId);
        if (especialidadEntity.isEmpty()) 
            throw new EntityNotFoundException("No se encontro la especialidad");

        Optional<MedicoEntity> medicoEntity = medicoRepository.findById(medicoId);
        if (medicoEntity.isEmpty()) 
            throw new EntityNotFoundException("Medico no encontrado");

        medicoEntity.get().getEspecialidades().add(especialidadEntity.get());
        log.info("Finalizacion de proceso de adicion de una especialidad ca un medico con id = {0}", medicoEntity);
        return especialidadEntity.get();
    }

    @Transactional
	public EspecialidadEntity getEspecialidad(Long medicoId, Long especialidadId)
			throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar una especialidad del medico con id = {0}", medicoId);
		Optional<EspecialidadEntity> especialidadEntity = especialidadRepository.findById(especialidadId);
		Optional<MedicoEntity> medicoEntity = medicoRepository.findById(medicoId);

		if (especialidadEntity.isEmpty())
			throw new EntityNotFoundException("Especialidad no encontrada");

		if (medicoEntity.isEmpty())
			throw new EntityNotFoundException("Medico no encontrado");
		log.info("Termina proceso de consultar una especialidad del medico con id = {0}", medicoId);
		if (!medicoEntity.get().getEspecialidades().contains(especialidadEntity.get()))
			throw new IllegalOperationException("The author is not associated to the book");
		
		return especialidadEntity.get();
	}
}
