package com.onezero.ozerp.appbase.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Remark: the rational of using char[] instead of {@link String} is to
 * allow the destruction of secret credential data by overwriting the
 * contents of the char[]. This is not possible with String: relying on
 * the system garbage collector to eventually erase credentials from
 * memory.
 */
public class Password {

    //private static Logger log = LoggerFactory.getLogger(Password.class);

    /**
     * Each token produced by this class uses this identifier as a prefix.
     */
    public static final String ID = "$FEHP1$";
    public static final int PIN_LENGTH = 8;
    /**
     * Choice of hash algorithm.
     */
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    /**
     * Cost of hash: higher means more CPU cycles needed to generate hash. This
     * is an exponent to the number of iterations. An increase of one will
     * double the number of iterations.
     */
    private static final int COST = 16;
    private static final int SIZE = 128;
    private static final Pattern LAYOUT = Pattern.compile("\\$FEHP1\\$(\\d\\d?)\\$(.{43})");

    /**
     * Make hash token from password. Token embeds token id, cost parameter,
     * salt and the actual hash. Use of char[] is so that implementation can
     * wipe clean from memory immediately after use (not so easy with String object).
     *
     * @param password
     * @return
     */
    public static String makeHashToken(char[] password) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SIZE / 8];
        random.nextBytes(salt);
        byte[] dk = pbkdf2(password, salt, 1 << COST);
        byte[] hash = new byte[salt.length + dk.length];
        System.arraycopy(salt, 0, hash, 0, salt.length);
        System.arraycopy(dk, 0, hash, salt.length, dk.length);
        Base64.Encoder enc = Base64.getUrlEncoder().withoutPadding();
        return ID + COST + '$' + enc.encodeToString(hash);
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations) {
        KeySpec spec = new PBEKeySpec(password, salt, iterations, SIZE);
        try {
            SecretKeyFactory f = SecretKeyFactory.getInstance(ALGORITHM);
            return f.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Missing algorithm: " + ALGORITHM, ex);
        } catch (InvalidKeySpecException ex) {
            throw new IllegalStateException("Invalid SecretKeyFactory", ex);
        }
    }

    /**
     * Return true if token is a value hash password token.
     *
     * @param token
     * @return
     */
    public static boolean isHashToken(String token) {
        Matcher m = LAYOUT.matcher(token);
        return m.matches();
    }

    /**
     * Authenticate with a password and a stored password token.
     *
     * @return true if the password and token match
     */
    public static boolean authenticate(char[] password, String token) {

        Matcher m = LAYOUT.matcher(token);
        if (!m.matches()) {
            throw new IllegalArgumentException("Invalid token format");
        }
        int iterations = iterations(Integer.parseInt(m.group(1)));
        byte[] hash = Base64.getUrlDecoder().decode(m.group(2));
        byte[] salt = Arrays.copyOfRange(hash, 0, SIZE / 8);
        byte[] check = pbkdf2(password, salt, iterations);
        int zero = 0;
        for (int i = 0; i < check.length; ++i) {
            zero |= hash[salt.length + i] ^ check[i];
        }
        return zero == 0;
    }

    public static String makeHashToken(String password) {
        return makeHashToken(password.toCharArray());
    }

    public static boolean authenticate(String password, String token) {
        return authenticate(password.toCharArray(), token);
    }


    private static int iterations(int cost) {
        // Is cost outside allowed range? (avoid potential DOS attack by
        // supplying impossibly costly tokens to check
        if (cost < 1 || cost > 31) {
            throw new IllegalArgumentException("cost: " + cost);
        }
        return 1 << cost;
    }

    /**
     * Generate a random 6 digit password.
     *
     * @return
     */
    public static String makeRandomPassword() {
        SecureRandom sr = new SecureRandom();

        // Mask upper bits to ensure positive number
        long random = sr.nextLong() & 0x00ffffffffffffffL;

        // 8 digits
        long pin = (random % 100000000L);
        return String.format("%08d", pin);
    }
}