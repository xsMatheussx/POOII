package estudante;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Classe Disciplina
class Disciplina {
    private String nome;
    private List<Estudante> estudantes;

    public Disciplina(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da disciplina não pode ser nulo ou vazio.");
        }
        this.nome = nome;
        this.estudantes = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void matricularEstudante(Estudante estudante) {
        if (estudante == null) {
            throw new IllegalArgumentException("Estudante não pode ser nulo.");
        }
        if (!estudantes.contains(estudante)) {
            estudantes.add(estudante);
            estudante.matricularDisciplina(this);
        }
    }

    public List<Estudante> getEstudantes() {
        return estudantes;
    }
}

// Classe Estudante
class Estudante {
    private String nome;
    private Map<Disciplina, Double> notas;

    public Estudante(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do estudante não pode ser nulo ou vazio.");
        }
        this.nome = nome;
        this.notas = new HashMap<>();
    }

    public String getNome() {
        return nome;
    }

    public void matricularDisciplina(Disciplina disciplina) {
        if (disciplina == null) {
            throw new IllegalArgumentException("Disciplina não pode ser nula.");
        }
        notas.putIfAbsent(disciplina, 0.0); // Matricula na disciplina com nota inicial 0
    }

    public void atribuirNota(Disciplina disciplina, double nota) {
        if (disciplina == null || nota < 0 || nota > 10) {
            throw new IllegalArgumentException("Disciplina não pode ser nula e nota deve estar entre 0 e 10.");
        }
        if (notas.containsKey(disciplina)) {
            notas.put(disciplina, nota);
        } else {
            throw new IllegalArgumentException("Estudante não está matriculado na disciplina.");
        }
    }

    public double calcularMedia() {
        return notas.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    public Map<Disciplina, Double> getNotas() {
        return notas;
    }
}

// Classe Professor
class Professor {
    private String nome;
    private List<Disciplina> disciplinas;

    public Professor(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do professor não pode ser vazio.");
        }
        this.nome = nome;
        this.disciplinas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void adicionarDisciplina(Disciplina disciplina) {
        if (disciplina == null) {
            throw new IllegalArgumentException("Disciplina não pode ser nula.");
        }
        if (!disciplinas.contains(disciplina)) {
            disciplinas.add(disciplina);
        }
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void listarDisciplinas() {
        if (disciplinas.isEmpty()) {
            System.out.println("O professor " + nome + " não leciona disciplinas.");
        } else {
            System.out.println("Disciplinas lecionadas pelo professor " + nome + ":");
            for (Disciplina disciplina : disciplinas) {
                System.out.println("- " + disciplina.getNome());
            }
        }
    }
}

// Classe principal para executar o código
public class Estudanteapp {

    public static void main(String[] args) {
        // Criando disciplinas
        Disciplina matematica = new Disciplina("Matemática");
        Disciplina programacao = new Disciplina("Programação");
        Disciplina fisica = new Disciplina("Física");

        // Criando professores
        Professor professorLuan = new Professor("Luan");
        Professor professorRobson = new Professor("Robson");

        // Adicionando disciplinas aos professores
        professorLuan.adicionarDisciplina(matematica);
        professorLuan.adicionarDisciplina(programacao);
        professorRobson.adicionarDisciplina(fisica);

        // Criando estudantes
        Estudante alice = new Estudante("Kately");
        Estudante bob = new Estudante("peter");

        // Matriculando estudantes em disciplinas
        matematica.matricularEstudante(alice);
        programacao.matricularEstudante(alice);
        fisica.matricularEstudante(bob);

        // Atribuindo notas aos estudantes
        alice.atribuirNota(matematica, 8.5);
        alice.atribuirNota(programacao, 9.0);
        bob.atribuirNota(fisica, 7.0);

        // Exibindo médias dos estudantes
        System.out.println("Média do estudante " + alice.getNome() + ": " + alice.calcularMedia());
        System.out.println("Média do estudante " + bob.getNome() + ": " + bob.calcularMedia());

        // Listando disciplinas dos professores
        professorLuan.listarDisciplinas();
        professorRobson.listarDisciplinas();
    }
}
