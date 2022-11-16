import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
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
        String userId = "?";
        double userBalance = 0;
        String userFriends = "{}";

        setAddr();
        for (;;) {
            System.out.println("======= =========User Info========= =======");
            System.out.println("token used: " + token);
            System.out.println("User ID: " + userId);
            if (!token.equals("?") && !token.equals("") && token != null) {
                update(token);
            }
            System.out.println("======= ==========MAIN========= =======");
            System.out.println(" 0. Sign up");
            System.out.println(" 1. Sign in");
            System.out.println(" 2. Check Friend Request");
            System.out.println(" 3. Request Friend");
            System.out.println(" 4. Accept/Decline Friend Request");
            System.out.println(" 5. Remove Friends");
            System.out.println(" 6. Check Transactions");
            System.out.println(" 7. Send Money");
            System.out.println(" 8. Request Money");
            System.out.println(" 9. Accept Transaction");
            System.out.println("10. Exit");
            System.out.print("\n>> ");

            String input = sc.nextLine();
            input = input.trim();
            int menu = 0;
            if (input.charAt(0) >= '0' && input.charAt(0) <= '9') {
                menu = Integer.parseInt(input);
            } else {
                continue;
            }

            if (menu == 0) {
                System.out.println("======= ==========SIGN UP========= =======");
                System.out.print("User Email: ");
                String email = sc.nextLine();
                System.out.print("\n");
                System.out.print("Name: ");
                String name = sc.nextLine();
                System.out.print("\n");
                System.out.print("Password: ");
                String password = sc.nextLine();
                System.out.print("\n");
                signUp(email, name, password);
            } else if (menu == 1) {
                System.out.println("======= ==========SIGN IN========= =======");
                System.out.print("User Email: ");
                String email = sc.nextLine();
                System.out.print("\n");
                System.out.print("Password: ");
                String password = sc.nextLine();
                System.out.print("\n");
                String tempToken = signIn(email, password);
                if (tempToken != null && !tempToken.equals("")) {
                    token = tempToken;
                    userId = email;
                    userBalance = Double.parseDouble(getUserBalance(token));
                    userFriends = getFriendList(token);
                }
            } else if (menu == 2) {
                System.out.println("======= ==========Friend Request========= =======");
                System.out.println("Friend who requests: " + getRequestList(token));
            } else if (menu == 3) {
                System.out.println("======= ==========Request Friend========= =======");
                System.out.print("Friend Email: ");
                String email = sc.nextLine();
                System.out.print("\n");
                requestFriend(token, email);
            } else if (menu == 4) {
                System.out.println("======= ==========Accept/Decline Friend Request========= =======");
                System.out.print("Friend Email: ");
                String email = sc.nextLine();
                System.out.print("\n");
                System.out.print("Accept (Y/n): ");
                String Yn = sc.nextLine();
                System.out.print("\n");
                boolean accept = Yn.equalsIgnoreCase("y");
                acceptFriendReq(token, email, accept);
            } else if (menu == 5) {
                System.out.println("======= ==========Remove Friends========= =======");
                System.out.print("Friend Email: ");
                String email = sc.nextLine();
                System.out.print("\n");
                removeFriend(token, email);
            } else if (menu == 6) {
                printTransactionList(token);
            } else if (menu == 7) {
                System.out.println("======= ==========Money Send========= =======");
                System.out.print("Friend Email: ");
                String email = sc.nextLine();
                System.out.print("\n");
                System.out.print("Amount: ");
                String amountStr = sc.nextLine();
                System.out.print("\n");
                double amount = Double.parseDouble(amountStr);
                sendMoneyINT(token, email, amount);
            } else if (menu == 8) {
                System.out.println("======= ==========Money Request========= =======");
                System.out.print("Friend Email: ");
                String email = sc.nextLine();
                System.out.print("\n");
                System.out.print("Amount: ");
                String amountStr = sc.nextLine();
                System.out.print("\n");
                double amount = Double.parseDouble(amountStr);
                requestMoneyINT(token, email, amount);
            } else if (menu == 9) {
                printTransactionNeedToConfirmList(token);
                System.out.println("======= ==========Transaction Accept/Decline========= =======");
                System.out.print("Transaction Id: ");
                String tid = sc.nextLine();
                System.out.print("\n");
                System.out.print("Accept (Y/n): ");
                String Yn = sc.nextLine();
                System.out.print("\n");
                boolean accept = Yn.equalsIgnoreCase("y");
                confirmTransaction(token, tid, accept);
            } else if (menu == 10) {
                break;
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
        addrMap.put("removeFriend", "friends/removeFriend");  //POST
        addrMap.put("getFriendsList", "friends/getFriendList");  //GET
        addrMap.put("getTransactionList", "transactions/getTransactionList");  //GET
        addrMap.put("getPendingTransactionListThatUserNeedToConfirm", "transactions/getPendingTransactionListThatUserNeedToConfirm");  //GET
        addrMap.put("getPendingTransactionListThatUserWaitingForConfirm", "transactions/getPendingTransactionListThatUserWaitingForConfirm");  //GET
        addrMap.put("getUserBalance", "transactions/getUserBalance");  //GET
        addrMap.put("sendMoneyINT", "transactions/sendMoneyINT");  //POST
        addrMap.put("requestMoneyINT", "transactions/requestMoneyINT");  //POST
        addrMap.put("confirmTransaction", "transactions/confirmTransaction");  //PUT
    }

    private static void signUp(String userEmail, String name, String password) {
        JSONObject req = new JSONObject();
        JSONObject res = new JSONObject();
        req.put("email", userEmail);
        req.put("name", name);
        req.put("password", password);

        try {
            res = apicall.callPost(addrMap.get("endPoint") + addrMap.get("signUp"), headerMap, req);
            //System.out.println(res + "\n");
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
            //System.out.println(res + "\n");
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
            //System.out.println(res + "\n");
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
            //System.out.println(res + "\n");
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
            //System.out.println(res + "\n");
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
            //System.out.println(res + "\n");
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return res.get("friendEmails").toString();
    }

    private static void removeFriend(String token, String friendEmail) {
        JSONObject req = new JSONObject();
        JSONObject res = new JSONObject();
        req.put("token", token);
        req.put("email", friendEmail);

        try {
            res = apicall.callPost(addrMap.get("endPoint") + addrMap.get("removeFriend"), headerMap, req);
            //System.out.println(res + "\n");
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getTransactionList(String token) {
        JSONObject req = new JSONObject();
        JSONObject res = new JSONObject();
        req.put("token", token);

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);

        try {
            res = apicall.callGet(addrMap.get("endPoint") + addrMap.get("getTransactionList"), headerMap, paramMap);
            //System.out.println(res + "\n");
        }  catch (Exception e) {
            e.printStackTrace();
        }
        
        return res.get("transactionList").toString();
    }

    private static String getPendingTransactionListThatUserNeedToConfirm(String token) {
        JSONObject req = new JSONObject();
        JSONObject res = new JSONObject();
        req.put("token", token);

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);

        try {
            res = apicall.callGet(addrMap.get("endPoint") + addrMap.get("getPendingTransactionListThatUserNeedToConfirm"), headerMap, paramMap);
            //System.out.println(res + "\n");
        }  catch (Exception e) {
            e.printStackTrace();
        }
        
        return res.get("pendingTransaction").toString();
    }

    private static String getPendingTransactionListThatUserWaitingForConfirm(String token) {
        JSONObject req = new JSONObject();
        JSONObject res = new JSONObject();
        req.put("token", token);

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);

        try {
            res = apicall.callGet(addrMap.get("endPoint") + addrMap.get("getPendingTransactionListThatUserWaitingForConfirm"), headerMap, paramMap);
            //System.out.println(res + "\n");
        }  catch (Exception e) {
            e.printStackTrace();
        }
        
        return res.get("pendingTransaction").toString();
    }

    private static String getUserBalance(String token) {
        JSONObject req = new JSONObject();
        JSONObject res = new JSONObject();
        req.put("token", token);

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);

        try {
            res = apicall.callGet(addrMap.get("endPoint") + addrMap.get("getUserBalance"), headerMap, paramMap);
            //System.out.println(res + "\n");
        }  catch (Exception e) {
            e.printStackTrace();
        }
        
        return res.get("userBalance").toString();
    }

    private static void sendMoneyINT(String token, String friendEmail, double amount) {
        JSONObject req = new JSONObject();
        JSONObject res = new JSONObject();
        req.put("token", token);
        req.put("email", friendEmail);
        req.put("amount", amount);

        try {
            res = apicall.callPost(addrMap.get("endPoint") + addrMap.get("sendMoneyINT"), headerMap, req);
            //System.out.println(res + "\n");
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void requestMoneyINT(String token, String friendEmail, double amount) {
        JSONObject req = new JSONObject();
        JSONObject res = new JSONObject();
        req.put("token", token);
        req.put("email", friendEmail);
        req.put("amount", amount);

        try {
            res = apicall.callPost(addrMap.get("endPoint") + addrMap.get("requestMoneyINT"), headerMap, req);
            //System.out.println(res + "\n");
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void confirmTransaction(String token, String transactionId, boolean confirmed) {
        JSONObject req = new JSONObject();
        JSONObject res = new JSONObject();
        req.put("token", token);
        req.put("transactionId", transactionId);
        req.put("confirmed", confirmed);

        try {
            res = apicall.callPut(addrMap.get("endPoint") + addrMap.get("confirmTransaction"), headerMap, req);
            //System.out.println(res + "\n");
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printTransactionList(String token) {
        JSONArray jsonArray = new JSONArray(getTransactionList(token));
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject explrObject = jsonArray.getJSONObject(i);
            if (i == 0) {
                System.out.println("-----------------------------------------------------");
                System.out.println("----------------------Comleted-----------------------");
                System.out.println("-----------------------------------------------------");
            }
            System.out.println("Transaction ID: " + explrObject.getString("transactionId"));
            System.out.println(explrObject.getString("payerEmail")
            + "(" + explrObject.getString("payerFSP") + ")"
            + " --> " + explrObject.getString("payeeEmail")
            + "(" + explrObject.getString("payeeFSP") + ")");

            System.out.println("Amount: $" + explrObject.getDouble("amount"));
            System.out.println("Timestamp: " + explrObject.getLong("timestamp"));
            System.out.println("-----------------------------------------------------");
        }

        JSONArray pendingArray = new JSONArray(getPendingTransactionListThatUserWaitingForConfirm(token));
        for (int i = 0; i < pendingArray.length(); i++) {
            JSONObject explrObject = pendingArray.getJSONObject(i);
            if (i == 0) {
                System.out.println("-----------------------------------------------------");
                System.out.println("-----------------------Pending-----------------------");
                System.out.println("-----------------------------------------------------");
            }
            System.out.println("Transaction ID: " + explrObject.getString("transactionId"));
            System.out.println(explrObject.getString("payerEmail")
            + "(" + explrObject.getString("payerFSP") + ")"
            + " --> " + explrObject.getString("payeeEmail")
            + "(" + explrObject.getString("payeeFSP") + ")");

            System.out.println("Amount: $" + explrObject.getDouble("amount"));
            System.out.println("-----------------------------------------------------");
        }
    }

    private static void printTransactionNeedToConfirmList(String token) {
        JSONArray pendingArray = new JSONArray(getPendingTransactionListThatUserNeedToConfirm(token));
        for (int i = 0; i < pendingArray.length(); i++) {
            JSONObject explrObject = pendingArray.getJSONObject(i);
            if (i == 0) {
                System.out.println("-----------------------------------------------------");
                System.out.println("-----------------------Pending-----------------------");
                System.out.println("-----------------------------------------------------");
            }
            System.out.println("Transaction ID: " + explrObject.getString("transactionId"));
            System.out.println(explrObject.getString("payerEmail")
            + "(" + explrObject.getString("payerFSP") + ")"
            + " --> " + explrObject.getString("payeeEmail")
            + "(" + explrObject.getString("payeeFSP") + ")");

            System.out.println("Amount: $" + explrObject.getDouble("amount"));
            System.out.println("-----------------------------------------------------");
        }
    }

    private static void update(String Tok) {
        if (Tok != null && !Tok.equals("")) {
            System.out.println("User Balance: " + Double.parseDouble(getUserBalance(Tok)));
            System.out.println("User Friends: " + getFriendList(Tok));
        }
    }
}
