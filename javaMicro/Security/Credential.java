package Security;
import java.io.*;
import java.util.*;

public class Credential {
    public String HashEnc(String Username, String Password) {
        String combined = Username + ":" + Password;
        return encrypt(combined);
    }

    private String encrypt(String input) {
        byte[] encodedBytes = Base64.getEncoder().encode(input.getBytes());
        String encodedString = new String(encodedBytes);
        return encodedString;
    }

    public boolean AuthUserValid(String encyptedCredit) {
        String enc = null;
        try {
            FileReader fileReader = new FileReader("Security/Resources.txt");
            Scanner sc = new Scanner(fileReader);

            while (sc.hasNextLine()) {
                enc = sc.nextLine();
            }
            sc.close();
            if (encyptedCredit.equals(enc))
                return true;
        } catch (FileNotFoundException e) {

            System.out.println("Something Went Wrong");
        }

            return false;
    }
}
