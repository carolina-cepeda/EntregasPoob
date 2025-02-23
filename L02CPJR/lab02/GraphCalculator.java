import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * GraphCalculator.java
 * 
 * @author Carolina Cepeda, Juanita Rubiano
 */
// con una sola muestra del grafo obtenemos todo el grafo.//

public class GraphCalculator {

    private TreeMap<String, Graph> variables;
    private Graph ultGrafo;
    private boolean status;
    /**
     * Constructor de calculadora de grafos
     */
    public GraphCalculator() {
        this.variables = new TreeMap<String, Graph>();
        ultGrafo = null;
        status = false;
    }

    /**
     * Método para obtener el último grafo en el TreeMap
     */
    public Graph getultGrafo() {
        if (variables.isEmpty()) {
            return null;
        }
        String llavefin = "";
        for (String llave : variables.keySet()) {
            llavefin = llave;
        }
        return variables.get(llavefin);
    }

    /**
     * Get Variables
     * 
     * @return Treemap
     */
    public TreeMap<String, Graph> getVariables() {
        return this.variables;
    }

    /**
     * Crea una nueva variable
     * 
     * @param String nombre
     * @return void
     */
    public void create(String nombre) {
        variables.put(nombre, null);
    }

    /**
     * Assign a graph to an existing variable
     * a := graph
     * 
     * @param : String, String [], String[][]
     */
    public void assign(String nombre_graph, String[] vertices, String[][] edges) {
        Graph grafo = new Graph(vertices, edges);

        variables.put(nombre_graph, grafo);

        status = true;

    }

    /**
     * 
     * Assigns the value of a binary operation to a variable
     * a = b op v*
     * The operator characters are: '+' adding a edge between two vertices, '-'
     * removing a edge between two vertex
     * '?' checking if a graph contains the given vertices
     * 'p' return the graph with a path that passes through all the vertices in the
     * indicated order
     */
    public void assignUnary(String a, String b, char op, String[] vertices) {

        ultGrafo = getultGrafo();

        switch (op) {
            case '+':
                insertarArco(ultGrafo, a, b);
                break;
            case '-':
                eliminarArco(ultGrafo, a, b);
                break;

            case '?':
                conjuntoPertenece(ultGrafo, vertices);
                break;

            case 'p':
                // mostrarCamino(ultGrafo, vertices);
                break;

            default:
                System.out.println("Operador no reconocido: " + op);
                break;

        }

    }

    /** Assigns the value of a binary operation to a variable
    a = b op c
    The operator characters are: 'u' union, 'i' intersection, 'd' difference, 'j'join
     */
    public Graph assignBinary(String a, String b, char op, String c){

        Graph bgrafo = variables.get(b);
        Graph cgrafo = variables.get(c);
        Graph agrafo = null;

        switch (op) {

            case 'u':

                agrafo = bgrafo.union(cgrafo);
                break;

            case 'i':
                agrafo = bgrafo.intersection(cgrafo);
                break;

            case 'd':                
                agrafo = bgrafo.difference(cgrafo); 
                break;

            case 'j':
                agrafo = bgrafo.join(cgrafo);
                break;

        }
        return agrafo;
    }

    /**
     * Returns the graph with the edges in uppercase in alphabetical order.
     */
    public String toString(Graph graph) {
        return graph.toString();
    }

    /**
     * Función que inserta un arco entre dos vertices al conjunto de arcos
     * del grafo dado.
     * @param Graph grafo, String vertice a , String vertice b
     * @return void
     */
    private void insertarArco(Graph grafo, String a, String b) {
        grafo.insertarArco(a, b);

    }

    /**
     * Función que elimina un arco entre dos vertices del conjunto de arcos
     * del grafo dado.
     * @param Graph grafo, String vertice a , String vertice b
     * @return void
     */
    private void eliminarArco(Graph grafo, String a, String b) {
        grafo.eliminarArco(a, b);
    }

    private boolean conjuntoPertenece(Graph grafo, String[] vertices) {
        ArrayList<String> lVertices = new ArrayList<>(Arrays.asList(vertices));

        for (int i = 0; i < lVertices.size(); i++) {
            String vertice = lVertices.get(i);
            boolean booleano = grafo.enElGrafo(vertice);
            if (booleano == false) {
                return false;
            }
        }
        return true;
    }

    /** No se lleva a cabo
     * 
     * private ArrayList<String> mostrarCamino(Graph grafo, String[] vertices){
     * 
     * ArrayList < String > caminoCompleto = new ArrayList<>();
     * ArrayList <String> listaVertices= new ArrayList <> (Arrays.asList(vertices));
     * 
     * for (int i = 0; i < listaVertices.size() -1 ; i++ ){
     * String inicio = listaVertices.get(i);
     * String destino = listaVertices.get(i+1);
     * ArrayList<String> subcamino = grafo.path(inicio, destino);
     * caminoCompleto.addAll(subcamino);
     * }
     * return caminoCompleto;
     * }
     */

    public boolean sameGraphExactly(Graph graf1,Graph graf2){
        if (graf1.vertices() != graf2.vertices()){
            return false;}

        if (!graf1.losVertices().containsAll(graf2.losVertices())){
            return false;
        }

        if (graf1.edges() != graf2.edges()){
            return false;
        }

        if (! graf1.losEdges().equals(graf2.losEdges())){
            return false;
        }        
        return true;
    }

    /**
     * Metodo que revisa q todas las operaciones se realizaron correctamente.
     * @return boolean
     */
    public boolean ok() {
        return status;
    }
}

