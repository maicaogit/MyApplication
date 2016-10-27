package br.org.arymax.katana.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * Classe modelo do usuário.
 *
 * @author Lorhan Sohaky
 */
@XStreamAlias("usuario")
public class Usuario implements Serializable {

    private long pkUsuario;
    private String nome;
    private String prontuario;
    private String email;
    private String senha;

    /**
     * Método construtor do Usuario.
     *
     * @param nome Nome do usuário.
     * @param prontuario Prontuário do usuário.
     * @param email E-mail do usuário.
     * @param senha Senha do usuário.
     */

    public Usuario(String nome, String prontuario, String email, String senha) {
        this.nome = nome;
        this.prontuario = prontuario;
        this.email = email;
        this.senha = senha;
    }

    /**
     * Método construtor do Usuario.
     *
     * @param nome Nome do usuário.
     */
    public Usuario(String nome) {
        this.nome = nome;
    }

    /**
     * Método construtor do Usuario.
     *
     * @param pkUsuario Número de identificação do usuário.
     */
    public Usuario(long pkUsuario) {
        this.pkUsuario = pkUsuario;
    }

    /**
     * Método construtor do Usuario.
     *
     * @param pkUsuario Número de identificação do usuário.
     * @param nome Nome do usuário.
     */
    public Usuario(long pkUsuario, String nome) {
        this.pkUsuario = pkUsuario;
        this.nome = nome;
    }

    /**
     * Construtor Usuario.
     */
    public Usuario() {
    }

    /**
     * Contrutor usuario.
     *
     * @param pkUsuario
     * @param nome
     * @param prontuario
     */
    public Usuario(long pkUsuario, String nome, String prontuario, String email) {
        this.pkUsuario = pkUsuario;
        this.nome = nome;
        this.prontuario = prontuario;
        this.email = email;
    }

    /**
     * Getter pkUsuario.
     *
     * @return Número de identificação do usuário
     */
    public long getPkUsuario() {
        return pkUsuario;
    }

    /**
     * Setter pkUsuario.
     *
     * @param pkUsuario Número de identificação do usuário.
     */
    public void setPkUsuario(long pkUsuario) {
        this.pkUsuario = pkUsuario;
    }

    /**
     * Getter nome.
     *
     * @return Nome do usuário.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Setter nome.
     *
     * @param nome Nome do usuário.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Getter email.
     *
     * @return E-mail do usuário.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter email.
     *
     * @param email E-mail do usuário.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter senha.
     *
     * @return Senha do usuário.
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Setter senha.
     *
     * @param senha Senha do usuário.
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Getter prontuário.
     *
     * @return
     */
    public String getProntuario() {
        return prontuario;
    }

    /**
     * Setter prontuário.
     *
     * @param prontuario
     */
    public void setProntuario(String prontuario) {
        this.prontuario = prontuario;
    }
}