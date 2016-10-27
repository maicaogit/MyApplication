package br.org.arymax.katana.utility;

import java.io.Serializable;
import java.util.List;

/**
 * Criado por Marco em 27/10/2016.
 */

public class SerializableListHolder implements Serializable {

    private List holder;

    public transient static final String KEY = "br.org.arymax.katana.serializable";

    public SerializableListHolder(List holder){
        this.holder = holder;
    }

    public List getList(){
        return this.holder;
    }
}
