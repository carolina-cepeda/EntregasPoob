package domain; 

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Plan15
 * @author POOB  
 * @version ECI 2025
 */

public class Plan15{
    private ArrayList<Unit> units;
    private TreeMap<String,Course> courses;

    /**
     * Create a Plan15
     */
    public Plan15(){
        units = new ArrayList<Unit>();
        courses = new TreeMap<String,Course>();
        addSome();
    }

    private void addSome(){
        String [][] courses = {{"PRI1", "Proyecto Integrador","9","3"},
                              {"DDYA", "Diseño de Datos y Algoritmos","4","4"},
                              {"MPIN", "Matematicas para Informatica","3","4"}};
        for (String [] c: courses){
            try{
                
            addCourse(c[0],c[1],c[2],c[3]);
        }
        catch(Plan15Exception e){
            System.out.println(e.getMessage());
        }
        }
        String [][] Core = {{"FCC","Nucleo formacion por comun por campo", "50", "PRI1\nDDYA\nMPIN"}};
        for (String [] c: Core){
            try{
            addCore(c[0],c[1],c[2],c[3]);
        }
            catch(Plan15Exception e){
            System.out.println(e.getMessage());
        }
        }
    }


    /**
     * Consult a unit
     */
    public Unit consult(String name){
        Unit c=null;
        for(int i=0;i<units.size() && c == null;i++){
            if (units.get(i).code().compareToIgnoreCase(name)==0) 
               c=units.get(i);
        }
        return c;
    }

    
    /**
     * Add a new course
     * @throws Plan15Exception 
    */
    public void addCourse(String code, String name, String credits, String inPerson)
    throws Plan15Exception{ 
        if(name.equals("")){
            throw new Plan15Exception(Plan15Exception.NAME_ERROR);
        }
        if (!esEntero(credits) || !esEntero(inPerson)) {
            throw new Plan15Exception(Plan15Exception.CREDITS_ERROR);
        }

        for (Unit u: units){
            if (u.name().equals(name)){
                throw new Plan15Exception(Plan15Exception.NAME_ALREADY_EXISTS);
            }
        }

        Course nc=new Course(code,name,Integer.parseInt(credits),Integer.parseInt(inPerson));
        units.add(nc);
        courses.put(code.toUpperCase(),nc); 
    }

    /**
     * metodo para saber si el número es entero
     * @param s
     * @return boolean
     */
    private boolean esEntero(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Add a new core
     * @throws Plan15Exception
    */
    public void addCore(String code, String name, String percentage, String theCourses)
    throws Plan15Exception{ 

        if(name.equals("")){
            throw new Plan15Exception(Plan15Exception.NAME_ERROR);
        }
        
        for (Unit u: units){
            if (u.name().equals(name)){
                throw new Plan15Exception(Plan15Exception.NAME_ALREADY_EXISTS);
            }
        }

        Core c = new Core(code,name,Integer.parseInt(percentage));
        String [] aCourses= theCourses.split("\n");
        for (String b : aCourses){
            c.addCourse(courses.get(b.toUpperCase()));
        }
        units.add(c);
    }

    /**
     * Consults the units that start with a prefix
     * @param  
     * @return 
     */
    public ArrayList<Unit> select(String prefix){
        ArrayList <Unit> answers=new ArrayList<Unit>();
        prefix=prefix.toUpperCase();
        for(int i=0;i<=units.size();i++){
            if(units.get(i).code().toUpperCase().startsWith(prefix)){
                answers.add(units.get(i));
            }   
        }
        return answers;
    }


    
    /**
     * Consult selected units
     * @param selected
     * @return  
     */
    public String data(ArrayList<Unit> selected){
        StringBuffer answer=new StringBuffer();
        answer.append(units.size()+ " unidades\n");
        for(Unit p : selected) {
            try{
                answer.append('>' + p.data());
                answer.append("\n");
            }catch(Plan15Exception e){
                answer.append("**** "+e.getMessage());
            }
        }    
        return answer.toString();
    }
    
    
     /**
     * Return the data of units with a prefix
     * @param prefix
     * @return  
     */ 
    public String search(String prefix){
        return data(select(prefix));
    }
    
    
    /**
     * Return the data of all units
     * @return  
     */    
    public String toString(){
        return data(units);
    }
    
    /**
     * Consult the number of units
     * @return 
     */
    public int numberUnits(){
        return units.size();
    }

}
