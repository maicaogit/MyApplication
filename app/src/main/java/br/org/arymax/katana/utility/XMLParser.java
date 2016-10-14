package br.org.arymax.katana.utility;

import com.thoughtworks.xstream.XStream;


/**
 * Criado por Marco em 13/08/2016.
 */

/**
 * Classe para serialização e deserialização de XML
 *
 * @author Marco
 */
public class XMLParser {


    /**
     * Desserializa um XML em um objeto
     *
     * @param XML O XML a ser deserializado
     * @param type Tipo da classe em que o XML será deserializado
     * @return Objeto da classe type
     */
    public static <T> T XMLToObject(String XML, Class<T> type){
        T obj = null;
        XStream stream = new XStream();

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
        String XML = "";

        return XML;
    }

}
