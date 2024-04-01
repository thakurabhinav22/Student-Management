import java.io.*;
import java.util.*;
import Security.Credential;

public class studentManagement {
    public static void main(String[] args) {
        HomePage obj = new HomePage();
    }
}

class HomePage {
    boolean userAuthSuccess = false;
    String cre = "";
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    GetStudentDetails studentDetails = new GetStudentDetails();

    HomePage() {
        File file = new File("Security/Resources.txt");
        File efile = new File("Security/studentSheet.txt");
        String[] headers = { "Name", "Surname", "Branch", "Enrollment" };
        int count = 0;
        try {
            if (!efile.exists()) {
                efile.createNewFile();
                FileWriter fileWriter = new FileWriter("Security/Resources.txt");
                fileWriter.write("YWRtaW46MTIz");
                fileWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileReader fileReader = new FileReader(file);
            Scanner sc = new Scanner(fileReader);
            while (sc.hasNextLine()) {
                cre += sc.nextLine();
            }
            sc.close();
            if (!cre.isEmpty()) {
                AuthUser obj = new AuthUser();
                userAuthSuccess = obj.validateUser();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (userAuthSuccess) {
            HomePageContent obj = new HomePageContent();
            obj.displayHomePageContent();
        }
    }
}

class AuthUser {
    Scanner scanner = new Scanner(System.in);

    public boolean validateUser() {
        System.out.print("\033c");
        System.out.println("+--------------------------------------------------------+");
        System.out.println("|                       Login                             |");
        System.out.println("+--------------------------------------------------------+");

        String userName;
        String password;
        String hash;

        do {
            System.out.print("UserId: ");
            userName = scanner.nextLine();
            System.out.print("Password: ");
            password = scanner.nextLine();
            Credential obj = new Credential();
            hash = obj.HashEnc(userName, password);


            boolean chk = obj.AuthUserValid(hash);

            if (chk) {
                return true;
            } else {
                System.out.print("\033c");
                System.out.println("+--------------------------------------------------------+");
                System.out.println("|                       Login                             |");
                System.out.println("+--------------------------------------------------------+");
                System.out.println("\u001B[31mInvalid UserName or Password\u001B[0m");
            }
        } while (true);
    }
}

class addstudentdata extends HomePageContent {
    addstudentdata(String Name, String Surname, String branch, String Enroll) {

        try {
            FileWriter eFileWriter = new FileWriter("Security/studentSheet.txt", true);
            eFileWriter.write(Enroll + "\t" + Name + "\t" + Surname + "\t" + branch + "\n");
            eFileWriter.close();
            // System.out.println("\033c");
            // HomePageContent obj = new HomePageContent();
            // obj.displayHomePageContent();
            eFileWriter.close();
        } catch (Exception e) {
            System.out.println("An Error Occurred While Updating Entries: " + e);
        }
    }
}

class Check {
    boolean check() {
        try (FileReader fileReader = new FileReader("Security/studentSheet.txt");
                Scanner scanner = new Scanner(fileReader)) {

            if (scanner.hasNextLine()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}

class displayStudentData {
    int i = 0;

    public displayStudentData() {
        boolean fileContent = false;

        try (FileReader fileReader = new FileReader("Security/studentSheet.txt")) {
            Scanner scanner = new Scanner(fileReader);
            Check c = new Check();
            fileContent = c.check();

            if (fileContent) {
                System.out.println(
                        "+-----------------+-----------------+-----------------+-----------------+-----------------+");
                System.out.println(
                        "|      SrNo       |   Enrollmentno  |      Name       |     Surname     |       Branch    |");
                System.out.println(
                        "+-----------------+-----------------+-----------------+-----------------+-----------------+");

                while (scanner.hasNextLine()) {
                    String studentData = scanner.nextLine();
                    String[] parts = studentData.split("\t");

                    System.out.printf("| %-15s | %-15s | %-15s | %-15s | %-15s |\n", ++i, parts[0], parts[1], parts[2],
                            parts[3]);
                    System.out.println(
                            "+-----------------+-----------------+-----------------+-----------------+-----------------+");
                }
                fileReader.close();
            } else {
                System.out.println("No data available in the file :(");
            }

        } catch (Exception e) {
            // System.out.println("An Error Occurred While Displaying Student Data: " + e);
        }
    }
}

class GetStudentDetails extends AuthUser {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String StudName, StudSurName, StudBranch, EnrollNo;

    public void addStudent() {
        System.out.print("\033c");
        System.out.println("+--------------------------------------------------------+");
        System.out.println("|                     ADD STUDENT                        |");
        System.out.println("+--------------------------------------------------------+");
        System.out.println("Type\u001B[31m 'exit'\u001B[0m for Main Menu");

        try {
            while (true) {
                System.out.print("Enter Name of Student: ");
                StudName = br.readLine();
                if (StudName.equalsIgnoreCase("exit")) {
                    HomePageContent obj = new HomePageContent();
                    obj.displayHomePageContent();
                }
                System.out.print("Enter Surname of Student: ");
                StudSurName = br.readLine();
                if (StudSurName.equalsIgnoreCase("exit")) {
                    HomePageContent obj = new HomePageContent();
                    obj.displayHomePageContent();
                }
                System.out.print("Enter Branch of Student: ");
                StudBranch = br.readLine();
                if (StudBranch.equalsIgnoreCase("exit")) {
                    HomePageContent obj = new HomePageContent();
                    obj.displayHomePageContent();
                }
                System.out.print("Enter Enrollment No of Student: ");
                EnrollNo = br.readLine();
                if (EnrollNo.equalsIgnoreCase("exit")) {
                    HomePageContent obj = new HomePageContent();
                    obj.displayHomePageContent();
                }
                addstudentdata obj = new addstudentdata(StudName, StudSurName, StudBranch, EnrollNo);
                System.out.print("\033c");
                System.out.println("+--------------------------------------------------------+");
                System.out.println("|                     ADD STUDENT                        |");
                System.out.println("+--------------------------------------------------------+");
                System.out.println("Type\u001B[31m 'exit'\u001B[0m for Main Menu");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchStudent() {
        int chk = 0;
        String enroll, searchres = "";
        System.out.print("\033c");
        System.out.println("+--------------------------------------------------------+");
        System.out.println("|                    Search Student                      |");
        System.out.println("+--------------------------------------------------------+");
        System.out.println("Type\u001B[31m 'exit'\u001B[0m for Main Menu");

        try {
            System.out.print("Enter Enrollment Number to Search: ");
            enroll = br.readLine();
            if (enroll.equalsIgnoreCase("exit")) {
                HomePageContent obj = new HomePageContent();
                obj.displayHomePageContent();
                return;
            }
            FileReader fileReader = new FileReader("Security/studentSheet.txt");
            Scanner s = new Scanner(fileReader);
            int i = 0;

            while (s.hasNextLine()) {
                searchres = s.nextLine();

                if (searchres.startsWith(enroll)) {
                    chk++;
                    if (chk >= 1) {
                        System.out.println(
                                "+-----------------+-----------------+-----------------+-----------------+-----------------+");
                        System.out.println(
                                "|      SrNo       |   Enrollmentno  |      Name       |     Surname     |       Branch    |");
                        System.out.println(
                                "+-----------------+-----------------+-----------------+-----------------+-----------------+");
                    }
                    String[] parts = searchres.split("\t");

                    System.out.printf("| %-15s | %-15s | %-15s | %-15s | %-15s |\n", ++i, parts[0], parts[1], parts[2],
                            parts[3]);
                    System.out.println(
                            "+-----------------+-----------------+-----------------+-----------------+-----------------+");
                    System.out.print("<Press Enter for Main Menu>");
                    br.readLine();
                    HomePageContent obj = new HomePageContent();
                    obj.displayHomePageContent();
                    return;
                }
            }

            if (i == 0) {
                System.out.println("\u001B[31mNo Student Record Found\u001B[0m");
                System.out.print("<Press Enter for Main Menu>");
                br.readLine();
                HomePageContent obj = new HomePageContent();
                obj.displayHomePageContent();
                return;
            }
        } catch (Exception ex) {
            System.out.print(ex);
        }

    }

    public void viewStudent() {
        String Enroll;
        System.out.print("\033c");
        System.out.println("+--------------------------------------------------------+");
        System.out.println("|                    Student List                        |");
        System.out.println("+--------------------------------------------------------+");
        System.out.println("Type\u001B[31m 'exit'\u001B[0m for Main Menu");
        try {
            displayStudentData obj = new displayStudentData();
            System.out.print("<Press Enter for Main Menu>");
            br.readLine();
            HomePageContent dis = new HomePageContent();
            dis.displayHomePageContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void deleteStudent() {
        String enrollToDelete = null;
        int totalrecords = 0;
        System.out.println("+--------------------------------------------------------+");
        System.out.println("|                    Delete Student                      |");
        System.out.println("+--------------------------------------------------------+");
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Enrollment for Student to delete: ");
        enrollToDelete = sc.nextLine();

        try {
            FileReader fileReader = new FileReader("Security/studentSheet.txt");
            Scanner s = new Scanner(fileReader);
            StringBuffer updatedContent = new StringBuffer();

            while (s.hasNextLine()) {
                String studentRecord = s.nextLine();

                if (!studentRecord.startsWith(enrollToDelete)) {
                    updatedContent.append(studentRecord).append("\n");
                } else {
                    totalrecords++;
                }
            }
            fileReader.close();

            FileWriter fileWriter = new FileWriter("Security/studentSheet.txt");
            fileWriter.write(updatedContent.toString());
            fileWriter.close();

            System.out.println("Total " + totalrecords + " Records deleted successfully.");
            System.out.print("<Press Enter for Main Menu>");
            br.readLine();
            HomePageContent obj = new HomePageContent();
            obj.displayHomePageContent();

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    void updateStudent() {
        int chkvalue = 0;
        String enrollmentToUpdate = null;
        System.out.println("+--------------------------------------------------------+");
        System.out.println("|                    Update Student                      |");
        System.out.println("+--------------------------------------------------------+");
        System.out.println("Type\u001B[31m 'exit'\u001B[0m for Main Menu");
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Enrollment for Student to update: ");
        enrollmentToUpdate = sc.nextLine();

        try {
            FileReader fileReader = new FileReader("Security/studentSheet.txt");
            Scanner s = new Scanner(fileReader);
            StringBuffer updatedContent = new StringBuffer();

            while (s.hasNextLine()) {
                String studentRecord = s.nextLine();

                if (studentRecord.startsWith(enrollmentToUpdate)) {
                    System.out.print("Student Details Starting with Enrollment no: " + enrollmentToUpdate + "\n"
                            + studentRecord + "\n");
                    chkvalue++;
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    if (name.equalsIgnoreCase("exit")) {
                        HomePageContent obj = new HomePageContent();
                        obj.displayHomePageContent();
                    }
                    System.out.print("Surname: ");
                    String Surname = sc.nextLine();
                    if (Surname.equalsIgnoreCase("exit")) {
                        HomePageContent obj = new HomePageContent();
                        obj.displayHomePageContent();
                    }
                    System.out.print("Branch: ");
                    String Branch = sc.nextLine();
                    if (Branch.equalsIgnoreCase("exit")) {
                        HomePageContent obj = new HomePageContent();
                        obj.displayHomePageContent();
                    }
                    String updatedStudentRecord = enrollmentToUpdate + "\t" + name + "\t" + Surname + "\t" + Branch;
                    updatedContent.append(updatedStudentRecord).append("\n");
                } else {

                    updatedContent.append(studentRecord).append("\n");
                }
            }
            fileReader.close();

            FileWriter fileWriter = new FileWriter("Security/studentSheet.txt");
            fileWriter.write(updatedContent.toString());
            fileWriter.close();

            if (chkvalue <= 0) {
                System.out.println("\u001B[31mNo Student Record Found\u001B[0m");
                System.out.print("<Press Enter for Main Menu>");
                br.readLine();
                HomePageContent obj = new HomePageContent();
                obj.displayHomePageContent();
                return;
            } else {
                System.out.println("Student with enrollment " + enrollmentToUpdate + " updated successfully.");
                System.out.print("<Press Enter for Main Menu>");
                br.readLine();
                HomePageContent obj = new HomePageContent();
                obj.displayHomePageContent();
                return;
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

class HomePageContent {
    String choice;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    void displayHomePageContent() {
        GetStudentDetails studentDetails = new GetStudentDetails();
        boolean valid = false;
        System.out.print("\033c");
        System.out.println("+--------------------------------------------------------+");
        System.out.println("|                      Main Menu                         |");
        System.out.println("+--------------------------------------------------------+");
        while (!valid) {
            System.out.print(
                    "1.Add Student\n2.View Student\n3.Search Student\n4.Delete Student\n5.Update Student\nEnter Your Choice: ");
            try {
                choice = br.readLine();
            } catch (Exception e) {
                System.out.println(e);
            }
            if ((choice != null && choice.length() > 0 && choice.charAt(0) == '1')) {
                valid = true;
                System.out.println("\033c");
                studentDetails.addStudent();
                return;
            } else if ((choice != null && choice.length() > 0 && choice.charAt(0) == '2')) {
                valid = true;
                System.out.println("\033c");
                studentDetails.viewStudent();
                return;
            } else if ((choice != null && choice.length() > 0 && choice.charAt(0) == '3')) {
                valid = true;
                System.out.println("\033c");
                studentDetails.searchStudent();
                return;
            } else if ((choice != null && choice.length() > 0 && choice.charAt(0) == '4')) {
                valid = true;
                System.out.println("\033c");
                studentDetails.deleteStudent();
                return;
            } else if ((choice != null && choice.length() > 0 && choice.charAt(0) == '5')) {
                valid = true;
                System.out.println("\033c");
                studentDetails.updateStudent();
                return;
            } else {
                System.out.print("\033c");
                System.out.println("+--------------------------------------------------------+");
                System.out.println("|                      Main Menu                         |");
                System.out.println("+--------------------------------------------------------+");
                System.out.println("\u001B[31mInvalid Input\u001B[0m");
            }
        }
    }
}
