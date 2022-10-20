import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;// add library manually using ./tempLibFiles/json-20220924.jar file. This will be added using gradle in Android Studio.
//reference for gradle: https://mvnrepository.com/artifact/org.json/json/20220924

import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static ApiCallMaker apicall = new ApiCallMaker();
    static Map<String, String> headerMap = new HashMap<>();
    static Map<String, String> addrMap = new HashMap<>();

    public static void main(String[] args) {
        String token = "?";
        setAddr();
        for (;;) {
            System.out.println("======= =========TOKEN========= =======");
            System.out.println("token: " + token);
            System.out.println("======= ==========MAIN========= =======");
            System.out.println("1. Sign up");
            System.out.println("2. Sign in");
            System.out.println("3. List Friends");
            System.out.println("4. Check Friend Request");
            System.out.println("5. Request Friend");
            System.out.println("6. Accept/Decline Friend Request");
            System.out.println("7. Remove Friends");

            String input = sc.nextLine();
            int menu = 0;
            if (input.length() == 1 && input.charAt(0) >= '1' && input.charAt(0) <= '7') {
                menu = Integer.parseInt(input);
            } else {
                continue;
            }

            if (menu == 1) {
                System.out.println("======= ==========SIGN UP========= =======");
                System.out.print("User Email: ");
                String email = sc.nextLine();
                System.out.print("\n");
                System.out.print("Password: ");
                String password = sc.nextLine();
                System.out.print("\n");
                signUp(email, password);
            } else if (menu == 2) {
                System.out.println("======= ==========SIGN IN========= =======");
                System.out.print("User Email: ");
                String email = sc.nextLine();
                System.out.print("\n");
                System.out.print("Password: ");
                String password = sc.nextLine();
                System.out.print("\n");
                token = signIn(email, password);
            } else if (menu == 3) {
                System.out.println("======= ==========List Friends========= =======");
                System.out.println("Friend: " + getFriendList(token));
            } else if (menu == 4) {
                System.out.println("======= ==========Friend Request========= =======");
                System.out.println("Friend who requests: " + getRequestList(token));
            } else if (menu == 5) {
                System.out.println("======= ==========Request Friend========= =======");
                System.out.print("Friend Email: ");
                String email = sc.nextLine();
                System.out.print("\n");
                requestFriend(token, email);
            } else if (menu == 6) {
                System.out.println("======= ==========Accept/Decline Friend Request========= =======");
                System.out.print("Friend Email: ");
                String email = sc.nextLine();
                System.out.print("\n");
                System.out.print("Accept (Y/n): ");
                String Yn = sc.nextLine();
                System.out.print("\n");
                boolean accept = Yn.equalsIgnoreCase("y");
                acceptFriendReq(token, email, accept);
            } else if (menu == 7) {
                System.out.println("======= ==========Remove Friends========= =======");
                System.out.print("Friend Email: ");
                String email = sc.nextLine();
                System.out.print("\n");
                removeFriend(token, email);
            }
        }
    }

    private static void setAddr() {
        addrMap.put("endPoint", "http://localhost:8080/");

        addrMap.put("signUp", "users/mkuser");  //POST
        addrMap.put("signIn", "users/userlogin");  //POST
        addrMap.put("friendRequest", "friends/request");  //POST
        addrMap.put("getRequestList", "friends/getRequestList");  //GET
        addrMap.put("requestAccept", "friends/requestAccept");  //PUT
        addrMap.put("removeFriend", "friends/removeFriend");  //DELETE
        addrMap.put("getFriendsList", "friends/getFriendList");  //GET
    }

    private static void signUp(String userEmail,  String password) {
        JSONObject req = new JSONObject();
        JSONObject res = new JSONObject();
        req.put("email", userEmail);
        req.put("password", password);
        try {
            res = apicall.callPost(addrMap.get("endPoint") + addrMap.get("signUp"), headerMap, req);
            System.out.println(res + "\n");
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String signIn(String userEmail, String password) {
        JSONObject req = new JSONObject();
        JSONObject res = new JSONObject();
        req.put("email", userEmail);
        req.put("password", password);
        String retStr = new String();

        try {
            res = apicall.callPost(addrMap.get("endPoint") + addrMap.get("signIn"), headerMap, req);
            System.out.println(res + "\n");
        }  catch (Exception e) {
            e.printStackTrace();
        }

        return res.get("token").toString();
    }

    private static void requestFriend(String token, String friendEmail) {
        JSONObject req = new JSONObject();
        JSONObject res = new JSONObject();
        req.put("token", token);
        req.put("email", friendEmail);

        try {
            res = apicall.callPost(addrMap.get("endPoint") + addrMap.get("friendRequest"), headerMap, req);
            System.out.println(res + "\n");
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param token
     * @param accept true accept, else decline
     */
    private static void acceptFriendReq(String token, String friendEmail, boolean accept) {
        JSONObject req = new JSONObject();
        JSONObject res = new JSONObject();
        req.put("token", token);
        req.put("email", friendEmail);
        req.put("accept", accept ? "accept" : "decline");

        try {
            res = apicall.callPut(addrMap.get("endPoint") + addrMap.get("requestAccept"), headerMap, req);
            System.out.println(res + "\n");
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String getRequestList(String token) {
        JSONObject req = new JSONObject();
        JSONObject res = new JSONObject();
        req.put("token", token);

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);

        try {
            res = apicall.callGet(addrMap.get("endPoint") + addrMap.get("getRequestList"), headerMap, paramMap);
            System.out.println(res + "\n");
        }  catch (Exception e) {
            e.printStackTrace();
        }
        
        return res.get("requestList").toString();
    }

    private static String getFriendList(String token) {
        JSONObject req = new JSONObject();
        JSONObject res = new JSONObject();
        req.put("token", token);

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);

        try {
            res = apicall.callGet(addrMap.get("endPoint") + addrMap.get("getFriendsList"), headerMap, paramMap);
            System.out.println(res + "\n");
        }  catch (Exception e) {
            e.printStackTrace();
        }
        
        return res.get("friendList").toString();
    }

    private static void removeFriend(String token, String friendEmail) {
        JSONObject req = new JSONObject();
        JSONObject res = new JSONObject();
        req.put("token", token);
        req.put("email", friendEmail);

        try {
            res = apicall.callPost(addrMap.get("endPoint") + addrMap.get("removeFriend"), headerMap, req);
            System.out.println(res + "\n");
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
}
