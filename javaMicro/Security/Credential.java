package Security;
import java.io.*;
import java.util.*;

public class Credential {
    public String HashEnc(String Username, String Password) {
        String combined = Username + ":" + Password;
        return encrypt(combined);
    }

    public String encrypt(String input) {
        String encodedString="";
        for(int i = 0 ; i < input.length() ; i++){
            char IntoAscii = input.charAt(i);
            long AsciiInt = (int)  IntoAscii;
            encodedString += AsciiInt * input.length();
        }
        for(int i = 0; i < input.length(); i++ ){
            char enc = encodedString.charAt(i);
            long AsciiInt = (int)  enc;
            encodedString += AsciiInt * input.length(); 
        }
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
