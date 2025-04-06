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
        try{
            p.addCourse("PRI2", "Proyecto Integrador 2", "3", "3");
        }

        catch(Plan15Exception e){
            System.out.println(e.getMessage());
        }
        assertEquals("Proyecto Integrador 2", p.consult("PRI2").name());
        assertEquals(5,p.numberUnits());
    }

    @Test
    public void shouldAddCorePlan15(){
        Plan15 p = new Plan15();
        try{
            p.addCourse("PRI2", "Proyecto Integrador 2", "3", "3");
            p.addCore("NFCC", "Nucleo de Formacion Comun por Campo", "50", "PRI2");
        }
        catch(Plan15Exception e){
            System.out.println(e.getMessage());
        }
        assertEquals("Nucleo de Formacion Comun por Campo", p.consult("NFCC").name());
        assertEquals(6,p.numberUnits());
    }

    @Test
public void shouldListToString() {
    Plan15 p = new Plan15();
    try {
        p.addCourse("PRI2", "Proyecto Integrador 2", "3", "3");
        p.addCore("NFCC", "Nucleo de Formacion Comun por Campo", "50", "PRI2");
    } catch (Plan15Exception e) {
        System.out.println(e.getMessage());
    }

    String result= p.toString();

    assertTrue(result.contains("PRI2: Proyecto Integrador 2. Creditos:3[3+6]"));
    assertTrue(result.contains("NFCC: Nucleo de Formacion Comun por Campo. [50%]"));
    assertTrue(result.contains("\tPRI2: Proyecto Integrador 2. Creditos:3[3+6]"));
    assertTrue(result.contains("PRI1: Proyecto Integrador. Creditos:9[3+24]"));
    assertTrue(result.contains("DDYA: Diseño de Datos y Algoritmos. Creditos:4[4+8]"));
    assertTrue(result.contains("MPIN: Matematicas para Informatica. Creditos:3[4+5]"));
    assertTrue(result.contains("FCC: Nucleo formacion por comun por campo. [50%]"));

    assertTrue(result.contains("6 unidades"));
}

    @Test
    public void shouldThrowExceptionCoreNameError(){
        Plan15 p = new Plan15();
        try {
            p.addCore("NFCC", "", "50", "PRI2");
            fail("Did not throw exception");
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.NAME_ERROR, e.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionCourseNameError(){
        Plan15 p = new Plan15();
        try {
            p.addCourse("PRI2", "", "3", "3");
            fail("Did not throw exception");
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.NAME_ERROR, e.getMessage());
        }
    }

    @Test
    public void shoultThrowExceptionIfCreditsAreNotInt(){
        Plan15 p = new Plan15();
        try {
            p.addCourse("PRI2", "Proyecto Integrador 2", "3.5", "3");
            fail("Did not throw exception");
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.CREDITS_ERROR, e.getMessage());
        }
    }

    @Test
    public void shoultThrowExceptionIfInPersonAreNotInt(){
        Plan15 p = new Plan15();
        try {
            p.addCourse("PRI2", "Proyecto Integrador 2", "3", "3.5");
            fail("Did not throw exception");
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.CREDITS_ERROR ,e.getMessage());
        }
    }

    @Test 
    public void ShouldThrowExceptionIfPercentageIsNotInt(){
        Plan15 p = new Plan15();
        try {
            p.addCore("NFCC", "Nucleo de Formacion Comun por Campo", "50.5", "PRI2");
            fail("Did not throw exception");
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.PERCENTAGE_ERROR, e.getMessage());
        }
    }

    @Test 
    public void shouldThrowExceptionIfPercentageGreaterThan100(){
        Plan15 p = new Plan15();
        try {
            p.addCore("NFCC", "Nucleo de Formacion", "150", "PRI");
            fail("Did not throw exception");
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.PERCENTAGE_ERROR, e.getMessage());
        }
    }

    @Test 
    public void shouldThrowExceptionIfPercentageLessThan0(){
        Plan15 p = new Plan15();
        try {
            p.addCore("NFCC", "Nucleo de Formacion Comun", "-50", "PRI2");
            fail("Did not throw exception");
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.PERCENTAGE_ERROR, e.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionCoreNameAlreadyExists(){
        Plan15 p = new Plan15();
        try {
            p.addCore("NFCC", "Nucleo de Formacion Comun por Campo", "50", "PRI2");
            p.addCore("NFCC", "Nucleo de Formacion Comun por Campo", "50", "PRI2");
            fail("Did not throw exception");
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.NAME_ALREADY_EXISTS, e.getMessage());
        }
    }

    @Test 
    public void shouldThrowExceptionIfCourseNameAlreadyExists(){
        Plan15 p = new Plan15();
        try {
            p.addCourse("PRI2", "Proyecto Integrador 2", "3", "3");
            p.addCourse("PRI2", "Proyecto Integrador 2", "3", "3");
            fail("Did not throw exception");
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.NAME_ALREADY_EXISTS, e.getMessage());
        }
    }

    @Test
    public void shouldSearchCourseByName(){
        Plan15 p = new Plan15();
        try {
            p.addCourse("PRI2", "Proyecto Integrador 2", "3", "3");
            p.addCore("NFCC", "Nucleo de Formacion Comun por Campo", "50", "PRI2");
        } 
        catch (Plan15Exception e) {
            System.out.println(e.getMessage());
        }
        String result = p.search("PRI2");
        assertTrue(result.contains("PRI2: Proyecto Integrador 2. Creditos:3[3+6]"));
    }

    @Test
    public void shouldSearchCoreByName(){
        Plan15 p = new Plan15();
        try {
            p.addCourse("PRI2", "Proyecto Integrador 2", "3", "3");
            p.addCore("NFCC", "Nucleo de Formacion Comun por Campo", "50", "PRI2");
        } 
        catch (Plan15Exception e) {
            System.out.println(e.getMessage());
        }
        String result = p.search("NFCC");
        assertTrue(result.contains("NFCC: Nucleo de Formacion Comun por Campo. [50%]"));
    }
}
