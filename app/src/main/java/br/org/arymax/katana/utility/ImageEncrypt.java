package br.org.arymax.katana.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Classe para manipulação de imagens
 *
 * Criado por Marco em 21/08/2016.
 */
public class ImageEncrypt {

    /**
     * Tamanho máximo de uma das dimensões da imagem
     */
    public static final int MAX_SIZE = 612;

    /**
     * Converte um Bitmap em um array de bytes.
     *
     * @param bitmap Imagem em bitmap a ser convertida.
     * @return A imagem convertida em array de bytes.
     */
    public static byte[] bitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytesFoto = stream.toByteArray();
        return bytesFoto;
    }

    /**
     * Converte um array de bytes em um Bitmap.
     *
     * @param byteArray Array de bytes a ser convertido.
     * @return O array de bytes convertido em Bitmap.
     */
    public static Bitmap byteArrayToBitmap(byte[] byteArray){
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;
    }

    /**
     * Retorna os bytes de uma URI
     *
     * @param context current context.
     * @param uri uri fo the file to read.
     * @return a bytes array.
     * @throws IOException
     */
    public static byte[] uriToByteArray(Context context, Uri uri) throws IOException {
        InputStream iStream = context.getContentResolver().openInputStream(uri);
        try {
            return getBytes(iStream);
        } finally {
            try {
                iStream.close();
            } catch (IOException e) {
                Log.e("Script", "Exceção lançada: ", e);
            }
        }
    }

    /**
     * Retorna os bytes de uma InputStream
     *
     * @param inputStream inputStream.
     * @return byte array read from the inputStream.
     * @throws IOException
     */
    public static byte[] getBytes(InputStream inputStream) throws IOException {

        byte[] bytesResult = null;
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        try {
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            bytesResult = byteBuffer.toByteArray();
        } finally {
            try {
                byteBuffer.close();
            }
            catch (IOException e){
                Log.e("Script", "Exceção lançada: ", e);
            }
        }
        return bytesResult;
    }

    /**
     * Retorna um bitmap redimensionado
     *
     * @param image Bitmap a ser redimensionado
     * @param maxSize Tamanho máximo de uma das dimensões
     * @return Bitmap redimensionado
     */
    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}
