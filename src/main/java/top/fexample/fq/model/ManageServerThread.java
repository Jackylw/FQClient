/**
 * @author Jacky Feng
 * @date 2024/3/5 13:45
 */
package top.fexample.fq.model;

import java.util.HashMap;

public class ManageServerThread {

    private static HashMap<String, ConServerThread> server = new HashMap<>();

    public static void addServer(String userId, ConServerThread conServerThread) {
        server.put(userId, conServerThread);
    }

    public static ConServerThread getServer(String userId) {
        return server.get(userId);
    }
}
