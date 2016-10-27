package br.org.arymax.katana.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Lorhan Sohaky
 */
@XStreamAlias("pergunta")
public class Pergunta implements Serializable {

    private long pkPergunta;
    private String titulo;
    private String texto;
    private boolean anonimo;
    private Date data;
    private Usuario usuario;

    /**
     * Construtor Pergunta.
     */
    public Pergunta() {
    }

    public Pergunta(long pkPergunta){
        this.pkPergunta = pkPergunta;
    }

    /**
     * Construtor Pergunta.
     *
     * @param titulo Título da pergunta.
     * @param texto Pergunta.
     * @param anonimo Se a pessoa quer manter o anonimato ou não.
     * @param pkUsuario Número de identificação do usuário.
     */
    public Pergunta(String titulo, String texto, boolean anonimo, long pkUsuario){
        this.titulo = titulo;
        this.texto = texto;
        this.anonimo = anonimo;
        this.usuario = new Usuario(pkUsuario);
    }

    /**
     *
     * Construtor Pergunta.
     *
     * @param pkPergunta Número de identificação da pergunta.
     * @param titulo Titulo da pergunta.
     * @param texto Pergunta.
     * @param anonimo Se a pessoa quer manter o anonimato ou não.
     * @param data Data em que foi feita a pergunta.
     * @param pkUsuario Número de identificação do usuário.
     */
    public Pergunta(long pkPergunta, String titulo, String texto, boolean anonimo, Date data, long pkUsuario) {
        this.pkPergunta = pkPergunta;
        this.titulo = titulo;
        this.texto = texto;
        this.anonimo = anonimo;
        this.data = (Date) data.clone();
        this.usuario = new Usuario(pkUsuario);
    }

    /**
     * Construtor Pergunta.
     *
     * @param pkPergunta Número de identificação da pergunta.
     * @param titulo Titulo da pergunta.
     * @param texto Pergunta.
     * @param pkUsuario Número de identificação do usuário.
     */
    public Pergunta(long pkPergunta, String titulo, String texto, long pkUsuario) {
        this.pkPergunta = pkPergunta;
        this.titulo = titulo;
        this.texto = texto;
        this.usuario = new Usuario(pkUsuario);
    }

    /**
     * Getter pkPergunta.
     *
     * @return pkPergunta.
     */
    public long getPkPergunta() {
        return pkPergunta;
    }

    /**
     * Setter pkPergunta.
     *
     * @param pkPergunta Número de identificação da pergunta.
     */
    public void setPkPergunta(long pkPergunta) {
        this.pkPergunta = pkPergunta;
    }

    /**
     * Getter pergunta.
     *
     * @return pergunta.
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Setter pergunta.
     *
     * @param texto Pergunta.
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * Getter data.
     *
     * @return data
     */
    public Date getData() {
        return (Date) data.clone();
    }

    /**
     * Setter data.
     *
     * @param data Data em que foi feita a pergunta.
     */
    public void setData(Date data) {
        this.data = (Date) data.clone();
    }

    /**
     * Getter user.
     *
     * @return user.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Setter user.
     *
     * @param usuario Objeto do usuário.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean isAnonimo() {
        return anonimo;
    }

    public void setAnonimo(boolean anonimo) {
        this.anonimo = anonimo;
    }
}