import java.util.ArrayList;
import java.util.List;

// Classe Produto
class Produto {
    private String nome;
    private double preco;
    private int estoque;

    public Produto(String nome, double preco, int estoque) {
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    public String getNome() {
        return nome;
    }

    public int getEstoque() {
        return estoque;
    }

    public void adicionarEstoque(int quantidade) {
        this.estoque += quantidade;
    }
}

// Classe Fornecedor
class Fornecedor {
    private String nome;

    public Fornecedor(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}

// Classe Pedido
class Pedido {
    private Fornecedor fornecedor;
    private Produto produto;
    private int quantidade;

    public Pedido(Fornecedor fornecedor, Produto produto, int quantidade) {
        this.fornecedor = fornecedor;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public void realizarPedido() {
        System.out.println("Pedido realizado para " + quantidade + " unidades de " + produto.getNome() +
                           " com o fornecedor " + fornecedor.getNome() + ".");
        produto.adicionarEstoque(quantidade);
    }
}

// Classe principal Estoque
public class Estoque {

    // Método para listar produtos com estoque baixo
    public static void listarProdutosEstoqueBaixo(List<Produto> produtos, int limite) {
        System.out.println("Produtos com estoque baixo:");
        for (Produto produto : produtos) {
            if (produto.getEstoque() < limite) {
                System.out.println(produto.getNome() + ": " + produto.getEstoque() + " unidades");
            }
        }
    }

    public static void main(String[] args) {
        // Criando produtos
        Produto produto1 = new Produto("Produto A", 50.0, 10);
        Produto produto2 = new Produto("Produto B", 30.0, 5);

        // Criando fornecedor
        Fornecedor fornecedor1 = new Fornecedor("Fornecedor X");

        // Realizando pedido
        Pedido pedido = new Pedido(fornecedor1, produto1, 20);
        pedido.realizarPedido();

        // Listando produtos com estoque baixo (limite de 10 unidades)
        List<Produto> produtos = new ArrayList<>();
        produtos.add(produto1);
        produtos.add(produto2);

        listarProdutosEstoqueBaixo(produtos, 10);
    }
}
