package ir.maktab32.java.projects.hw6.scholarshipmanagement.core.utilities;

import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.share.AuthenticationService;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.model.User;

public class Menu {
    public static void display(){
        User loginUser = AuthenticationService.getInstance().getLoginUser();
        if (loginUser == null){
            System.out.println("\t+------------------------------------------------------+");
            System.out.println("\t|                         Menu                         |");
            System.out.println("\t+------------------------------------------------------+");
            System.out.println("\t| login     -->  Login.                                |");
            System.out.println("\t| register  -->  Register as a New Student.            |");
            System.out.println("\t| exit      -->  Exit App.                             |");
            System.out.println("\t+------------------------------------------------------+");        }

        else if (loginUser.getRole().equals("Student")) {
            System.out.println("\t+------------------------------------------------------+");
            System.out.println("\t|                         Menu                         |");
            System.out.println("\t+------------------------------------------------------+");
            System.out.println("\t| request   -->  Request Scholarship as Student.       |");
            System.out.println("\t| stulist   -->  Displays Student's List.              |");
            System.out.println("\t| dashboard -->  Displays Dashboard                    |");
            System.out.println("\t| history   -->  Displays Logs for Given Scholarship.  |");
            System.out.println("\t| logout    -->  Logout.                               |");
            System.out.println("\t| exit      -->  Exit App.                             |");
            System.out.println("\t+------------------------------------------------------+");
        }
        else if (loginUser.getRole().equals("Supervisor")) {
            System.out.println("\t+------------------------------------------------------+");
            System.out.println("\t|                         Menu                         |");
            System.out.println("\t+------------------------------------------------------+");
            System.out.println("\t| svlist    -->  Displays Supervisor's List.           |");
            System.out.println("\t| svaccept  -->  Supervisor Accepts Given Scholarship. |");
            System.out.println("\t| svreject  -->  Supervisor Rejects Given Scholarship. |");
            System.out.println("\t| dashboard -->  Displays Dashboard                    |");
            System.out.println("\t| history   -->  Displays Logs for Given Scholarship.  |");
            System.out.println("\t| logout    -->  Logout.                               |");
            System.out.println("\t| exit      -->  Exit App.                             |");
            System.out.println("\t+------------------------------------------------------+");
        }
        else if (loginUser.getRole().equals("Manager")) {
            System.out.println("\t+------------------------------------------------------+");
            System.out.println("\t|                         Menu                         |");
            System.out.println("\t+------------------------------------------------------+");
            System.out.println("\t| mnglist   -->  Displays Manager's List.              |");
            System.out.println("\t| mngaccept -->  Manager Accepts Given Scholarship.    |");
            System.out.println("\t| mngreject -->  Manager Rejects Given Scholarship.    |");
            System.out.println("\t| all       -->  Displays All Scholarships.            |");
            System.out.println("\t| dashboard -->  Displays Dashboard                    |");
            System.out.println("\t| history   -->  Displays Logs for Given Scholarship.  |");
            System.out.println("\t| logout    -->  Logout.                               |");
            System.out.println("\t| exit      -->  Exit App.                             |");
            System.out.println("\t+------------------------------------------------------+");
        }
        else if (loginUser.getRole().equals("University")) {
            System.out.println("\t+------------------------------------------------------+");
            System.out.println("\t|                         Menu                         |");
            System.out.println("\t+------------------------------------------------------+");
            System.out.println("\t| unilist   -->  Displays University's List.           |");
            System.out.println("\t| dashboard -->  Displays Dashboard                    |");
            System.out.println("\t| history   -->  Displays Logs for Given Scholarship.  |");
            System.out.println("\t| logout    -->  Logout.                               |");
            System.out.println("\t| exit      -->  Exit App.                             |");
            System.out.println("\t+------------------------------------------------------+");
        }
    }
}
