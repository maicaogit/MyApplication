package br.org.arymax.katana.utility;

import static java.lang.System.out;

/**
 * Created by douglas on 13/10/16.
 */
public class Validacao {

    /**
     * Retorna o digito verificador do prontuário digitado
     *
     * @param num prontuário
     * @return digito verificador
     */
    public static String getMod11(String num) {
        out.println("antes num: "+num);
        num.toLowerCase();
        out.println("Agora num: "+num);
        //variáveis de instancia

        int soma = 0;
        int resto = 0;
        String dv;
        int dvAux;
        String[] numeros = new String[num.length()+1];
        int multiplicador = 2;
        for (int i = num.length(); i > 0; i--) {
            //Multiplica da direita pra esquerda, incrementando o multiplicador de 2 a 9
            //Caso o multiplicador seja maior que 9 o mesmo recomeça em 2
            if(multiplicador > 9){
                // pega cada numero isoladamente
                multiplicador = 2;
                numeros[i] = String.valueOf(Integer.valueOf(num.substring(i-1,i))*multiplicador);
                multiplicador++;
            }else{
                numeros[i] = String.valueOf(Integer.valueOf(num.substring(i-1,i))*multiplicador);
                multiplicador++;
            }
        }
        //Realiza a soma de todos os elementos do array e calcula o digito verificador
        //na base 11 de acordo com a regra.
        for(int i = numeros.length; i > 0 ; i--){
            if(numeros[i-1] != null){
                soma += Integer.valueOf(numeros[i-1]);
            }
        }
        resto = soma%11;
        dvAux = 11 - resto;

        switch (dvAux) {
            case 10: dv = "x"; return dv;
            case 11: dvAux = dvAux-10; return String.valueOf(dvAux);
            default: return String.valueOf(dvAux);
        }
        //retorna o digito verificador
    }

    /**
     * Verifica se o prontuário digitado é valido
     *
     * @param num digito verificador digitado pelo usuário
     * @param prontuario prontuário digitado pelo usuário
     * @return true
     *              se o prontuário é válido
     *         false
     *              se o prontuário não é válido
     */
    public static boolean isProntuarioValido(String num, String prontuario){
        String digitoVerificador = getMod11(prontuario);
        if(digitoVerificador.equals(num))
            return true;
        else
            return false;
    }
}
