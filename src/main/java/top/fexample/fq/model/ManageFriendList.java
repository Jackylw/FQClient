/**
 * @author Jacky Feng
 */
package top.fexample.fq.model;

import top.fexample.fq.controller.FriendListController;

import java.util.HashMap;

public class ManageFriendList {
    private static HashMap<String, FriendListController> friendListControllers = new HashMap<>();

    public static void addFriendListController(String userId, FriendListController friendListController)
    {
        friendListControllers.put(userId, friendListController);
    }

    public static FriendListController getFriendListController(String userId)
    {
        return friendListControllers.get(userId);
    }

}
