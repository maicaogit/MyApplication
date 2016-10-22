package br.org.arymax.katana.utility;

import android.support.annotation.ArrayRes;
import android.util.Log;

import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.List;

import br.org.arymax.katana.model.ArrayPerguntas;
import br.org.arymax.katana.model.ArrayRespostas;
import br.org.arymax.katana.model.Pergunta;


/**
 * Criado por Marco em 13/08/2016.
 */

/**
 * Classe para serialização e deserialização de XML
 *
 * @author Marco
 */
public class XMLParser {

    private static XStream stream;

    /**
     * Deserializa um XML em um objeto
     *
     * @param XML O XML a ser deserializado
     * @param type Tipo da classe em que o XML será deserializado
     * @return Objeto da classe type
     */
    public static <T> T XMLToObject(String XML, Class<T> type){
        stream = getStream();
        T obj = null;
        stream.processAnnotations(type);
        obj = (T) stream.fromXML(XML);
        return obj;
    }

    /**
     * Serializa um objeto em XML
     *
     * @param obj Objeto a ser serializado
     * @param type Classe do objeto
     * @return XML serializado
     */
    public static String objectToXML(Object obj, Class type){
        stream = getStream();
        String XML = "";
        stream.processAnnotations(type);
        XML = stream.toXML(obj);
        return XML;
    }

    /**
     * Retorna a stream atual
     *
     * @return objeto da classe {@link}XStream.java
     */
    public static XStream getStream(){
        if(stream == null)
            return new XStream();
        else
            return stream;
    }

    public static <T, A> List XMLtoListObject(String XML, Class<A> arrayClass, Class<T> listObjectClass){
        stream = getStream();
        List<T> objectList = new ArrayList<>();
        stream.processAnnotations(arrayClass);
        stream.processAnnotations(listObjectClass);
        if(arrayClass == ArrayPerguntas.class){
            ArrayPerguntas perguntas = (ArrayPerguntas) stream.fromXML(XML);
            for(int i=0; i< perguntas.getPerguntas().size(); i++){
                objectList.add((T) perguntas.getPerguntas().get(i));
            }
        } else if(arrayClass == ArrayRespostas.class){
            ArrayRespostas respostas = (ArrayRespostas) stream.fromXML(XML);
            for(int i=0; i< respostas.getRespostas().size(); i++){
                objectList.add((T) respostas.getRespostas().get(i));
            }
        }
        return objectList;
    }



}
