package ir.maktab32.java.projects.hw6.scholarshipmanagement;

import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.share.AuthenticationService;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.dashboard.impl.DashboardUseCaseImpl;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.dashboard.usecases.DashboardUseCase;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.logmanagement.impl.FindAllScholarshipsByApplicationUseCaseImpl;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.logmanagement.impl.FindScholarshipHistoryUseCaseImpl;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.logmanagement.usecases.FindAllScholarshipsByApplicationUseCase;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.logmanagement.usecases.FindScholarshipHistoryUseCase;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.scholarshipverification.impl.*;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.scholarshipverification.usecases.*;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.usermanagement.impl.LoginUseCaseImpl;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.usermanagement.impl.LogoutUseCaseImpl;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.usermanagement.usecases.LoginUseCase;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.usermanagement.usecases.LogoutUseCase;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.model.Scholarship;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.model.ScholarshipLog;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.model.User;

import java.util.List;
import java.util.Scanner;

public class ScholarshipManagementApplication {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String command = "";
        while (! command.equals("exit")) {

            System.out.println("\u29bf What Do You Want To Do?\t(Press m to See Menu)");
            command = scanner.nextLine();

            //display menu
            if (command.equals("m")){
                displayMenu();
            }

            // Login
            else if (command.equals("login")) {
                if (AuthenticationService.getInstance().getLoginUser() != null){
                    System.out.println("\t\u26a0 You Are Logged In as "
                            + AuthenticationService.getInstance().getLoginUser().getUsername()
                    + "! Please Logout First!");
                }
                else {
                    System.out.print("\t\u29bf Username : ");
                    String username = scanner.nextLine();
                    System.out.print("\t\u29bf Password : ");
                    String password = scanner.nextLine();
                    LoginUseCase loginUseCase = new LoginUseCaseImpl();
                    User user = loginUseCase.login(username, password);
                    if (user != null) {
                        System.out.println("\t\t\u2705 Login Successful by " + user.getRole() + "!");
                    } else {
                        System.out.println("\t\t\u26a0 Login Failed! Invalid Username or Password!");
                    }
                }
            }

            //logout
            else if (command.equals("logout")){
                if (AuthenticationService.getInstance().getLoginUser() == null){
                    System.out.println("\t\u26a0 You Aren't Logged In!");
                }
                else {
                    LogoutUseCase logoutUseCase = new LogoutUseCaseImpl();
                    logoutUseCase.logout();
                    System.out.println("\t\u2705 Logout successful!");
                }
            }

            //request scholarship by student
            else if(command.equals("request")){
                User loginUser = AuthenticationService.getInstance().getLoginUser();
                if (loginUser == null || !loginUser.getRole().equals("Student")){
                    System.out.println("\t\u26a0 Please Login as Student!");
                }
                else {
                    System.out.print("\t\u29bf Name: ");
                    String name = scanner.nextLine();
                    System.out.print("\t\u29bf Family: ");
                    String family = scanner.nextLine();
                    String nationalCode = "";
                    do {
                        System.out.print("\t\u29bf National Code: ");
                        nationalCode = scanner.nextLine();
                        if (nationalCode.length() != 10)
                            System.out.println("\t\t\u26a0 Invalid National Code Length!");
                    }while (nationalCode.length() != 10);

                    System.out.print("\t\u29bf Last University: ");
                    String lastUni = scanner.nextLine();
                    System.out.print("\t\u29bf Last Degree: ");
                    String lastDegree = scanner.nextLine();
                    System.out.print("\t\u29bf Last Field: ");
                    String lastField = scanner.nextLine();

                    String strScore;
                    do {
                        System.out.print("\t\u29bf Last Score: ");
                        strScore = scanner.nextLine();
                        if (!isFloat(strScore))
                            System.out.println("\t\t\u26a0 Invalid Score!");
                    }while (!isFloat(strScore));
                    Float lastScore = Float.parseFloat(strScore);

                    System.out.print("\t\u29bf Apply University: ");
                    String applyUni = scanner.nextLine();
                    System.out.print("\t\u29bf Apply Degree: ");
                    String applyDegree = scanner.nextLine();
                    System.out.print("\t\u29bf Apply Field: ");
                    String applyField = scanner.nextLine();
                    System.out.print("\t\u29bf Apply Date: ");
                    String applyDate = scanner.nextLine();

                    Scholarship scholarship = new Scholarship(null, "RequestedByStudent", name, family, nationalCode
                            , lastUni, lastDegree, lastField, lastScore, applyUni, applyDegree, applyField, applyDate);

                    new RequestScholarshipByStudentUseCaseImpl().request(scholarship);
                    System.out.println("\t\t\u2705 Request Sent Successfully!");
                }

            }


            // find or accept or rejrct by supervisor
            else if (command.equals("svlist") || command.equals("svaccept") || command.equals("svreject")){
                User loginUser = AuthenticationService.getInstance().getLoginUser();
                if (loginUser == null || !loginUser.getRole().equals("Supervisor")){
                    System.out.println("\t\u26a0 Please Login as Supervisor!");
                }
                else {
                    FindScholarshipBySupervisorUseCase findScholarshipBySupervisorUseCase
                            = new FindScholarshipBySupervisorUseCaseImpl();

                    List<Scholarship> scholarships = findScholarshipBySupervisorUseCase
                            .listScholarships();

                    if (command.equals("svlist")) {
                        System.out.println("+-----------------------+\n|   Supervisor's List   |\n+-----------------------+");
                        if (scholarships.size() == 0)
                            System.out.println("Supervisor's List is Empty!");
                        for (int i = 0; i < scholarships.size(); i++) {
                            System.out.println(scholarships.get(i));
                        }
                    }
                    else {
                        System.out.println("Scholarship Id: ");
                        String scholarshipId = scanner.nextLine();

                        if (!isLong(scholarshipId)) {
                            System.out.println("\t\u26a0 Invalid Scholarship Id!");
                        }
                        else {
                            Scholarship selectedScholarship = null;
                            for (Scholarship i : scholarships) {
                                if (i.getId() == Long.parseLong(scholarshipId)){
                                    selectedScholarship = i;
                                }
                            }
                            if (selectedScholarship == null){
                                System.out.println("\t\u26a0 Entered Scholarship Doesn't Exist in Supervisor's List!");
                            }
                            else {
                                if (command.equals("svaccept")){
                                    AcceptScholarshipBySupervisorUseCase acceptScholarshipBySupervisorUseCase
                                            = new AcceptScholarshipBySupervisorUseCaseImpl();
                                    acceptScholarshipBySupervisorUseCase.accept(Long.parseLong(scholarshipId));
                                    System.out.println("\t\u2705 Accepted by Supervisor Successfully!");
                                }
                                else {
                                    RejectScholarshipBySupervisorUseCase rejectScholarshipBySupervisorUseCase
                                            = new RejectScholarshipBySupervisorUseCaseImpl();
                                    rejectScholarshipBySupervisorUseCase.reject(Long.parseLong(scholarshipId));
                                    System.out.println("\t\u2705 Rejected by Supervisor Successfully!");
                                }

                            }
                        }
                    }
                }
            }

            // find scholarship by supervisor
//            else if (command.equals("svlist")) {
//                User loginUser = AuthenticationService.getInstance().getLoginUser();
//                if (loginUser == null || !loginUser.getRole().equals("Supervisor")){
//                    System.out.println("\t\u26a0 Please Login as Supervisor!");
//                }
//                else {
//                    FindScholarshipBySupervisorUseCase findScholarshipBySupervisorUseCase
//                            = new FindScholarshipBySupervisorUseCaseImpl();
//
//                    List<Scholarship> scholarships = findScholarshipBySupervisorUseCase
//                            .listScholarships();
//                    System.out.println("+-----------------------+\n|   Supervisor's List   |\n+-----------------------+");
//                    if (scholarships.size() == 0)
//                        System.out.println("Supervisor's List is Empty!");
//                    for (int i = 0; i < scholarships.size(); i++) {
//                        System.out.println(scholarships.get(i));
//                    }
//                }
//            }



            // accept by supervisor
//            else if (command.equals("svaccept")) {
//                User loginUser = AuthenticationService.getInstance().getLoginUser();
//                if (loginUser == null || !loginUser.getRole().equals("Supervisor")) {
//                    System.out.println("\t\u26a0 Please Login as Supervisor!");
//                }
//                else {
//                    AcceptScholarshipBySupervisorUseCase acceptScholarshipBySupervisorUseCase
//                            = new AcceptScholarshipBySupervisorUseCaseImpl();
//
//                    FindScholarshipBySupervisorUseCase findScholarshipBySupervisorUseCase
//                            = new FindScholarshipBySupervisorUseCaseImpl();
//                    List<Scholarship> scholarships = findScholarshipBySupervisorUseCase.listScholarships();
//
//                    System.out.println("Scholarship Id: ");
//                    String scholarshipId = scanner.nextLine();
//
//                    if (!isLong(scholarshipId)) {
//                        System.out.println("\t\u26a0 Invalid Scholarship Id!");
//                    }
//                    else {
//                        Scholarship selectedScholarship = null;
//                        for (Scholarship i : scholarships) {
//                            if (i.getId() == Long.parseLong(scholarshipId)){
//                                selectedScholarship = i;
//                            }
//                        }
//                        if (selectedScholarship == null){
//                            System.out.println("\t\u26a0 Entered Scholarship Doesn't Exist in Supervisor's List!");
//                        }
//                        else {
//                            acceptScholarshipBySupervisorUseCase.accept(Long.parseLong(scholarshipId));
//                            System.out.println("\t\u2705 Accepted by Supervisor Successfully!");
//                        }
//                    }
//
//
//                }
//            }

            //reject by supervisor
//            else if (command.equals("svreject")){
//
//                User loginUser = AuthenticationService.getInstance().getLoginUser();
//                if (loginUser == null || !loginUser.getRole().equals("Supervisor")) {
//                    System.out.println("\t\u26a0 Please Login as Supervisor!");
//                }
//                else {
//                    RejectScholarshipBySupervisorUseCase rejectScholarshipBySupervisorUseCase
//                            = new RejectScholarshipBySupervisorUseCaseImpl();
//                    System.out.println("Scholarship Id: ");
//                    String scholarshipId = scanner.nextLine();
//                    rejectScholarshipBySupervisorUseCase.reject(Long.parseLong(scholarshipId));
//                    System.out.println("Done.");
//                }
//            }

            // find scholarship by manager
            else if (command.equals("mnglist")){
                FindScholarshipByManagerUseCase findScholarshipByManagerUseCase
                        = new FindScholarshipByManagerUseCaseImpl();
                List<Scholarship> scholarships = findScholarshipByManagerUseCase.listScholarships();
                if (scholarships.size() == 0){
                    System.out.println("Manager's List is Empty!");
                }
                for (int i = 0; i < scholarships.size(); i++){
                    System.out.println(scholarships.get(i));
                }
            }

            //accept by manager
            else  if (command.equals("mngaccept")){
                AcceptScholarshipByManagerUseCase acceptScholarshipByManagerUseCase
                        = new AcceptScholarshipByManagerUseCaseImpl();
                System.out.print("Scholarship Id: ");
                String scholarshipId = scanner.next();
                acceptScholarshipByManagerUseCase.accept(Long.parseLong(scholarshipId));
                System.out.println("Done.");
            }

            //reject by manager
            else if (command.equals("mngreject")){
                RejectScholarshipByManagerUseCase rejectScholarshipByManagerUseCase
                        = new RejectScholarshipByManagerUseCaseImpl();
                System.out.print("Scholarship Id: ");
                String scholarshipId = scanner.next();
                rejectScholarshipByManagerUseCase.reject(Long.parseLong(scholarshipId));
                System.out.println("Done.");
            }

            //find by university
            else if (command.equals("unilist")){
                FindScholarshipByUniversityUseCase findScholarshipByUniversityUseCase
                        = new FindScholarshipByUniversityUseCaseImpl();
                List<Scholarship> scholarships = findScholarshipByUniversityUseCase.listScholarships();
                if (scholarships.size() == 0){
                    System.out.println("University List is Empty!");
                }
                for (int i =0; i < scholarships.size(); i++){
                    System.out.println(scholarships.get(i));
                }
            }

            //display dashboard
            else if (command.equals("dashboard")){
                DashboardUseCase dashboardUseCase = new DashboardUseCaseImpl();
                dashboardUseCase.display();
            }

            //display given scholarship's logs
            else if (command.equals("history")){
                System.out.print("Scholarship Id: ");
                Long scholarshipId = scanner.nextLong();
                FindScholarshipHistoryUseCase findScholarshipHistoryUseCase
                        = new FindScholarshipHistoryUseCaseImpl();
                List<ScholarshipLog> logs = findScholarshipHistoryUseCase.listLogs(scholarshipId);

                for (ScholarshipLog scholarshipLog : logs){
                    System.out.println(scholarshipLog);
                }
            }

            else if (command.equals("exit"));
            else {
                System.out.println("Invalid Command!");
            }

            System.out.println();
        }
    }

    public static void displayMenu(){

        System.out.println("\t+------------------------------------------------------+");
        System.out.println("\t|                         Menu                         |");
        System.out.println("\t+------------------------------------------------------+");
        System.out.println("\t| login     -->  Login.                                |");
        System.out.println("\t| request   -->  Request Scholarship as Student.       |");
        System.out.println("\t| svlist    -->  Displays Supervisor's List.           |");
        System.out.println("\t| svaccept  -->  Supervisor Accepts Given Scholarship. |");
        System.out.println("\t| svreject  -->  Supervisor Rejects Given Scholarship. |");
        System.out.println("\t| mnglist   -->  Displays Manager's List.              |");
        System.out.println("\t| mngaccept -->  Manager Accepts Given Scholarship.    |");
        System.out.println("\t| mngreject -->  Manager Rejects Given Scholarship.    |");
        System.out.println("\t| unilist   -->  Displays University's List.           |");
        System.out.println("\t| dashboard -->  Displays Dashboard                    |");
        System.out.println("\t| history   -->  Displays Logs for Given Scholarship.  |");
        System.out.println("\t| exit      -->  Exit.                                 |");
        System.out.println("\t+------------------------------------------------------+");

    }


    public static boolean isFloat(String string){
        try {
            Float.parseFloat(string);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public static boolean isLong(String string){
        try {
            Long.parseLong(string);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

}
