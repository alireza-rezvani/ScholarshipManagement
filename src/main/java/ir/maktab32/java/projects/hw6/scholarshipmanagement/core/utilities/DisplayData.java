package ir.maktab32.java.projects.hw6.scholarshipmanagement.core.utilities;

import ir.maktab32.java.projects.hw6.scholarshipmanagement.features.scholarshipverification.impl.FindAllScholarshipsByApplicationUseCaseImpl;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.model.Scholarship;
import ir.maktab32.java.projects.hw6.scholarshipmanagement.model.ScholarshipLog;

import java.util.List;

public class DisplayData {

    public static void printScholarshipList(List<Scholarship> scholarshipList, String role){

        System.out.println("=======================================================" +
                "=======================================================" +
                "=======================================================" +
                "=================================");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+role+"'s List");
        System.out.println("=======================================================" +
                "=======================================================" +
                "=======================================================" +
                "=================================");
        System.out.printf("%-3s %-22s %-10s %-10s  %-12s  %-15s %-10s %-20s  %-9s  %-15s  %-11s  %-20s %-10s %-11s\n"
                , "Id", "Status", "Name", "Family", "NationalCode", "LastUniversity", "LastDegree", "LastField",
                "LastScore", "ApplyUniversity", "ApplyDegree", "ApplyField", "ApplyDate", "RequesterId");
        System.out.println("-------------------------------------------------------" +
                "-------------------------------------------------------" +
                "-------------------------------------------------------" +
                "---------------------------------");

        for (int i = 0; i < scholarshipList.size(); i++) {
            System.out.println(scholarshipList.get(i));
        }

        System.out.println("-------------------------------------------------------" +
                "-------------------------------------------------------" +
                "-------------------------------------------------------" +
                "---------------------------------");
    }

    public static void printScholarship(Long scholarshipId){

        Scholarship selectedScholarship = null;
        for (Scholarship scholarship : new FindAllScholarshipsByApplicationUseCaseImpl().listAllScholarships()){
            if (scholarship.getId() == scholarshipId)
                selectedScholarship = scholarship;
        }

        System.out.println("=======================================================" +
                "=======================================================" +
                "=======================================================" +
                "=================================");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tInformation of Scholarship Id: " + scholarshipId);
        System.out.println("=======================================================" +
                "=======================================================" +
                "=======================================================" +
                "=================================");
        System.out.printf("%-3s %-22s %-10s %-10s  %-12s  %-15s %-10s %-20s  %-9s  %-15s  %-11s  %-20s %-10s %-11s\n"
                , "Id", "Status", "Name", "Family", "NationalCode", "LastUniversity", "LastDegree", "LastField",
                "LastScore", "ApplyUniversity", "ApplyDegree", "ApplyField", "ApplyDate", "RequesterId");
        System.out.println("-------------------------------------------------------" +
                "-------------------------------------------------------" +
                "-------------------------------------------------------" +
                "---------------------------------");

        System.out.println(selectedScholarship);

        System.out.println("-------------------------------------------------------" +
                "-------------------------------------------------------" +
                "-------------------------------------------------------" +
                "---------------------------------");
    }

    public static void printLogsList(List<ScholarshipLog> scholarshipLogList, Long scholarshipId){
        System.out.println("========================================" +
                "======================================");
        System.out.println("\t\t\t\t\t\t\tHistory of Scholarship Id: "+scholarshipId);
        System.out.println("=======================================" +
                "=======================================");
        System.out.printf("%-5s\t%-22s\t%-6s\t%-12s\t%12s\n"
                , "Id", "Action", "UserId", "UserRole", "Date");
        System.out.println("---------------------------------------" +
                "---------------------------------------");

        for (int i = 0; i < scholarshipLogList.size(); i++) {
            System.out.println(scholarshipLogList.get(i));
        }

        System.out.println("---------------------------------------" +
                "---------------------------------------");
    }

}
