package br.org.arymax.katana.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.Date;

/**
 *
 * @author Lorhan Sohaky
 */
@XStreamAlias("resposta")
public class Resposta {

    private long pkResposta;
    private String resposta;
    private Date data;
    private Usuario usuario;
    private Pergunta pergunta;
    private boolean melhorResposta;

    /**
     * Construtor Resposta.
     *
     * @param pkResposta Número de identificação da resposta.
     * @param resposta Resposta.
     * @param data Data em que foi dada a resposta;
     * @param pkUsuario Número de indeitificação do usuário.
     * @param melhorResposta Melhor resposta.
     */

    public Resposta(long pkResposta, String resposta, Date data, long pkUsuario, boolean melhorResposta) {
        this.pkResposta = pkResposta;
        this.resposta = resposta;
        this.data = (Date) data.clone();
        this.usuario = new Usuario(pkUsuario);
        this.melhorResposta = melhorResposta;
    }

    /**
     * Construtor Resposta.
     *
     * @param pkResposta Número de identificação da resposta.
     * @param resposta Resposta.
     * @param nome Nome do usuário.
     */
    public Resposta(long pkResposta, String resposta, String nome) {
        this.pkResposta = pkResposta;
        this.resposta = resposta;
        this.usuario = new Usuario(nome);
        this.usuario.setNome(nome);
    }

    /**
     * Construtor Resposta.
     */
    public Resposta() {
    }

    /**
     * Getter pkResposta.
     *
     * @return pkResposta.
     */
    public long getPkResposta() {
        return pkResposta;
    }

    /**
     * Setter pkResposta.
     *
     * @param pkResposta Número de identificação da resposta.
     */
    public void setPkResposta(long pkResposta) {
        this.pkResposta = pkResposta;
    }

    /**
     * Getter resposta.
     *
     * @return resposta.
     */
    public String getResposta() {
        return resposta;
    }

    /**
     * Setter resposta.
     *
     * @param resposta Resposta.
     */
    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    /**
     * is melhorResposta.
     *
     * @return melhorResposta.
     */
    public boolean isMelhorResposta() {
        return melhorResposta;
    }

    /**
     * Setter melhorResposta.
     *
     * @param melhorResposta Se é a melhor resposta.
     */
    public void setMelhorResposta(boolean melhorResposta) {
        this.melhorResposta = melhorResposta;
    }

    /**
     * Getter data.
     *
     * @return data.
     */
    public Date getData() {
        return (Date) data.clone();
    }

    /**
     * Setter data.
     *
     * @param data data em que foi dada a resposta.
     */
    public void setData(Date data) {
        this.data = (Date) data.clone();
    }

    /**
     * Getter usuário.
     *
     * @return user.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Setter usuário.
     *
     * @param usuario Objeto usuário.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    public Pergunta getPergunta() {
        return pergunta;
    }

    public void setPergunta(Pergunta pergunta) {
        this.pergunta = pergunta;
    }
}