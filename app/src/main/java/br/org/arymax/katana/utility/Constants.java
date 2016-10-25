package br.org.arymax.katana.utility;

/**
 * Criado por Marco em 15/10/2016.
 */
public class Constants {

    /**
     * Constante para acesso dos dados guardados em uma {@code SharedPreferences}
     */
    public static final String PREFERENCES = "br.org.arymax.katana.preferences";



    /**
     * Constante para lançar uma notificação ao usuário
     */
    public static final String ACTION_NOTIFICATE_USER = "br.org.arymax.katana.intent.ACTION_NOTIFICATE_USER";



    /**
     * Constante para iniciar um {@code Service} que verifica de hora em hora se o usuário deve
     * recever uma notificação
     */
    public static final String ACTION_START_NOTIFICATION_SERVICE = "br.org.arymax.katana.intent.ACTION_START_NOTIFICATION_SERVICE";
}
