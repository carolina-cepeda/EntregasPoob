package domain;

import static org.junit.Assert.*;

import java.beans.Transient;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CoreTest {

    @Test
    public void shouldCalculateTheCreditsOfACoreCostume() {
        Core c = new Core("NFCC", "Nucleo de Formacion Comun por Campo", 50);
        c.addCourse(new Course("PRI1", "Proyecto Integrador 1", 3, 3));
        c.addCourse(new Course("DDYA", "Diseño de Datos y Algoritmos", 4, 4));
        c.addCourse(new Course("MPIN", "Matematicas para Informatica", 3, 4));
        try {
            assertEquals(10, c.credits());
        } catch (Plan15Exception e) {
            fail("Threw a exception");
        }
    }

    @Test
    public void shouldThrowExceptionIfCoreHasNoCourse() {
        Core c = new Core("NFCC", "Nucleo de Formacion Comun por Campo", 50);
        try {
            int price = c.credits();
            fail("Did not throw exception");
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.IMPOSSIBLE, e.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionIfThereIsErrorInCredits() {
        Core c = new Core("NFCC", "Nucleo de Formacion Comun por Campo", 50);
        c.addCourse(new Course("PRI1", "Proyecto Integrador 1", -3, 3));
        c.addCourse(new Course("DDYA", "Diseño de Datos y Algoritmos", 4, 4));
        c.addCourse(new Course("MPIN", "Matematicas para Informatica", 3, 4));
        try {
            int price = c.credits();
            fail("Did not throw exception");
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.CREDITS_ERROR, e.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionIfCreditsIsNotKnown() {
        Core c = new Core("NFCC", "Nucleo de Formacion Comun por Campo", 50);
        c.addCourse(new Course("PRI1", "Proyecto Integrador 1", 3, 3));
        c.addCourse(new Course("DDYA", "Diseño de Datos y Algoritmos", 4, 4));
        c.addCourse(new Course("MPIN", "Matematicas para Informatica"));
        try {
            int price = c.credits();
            fail("Did not throw exception");
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.CREDITS_UNKNOWN, e.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionIfNoCourses() {
        Core c = new Core("LALA", "LA PRUEBA 1", 50);
        try {
            int totalEstimado = c.creditsEstimated();
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.IMPOSSIBLE, e.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionIfCreditsIs0() {
        Core c = new Core("LALA", "LA PRUEBA 1", 50);
        c.addCourse(new Course("PRI1", "Proyecto Integrador 1", 0, 3));

        try {
            int totalEstimado = c.creditsEstimated();
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.CREDITS_ERROR, e.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionIfAllCoursesHaveCreditsIssues(){
        Core c = new Core("LALA", "LA PRUEBA 1", 50);
        c.addCourse(new Course("PRI1", "Proyecto Integrador 1", -3, 3));
        c.addCourse(new Course("DDYA", "Diseño de Datos y Algoritmos", -4, 4));
        c.addCourse(new Course("MPIN", "Matematicas para Informatica", -3, 4));

        try {
            int horas = c.inPersonEstimated();
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.IMPOSSIBLE, e.getMessage());
        }
    }

    @Test
    public void shouldinPersonEstimatedIfInPersonNormal(){
        Core c = new Core("LALA", "LA PRUEBA 1", 50);
        c.addCourse(new Course("PRI1", "Proyecto Integrador 1", 3,2));
        c.addCourse(new Course("DDYA", "Diseño de Datos y Algoritmos", 4, 4));
        c.addCourse(new Course("MPIN", "Matematicas para Informatica", 3, 4));

        try {
            int horas = c.inPersonEstimated();

            assertEquals(10,horas);
            
        } catch (Plan15Exception e) {
            fail("Threw a exception");
        }
        
    }

    @Test
    public void shouldAddCoursePlan15(){
        Plan15 p = new Plan15();
        p.addCourse("PRI2", "Proyecto Integrador 2", "3", "3");
        assertEquals("Proyecto Integrador 2", p.consult("PRI2").name());
        assertEquals(5,p.numberUnits());
    }

    @Test
    public void shouldAddCorePlan15(){
        Plan15 p = new Plan15();
        p.addCourse("PRI2", "Proyecto Integrador 2", "3", "3");
        p.addCore("NFCC", "Nucleo de Formacion Comun por Campo", "50", "PRI2");
        assertEquals("Nucleo de Formacion Comun por Campo", p.consult("NFCC").name());
        assertEquals(6,p.numberUnits());
    }

    @Test
    public void shouldListToString(){
        Plan15 p = new Plan15();
        p.addCourse("PRI2", "Proyecto Integrador 2", "3", "3");
        p.addCore("NFCC", "Nucleo de Formacion Comun por Campo", "50", "PRI2");
        String expected = "PRI1: Proyecto Integrador 1. Creditos:9\n" +
                "DDYA: Diseño de Datos y Algoritmos. Creditos:4\n" +
                "MPIN: Matematicas para Informatica. Creditos:3\n" +
                "PRI2: Proyecto Integrador 2. Creditos:3\n" +
                "NFCC: Nucleo de Formacion Comun por Campo. Creditos:50\n";
        assertEquals(expected, p.toString());
    }
}
