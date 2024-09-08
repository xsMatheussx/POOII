package biblioteca;

import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

class LivroIndisponivelException extends Exception {

    public LivroIndisponivelException(String mensagem) {
        super(mensagem);
    }
}

class LivroNaoEmprestadoException extends Exception {

    public LivroNaoEmprestadoException(String mensagem) {
        super(mensagem);
    }
}

class Autor {

    private String nome;

    public Autor(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do autor não pode ser nulo ou vazio.");
        }
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}

class Livro implements Serializable {

    private static final long serialVersionUID = 1L;

    private String titulo;
    private Autor autor;
    private int exemplares;
    private int emprestados;
    private List<Usuario> reservas;

    public Livro(String titulo, Autor autor, int exemplares) {
        if (titulo == null || titulo.trim().isEmpty() || autor == null || exemplares <= 0) {
            throw new IllegalArgumentException("Título, autor e exemplares devem ser válidos.");
        }
        this.titulo = titulo;
        this.autor = autor;
        this.exemplares = exemplares;
        this.emprestados = 0;
        this.reservas = new ArrayList<>();
    }

    public boolean isDisponivel() {
        return exemplares > emprestados;
    }

    public void emprestar() throws LivroIndisponivelException {
        if (isDisponivel()) {
            emprestados++;
        } else {
            throw new LivroIndisponivelException("Nenhum exemplar disponível para empréstimo.");
        }
    }

    public void devolver() {
        if (emprestados > 0) {
            emprestados--;
            notificarReserva();
        }
    }

    public void reservar(Usuario usuario) {
        if (!reservas.contains(usuario)) {
            reservas.add(usuario);
            System.out.println("O usuário " + usuario.getNome() + " reservou o livro \"" + titulo + "\".");
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public List<Usuario> getReservas() {
        return reservas;
    }

    private void notificarReserva() {
        if (!reservas.isEmpty() && isDisponivel()) {
            Usuario proximoUsuario = reservas.remove(0);
            System.out.println("O livro \"" + titulo + "\" está disponível para " + proximoUsuario.getNome());
        }
    }
}

class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private List<Emprestimo> emprestimos;
    private long multaAcumulada;

    public Usuario(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do usuário não pode ser nulo ou vazio.");
        }
        this.nome = nome;
        this.emprestimos = new ArrayList<>();
        this.multaAcumulada = 0;
    }

    public void emprestarLivro(Livro livro, Biblioteca biblioteca) throws LivroIndisponivelException {
        if (livro.isDisponivel()) {
            Emprestimo emprestimo = new Emprestimo(this, livro);
            biblioteca.registrarEmprestimo(emprestimo);
            livro.emprestar();
            emprestimos.add(emprestimo);
            System.out.println("O usuário " + nome + " emprestou o livro \"" + livro.getTitulo() + "\".");
        } else {
            livro.reservar(this);
        }
    }

    public void devolverLivro(Livro livro, Biblioteca biblioteca) throws LivroNaoEmprestadoException {
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getLivro().equals(livro)) {
                biblioteca.registrarDevolucao(emprestimo);
                livro.devolver();
                emprestimos.remove(emprestimo);
                System.out.println("O usuário " + nome + " devolveu o livro \"" + livro.getTitulo() + "\".");
                return;
            }
        }
        throw new LivroNaoEmprestadoException("Este livro não está emprestado a este usuário.");
    }

    public String getNome() {
        return nome;
    }

    public long getMultaAcumulada() {
        return multaAcumulada;
    }

    public void adicionarMulta(long multa) {
        this.multaAcumulada += multa;
    }

    public void pagarMulta(long valor) {
        if (valor <= multaAcumulada) {
            this.multaAcumulada -= valor;
            System.out.println("O usuário " + nome + " pagou " + valor + " de multa.");
        } else {
            System.out.println("Valor de pagamento maior do que a multa acumulada.");
        }
    }
}

class Emprestimo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Usuario usuario;
    private Livro livro;
    private LocalDate dataEmprestimo;

    public Emprestimo(Usuario usuario, Livro livro) {
        this.usuario = usuario;
        this.livro = livro;
        this.dataEmprestimo = LocalDate.now();
    }

    public boolean isEmAtraso() {
        return LocalDate.now().isAfter(dataEmprestimo.plusDays(7));
    }

    public long calcularMulta() {
        if (isEmAtraso()) {
            long diasAtraso = ChronoUnit.DAYS.between(dataEmprestimo.plusDays(7), LocalDate.now());
            return diasAtraso * 2;  // Multa de 2 unidades por dia de atraso
        }
        return 0;
    }

    public Livro getLivro() {
        return livro;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}

