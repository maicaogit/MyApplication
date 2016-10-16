package br.org.arymax.katana.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * Criado por Marco em 16/10/2016.
 */
@XStreamAlias("perguntas")
public class ArrayPerguntas {

    @XStreamImplicit(itemFieldName = "pergunta")
    private List<Pergunta> perguntas = new ArrayList<>();

    public ArrayPerguntas(){

    }

    /**
     * Retorna a lista de perguntas
     *
     * @return
     */
    public List<Pergunta> getPerguntas(){
        return perguntas;
    }
}
