package ir.maktab32.java.projects.hw6.scholarshipmanagement.core.utilities;

public class MyMath {

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
