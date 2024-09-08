package gerenciamento;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Sistema de Gerenciamento de Clínica.
 */
public class Clinica {

    /**
     * Classe que representa um médico.
     */
    public static class Medico {
        private final String nome;
        private final List<Consulta> consultas = new ArrayList<>();

        public Medico(String nome) {
            this.nome = Objects.requireNonNull(nome, "Nome não pode ser nulo");
        }

        public void agendarConsulta(Paciente paciente, String data) {
            Consulta consulta = new Consulta(this, paciente, data);
            consultas.add(consulta);
            paciente.adicionarConsulta(consulta);
            System.out.println("Consulta agendada com sucesso para o médico: " + nome + " na data: " + data);
        }

        public void listarPacientes() {
            System.out.println("Pacientes do médico " + nome + ":");
            consultas.stream()
                    .map(Consulta::getPaciente)
                    .distinct()
                    .forEach((Paciente paciente) -> System.out.println(paciente.getNome()));
        }

        public void exibirProximasConsultas() {
            System.out.println("Próximas consultas para o médico " + nome + ":");
            consultas.forEach(Consulta::exibir);
        }

        public String getNome() {
            return nome;
        }
    }

    /**
     * Classe que representa um paciente.
     */
    public static class Paciente {
        private final String nome;
        private final List<Consulta> consultas = new ArrayList<>();

        public Paciente(String nome) {
            this.nome = Objects.requireNonNull(nome, "Nome não pode ser nulo");
        }

        public void adicionarConsulta(Consulta consulta) {
            consultas.add(Objects.requireNonNull(consulta, "Consulta não pode ser nula"));
        }

        public void listarConsultas() {
            System.out.println("Consultas do paciente " + nome + ":");
            consultas.forEach(Consulta::exibir);
        }

        public String getNome() {
            return nome;
        }
    }

    /**
     * Classe que representa uma consulta.
     */
    public static class Consulta {
        private final Medico medico;
        private final Paciente paciente;
        private final String data;

        public Consulta(Medico medico, Paciente paciente, String data) {
            this.medico = Objects.requireNonNull(medico, "Médico não pode ser nulo");
            this.paciente = Objects.requireNonNull(paciente, "Paciente não pode ser nulo");
            this.data = Objects.requireNonNull(data, "Data não pode ser nula");
        }

        public Medico getMedico() {
            return medico;
        }

        public Paciente getPaciente() {
            return paciente;
        }

        public String getData() {
            return data;
        }

        public void exibir() {
            System.out.println("Consulta com o médico: " + medico.getNome() + " - Paciente: " + paciente.getNome() + " - Data: " + data);
        }
    }

    public static void main(String[] args) {
        // Criando médicos
        Medico drLuan = new Medico("Dr. Luan");
        Medico drRobson = new Medico("Dr. Robson");

        // Criando pacientes
        Paciente livia = new Paciente("Lívia");
        Paciente natan = new Paciente("Natan");

        // Agendando consultas
        drRobson.agendarConsulta(livia, "2024-09-15");
        drRobson.agendarConsulta(natan, "2024-09-22");
        drLuan.agendarConsulta(livia, "2024-09-18");

        // Listando pacientes de um médico
        System.out.println("--- Listagem de Pacientes ---");
        drRobson.listarPacientes();  // Esperado: Lívia e Natan
        drLuan.listarPacientes();    // Esperado: Lívia

        // Exibindo próximas consultas
        System.out.println("--- Próximas Consultas ---");
        drRobson.exibirProximasConsultas();  // Esperado: Consulta de Lívia e Natan
        drLuan.exibirProximasConsultas();   // Esperado: Consulta de Lívia

        // Listando consultas de um paciente
        System.out.println("--- Consultas do Paciente Lívia ---");
        livia.listarConsultas();  // Esperado: Consulta com Dr. Luan e Dr. Robson
    }
}
