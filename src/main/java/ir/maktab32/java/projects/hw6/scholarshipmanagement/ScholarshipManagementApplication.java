package ir.maktab32.java.projects.hw6.scholarshipmanagement;

import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.share.AuthenticationService;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.utilities.DisplayData;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.utilities.Menu;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.core.utilities.MyMath;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.dashboard.impl.DashboardUseCaseImpl;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.dashboard.usecases.DashboardUseCase;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.logmanagement.impl.FindScholarshipHistoryUseCaseImpl;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.logmanagement.usecases.FindScholarshipHistoryUseCase;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.scholarshipverification.impl.*;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.scholarshipverification.usecases.*;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.usermanagement.impl.FindAllUsersUseCaseImpl;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.usermanagement.impl.LoginUseCaseImpl;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.usermanagement.impl.LogoutUseCaseImpl;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.usermanagement.impl.RegisterUseCaseImpl;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.usermanagement.usecases.LoginUseCase;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.usermanagement.usecases.LogoutUseCase;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.usermanagement.usecases.RegisterUseCase;
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
            System.out.println("\n\u29bf What Do You Want To Do?\t(Press m to See Menu)");
            command = scanner.nextLine();

            // ************************************menu***************************************
            if (command.equals("m"))
                Menu.display();

            // ************************************register***********************************

            else if (command.equals("register")){
                if (AuthenticationService.getInstance().getLoginUser() != null){
                    System.out.println("\t\u26a0 You Are Logged In as "
                            + AuthenticationService.getInstance().getLoginUser().getUsername()
                            + "! Please Logout First!");
                }
                else {
                    List<User> users = new FindAllUsersUseCaseImpl().getUsersList();
                    String username = null;
                     do {
                        System.out.print("\t\u29bf Username : ");
                        username = scanner.nextLine();
                        for (User i : users){
                            if (i.getUsername().equals(username))
                                username = null;
                        }
                        if (username == null)
                            System.out.println("\t\t\u2705 This Username Already Exists! Choose Another Username!");
                    }while (username == null);
                    System.out.print("\t\u29bf Password: ");
                    String password = scanner.nextLine();
                    RegisterUseCase registerUseCase = new RegisterUseCaseImpl();
                    User user = registerUseCase.register(new User(null, username, password, "Student"));
                    if (user != null) {
                        System.out.println("\t\t\u2705 Register Successful by " + user.getUsername() + " as " + user.getRole() + "!");
                        System.out.println("\t\t\u2705 Your User Id: " + user.getId());
                        AuthenticationService.getInstance().setLoginUser(user);
                        System.out.println("\t\t\u2705 Login Successful by " + user.getUsername() + " as " + user.getRole() + "!");
                    } else {
                        System.out.println("\t\t\u26a0 Register Failed!");
                    }
                }

            }

            // *************************************Login*************************************
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
                        System.out.println("\t\t\u2705 Login Successful by " + user.getUsername() + " as " + user.getRole() + "!");
                    } else {
                        System.out.println("\t\t\u26a0 Login Failed! Invalid Username or Password!");
                    }
                }
            }

            //**************************************logout************************************
            else if (command.equals("logout")){
                if (AuthenticationService.getInstance().getLoginUser() == null){
                    System.out.println("\t\u26a0 You Aren't Logged In!");
                }
                else {
                    LogoutUseCase logoutUseCase = new LogoutUseCaseImpl();
                    logoutUseCase.logout();
                    System.out.println("\t\u2705 Logout Successful!");
                }
            }

            //************************request scholarship by student***************************
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
                        if (!MyMath.isFloat(strScore))
                            System.out.println("\t\t\u26a0 Invalid Score!");
                    }while (!MyMath.isFloat(strScore));
                    Float lastScore = Float.parseFloat(strScore);

                    System.out.print("\t\u29bf Apply University: ");
                    String applyUni = scanner.nextLine();
                    System.out.print("\t\u29bf Apply Degree: ");
                    String applyDegree = scanner.nextLine();
                    System.out.print("\t\u29bf Apply Field: ");
                    String applyField = scanner.nextLine();
                    System.out.print("\t\u29bf Apply Date: ");
                    String applyDate = scanner.nextLine();

                    Long requesterId = AuthenticationService.getInstance().getLoginUser().getId();

                    Scholarship scholarship = new Scholarship(null, "RequestedByStudent", name, family, nationalCode
                            , lastUni, lastDegree, lastField, lastScore, applyUni, applyDegree, applyField, applyDate, requesterId);

                    new RequestScholarshipByStudentUseCaseImpl().request(scholarship);
                    System.out.println("\t\t\u2705 Request Sent Successfully!");
                }

            }

            //**********************************find by student********************************
            else if (command.equals("stulist")){
                User loginUser = AuthenticationService.getInstance().getLoginUser();
                if (loginUser == null || !loginUser.getRole().equals("Student")){
                    System.out.println("\t\u26a0 Please Login as Student!");
                }
                else {
                    FindScholarshipByStudentUseCase findScholarshipByStudentUseCase
                            = new FindScholarshipByStudentUseCaseImpl();

                    List<Scholarship> scholarships = findScholarshipByStudentUseCase
                            .listScholarships();

                    if (scholarships.size() == 0)
                        System.out.println("\t\u26a0 Student's List is Empty!");
                    else {
                        DisplayData.printScholarshipList(scholarships, "Student");
                    }
                }
            }


            // *********************find or accept or reject by supervisor*********************
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
                        if (scholarships.size() == 0)
                            System.out.println("\t\u26a0 Supervisor's List is Empty!");
                        else {
                            DisplayData.printScholarshipList(scholarships, "Supervisor");
                        }
                    }
                    else {
                        System.out.print("\t\u29bf Scholarship Id: ");
                        String scholarshipId = scanner.nextLine();

                        if (!MyMath.isLong(scholarshipId)) {
                            System.out.println("\t\t\u26a0 Invalid Scholarship Id!");
                        }
                        else {
                            Scholarship selectedScholarship = null;
                            for (Scholarship i : scholarships) {
                                if (i.getId() == Long.parseLong(scholarshipId)){
                                    selectedScholarship = i;
                                }
                            }
                            if (selectedScholarship == null){
                                System.out.println("\t\t\u26a0 Entered Scholarship Doesn't Exist in Supervisor List!");
                            }
                            else {
                                if (command.equals("svaccept")){
                                    AcceptScholarshipBySupervisorUseCase acceptScholarshipBySupervisorUseCase
                                            = new AcceptScholarshipBySupervisorUseCaseImpl();
                                    acceptScholarshipBySupervisorUseCase.accept(Long.parseLong(scholarshipId));
                                    System.out.println("\t\t\u2705 Accepted by Supervisor Successfully!");
                                }
                                else {
                                    RejectScholarshipBySupervisorUseCase rejectScholarshipBySupervisorUseCase
                                            = new RejectScholarshipBySupervisorUseCaseImpl();
                                    rejectScholarshipBySupervisorUseCase.reject(Long.parseLong(scholarshipId));
                                    System.out.println("\t\t\u2705 Rejected by Supervisor Successfully!");
                                }

                            }
                        }
                    }
                }
            }

            // ************************find or accept or reject by manager*********************
            else if (command.equals("mnglist") || command.equals("mngaccept") || command.equals("mngreject")){
                User loginUser = AuthenticationService.getInstance().getLoginUser();
                if (loginUser == null || !loginUser.getRole().equals("Manager")){
                    System.out.println("\t\u26a0 Please Login as Manager!");
                }
                else {
                    FindScholarshipByManagerUseCase findScholarshipByManagerUseCase
                            = new FindScholarshipByManagerUseCaseImpl();

                    List<Scholarship> scholarships = findScholarshipByManagerUseCase
                            .listScholarships();

                    if (command.equals("mnglist")) {
                        if (scholarships.size() == 0)
                            System.out.println("\t\u26a0 Manager's List is Empty!");
                        else
                            DisplayData.printScholarshipList(scholarships, "Manager");
                    }
                    else {
                        System.out.print("\t\u29bf Scholarship Id: ");
                        String scholarshipId = scanner.nextLine();

                        if (!MyMath.isLong(scholarshipId)) {
                            System.out.println("\t\t\u26a0 Invalid Scholarship Id!");
                        }
                        else {
                            Scholarship selectedScholarship = null;
                            for (Scholarship i : scholarships) {
                                if (i.getId() == Long.parseLong(scholarshipId)){
                                    selectedScholarship = i;
                                }
                            }
                            if (selectedScholarship == null){
                                System.out.println("\t\t\u26a0 Entered Scholarship Doesn't Exist in Manager List!");
                            }
                            else {
                                if (command.equals("mngaccept")){
                                    AcceptScholarshipByManagerUseCase acceptScholarshipByManagerUseCase
                                            = new AcceptScholarshipByManagerUseCaseImpl();
                                    acceptScholarshipByManagerUseCase.accept(Long.parseLong(scholarshipId));
                                    System.out.println("\t\t\u2705 Accepted by Manager Successfully!");
                                }
                                else {
                                    RejectScholarshipByManagerUseCase rejectScholarshipByManagerUseCase
                                            = new RejectScholarshipByManagerUseCaseImpl();
                                    rejectScholarshipByManagerUseCase.reject(Long.parseLong(scholarshipId));
                                    System.out.println("\t\t\u2705 Rejected by Manager Successfully!");
                                }

                            }
                        }
                    }
                }
            }

            //*************************display all scholarships only for manager****************
            else if (command.equals("all")){
                User loginUser = AuthenticationService.getInstance().getLoginUser();
                if (loginUser == null || !loginUser.getRole().equals("Manager")){
                    System.out.println("\t\u26a0 Please Login as Manager!");
                }
                else {
                    FindAllScholarshipsByApplicationUseCase findAllScholarshipsByApplicationUseCase
                            =new  FindAllScholarshipsByApplicationUseCaseImpl();
                    List<Scholarship> scholarships = findAllScholarshipsByApplicationUseCase.listAllScholarships();
                    if (scholarships.size() == 0){
                        System.out.println("\t\u26a0 Scholarship List is Empty!");
                    }
                    else {
                        DisplayData.printScholarshipList(scholarships, "Scholarship");
                    }
                }
            }


            //*****************************find by university***********************************
            else if (command.equals("unilist")){
                User loginUser = AuthenticationService.getInstance().getLoginUser();
                if (loginUser == null || !loginUser.getRole().equals("University")) {
                    System.out.println("\t\u26a0 Please Login as University!");
                }
                else {
                    FindScholarshipByUniversityUseCase findScholarshipByUniversityUseCase
                            = new FindScholarshipByUniversityUseCaseImpl();
                    List<Scholarship> scholarships = findScholarshipByUniversityUseCase.listScholarships();
                    if (scholarships.size() == 0)
                        System.out.println("\t\u26a0 University List is Empty!");
                    else
                        DisplayData.printScholarshipList(scholarships, "University");
                }
            }

            //**********************************display dashboard*******************************
            else if (command.equals("dashboard")){
                User user = AuthenticationService.getInstance().getLoginUser();
                if (user == null)
                    System.out.println("\t\u26a0 Login Please!");
                else {
                    DashboardUseCase dashboardUseCase = new DashboardUseCaseImpl();
                    dashboardUseCase.display();
                }
            }

            //***************************display given scholarship's logs************************
            else if (command.equals("history")){
                User user = AuthenticationService.getInstance().getLoginUser();
                if (user == null)
                    System.out.println("\t\u26a0 Login Please!");
                else {
                    System.out.print("\t\u29bf Scholarship Id: ");
                    String scholarshipId = scanner.nextLine();

                    if (!MyMath.isLong(scholarshipId)) {
                        System.out.println("\t\t\u26a0 Invalid Scholarship Id!");
                    }
                    else {
                        FindScholarshipHistoryUseCase findScholarshipHistoryUseCase
                                = new FindScholarshipHistoryUseCaseImpl();
                        List<ScholarshipLog> logs = findScholarshipHistoryUseCase.listLogs(Long.parseLong(scholarshipId));
                        if (logs.size() == 0)
                            System.out.println("\t\t\u26a0 No Logs Found! Make Sure that Entered Scholarship Id is Valid!");
                        else if (user.getRole().equals("Student")){
                            List<Scholarship> currentStudentScholarships = new FindScholarshipByStudentUseCaseImpl().listScholarships();
                            boolean hasAccess = false;
                            for (Scholarship i : currentStudentScholarships){
                                if (i.getId() == Long.parseLong(scholarshipId))
                                    hasAccess = true;
                            }
                            if (hasAccess == false)
                                System.out.println("\t\t\u26a0 You Don't Have Access to This Scholarship!");
                            else {
                                DisplayData.printScholarship(Long.parseLong(scholarshipId));
                                System.out.println();
                                DisplayData.printLogsList(logs, Long.parseLong(scholarshipId));
                            }
                        }
                        else {
                            DisplayData.printScholarship(Long.parseLong(scholarshipId));
                            System.out.println();
                            DisplayData.printLogsList(logs, Long.parseLong(scholarshipId));
                        }
                    }
                }
            }

            else if (command.equals("exit")){
                System.out.println("\t\u2705 Have a Nice Day!");
            }
            else {
                System.out.println("\t\u26a0 Invalid Command!");
            }
            System.out.println();
        }
    }


}
