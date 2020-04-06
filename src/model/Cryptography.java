package model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Cryptography {

    private static MessageDigest md = null;

    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String createHash(String data) {
        if (md != null) {
            String hash;
            md.update(data.getBytes(), 0, data.length());
            BigInteger dataAux = new BigInteger(1, md.digest());
            hash = String.format("%1$032X", dataAux);
            return hash;
        }
        return null;
    }
}
