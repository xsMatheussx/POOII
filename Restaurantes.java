package restaurantes;

import java.util.ArrayList;
import java.util.List;

// Classe Prato
class Prato {
    private String nome;
    private double preco;

    public Prato(String nome, double preco) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do prato não pode ser nulo ou vazio.");
        }
        if (preco <= 0) {
            throw new IllegalArgumentException("Preço do prato deve ser positivo.");
        }
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }
}

// Classe Pedido
class Pedido {
    private List<Prato> pratos;
    private List<Integer> quantidades;

    public Pedido() {
        this.pratos = new ArrayList<>();
        this.quantidades = new ArrayList<>();
    }

    public void adicionarPrato(Prato prato, int quantidade) {
        if (prato == null || quantidade <= 0) {
            throw new IllegalArgumentException("Prato e quantidade devem ser válidos.");
        }
        pratos.add(prato);
        quantidades.add(quantidade);
    }

    public double calcularTotal() {
        double total = 0;
        for (int i = 0; i < pratos.size(); i++) {
            total += pratos.get(i).getPreco() * quantidades.get(i);
        }
        return total;
    }

    public void listarPedidos() {
        System.out.println("Pedidos:");
        for (int i = 0; i < pratos.size(); i++) {
            System.out.println("- " + pratos.get(i).getNome() + ": " + quantidades.get(i) + " x " + pratos.get(i).getPreco() + " = " + pratos.get(i).getPreco() * quantidades.get(i));
        }
        System.out.println("Total: " + calcularTotal());
    }
}

// Classe Mesa
class Mesa {
    private int numero;
    private Pedido pedidoAtual;
    private String reserva;

    public Mesa(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("Número da mesa deve ser positivo.");
        }
        this.numero = numero;
        this.pedidoAtual = new Pedido();
        this.reserva = null;
    }

    public int getNumero() {
        return numero;
    }

    public Pedido getPedidoAtual() {
        return pedidoAtual;
    }

    public void adicionarPratoPedido(Prato prato, int quantidade) {
        pedidoAtual.adicionarPrato(prato, quantidade);
    }

    public void reservarMesa(String reserva) {
        if (reserva == null || reserva.trim().isEmpty()) {
            throw new IllegalArgumentException("Reserva deve ser válida.");
        }
        this.reserva = reserva;
    }

    public String getReserva() {
        return reserva;
    }

    public void exibirReserva() {
        if (reserva == null) {
            System.out.println("Mesa " + numero + " não tem reserva.");
        } else {
            System.out.println("Mesa " + numero + " reservada para: " + reserva);
        }
    }
}

// Classe principal para executar o código
public class Restaurantes {

    public static void main(String[] args) {
        // Criando pratos
        Prato prato1 = new Prato("Pizza Margherita", 25.00);
        Prato prato2 = new Prato("Lasanha", 30.00);
        Prato prato3 = new Prato("Espaguete à Carbonara", 28.00);

        // Criando mesas
        Mesa mesa1 = new Mesa(1);
        Mesa mesa2 = new Mesa(2);

        // Reservando mesas
        mesa1.reservarMesa("Felipe - 19:00");
        mesa2.reservarMesa("lisa - 20:00");

        // Criando pedidos
        mesa1.adicionarPratoPedido(prato1, 2); // 2 Pizzas Margherita
        mesa1.adicionarPratoPedido(prato2, 1); // 1 Lasagna

        mesa2.adicionarPratoPedido(prato3, 3); // 3 Espaguetes à Carbonara

        // Exibindo reservas e pedidos
        mesa1.exibirReserva();
        mesa1.getPedidoAtual().listarPedidos();

        mesa2.exibirReserva();
        mesa2.getPedidoAtual().listarPedidos();
    }
}
