package tests;

import dominio.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Juego juego = new Juego();
        boolean seguirJugando = true;

        while (seguirJugando) {
            System.out.println("\n=== Bienvenido al juego Pokémon ===");
            System.out.println("Selecciona un modo de juego:");
            System.out.println("1. Modo Normal");
            System.out.println("2. Modo Supervivencia");
            System.out.println("3. Salir");

            System.out.print("Opción: ");
            int modo = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (modo) {
                case 1 -> {
                    Normal normal = new Normal();
                    juego.seleccionarModoJuego(normal);  // Esto ya llama a configurarJuego()
                    normal.configurarJuego(juego);       // Aquí se elige el submodo
                }
                case 2 -> {
                    System.out.println("\n[Modo Supervivencia en construcción]");
                }
                case 3 -> {
                    System.out.println("¡Gracias por jugar! Saliendo...");
                    seguirJugando = false;
                }
                default -> System.out.println("Opción inválida.");
            }

            if (seguirJugando) {
                System.out.print("\n¿Quieres jugar otra vez? (s/n): ");
                char respuesta = scanner.nextLine().trim().toLowerCase().charAt(0);
                if (respuesta != 's') {
                    seguirJugando = false;
                    System.out.println("¡Gracias por jugar! Saliendo...");
                }
            }
        }

        scanner.close();
    }
}
