package aplicativo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Sistema de Mídia Social.
 */
public class MidiaSocial {

    /**
     * Classe que representa um usuário.
     */
    public static class Usuario {
        private final String nome;
        final List<Publicacao> publicacoes = new ArrayList<>();

        public Usuario(String nome) {
            this.nome = Objects.requireNonNull(nome, "Nome não pode ser nulo");
        }

        public void criarPublicacao(String conteudo) {
            Publicacao publicacao = new Publicacao(this, conteudo);
            publicacoes.add(publicacao);
        }

        public void listarPublicacoes() {
            System.out.println("Publicações de " + nome + ":");
            publicacoes.forEach(Publicacao::exibir);
        }

        public String getNome() {
            return nome;
        }
    }

    /**
     * Classe que representa uma publicação.
     */
    public static class Publicacao {
        private final Usuario autor;
        private final String conteudo;
        private final List<Comentario> comentarios = new ArrayList<>();

        public Publicacao(Usuario autor, String conteudo) {
            this.autor = Objects.requireNonNull(autor, "Autor não pode ser nulo");
            this.conteudo = Objects.requireNonNull(conteudo, "Conteúdo não pode ser nulo");
        }

        public void adicionarComentario(Comentario comentario) {
            comentarios.add(Objects.requireNonNull(comentario, "Comentário não pode ser nulo"));
        }

        public void exibir() {
            System.out.println("Publicação: " + conteudo);
            System.out.println("Comentários:");
            comentarios.forEach(Comentario::exibir);
        }
    }

    /**
     * Classe que representa um comentário.
     */
    public static class Comentario {
        private final Usuario autor;
        private final String conteudo;

        public Comentario(Usuario autor, String conteudo) {
            this.autor = Objects.requireNonNull(autor, "Autor não pode ser nulo");
            this.conteudo = Objects.requireNonNull(conteudo, "Conteúdo não pode ser nulo");
        }

        public void exibir() {
            System.out.println(autor.getNome() + ": " + conteudo);
        }
    }
}
