package domain;

import domain.Plan15Exception;
import java.util.ArrayList;

public class Core extends Unit {

    private int inPersonPercentage;
    private ArrayList<Course> courses;

    /**
     * Constructs a new core unit
     * 
     * @param code
     * @param name
     * @param credits
     */
    public Core(String code, String name, int inPersonPercentage) {
        super(code, name);
        this.inPersonPercentage = inPersonPercentage;
        courses = new ArrayList<Course>();
    }

    /**
     * Add a new course
     * 
     * @param s
     */
    public void addCourse(Course c) {
        courses.add(c);
    }

    /*
     * Sums the credits of the courses that the core has
     * 
     * @return int totalCreditos
     * 
     * @throws Plan15Exception IMPOSSIBLE, if there are no courses
     */
    @Override
    public int credits() throws Plan15Exception {
        if (courses.isEmpty()) {
            throw new Plan15Exception(Plan15Exception.IMPOSSIBLE);
        }
        int totalCreditos = 0;
        for (Course c : courses) {
            totalCreditos += c.credits();
        }
        return totalCreditos;

    };

    @Override
    public int inPerson() throws Plan15Exception {
        return 0;
    };

    /**
     * Calculate the actual or estimated credits
     * If necessary (unknown or error), assumes the number of credits is 3
     * is equal to the in-person hours.
     * 
     * @return int suma
     * @throws Plan15Exception IMPOSSIBLE, If there are no credits
     */
    public int creditsEstimated() throws Plan15Exception {
        int suma = 0;
        if (courses.isEmpty()) {
            throw new Plan15Exception(Plan15Exception.IMPOSSIBLE);
        }
        for (Course c : courses) {

            try {
                suma += c.credits();
            } catch (Plan15Exception e) {
                suma += 3;
            }
        }

        return suma;
    }

    /**
     * Calculate the estimated in-person hours, considering courses that do not have
     * credit issues.
     * If the hours of a course are not known, calculate the course in-person hours
     * using the percentage suggested in the unit core.
     * If the hours of a course are incorrect, it is assumed that all the time is in
     * person.
     * 
     * @return int horas
     * @throws Plan15Exception IMPOSSIBLE, If there are no courses or all courses
     *                         has credit issues
     */
    public int inPersonEstimated() throws Plan15Exception {
        int horas = 0;
        boolean cursoValido = false;

        if (courses.isEmpty()) {
            throw new Plan15Exception(Plan15Exception.IMPOSSIBLE);
        }
        for (Course c : courses) {
            try {
                horas += inPerson();
                cursoValido = true;

            } catch (Plan15Exception e) {
                if (e.getMessage().equals(Plan15Exception.IN_PERSON_UNKNOWN)) {
                    horas += c.credits() * inPersonPercentage;
                    cursoValido = true;
                }

                if (e.getMessage().equals(Plan15Exception.IN_PERSON_ERROR)) {
                    horas += c.credits();
                    cursoValido = true;
                }
            }
        }
        if (!cursoValido) {
            throw new Plan15Exception(Plan15Exception.IMPOSSIBLE);
        }
        return horas;

    }

    @Override
    public String data() throws Plan15Exception {
        StringBuffer answer = new StringBuffer();
        answer.append(code + ": " + name + ". [" + inPersonPercentage + "%]");
        for (Course c : courses) {
            answer.append("\n\t" + c.data());
        }
        return answer.toString();
    }

}
