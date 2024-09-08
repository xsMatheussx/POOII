package reservas;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Sistema de Reservas de Passagens.
 */
public class SistemaReservas {

    /**
     * Classe que representa um passageiro.
     */
    public static class Passageiro {
        private final String nome;
        private final List<Reserva> reservas = new ArrayList<>();

        public Passageiro(String nome) {
            this.nome = Objects.requireNonNull(nome, "Nome não pode ser nulo");
        }

        public void reservarAssento(Voo voo) {
            if (voo.isDisponivel()) {
                Reserva reserva = new Reserva(this, voo);
                reservas.add(reserva);
                voo.adicionarReserva(reserva);
                System.out.println("Reserva confirmada para o voo: " + voo.getNumero());
            } else {
                System.out.println("O voo " + voo.getNumero() + " está cheio.");
            }
        }

        public void listarReservas() {
            System.out.println("Reservas de " + nome + ":");
            reservas.forEach(Reserva::exibir);
        }

        public String getNome() {
            return nome;
        }
    }

    /**
     * Classe que representa um voo.
     */
    public static class Voo {
        private final String numero;
        private final int capacidade;
        private final List<Reserva> reservas = new ArrayList<>();

        public Voo(String numero, int capacidade) {
            this.numero = Objects.requireNonNull(numero, "Número do voo não pode ser nulo");
            this.capacidade = capacidade;
        }

        public boolean isDisponivel() {
            return reservas.size() < capacidade;
        }

        public void adicionarReserva(Reserva reserva) {
            if (isDisponivel()) {
                reservas.add(Objects.requireNonNull(reserva, "Reserva não pode ser nula"));
            } else {
                System.out.println("Não é possível adicionar reserva. O voo " + numero + " está cheio.");
            }
        }

        public String getNumero() {
            return numero;
        }

        public void exibir() {
            System.out.println("Voo: " + numero + " - Capacidade: " + capacidade + " - Reservas: " + reservas.size() + "/" + capacidade);
        }
    }

    /**
     * Classe que representa uma reserva.
     */
    public static class Reserva {
        private final Passageiro passageiro;
        private final Voo voo;

        public Reserva(Passageiro passageiro, Voo voo) {
            this.passageiro = Objects.requireNonNull(passageiro, "Passageiro não pode ser nulo");
            this.voo = Objects.requireNonNull(voo, "Voo não pode ser nulo");
        }

        public void exibir() {
            System.out.println("Reserva para o voo: " + voo.getNumero() + " - Passageiro: " + passageiro.getNome());
        }
    }

    public static void main(String[] args) {
        // Criando passageiros
        Passageiro amanda = new Passageiro("Amanda");
        Passageiro ryan = new Passageiro("Ryan");

        // Criando voos
        Voo vooA = new Voo("V001", 2);
        Voo vooB = new Voo("V002", 1);

        // Fazendo reservas
        amanda.reservarAssento(vooA);  // Reserva deve ser bem-sucedida
        ryan.reservarAssento(vooA);    // Reserva deve ser bem-sucedida
        amanda.reservarAssento(vooB);  // Reserva deve ser bem-sucedida
        ryan.reservarAssento(vooB);    // Não deve ser possível, voo cheio

        // Listando reservas
        amanda.listarReservas();
        ryan.listarReservas();
        
        // Exibindo status dos voos
        vooA.exibir();
        vooB.exibir();
    }
}
