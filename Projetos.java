package projetos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Classe Tarefa
class Tarefa {
    private String descricao;
    private Desenvolvedor desenvolvedor;
    private int cargaHoraria; // Horas necessárias para concluir a tarefa

    public Tarefa(String descricao, int cargaHoraria) {
        validarDescricao(descricao);
        validarCargaHoraria(cargaHoraria);
        this.descricao = descricao;
        this.cargaHoraria = cargaHoraria;
        this.desenvolvedor = null;
    }

    private void validarDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição deve ser válida.");
        }
    }

    private void validarCargaHoraria(int cargaHoraria) {
        if (cargaHoraria <= 0) {
            throw new IllegalArgumentException("Carga horária deve ser positiva.");
        }
    }

    public String getDescricao() {
        return descricao;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public Desenvolvedor getDesenvolvedor() {
        return desenvolvedor;
    }

    public void atribuirDesenvolvedor(Desenvolvedor desenvolvedor) {
        this.desenvolvedor = desenvolvedor;
    }
}

// Classe Desenvolvedor
class Desenvolvedor {
    private String nome;
    private List<Tarefa> tarefas;

    public Desenvolvedor(String nome) {
        validarNome(nome);
        this.nome = nome;
        this.tarefas = new ArrayList<>();
    }

    private void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do desenvolvedor não pode ser nulo ou vazio.");
        }
    }

    public String getNome() {
        return nome;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void adicionarTarefa(Tarefa tarefa) {
        if (tarefa != null && !tarefas.contains(tarefa)) {
            tarefas.add(tarefa);
        }
    }

    public void listarTarefas() {
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa atribuída ao desenvolvedor " + nome + ".");
        } else {
            System.out.println("Tarefas do desenvolvedor " + nome + ":");
            tarefas.forEach(tarefa ->
                System.out.println("- " + tarefa.getDescricao() + " (Carga Horária: " + tarefa.getCargaHoraria() + " horas)")
            );
        }
    }
}

// Classe Projeto
class Projeto {
    private String nome;
    private List<Tarefa> tarefas;
    private Map<Desenvolvedor, Integer> cargaTrabalho;

    public Projeto(String nome) {
        validarNome(nome);
        this.nome = nome;
        this.tarefas = new ArrayList<>();
        this.cargaTrabalho = new HashMap<>();
    }

    private void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do projeto não pode ser nulo ou vazio.");
        }
    }

    public String getNome() {
        return nome;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void adicionarTarefa(Tarefa tarefa) {
        if (tarefa != null) {
            tarefas.add(tarefa);
        }
    }

    public void atribuirTarefa(Tarefa tarefa, Desenvolvedor desenvolvedor) {
        if (tarefa != null && desenvolvedor != null) {
            tarefa.atribuirDesenvolvedor(desenvolvedor);
            desenvolvedor.adicionarTarefa(tarefa);

            int cargaAtual = cargaTrabalho.getOrDefault(desenvolvedor, 0);
            cargaTrabalho.put(desenvolvedor, cargaAtual + tarefa.getCargaHoraria());
        }
    }

    public int calcularCargaTrabalho() {
        return cargaTrabalho.values().stream().mapToInt(Integer::intValue).sum();
    }

    public void listarTarefasPorDesenvolvedor(Desenvolvedor desenvolvedor) {
        if (desenvolvedor != null) {
            System.out.println("Tarefas do desenvolvedor " + desenvolvedor.getNome() + " no projeto " + nome + ":");
            desenvolvedor.listarTarefas();
        }
    }
}

// Classe principal para executar o código
public class Projetos {
    public static void main(String[] args) {
        // Criando desenvolvedores
        Desenvolvedor dev1 = new Desenvolvedor("Gabriela");
        Desenvolvedor dev2 = new Desenvolvedor("Biel");

        // Criando tarefas
        Tarefa tarefa1 = new Tarefa("Desenvolver API", 20);
        Tarefa tarefa2 = new Tarefa("Implementar front-end", 30);
        Tarefa tarefa3 = new Tarefa("Configurar banco de dados", 15);

        // Criando projeto
        Projeto projeto = new Projeto("Projeto X");

        // Adicionando tarefas ao projeto
        projeto.adicionarTarefa(tarefa1);
        projeto.adicionarTarefa(tarefa2);
        projeto.adicionarTarefa(tarefa3);

        // Atribuindo tarefas aos desenvolvedores
        projeto.atribuirTarefa(tarefa1, dev1);
        projeto.atribuirTarefa(tarefa2, dev2);
        projeto.atribuirTarefa(tarefa3, dev1);

        // Exibindo carga de trabalho do projeto
        System.out.println("Carga de trabalho total do projeto " + projeto.getNome() + ": " + projeto.calcularCargaTrabalho() + " horas");

        // Listando tarefas dos desenvolvedores
        projeto.listarTarefasPorDesenvolvedor(dev1);
        projeto.listarTarefasPorDesenvolvedor(dev2);
    }
}
