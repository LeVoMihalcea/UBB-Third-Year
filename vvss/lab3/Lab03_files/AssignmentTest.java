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

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AssignmentTest {
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
    public void testAddValidAssignment() {
        assertEquals(0, service.saveTema("tema_05", "o descriere cumsecade", 8, 7));
    }

    @Test
    public void testAddAssignmentWithDeadlineEarlierThanStartline() {
        assertEquals(1, service.saveTema("tema_06", "o descriere cumsecade", 4, 5));
    }

    @Test
    public void testAddAssignmentValid_redPath() {
        assertEquals(0, service.saveTema("tema_05", "red", 5, 4));
    }

    @Test
    public void testAddCompletlyNewAssignment_blackPath() {
        assertEquals(1, service.saveTema(String.valueOf(UUID.randomUUID()), "black", 5, 4));
    }

    @Test
    public void testAddAssignmentWithNullId_bluePath() {
        assertEquals(1, service.saveTema(null, "blue", 5, 4));
    }

    @Test
    public void testAddAssignmentWithInvalidDeadline_greenPath() {
        assertEquals(1, service.saveTema("tema_06", "green", 0, 4));
    }

    @Test
    public void testAddAssignmentWithInvalidStartline_purplePath() {
        assertEquals(1, service.saveTema("tema_06", "purple", 5, 0));
    }

    @Test
    public void testAddAssignmentWithNullDescription_yellowPath() {
        assertEquals(1, service.saveTema("tema_06", null, 5, 4));
    }
}
