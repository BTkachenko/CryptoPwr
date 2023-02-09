import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.ChaCha20ParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static com.company.ChaCha20v2.convertBytesToHex;

public class ChaCha20Enc
{
    private static SecretKey getKey(String name) throws NoSuchAlgorithmException, IOException {


        byte a[] = name.getBytes();
        byte b[] = new byte[32-a.length];
        for (byte by: b
             ) {
            by = (byte) 0x000000FF;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        outputStream.write( a );
        outputStream.write( b );

        byte c[] = outputStream.toByteArray( );

        SecretKey secretKey = new SecretKeySpec(c, "ChaCha20");
        return  secretKey;
    }

    // 96-bit nonce (12 bytes)
    private static byte[] getNonce() {
        byte[] newNonce = new byte[12];
        for (byte b: newNonce) {
            b = (byte) 0x000000FF;
        }
        return newNonce;
    }

    public static void main(String[] args) throws Exception
    {
        byte[] nonce = getNonce();
        int counter = 1;
        byte[] input = new byte[128];
        for (byte b:
                input) {
            b = (byte) 0x000000FF;
        }
        // Generate Key
        SecretKey key = getKey("Bohdan");

        System.out.println("Original Text  : " + input.toString());

        byte[] cipherText = encrypt(input, key,nonce,counter);
        System.out.println("Encrypted Text : " + Base64.getEncoder().encodeToString(cipherText));
        System.out.println("Cypher text length: "+ cipherText.length);
        try (FileOutputStream outputStream = new FileOutputStream("bytes")) {
            outputStream.write(cipherText);
            outputStream.close();
        }
        byte [] cipherText1 = new byte[128];
        try(FileInputStream inputStream = new FileInputStream("bytes"))
        {
            inputStream.read(cipherText1);
        }

        byte[] decryptedText = decrypt(cipherText1, key,nonce,counter);
        System.out.println("DeCrypted Text : " + decryptedText);

       // System.out.println("HEX : \n Input: "+ convertBytesToHex(input) +"\n Decrypt : "+ convertBytesToHex(decryptedText) );
        Boolean flag = true;
        for(int i = 0 ; i <128;i++)
        {

            if( input[i] != decryptedText[i]) {
                System.out.println("Byte #" + i + " is different");
                flag = false;
            }

        }
        System.out.println("All bytes in input and decrypt are equal: "+ flag);

    }

    public static byte[] encrypt(byte[] plaintext, SecretKey key,byte[] nonceBytes,int counter) throws Exception
    {


        // Get Cipher Instance
        Cipher cipher = Cipher.getInstance("ChaCha20");

        // Create ChaCha20ParameterSpec
        ChaCha20ParameterSpec paramSpec = new ChaCha20ParameterSpec(nonceBytes, counter);

        // Create SecretKeySpec
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "ChaCha20");

        // Initialize Cipher for ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);

        // Perform Encryption
        byte[] cipherText = cipher.doFinal(plaintext);

        return cipherText;
    }

    public static byte[] decrypt(byte[] cipherText, SecretKey key,byte[] nonceBytes,int counter) throws Exception
    {


        // Get Cipher Instance
        Cipher cipher = Cipher.getInstance("ChaCha20");

        // Create ChaCha20ParameterSpec
        ChaCha20ParameterSpec paramSpec = new ChaCha20ParameterSpec(nonceBytes, counter);

        // Create SecretKeySpec
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "ChaCha20");

        // Initialize Cipher for DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);

        // Perform Decryption
        byte[] decryptedText = cipher.doFinal(cipherText);

        return  decryptedText;
    }
}