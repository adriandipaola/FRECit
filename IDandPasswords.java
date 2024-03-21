import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Random;

public class IDandPasswords {
    private HashMap<String, String> logininfo;
    private static final String FILE_NAME = "userdata.dat";
    private static final int SALT_LENGTH = 16;

    public IDandPasswords() {
        logininfo = new HashMap<>();
        loadUserData(); // Load user data when the class is instantiated
    }

    // Add a new user with hashed and salted password
    public void addUser(String username, String password, String gender) {
        String salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);
        // where gender is stored
        logininfo.put(username, hashedPassword + "|" + salt + "|" + gender);
        saveUserData(); // Save user data after adding a new user
    }

    // Where it authenticates users
    public boolean authenticateUser(String username, String password) {
        if (logininfo.containsKey(username)) {
            String storedInfo = logininfo.get(username);
            String storedPassword = storedInfo.split("\\|")[0];
            String salt = storedInfo.split("\\|")[1];
            String hashedPassword = hashPassword(password, salt);
            return storedPassword.equals(hashedPassword);
        }
        return false;
    }

    // Check if the user exists
    public boolean userExists(String username) {
        return logininfo.containsKey(username);
    }

    // Load user data from a file
    private void loadUserData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            logininfo = (HashMap<String, String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Handle file not found or deserialization error
            e.printStackTrace();
        }
    }

    // Save user data to a file
    private void saveUserData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(logininfo);
        } catch (IOException e) {
            // Handle file output error
            e.printStackTrace();
        }
    }

    // Generate a random salt
    private String generateSalt() {
        Random random = new Random();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Hash password with salt using SHA-256
    private String hashPassword(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(Base64.getDecoder().decode(salt));
            byte[] hashedBytes = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public HashMap<String, String> getAllUsers() {
        return logininfo;
    }
}
