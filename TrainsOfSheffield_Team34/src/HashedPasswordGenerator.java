import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The HashedPasswordGenerator class provides methods to hash passwords using SHA-256.
 */
public class HashedPasswordGenerator {
    // Replace with your own static salt
    private static final String SALT = "MyStaticSalt";

    /**
     * Hashes the provided password using SHA-256.
     *
     * @param password The password to be hashed.
     * @return The hashed password as a hexadecimal string.
     */
    public static String hashPassword(char[] password) {
        try {
            // Create a MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Concatenate the salt and password bytes
            byte[] saltedPasswordBytes = concatenateBytes(SALT.getBytes(), new String(password).getBytes());

            // Update the digest with the salted password bytes
            md.update(saltedPasswordBytes);

            // Get the hashed password bytes
            byte[] hashedPasswordBytes = md.digest();

            // Convert the hashed password bytes to a hexadecimal string
            StringBuilder hexStringBuilder = new StringBuilder();
            for (byte b : hashedPasswordBytes) {
                hexStringBuilder.append(String.format("%02x", b));
            }
            return hexStringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception, e.g., log it or throw a custom exception
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Concatenates two byte arrays.
     *
     * @param arr1 The first byte array.
     * @param arr2 The second byte array.
     * @return The combined byte array.
     */
    public static byte[] concatenateBytes(byte[] arr1, byte[] arr2) {
        byte[] combined = new byte[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, combined, 0, arr1.length);
        System.arraycopy(arr2, 0, combined, arr1.length, arr2.length);
        return combined;
    }

    /**
     * Main method for testing the HashedPasswordGenerator class.
     *
     * @param args Command line arguments (not used in this example).
     */
    public static void main(String[] args) {
        char[] password = "asd@456".toCharArray();
        String hashedPassword = hashPassword(password);

        System.out.println("Original Password: " + new String(password));
        System.out.println("Hashed Password: " + hashedPassword);
    }
}