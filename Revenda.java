package carros;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Sistema de gerenciamento de carros.
 */
public class Revenda {

    /**
     * Classe que representa um carro.
     */
    public static class Carro {
        private final String modelo;
        private final Marca marca;
        private final double preco;

        public Carro(String modelo, Marca marca, double preco) {
            this.modelo = Objects.requireNonNull(modelo, "Modelo não pode ser nulo");
            this.marca = Objects.requireNonNull(marca, "Marca não pode ser nula");
            if (preco <= 0) {
                throw new IllegalArgumentException("Preço deve ser maior que zero");
            }
            this.preco = preco;
        }

        public String getModelo() {
            return modelo;
        }

        public Marca getMarca() {
            return marca;
        }

        public double getPreco() {
            return preco;
        }

        @Override
        public String toString() {
            return String.format("Modelo: %s, Preço: %.2f", modelo, preco);
        }
    }

    /**
     * Classe que representa uma marca de carro.
     */
    public static class Marca {
        private final String nome;
        private final List<Carro> carros = new ArrayList<>();

        public Marca(String nome) {
            this.nome = Objects.requireNonNull(nome, "Nome da marca não pode ser nulo");
        }

        public void adicionarCarro(Carro carro) {
            Objects.requireNonNull(carro, "Carro não pode ser nulo");
            if (!carro.getMarca().equals(this)) {
                throw new IllegalArgumentException("O carro deve pertencer a esta marca");
            }
            carros.add(carro);
        }

        public String getNome() {
            return nome;
        }

        public double calcularMediaPrecos() {
            return carros.stream()
                    .mapToDouble(Carro::getPreco)
                    .average()
                    .orElse(0);
        }
    }

    /**
     * Classe que representa um vendedor.
     */
    public static class Vendedor {
        private final String nome;
        private final List<Carro> carrosVendidos = new ArrayList<>();

        public Vendedor(String nome) {
            this.nome = Objects.requireNonNull(nome, "Nome do vendedor não pode ser nulo");
        }

        public void venderCarro(Carro carro) {
            Objects.requireNonNull(carro, "Carro não pode ser nulo");
            carrosVendidos.add(carro);
        }

        public void exibirCarrosVendidos() {
            System.out.println("Carros vendidos por " + nome + ":");
            if (carrosVendidos.isEmpty()) {
                System.out.println("Nenhum carro vendido.");
            } else {
                carrosVendidos.forEach(carro -> System.out.println(carro));
            }
        }
    }

    public static void main(String[] args) {
        // Criando marcas
        Marca volkswagen = new Marca("Volkswagen");
        Marca audi = new Marca("Audi");

        // Criando carros
        Carro gol2020 = new Carro("Gol 2020", volkswagen, 45000);
        Carro polo2021 = new Carro("Polo 2021", volkswagen, 55000);
        Carro passat2019 = new Carro("Passat 2019", volkswagen, 80000);
        Carro a3 = new Carro("A3", audi, 120000);

        // Adicionando carros às marcas
        volkswagen.adicionarCarro(gol2020);
        volkswagen.adicionarCarro(polo2021);
        volkswagen.adicionarCarro(passat2019);
        audi.adicionarCarro(a3);

        // Criando vendedores
        Vendedor victor = new Vendedor("Victor");
        Vendedor anna = new Vendedor("Anna");

        // Vendendo carros
        victor.venderCarro(gol2020);
        anna.venderCarro(polo2021);
        anna.venderCarro(passat2019);
        anna.venderCarro(a3);

        // Exibindo carros vendidos por Victor
        System.out.println("--- Carros Vendidos por Victor ---");
        victor.exibirCarrosVendidos();

        // Exibindo carros vendidos por Anna
        System.out.println("--- Carros Vendidos por Anna ---");
        anna.exibirCarrosVendidos();

        // Calculando a média de preços por marca
        System.out.println("--- Média de Preços por Marca ---");
        System.out.printf("Marca: %s - Média de Preços: %.2f%n", volkswagen.getNome(), volkswagen.calcularMediaPrecos());
        System.out.printf("Marca: %s - Média de Preços: %.2f%n", audi.getNome(), audi.calcularMediaPrecos());
    }
}
