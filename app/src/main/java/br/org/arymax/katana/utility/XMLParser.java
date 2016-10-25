package br.org.arymax.katana.utility;

import android.util.Log;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.extended.ISO8601DateConverter;

import java.util.ArrayList;
import java.util.List;

import br.org.arymax.katana.model.ArrayPerguntas;
import br.org.arymax.katana.model.ArrayRespostas;
import br.org.arymax.katana.model.Pergunta;
import br.org.arymax.katana.model.Resposta;


/**
 * Classe para serialização e deserialização de XML
 *
 * @author Marco
 */
public class XMLParser {

    private static XStream stream;
    private static final String TAG = "XMLParser.java";

    /**
     * Deserializa um XML em um objeto
     *
     * @param XML O XML a ser deserializado
     * @param type Tipo da classe em que o XML será deserializado
     * @return Objeto da classe type
     */
    public static <T> T XMLToObject(String XML, Class<T> type){
        stream = getStream();
        T obj;
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
        String XML;
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

    /**
     * Converte um XML em uma lista de objetos;
     *
     * @param XML XML a ser convertido
     * @param listHolder classe do array
     * @param listObjectClass classe do objeto da lista
     * @return lista de objetos
     */
    public static <T, A> List xmlToListObject(String XML, Class<A> listHolder, Class<T> listObjectClass){
        stream = getStream();
        List<T> objectList = new ArrayList<>();
        stream.registerConverter(new ISO8601DateConverter());
        stream.processAnnotations(listHolder);
        stream.processAnnotations(listObjectClass);
        if(XML != null){
            if(listHolder == ArrayPerguntas.class){
                Log.d(TAG, "ArrayPerguntas.class");
                ArrayPerguntas perguntas = (ArrayPerguntas) stream.fromXML(XML);
                if(perguntas.getPerguntas() != null){
                    for(Pergunta pergunta : perguntas.getPerguntas()){
                        objectList.add((T) pergunta);
                    }
                }
            } else if(listHolder == ArrayRespostas.class){
                Log.d(TAG, "ArrayRespostas.class");
                ArrayRespostas respostas = (ArrayRespostas) stream.fromXML(XML);
                if(respostas.getRespostas() != null){
                    for(Resposta resposta : respostas.getRespostas()){
                        objectList.add((T) resposta);
                    }
                }
            }
        }
        return objectList;
    }
}
