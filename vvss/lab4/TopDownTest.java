package ssvv.lab2;

import org.junit.Before;
import org.junit.Test;
import ssvv.lab2.domain.Nota;
import ssvv.lab2.domain.Student;
import ssvv.lab2.domain.Tema;
import ssvv.lab2.repository.NotaXMLRepository;
import ssvv.lab2.repository.StudentXMLRepository;
import ssvv.lab2.repository.TemaXMLRepository;
import ssvv.lab2.service.Service;
import ssvv.lab2.validation.NotaValidator;
import ssvv.lab2.validation.StudentValidator;
import ssvv.lab2.validation.TemaValidator;
import ssvv.lab2.validation.Validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TopDownTest {
    private Validator<Student> studentValidator;
    private Validator<Tema> temaValidator;
    private Validator<Nota> notaValidator;

    private StudentXMLRepository fileRepository1;
    private TemaXMLRepository fileRepository2 ;
    private NotaXMLRepository fileRepository3;

    private Service service;

    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Before
    public void load(){
        studentValidator = new StudentValidator();
        temaValidator = new TemaValidator();
        notaValidator = new NotaValidator();

        fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
        fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
        fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @Test
    public void testAddStudent() {
        assertEquals(0, service.saveStudent("1", "Leo", 935));
    }

    @Test
    public void testAddAssignmentIntegration() {
        assertEquals(0, service.saveStudent("2", "Amogus", 935));
        assertEquals(0, service.saveTema("2", "Kinda sus", 7, 4));
    }

    @Test
    public void testAddGradeIntegration() {
        assertEquals(0, service.saveStudent("1", "Amogus", 935));
        assertEquals(0, service.saveTema("1", "Kinda sus", 7, 4));
        assertEquals(0, service.saveNota("1","1",4.20,5, "Red is impostor"));
    }
}
