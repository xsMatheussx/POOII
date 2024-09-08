package Funcionario;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Classe principal que gerencia o sistema
public class Funcionarios {

    // Classe Funcionario
    static class Funcionario {
        private final String nome;
        private final double salario;
        private final List<Projeto> projetos;

        public Funcionario(String nome, double salario) {
            this.nome = Objects.requireNonNull(nome, "Nome não pode ser nulo");
            if (salario < 0) {
                throw new IllegalArgumentException("Salário não pode ser negativo");
            }
            this.salario = salario;
            this.projetos = new ArrayList<>();
        }

        public void adicionarProjeto(Projeto projeto) {
            if (projeto != null && !projetos.contains(projeto)) {
                projetos.add(projeto);
            }
        }

        public void exibirProjetos() {
            System.out.println("Projetos de " + nome + ":");
            if (projetos.isEmpty()) {
                System.out.println("Nenhum projeto atribuído.");
            } else {
                projetos.forEach(projeto -> System.out.println(projeto.getNome()));
            }
        }
    }

    // Classe Departamento
    static class Departamento {
        private final String nome;
        private final List<Funcionario> funcionarios;

        public Departamento(String nome) {
            this.nome = Objects.requireNonNull(nome, "Nome não pode ser nulo");
            this.funcionarios = new ArrayList<>();
        }

        public void adicionarFuncionario(Funcionario funcionario) {
            if (funcionario != null && !funcionarios.contains(funcionario)) {
                funcionarios.add(funcionario);
            }
        }

        public double calcularMediaSalarial() {
            if (funcionarios.isEmpty()) {
                return 0;
            }
            return funcionarios.stream()
                               .mapToDouble(f -> f.salario) // Simplificado para acessar diretamente o salário
                               .average()
                               .orElse(0);
        }
    }

    // Classe Projeto
    static class Projeto {
        private final String nome;

        public Projeto(String nome) {
            this.nome = Objects.requireNonNull(nome, "Nome não pode ser nulo").trim();
            if (this.nome.isEmpty()) {
                throw new IllegalArgumentException("Nome do projeto não pode ser vazio");
            }
        }

        public String getNome() {
            return nome;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Projeto projeto = (Projeto) obj;
            return nome.equals(projeto.nome);
        }

        @Override
        public int hashCode() {
            return nome.hashCode();
        }
    }

    // Método principal
    public static void main(String[] args) {
        // Criando projetos
        Projeto projeto1 = new Projeto("Desenvolvimento de Software de IA");
        Projeto projeto2 = new Projeto("Sistema de Gerenciamento de Dados");

        // Criando funcionários com novos nomes
        Funcionario funcionario1 = new Funcionario("Bruna", 6500.0);
        Funcionario funcionario2 = new Funcionario("João", 5500.0);

        // Criando departamento
        Departamento departamento = new Departamento("Inovação e Tecnologia");
        departamento.adicionarFuncionario(funcionario1);
        departamento.adicionarFuncionario(funcionario2);

        // Atribuindo projetos aos funcionários
        funcionario1.adicionarProjeto(projeto1);
        funcionario1.adicionarProjeto(projeto2);
        funcionario2.adicionarProjeto(projeto1);

        // Exibindo projetos dos funcionários
        funcionario1.exibirProjetos();
        funcionario2.exibirProjetos();

        // Calculando e exibindo a média salarial do departamento
        double mediaSalarial = departamento.calcularMediaSalarial();
        System.out.println("Média salarial do departamento " + departamento.nome + ": " + mediaSalarial);
    }
}
