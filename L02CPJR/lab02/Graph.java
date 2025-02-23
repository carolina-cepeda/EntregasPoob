import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Graph {
    private ArrayList<String> vertices;
    private ArrayList<ArrayList<String>> edges;

    /**
     * Constructor que inicializa el grafo con un conjunto de vértices y aristas.
     * Convierte todos los nombres de vértices a mayúsculas y elimina duplicados.
     *
     * @param vertices Lista de vértices
     * @param edges Matriz de aristas, donde cada arista es un par de vértices
     */
    public Graph(String[] vertices, String[][] edges) {
        this.vertices = new ArrayList<>();

        for (String vertex : vertices) {
            String v = vertex.toUpperCase();
            if (!this.vertices.contains(v)) {
                this.vertices.add(v);
            }
        }

        for (String[] edge : edges) {
            for (int i = 0; i < edge.length; i++) {
                edge[i] = edge[i].toUpperCase();
            }
        }

        this.edges = new ArrayList<>();
        for (String[] edge : edges) {
            if (edge.length == 2 && this.vertices.contains(edge[0]) && this.vertices.contains(edge[1])) {
                ArrayList<String> newEdge = new ArrayList<>(Arrays.asList(edge));
                if (!containsEdge(this.edges, newEdge)) {
                    this.edges.add(newEdge);
                }
            }
        }
    }

    /**
     * * Se hizo una sobre escritura de constructor para ser utilizado en el metodo de Union.
     * Se utilizo programacion funcional
     * Constructor que recibe listas de vértices y aristas.
     * Convierte los nombres a mayúsculas y elimina duplicados.
     *
     * @param vertices Lista de vértices
     * @param edges Lista de aristas
     */
    public Graph(ArrayList<String> vertices, ArrayList<ArrayList<String>> edges) {
        this.vertices = vertices.stream().map((String s) -> s.toUpperCase())
        .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<ArrayList<String>> newEdges = new ArrayList<ArrayList<String>>();
        for (ArrayList<String> edge : edges) {
            newEdges.add(
                edge.stream().map((String s) -> s.toUpperCase()).collect(Collectors.toCollection(ArrayList::new)));
        }
        this.edges = newEdges;
    }

    public Graph path(String start, String end) {
        return null;
    }

    private ArrayList<String> getNeighbors(String vertex) {
        return null;

    }

    /**
     * Calcula la unión de este grafo con otro grafo.
     *
     * @param g Grafo con el cual se quiere unir
     * @return Nuevo grafo que representa la unión de ambos
     */
    public Graph union(Graph g) {
        ArrayList<String> unionVertices = new ArrayList<>();
        // union vertices sin repertir
        for (String vertice : this.vertices) {
            unionVertices.add(vertice);
        }

        for (String vertice : g.vertices) {
            if (!this.vertices.contains(vertice)) {
                unionVertices.add(vertice);
            }
        }
        // unir aristas sin duplicar
        ArrayList<ArrayList<String>> unionEdges = new ArrayList<>();
        for (ArrayList<String> edge : this.edges) {
            unionEdges.add(edge);
        }
        for (ArrayList<String> edge : g.edges) {
            if (!this.edges.contains(edge)) {
                unionEdges.add(edge);
            }
        }
        return new Graph(unionVertices, unionEdges);
    }

    /**
     * Verifica si una lista de aristas contiene una arista específica.
     *
     * @param edgeList Lista de aristas
     * @param edge Arista a verificar
     * @return true si la arista ya existe en la lista, false en caso contrario
     */
    private boolean containsEdge(ArrayList<ArrayList<String>> edgeList, ArrayList<String> edge) {
        for (ArrayList<String> e : edgeList) {
            if (e.size() == edge.size() && e.containsAll(edge) && edge.containsAll(e)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna la cantidad de vertices del grafo
     */
    public int vertices() {
        return vertices.size();
    }

    /**
     * Retorna la cantidad de aristas del grafo
     */
    public int edges() {
        return edges.size();
    }

    /**
     * Verifica si dos grafos son iguales comparando vértices y aristas.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Graph)) {
            return false;
        }
        if ((this.vertices == ((Graph) o).vertices) && (this.edges == ((Graph) o).edges)) {
            return true;
        }
        return false;
    }

    // Only arcs in space-separated tuples. The vertices are capitalized. The edges
    // must always be in alphabetical order.
    // For example, (A, B) (C, D)
    public static void main(String[] args) {
        // Grafo de ejemplo
        String[] vertices = { "A", "B", "C", "D", "E" };
        String[][] edges = { { "A", "B" }, { "B", "C" }, { "C", "D" }, { "D", "E" }, { "B", "D" } };
        String[] vertices1 = { "A", "B", "C", "D", "E", "f" };
        String[][] edges1 = { { "A", "B" }, { "B", "C" }, { "C", "D" }, { "D", "E" }, { "B", "D" }, { "f", "a" } };

        Graph graph = new Graph(vertices, edges);
        System.out.println("Grafo original:");
        System.out.println(graph);
        Graph graph2 = new Graph(vertices1, edges1);
        System.out.println("Grafo original:");
        System.out.println(graph2);
        System.out.println(graph2.union(graph));

        // Obtener un camino desde A hasta E
        // Graph pathGraph = graph.path("A", "E");
        // if (pathGraph != null) {
        // System.out.println("\nCamino encontrado desde A hasta E:");
        // System.out.println(pathGraph);
        // } else {
        // System.out.println("\nNo se encontró camino desde A hasta E.");
        // }
    }

    /**
     * Retorna una representación en cadena del grafo, se usa @Override ya que el método toString(),
     * se esta sobrescribiendo el método toString() de la clase base Object de java.
     *
     * @return Una cadena que representa las aristas del grafo en el formato (A, B) (C, D) ...
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = edges.size() - 1; i >= 0; i--) {
            sb.append("(").append(edges.get(i).get(0)).append(", ").append(edges.get(i).get(1)).append(") ");
            if (i < edges.size() - 1) {
                sb.append(" ");
            }
        }
        return sb.toString().strip();
    }

    // Adicionales

    /**
     * Función que inserta un arco entre dos vertices al conjunto de arcos
     * del grafo.
     * 
     * @param String vertice a , String vertice b
     * @return void
     */
    public void insertarArco(String a, String b) {
        this.edges.add(new ArrayList<>(Arrays.asList(a, b)));
    }

    /**
     * Función que elimina un arco entre dos vertices del conjunto de arcos
     * del grafo.
     * 
     * @param String vertice a , String vertice b
     * @return void
     */
    public void eliminarArco(String a, String b) {
        for (int i = 0; i < edges.size(); i++) {
            ArrayList<String> sublista = edges.get(i);
            if (sublista.equals(Arrays.asList(a, b)) || sublista.equals(Arrays.asList(b, a))) {
                edges.remove(i);
                i--;
            }
        }
    }

    /**
     * Función que verifica si un vértice dado es parte de
     * el conjunto de vertices del grafo
     * 
     * @param String vertices
     * @return boolean
     */
    public boolean enElGrafo(String vertice) {
        return vertices.contains(vertice);
    }

    /**
     * Función que retorna los vertices del grafo
     * 
     * @return ArrayList<String>
     */
    public ArrayList<String> losVertices() {
        return this.vertices;
    }

    /**
     * Función que retorna los arcos del grafo
     * 
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> losEdges() {
        return this.edges;
    }

    /**
     * Método que calcula la junta de dos grafos.
     * @return un grafo que es el resultado de la junta entre el grafo actual y el grafo g
     */
    public Graph join(Graph g) {
        ArrayList <String> verticesJoin = new ArrayList <>();
        ArrayList<ArrayList<String>> arcosJoin = new ArrayList <>();

        for (String vertice : this.vertices){
            if (!g.losVertices().contains(vertice)){
                verticesJoin.add(vertice);
            }

        }

        for ( ArrayList <String> edge: this.edges){
            if (!g.losEdges().contains(edge)){
                arcosJoin.add(edge);
            }
        }
        
        for (String ver1 : this.vertices){
            for (String ver2 : g.losVertices()){
              if (!this.vertices.contains(ver2)){
                ArrayList<String> nuevoArc = new ArrayList <>();
                nuevoArc.add(ver1);
                nuevoArc.add(ver2);
                arcosJoin.add(nuevoArc);
                }  
            }
        }

        return new Graph (verticesJoin,arcosJoin);
    }

    /**
     * Método que calcula la diferencia de dos grafos.
     *     * @return un grafo que es el resultado de la diferencia entre el grafo actual y el grafo g
     */
    public Graph difference(Graph g) {
        ArrayList <String> verticesDif = new ArrayList <>();
        ArrayList<ArrayList<String>> arcosDif = new ArrayList <>();

        for (String vertice : this.vertices){
            if (!g.losVertices().contains(vertice)){
                verticesDif.add(vertice);
            }

        }

        for ( ArrayList <String> edge: this.edges){
            if (!g.losEdges().contains(edge)){
                arcosDif.add(edge);
            }
        }

        return new Graph (verticesDif,arcosDif);
    }

    /**
     * Método que calcula la intersección de dos grafos.
     *     * @return un grafo que es el resultado de la intersección entre el grafo actual y el grafo g
     */
    public Graph intersection(Graph g) {
        ArrayList<String> verComunes = new ArrayList<>();
        ArrayList<ArrayList<String>> arComunes = new ArrayList<>();

        for (String vertice : this.vertices) {
            if (g.losVertices().contains(vertice)) {
                verComunes.add(vertice);
            }
        }

        for (ArrayList<String> edge : this.edges) {
            if (g.losEdges().contains(edge)) {
                arComunes.add(edge);
            }
        }
        return new Graph(verComunes, arComunes);
    }

}