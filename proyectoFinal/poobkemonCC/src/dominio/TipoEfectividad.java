package dominio;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que representa la efectividad de los movimientos de un Pokémon
 * contra otros tipos de Pokémon. Contiene un mapa que relaciona los tipos de ataque
  * 
  */
  public class TipoEfectividad {
      private static final Map<String, Map<String, Double>> efectividades = new HashMap<>();
  
      static {
          add("ACERO", Map.of(
              "ACERO", 0.5, "AGUA", 0.5,"ELECTRICO", 0.5,"FUEGO", 0.5, "HADA", 2.0, "HIELO", 2.0, "ROCA", 2.0 
          ));
          
          add("AGUA", Map.of(
              "AGUA", 0.5, "DRAGON", 0.5, "FUEGO", 2.0, "PLANTA", 0.5, "ROCA", 2.0, "TIERRA", 2.0
          ));

          add("BICHO", Map.of(
              "ACERO", 0.5,"FANTASMA", 0.5, "FUEGO", 0.5, "HADA", 0.5,"LUCHA", 0.5,"PLANTA", 2.0, "PSIQUICO", 2.0,"SINIESTRO", 2.0, "VENENO", 0.5, "VOLADOR", 0.5
          ));

          add("DRAGON", Map.of(
              "ACERO", 0.5, "DRAGON", 2.0, "HADA", 0.0
          ));

          add("ELECTRICO", Map.of(
              "AGUA", 2.0,"DRAGON", 0.5, "ELECTRICO", 0.5, "PLANTA", 0.5, "TIERRA", 0.0, "VOLADOR", 2.0
          ));


          //valores al azar, arreglar
          add("FANTASMA", Map.of(
              "FANTASMA", 2.0, "NORMAL", 0.0, "PSIQUICO", 2.0, "SINIESTRO", 0.5
          ));
          add("FUEGO", Map.of(
              "ACERO", 2.0, "AGUA", 0.5, "BICHO", 2.0, "DRAGON", 0.5, "FUEGO", 0.5, "HIELO", 2.0, "PLANTA", 2.0, "ROCA", 0.5
          ));
          add("HADA", Map.of(
              "DRAGON", 2.0, "FUEGO", 0.5, "LUCHA", 2.0, "VENENO", 0.5, "SINIESTRO", 2.0, "ACERO", 0.5
          ));
          add("HIELO", Map.of(
              "DRAGON", 2.0, "PLANTA", 2.0, "TIERRA", 2.0, "VOLADOR", 2.0, "AGUA", 0.5, "ACERO", 0.5, "FUEGO", 0.5, "HIELO", 0.5
          ));
          add("LUCHA", Map.of(
              "ACERO", 2.0, "HIELO", 2.0, "NORMAL", 2.0, "ROCA", 2.0, "SINIESTRO", 2.0, "BICHO", 0.5, "HADA", 0.5, "VENENO", 0.5,
              "PSIQUICO", 0.5, "VOLADOR", 0.5
          ));
          add("NORMAL", Map.of(
              "ROCA", 0.5, "ACERO", 0.5, "FANTASMA", 0.0
          ));
          add("PLANTA", Map.of(
              "AGUA", 2.0, "ROCA", 2.0, "TIERRA", 2.0, "BICHO", 0.5, "DRAGON", 0.5, "FUEGO", 0.5, "PLANTA", 0.5,
              "VENENO", 0.5, "VOLADOR", 0.5, "ACERO", 0.5
          ));
          add("PSIQUICO", Map.of(
              "LUCHA", 2.0, "VENENO", 2.0, "ACERO", 0.5, "PSIQUICO", 0.5, "SINIESTRO", 0.0
          ));
          add("ROCA", Map.of(
              "BICHO", 2.0, "FUEGO", 2.0, "HIELO", 2.0, "VOLADOR", 2.0, "LUCHA", 0.5, "ACERO", 0.5, "TIERRA", 0.5
          ));
          add("SINIESTRO", Map.of(
              "FANTASMA", 2.0, "PSIQUICO", 2.0, "SINIESTRO", 0.5, "HADA", 0.5, "LUCHA", 0.5
          ));
          add("TIERRA", Map.of(
              "ACERO", 2.0, "FUEGO", 2.0, "ELECTRICO", 2.0, "ROCA", 2.0, "VENENO", 2.0, "BICHO", 0.5, "PLANTA", 0.5,
              "VOLADOR", 0.0
          ));
          add("VENENO", Map.of(
              "HADA", 2.0, "PLANTA", 2.0, "FANTASMA", 0.5, "ROCA", 0.5, "TIERRA", 0.5, "VENENO", 0.5, "ACERO", 0.0
          ));
          add("VOLADOR", Map.of(
              "BICHO", 2.0, "LUCHA", 2.0, "PLANTA", 2.0, "ELECTRICO", 0.5, "ROCA", 0.5, "ACERO", 0.5
          ));
      }
  
      private static void add(String tipo, Map<String, Double> efectividadesTipo) {
          efectividades.put(tipo, new HashMap<>(efectividadesTipo));
      }
  
      /**
       * metodo para obtener el multiplicador de efectividad entre dos tipos
       * @param tipoAtaque
       * @param tipoObjetivo
       * @return
       */
      public static double getMultiplicador(String tipoAtaque, String tipoObjetivo) {
          return efectividades
              .getOrDefault(tipoAtaque.toUpperCase(), Collections.emptyMap())
              .getOrDefault(tipoObjetivo.toUpperCase(), 1.0);
      }
  }
  