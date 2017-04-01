package com.introtoandroid.samplematerial.EncryptDecrypt;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * Esta clase ha sido diseñada para ayudar a la aplicación en las tareas de cifrado y descifrado de
 * las credenciales necesarias que un usuario precisa para el acceso a la informacion en la base de
 * datos utilizada por la aplicación. Métodos de cifrado como el 3DES precisan de una clave privada
 * para llevar a cabo la operación de encriptación. Esta clave privada se guarda en un diccionario
 * dentro de la apicación y en algunos casos será necesaria su utilización. Consute la información
 * relativa en cada uno de los metodos de esta clase.
 *
 * Created by jmonaste on 14/03/2017.
 */
public class EncryptDecryptHelper {

    /**
     * Este método cifra una cadena de caractéres con el algoritmo MD5. El algoritmo MD5 no necesita
     * ninguna clave adicional para cifrar por lo que se vale unicamente de la cadena que se pretende
     * cifrar como parametro de entrada.
     * @param md5 Cadena de caracteres que se pretende cifrar con el algoritmo MD5
     * @return El valor devuelto por este método es un Array de bytes con la cadena cifrada
     */
    public static byte[] MD5Helper(String md5) {
        try {

            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes("UTF-8"));
            return array;

        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Este método cifra una cadena de caractéres con el algoritmo 3DES (o triple DES) en modo ECB
     * utilizando un relleno o padding de bits del tipo PKCS5. Para ello utiliza una clave privada
     * disponible en el diccionario de la aplicación, y que se cifra con el algoritmo MD5
     * @param str Cadena de caracteres que se pretende cifrar
     * @return El valor devuelto por este método es un String que contiene la cadena cifrada
     */
    public static String TDESHelper(String str) {

        String privateKey = "AfdR0tDg3ud5Put2isuHSUL6";
        byte[] privateKey_2;

        //Se cifra con MD5 la clave privada como esta estipulado por el funcionamiento del WebService
        privateKey_2 = MD5Helper(privateKey);
        SecretKey skaes = new SecretKeySpec(privateKey_2, "DESede");

        Cipher cip = null; //Se inicialliza el Cipher

        try {
            //Se intenta obtener una instancia del Cipher con los parámetros que nos interesan
            cip = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        try {
            //Se intenta iniciar el Cipher con el mdo seleccionado y con la clave privada
            cip.init(Cipher.ENCRYPT_MODE,skaes);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        byte[] ciphered = new byte[0];

        try {
            //Se intenta cifrar
            ciphered = cip.doFinal(str.getBytes());
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        String credential = Base64.encodeToString(ciphered, Base64.DEFAULT);

        return(credential);
    }

    //IMPLEMENTACION DE LOS METODOS PARA DESCIFRAR LAS CREDENCIALES

    /**
     * Este método descifra una cadena de caractéres que ha sido previamente cifrada con el
     * algoritmo 3DES (o triple DES) en modo ECB utilizando un relleno o padding de bits del tipo
     * PKCS5. Para ello utiliza una clave privada disponible en el diccionario de la aplicación, y
     * que se cifra con el algoritmo MD5. La metodología utilizada para el descifrado es análoga a
     * la empleada en el cifrado con el método TDESHelper.
     * @param str Cadena de caracteres que se pretende cifrar
     * @return El valor devuelto por este método es un String que contiene la cadena cifrada
     * @see EncryptDecryptHelper
     */
    public static String TDESDecrypter(String str) {

        String privateKey = "AfdR0tDg3ud5Put2isuHSUL6";
        byte[] privateKey_2;

        //Se cifra con MD5 la clave privada como esta estipulado por el funcionamiento del WebService
        //Como se ha cifrado con la clave privada cifrada en MD5, es esta la que necesitamos para
        //desencriptar la clave cifrada con el algoritmo 3DES
        privateKey_2 = MD5Helper(privateKey); //esto es correcto /ccRZ33K57MPdpfpsOnH4g==
        SecretKey skaes = new SecretKeySpec(privateKey_2, "DESede");
        Cipher cip = null; //Se inicialliza el Cipher

        try {
            //Se intenta obtener una instancia del Cipher con los parámetros que nos interesan
            cip = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        try {
            //Se intenta iniciar el Cipher con el modo seleccionado y con la clave privada, el modo
            //sería evidenteme el modo de desencriptar
            cip.init(Cipher.DECRYPT_MODE,skaes);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        byte[] ciphered = new byte[0];

        try {
            //Se intenta descifrar
            byte[] decodedValue = Base64.decode(str.getBytes(), Base64.DEFAULT);
            ciphered = cip.doFinal(decodedValue);
            //ciphered = cip.doFinal(str.getBytes());
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        String decryptedValue = new String(ciphered);
        return decryptedValue;
    }

    //CODIFICACION Y DECODIFICACIÓN AES

    /**
     *
     * @param message
     * @param passphrase
     * @return
     * @throws Exception
     */
    public static byte[] encodeAES(byte[] message, String passphrase) {
        SecretKeySpec secretKey = new SecretKeySpec(passphrase.getBytes(), "AES");
        Cipher cipher = null;

        try {

            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encodedText = cipher.doFinal(message);
            return encodedText;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     *
     * @param encodedMessage
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decodeAES(byte[] encodedMessage, String key) {

        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedText = cipher.doFinal(encodedMessage);
            return decodedText;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
             e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return null;
    }

    //RSA ENCRYPTATION METHODS

    public static String RSAEncrypt (final String message, PublicKey publKey) {

        try{

            // Base64 decode the message
            //byte [] pkcs8EncodedBytes = Base64.decode(message, Base64.DEFAULT);

            // extract the public key
            //PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
            //KeyFactory kf = KeyFactory.getInstance("RSA");
            //PublicKey publKey = kf.generatePublic(keySpec);

            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publKey);
            byte[] encryptedBytes = cipher.doFinal(message.getBytes());
            String encrypted = new String(encryptedBytes);

            //System.out.println("EEncrypted?????" + encrypted);

            return encrypted;

        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        } catch (NoSuchPaddingException e){
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e){
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String RSADecrypt (final String message, PrivateKey privKey) {

        try{

            Cipher cipher1=Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            cipher1.init(Cipher.DECRYPT_MODE, privKey);
            byte[] decryptedBytes = cipher1.doFinal(message.getBytes());
            String decrypted = new String(decryptedBytes);

            //System.out.println("DDecrypted?????"+decrypted);

            return decrypted;

        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        } catch (NoSuchPaddingException e){
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e){
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return null;
    }











}
