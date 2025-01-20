package org.example;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Database dbManager = new Database();
        try {
            dbManager.connect();
            dbManager.createTableIfNotExists();

            String dni;
            while (true) {
                System.out.print("Por favor, introduzca su DNI (formato: 12345678A): ");
                dni = scanner.nextLine();
                if (dni.matches("\\d{8}[A-Za-z]")) {
                    break;
                } else {
                    System.out.println("Formato de DNI no válido. Intente de nuevo.");
                }
            }

            if (!dbManager.authenticateUser(dni)) {
                System.out.println("Usuario no encontrado. Registrando nuevo usuario...");
                dbManager.registerUser(dni);
            }

            double saldo = 0.0;
            while (true) {
                System.out.println("\n¿Qué desea realizar?");
                System.out.println("1. Gastos");
                System.out.println("2. Ingresos");
                System.out.println("3. Saldo Actual");
                System.out.println("4. Salir");
                System.out.print("Seleccione una opción: ");
                int opcion = scanner.nextInt();

                switch (opcion) {
                    case 1 -> saldo = registrarGasto(saldo);
                    case 2 -> saldo = registrarIngreso(saldo);
                    case 3 -> System.out.printf("Su saldo actual es: %.2f€%n", saldo);
                    case 4 -> {
                        System.out.println("¡Gracias por usar el programa!");
                        return;
                    }
                    default -> System.out.println("Opción no válida. Intente de nuevo.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbManager.disconnect();
        }
    }

    public static double registrarGasto(double saldo) {
        System.out.println("Seleccione el concepto de gasto:");
        System.out.println("1. Vacaciones");
        System.out.println("2. Alquiler");
        System.out.println("3. IRPF (15% de su nómina)");
        System.out.println("4. Vicios variados");
        System.out.print("Ingrese su opción: ");
        int opcion = scanner.nextInt();

        double monto = 0.0;
        switch (opcion) {
            case 1 -> {
                System.out.print("Ingrese el gasto en vacaciones: ");
                monto = scanner.nextDouble();
                System.out.printf("Se ha realizado un gasto de %.2f€ en Vacaciones.%n", monto);
            }
            case 2 -> {
                System.out.print("Ingrese el gasto en Alquiler: ");
                monto = scanner.nextDouble();
                System.out.printf("Se ha realizado un gasto de %.2f€ en Alquiler.%n", monto);
            }
            case 3 -> {
                System.out.print("Ingrese su nómina mensual para calcular IRPF: ");
                double nomina = scanner.nextDouble();
                monto = nomina * 0.15;
                System.out.printf("Se ha realizado un gasto de %.2f€ en IRPF.%n", monto);
            }
            case 4 -> {
                System.out.print("Ingrese el gasto en Vicios variados: ");
                monto = scanner.nextDouble();
                System.out.printf("Se ha realizado un gasto de %.2f€ en Vicios variados.%n", monto);
            }
            default -> System.out.println("Opción no válida.");
        }
        saldo -= monto;
        System.out.printf("Su saldo final es: %.2f€%n", saldo);
        return saldo;
    }

    public static double registrarIngreso(double saldo) {
        System.out.println("Seleccione el concepto de ingreso:");
        System.out.println("1. Nómina");
        System.out.println("2. Venta en páginas de segunda mano");
        System.out.print("Ingrese su opción: ");
        int opcion = scanner.nextInt();

        double monto = 0.0;
        switch (opcion) {
            case 1 -> {
                System.out.print("Ingrese su Nómina: ");
                monto = scanner.nextDouble();
                System.out.printf("Se ha realizado un ingreso de %.2f€ por Nómina.%n", monto);
            }
            case 2 -> {
                System.out.print("Ingrese ganancias en páginas de segunda mano: ");
                monto = scanner.nextDouble();
                System.out.printf("Se ha realizado un ingreso de %.2f€ por ventas.%n", monto);
            }
            default -> System.out.println("Opción no válida.");
        }
        saldo += monto;
        System.out.printf("Su saldo final es: %.2f€%n", saldo);
        return saldo;
    }
}
