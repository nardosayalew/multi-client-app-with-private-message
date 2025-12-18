package server;


import java.util.*;


public class UserDatabase {
private static Map<String, String> users = new HashMap<>();


static {
users.put("admin", "1234");
users.put("user", "pass");
users.put("guest", "guest");
}


public static boolean authenticate(String username, String password) {
return users.containsKey(username) && users.get(username).equals(password);
}
}
