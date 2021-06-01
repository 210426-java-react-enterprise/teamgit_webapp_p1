package security;


import io.jsonwebtoken.*;

import javax.crypto.spec.*;
import javax.xml.bind.*;
import java.io.*;
import java.security.*;
import java.util.*;

public class JwtConfig {

    //we will always put our JWT in our Authorization, thus the header will be authorization
    public static String HEADER;
    public static String PREFIX;
    private static String SECRET_KEY;
    //makes a day
    public final int EXPIRATION = 24 * 60 * 60 * 1000;
    private static final SignatureAlgorithm sigAlg = SignatureAlgorithm.HS256;
    private static Key signingKey = null;


    static {
        try {
            Properties props = new Properties();
            InputStream is = Thread.currentThread()
                                   .getContextClassLoader()
                                   .getResourceAsStream("jwt.properties");
            props.load(is);

            SECRET_KEY = props.getProperty("SECRET_KEY");
            HEADER = props.getProperty("JWT_HEADER");
            PREFIX = props.getProperty("JWT_PREFIX");

            byte[] secretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
            signingKey = new SecretKeySpec(secretBytes, sigAlg.getJcaName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JwtConfig() {

    }

    public SignatureAlgorithm getSigAlg() {
        return sigAlg;
    }

    public Key getSigningKey() {
        return signingKey;
    }

    public String getHEADER() {
        return HEADER;
    }

    public String getPREFIX() {
        return PREFIX;
    }

    public int getEXPIRATION() {
        return EXPIRATION;
    }

}

