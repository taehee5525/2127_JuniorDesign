import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;// add library manually using ./tempLibFiles/json-20220924.jar file. This will be added using gradle in Android Studio.
//reference for gradle: https://mvnrepository.com/artifact/org.json/json/20220924

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        
        ApiCallMaker apicall = new ApiCallMaker();
        Map<String, String> headerMap = new HashMap<>();

        JSONObject req = new JSONObject();
        JSONObject res = new JSONObject();
        

        System.out.println("\n======= User Registration =======");
        for (int i = 0; i < 2; i++) {
            System.out.print("Enter User Email: ");
            String email = sc.nextLine();

            System.out.print("Enter Password: ");
            String pw = sc.nextLine();

            req.put("email", email);
            req.put("password", pw);
            try {
                res = apicall.callPost("http://localhost:8080/users/mkuser", headerMap, req);
                System.out.println(res + "\n");
            }  catch (Exception e) {
                e.printStackTrace();
            }
        }


        System.out.println("\n======= User Log-in =======");
        for (int i = 0; i < 3; i++) {
            System.out.print("Enter User Email: ");
            String email = sc.nextLine();

            System.out.print("Enter Password: ");
            String pw = sc.nextLine();

            req.put("email", email);
            req.put("password", pw);

            try {
                res = apicall.callPost("http://localhost:8080/users/userlogin", headerMap, req);
                System.out.println(res + "\n");
            }  catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        // req.put("email", "jl124ee36ee8f8@gatech.edu");
        // req.put("password", "12334");

        // try {
        //     res = apicall.callPost("http://localhost:8080/users/mkuser", headerMap, req);
        //     System.out.println(res);
        // }  catch (Exception e) {
        //     e.printStackTrace();
        // }
    }
}
