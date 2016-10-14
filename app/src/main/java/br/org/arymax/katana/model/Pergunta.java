package br.org.arymax.katana.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.Date;

import br.org.arymax.katana.model.Usuario;

/**
 *
 * @author Lorhan Sohaky
 */
@XStreamAlias("pergunta")
public class Pergunta {

    private long pkPergunta;
    private String titulo;
    private String pergunta;
    private boolean anonimo;
    private Date data;
    @XStreamImplicit(itemFieldName = "usuario")
    private Usuario user;

    /**
     * Construtor Pergunta.
     */
    public Pergunta() {
    }

    /**
     * Construtor Pergunta.
     *
     * @param pkPergunta Número de identificação da pergunta.
     * @param titulo Titulo da pergunta.
     * @param pergunta Pergunta.
     * @param anonimo Se a pessoa quer manter o anonimato ou não.
     * @param data Data em que foi feita a pergunta.
     * @param pkUsuario Número de identificação do usuário.
     */
    public Pergunta(long pkPergunta, String titulo, String pergunta, boolean anonimo, Date data, long pkUsuario) {
        this.pkPergunta = pkPergunta;
        this.titulo = titulo;
        this.pergunta = pergunta;
        this.anonimo = anonimo;
        this.data = (Date) data.clone();
        this.user = new Usuario(pkUsuario);
    }

    /**
     * Construtor Pergunta.
     *
     * @param pkPergunta Número de identificação da pergunta.
     * @param titulo Titulo da pergunta.
     * @param pergunta Pergunta.
     * @param pkUsuario Número de identificação do usuário.
     */
    public Pergunta(long pkPergunta, String titulo, String pergunta, long pkUsuario) {
        this.pkPergunta = pkPergunta;
        this.titulo = titulo;
        this.pergunta = pergunta;
        this.user = new Usuario(pkUsuario);
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
    public String getPergunta() {
        return pergunta;
    }

    /**
     * Setter pergunta.
     *
     * @param pergunta Pergunta.
     */
    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
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
    public Usuario getUser() {
        return user;
    }

    /**
     * Setter user.
     *
     * @param user Objeto do usuário.
     */
    public void setUser(Usuario user) {
        this.user = user;
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