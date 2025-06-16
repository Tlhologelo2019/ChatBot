import javax.swing.*;
import java.util.ArrayList;

public class Login {

    public boolean checkUserName(String username) {
        return !username.contains("_") || username.length() != 5;
    }

    public boolean checkUserPassword(String password) {
        return password.length() < 8
                || !password.matches(".*[A-Z].*")
                || !password.matches(".*[0-9].*")
                || !password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")
                || !password.matches(".*[a-z].*");
    }

    public boolean checkCellPhoneNumber(String cellPhone) {
        String cleanedNumber = cellPhone.replaceAll("\\s+", "");
        return !cleanedNumber.matches(".*\\+27[0-9]{9}$") && !cleanedNumber.matches(".*^0[0-9]{9}");
    }

    public boolean login(ArrayList<String> userNames, ArrayList<String> passwords, String inputUsername, String inputPassword) {
        for (int i = 0; i < userNames.size(); i++) {
            if (userNames.get(i).equals(inputUsername) && passwords.get(i).equals(inputPassword)) {
                JOptionPane.showMessageDialog(null, "Login successful. Welcome " + inputUsername + "!");
                return true;
            }
        }
        return false;
    }

}
