package co.edu.uniandes.dse.parcialejemplo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(MedicoEspecialidadService.class)
class MedicoEspecialidadServiceTest {
    
    @Autowired
    private MedicoEspecialidadService medicoEspecialidadService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();
    
    private MedicoEntity medico = new MedicoEntity();
    private List<EspecialidadEntity> especialidadList = new ArrayList<>();

    @BeforeEach
    void setUp(){
        clearData();
        insertData();
    }

    void clearData(){
        entityManager.getEntityManager().createQuery("delete from MedicoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from EspecialidadEntity").executeUpdate();
    }

    void insertData(){

        medico = factory.manufacturePojo(MedicoEntity.class);
        entityManager.persist(medico);

        for (int i = 0; i < 3; i++) {
            EspecialidadEntity entity = factory.manufacturePojo(EspecialidadEntity.class);
            entityManager.persist(entity);
            entity.getMedicos().add(medico);
            especialidadList.add(entity);
            medico.getEspecialidades().add(entity);
        }
    }

    @Test
	void testAddEspecialidad() throws EntityNotFoundException, IllegalOperationException {
		MedicoEntity newMedico = factory.manufacturePojo(MedicoEntity.class);
		entityManager.persist(newMedico);
		
		EspecialidadEntity especialidad = factory.manufacturePojo(EspecialidadEntity.class);
		entityManager.persist(especialidad);
		
		medicoEspecialidadService.addEspecialidad(newMedico.getId(), especialidad.getId());
		
		EspecialidadEntity lastEspecialidad = medicoEspecialidadService.getEspecialidad(newMedico.getId(), especialidad.getId());
		assertEquals(especialidad.getId(), lastEspecialidad.getId());
		assertEquals(especialidad.getNombre(), lastEspecialidad.getNombre());
		assertEquals(especialidad.getDescripcion(), lastEspecialidad.getDescripcion());
	}
	
	
	@Test
	void testAddInvalidEspecialidad() {
		assertThrows(EntityNotFoundException.class, ()->{
			MedicoEntity newMedico = factory.manufacturePojo(MedicoEntity.class);
			entityManager.persist(newMedico);
			medicoEspecialidadService.addEspecialidad(newMedico.getId(), 0L);
		});
	}
	
	
	@Test
	void testAddEspecialidadInvalidMedico() throws EntityNotFoundException, IllegalOperationException {
		assertThrows(EntityNotFoundException.class, ()->{
			EspecialidadEntity especialidad = factory.manufacturePojo(EspecialidadEntity.class);
			entityManager.persist(especialidad);
			medicoEspecialidadService.addEspecialidad(0L, especialidad.getId());
		});
	}
}