public class Biblioteca implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Livro> livros;
    private List<Emprestimo> emprestimos;
    private List<Usuario> usuarios;

    public Biblioteca() {
        this.livros = new ArrayList<>();
        this.emprestimos = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    public void adicionarLivro(Livro livro) {
        livros.add(livro);
    }

    public void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public void registrarEmprestimo(Emprestimo emprestimo) {
        emprestimos.add(emprestimo);
    }

    public void registrarDevolucao(Emprestimo emprestimo) {
        if (emprestimo.isEmAtraso()) {
            long multa = emprestimo.calcularMulta();
            System.out.println("O usuário " + emprestimo.getUsuario().getNome() + " está em atraso. Multa: " + multa);
            emprestimo.getUsuario().adicionarMulta(multa);
        }
        emprestimos.remove(emprestimo);
    }

    public List<String> listarLivrosPopulares() {
        Map<String, Integer> popularidade = new HashMap<>();
        for (Livro livro : livros) {
            popularidade.put(livro.getTitulo(), livro.getReservas().size());
        }
        return popularidade.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public void exibirStatusMultas() {
        for (Usuario usuario : usuarios) {
            System.out.println("Usuário: " + usuario.getNome() + " - Multa acumulada: " + usuario.getMultaAcumulada());
        }
    }

    public void salvarDados() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("biblioteca.dat"))) {
            oos.writeObject(livros);
            oos.writeObject(usuarios);
            oos.writeObject(emprestimos);
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarDados() throws IOException, ClassNotFoundException {
        File file = new File("biblioteca.dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                livros = (List<Livro>) ois.readObject();
                usuarios = (List<Usuario>) ois.readObject();
                emprestimos = (List<Emprestimo>) ois.readObject();
            }
        } else {
            System.out.println("Arquivo de dados não encontrado. Iniciando com dados vazios.");
            livros = new ArrayList<>();
            usuarios = new ArrayList<>();
            emprestimos = new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        try {
            Autor autor1 = new Autor("Machado de Assis");
            Autor autor2 = new Autor("José Saramago");

            Livro livro1 = new Livro("Dom Casmurro", autor1, 3);
            Livro livro2 = new Livro("Ensaio sobre a cegueira", autor2, 2);
            Livro livro3 = new Livro("Memórias Póstumas de Brás Cubas", autor1, 1);

            Usuario usuario1 = new Usuario("Natan");
            Usuario usuario2 = new Usuario("Lívia");
            Usuario usuario3 = new Usuario("Roberta");

            Biblioteca biblioteca = new Biblioteca();
            biblioteca.adicionarLivro(livro1);
            biblioteca.adicionarLivro(livro2);
            biblioteca.adicionarLivro(livro3);
            biblioteca.adicionarUsuario(usuario1);
            biblioteca.adicionarUsuario(usuario2);
            biblioteca.adicionarUsuario(usuario3);

            usuario1.emprestarLivro(livro1, biblioteca);
            usuario2.emprestarLivro(livro2, biblioteca);
            usuario3.emprestarLivro(livro1, biblioteca); // Reserva

            // Simulação de atraso e devolução
            Thread.sleep(5000);  // Simula atraso (5 segundos)
            usuario1.devolverLivro(livro1, biblioteca); // Devolução após 5 segundos

            Thread.sleep(10000); // Simula mais 10 segundos de atraso
            usuario2.devolverLivro(livro2, biblioteca); // Devolução após 10 segundos

            // Pagamento da multa por Lívia
            long multaLivia = usuario2.getMultaAcumulada();
            if (multaLivia > 0) {
                usuario2.pagarMulta(multaLivia);  // Pagando a multa total acumulada
            }

            System.out.println("Status de multas após pagamento:");
            biblioteca.exibirStatusMultas();

            List<String> livrosPopulares = biblioteca.listarLivrosPopulares();
            System.out.println("Livros mais populares: " + livrosPopulares);

            // Salvando dados
            biblioteca.salvarDados();

            // Carregando dados (para teste de persistência)
            Biblioteca bibliotecaCarregada = new Biblioteca();
            bibliotecaCarregada.carregarDados();

        } catch (LivroIndisponivelException | LivroNaoEmprestadoException | IOException | ClassNotFoundException | InterruptedException e) {
        }
    }
}
