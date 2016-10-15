package br.org.arymax.katana.utility;

import com.thoughtworks.xstream.XStream;

import br.org.arymax.katana.model.Usuario;


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
     *
     * Desserializa um XML em um objeto
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

}
