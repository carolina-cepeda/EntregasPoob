package tests;
import dominio.*;
import java.util.Scanner;

public class Main {

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            Juego juego = new Juego();
            boolean seguirJugando = true;
    
            while (seguirJugando) {

                System.out.println("=== Bienvenido al juego Pokémon ===");
                System.out.println("Selecciona un modo de juego:");
                System.out.println("1. Modo Normal");
                System.out.println("2. Modo Supervivencia");
                System.out.println("3. Salir");
    
                System.out.print("Opción: ");
                int modo = scanner.nextInt();
    
                switch (modo) {
                    case 1 -> {
                        // Modo normal
                        System.out.println("\nSelecciona un submodo:");
                        System.out.println("1. Player vs Player");
                        System.out.println("2. Player vs Máquina");
    
                        System.out.print("Opción: ");
                        int submodo = scanner.nextInt();
    
                        if (submodo == 1) {
                            juego.seleccionarModoJuego(new Normal());
                        } else if (submodo == 2) {
                            System.out.println("\n[Modo Player vs Máquina en construcción]");
                        } else {
                            System.out.println("\nOpción inválida.");
                        }
                    }
                    case 2 -> {
                        System.out.println("\n[Modo Supervivencia en construcción]");
                    }
                    case 3 -> {
                        System.out.println("¡Gracias por jugar! Saliendo...");
                        seguirJugando = false;
                    }
                    default -> System.out.println("\nOpción inválida.");
                }
    
                if (seguirJugando) {
                    System.out.print("\n¿Quieres jugar otra vez? (s/n): ");
                    char respuesta = scanner.next().charAt(0);
                    if (respuesta != 's' && respuesta != 'S') {
                        seguirJugando = false;
                        System.out.println("¡Gracias por jugar! Saliendo...");
                    }
                }
            }
    
            scanner.close();
        }
    }

    

