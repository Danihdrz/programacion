package miAnimal;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Animal {
    protected int chip;
    protected String nombre;
    protected int edad;
    protected String raza;
    protected boolean adoptado;

    public Animal(int chip, String nombre, int edad, String raza, boolean adoptado) {
        this.chip = chip;
        this.nombre = nombre;
        this.edad = edad;
        this.raza = raza;
        this.adoptado = adoptado;
    }

    public int getChip() {
        return chip;
    }

    public abstract void mostrar();
    
    public static void main(String[] args) {
    	Scanner scanner = new Scanner(System.in);
    	Gestor gestor = new Gestor();
    	
    	int opcion;
    	
    	do {
    		System.out.println("1. Dar de alta perro");
    		System.out.println("2. Dar de alta gato");
    		System.out.println("3. Buscar animal");
    		System.out.println("4. Salir");
    		opcion = scanner.nextInt();
    		switch (opcion) {
            case 1:
                System.out.print("Chip: ");
                int chipPerro = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Nombre: ");
                String nombrePerro = scanner.nextLine();

                System.out.print("Edad: ");
                int edadPerro = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Raza: ");
                String razaPerro = scanner.nextLine();

                System.out.print("Adoptado true o false: ");
                boolean adoptadoPerro = scanner.nextBoolean();
                scanner.nextLine();

                System.out.print("Tamaño pequeño, mediano o grande: ");
                String tamaño = scanner.nextLine();

                Perro nuevoPerro = new Perro(tamaño, chipPerro, nombrePerro, edadPerro, razaPerro, adoptadoPerro);
                gestor.alta(nuevoPerro);
                return;

            case 2:
                System.out.print("chip: ");
                int chipGato = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Nombre: ");
                String nombreGato = scanner.nextLine();

                System.out.print("Edad: ");
                int edadGato = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Raza: ");
                String razaGato = scanner.nextLine();

                System.out.print("Adoptado true o false: ");
                boolean adoptadoGato = scanner.nextBoolean();
                scanner.nextLine();

                System.out.print("Test true o false: ");
                boolean test = scanner.nextBoolean();
                scanner.nextLine();

                Gato nuevoGato = new Gato(test, chipGato, nombreGato, edadGato, razaGato, adoptadoGato);
                gestor.alta(nuevoGato);
                return;

            case 3:
                System.out.print("Número de chip: ");
                int chipBuscar = scanner.nextInt();
                scanner.nextLine();
                gestor.buscar(chipBuscar);
                return;
            case 4: 
            	System.out.println("saliendo");
            	
            default:
                System.out.println("nuemero no valido");
    		}
    	} while (opcion != 4);

    scanner.close();
    }

    				    
}

class Perro extends Animal {
    private String tamaño;

    public Perro(String tamaño, int chip, String nombre, int edad, String raza, boolean adoptado) {
        super(chip, nombre, edad, raza, adoptado);
        this.tamaño = tamaño;
    }

    @Override
    public void mostrar() {
        System.out.println("Perro: chip = " + chip + ", nombre = " + nombre + ", edad = " + edad +
                ", raza = " + raza + ", adoptado = " + (adoptado ? "si" : "no") + ", tamaño = " + tamaño);
    }
}

class Gato extends Animal {
    private boolean test;

    public Gato(boolean test, int chip, String nombre, int edad, String raza, boolean adoptado) {
        super(chip, nombre, edad, raza, adoptado);
        this.test = test;
    }

    @Override
    public void mostrar() {
        System.out.println("Gato: chip = " + chip + ", nombre = " + nombre + ", edad = " + edad +
                ", raza = " + raza + ", adoptado = " + (adoptado ? "si" : "no") +
                ", test = " + (test ? "positivo" : "negativo"));
    }
}

class Gestor {
    private ArrayList<Animal> animales;

    public Gestor() {
        animales = new ArrayList<>();
    }

    public boolean alta(Animal animal) {
        for (Animal a : animales) {
            if (a.getChip() == animal.getChip()) {
                System.out.println("Hay un animal con este chip");
                return false;
            }
        }
        animales.add(animal);
        System.out.println("Se ha dado de alta");
        return true;
    }

    public void buscar(int chip) {
        for (Animal a : animales) {
            if (a.getChip() == chip) {
                a.mostrar();
                return;
            }
        }
        System.out.println("No hay animales con este chip");
    }
}





