package br.org.arymax.katana.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by douglas on 16/10/16.
 */

@XStreamAlias("respostas")
public class ArrayRespostas {

    @XStreamImplicit(itemFieldName = "pergunta")
    private List<Resposta> respostas = new ArrayList<>();

    public ArrayRespostas()
    {

    }

    /**
     * Retorna a lista de perguntas
     *
     * @return
     */
    public List<Resposta> getRespostas(){
        return respostas;
    }
}