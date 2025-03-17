import java.util.HashSet;

class Friend {
    int friendID;
    Friend next;

    public Friend(int friendID) {
        this.friendID = friendID;
        this.next = null;
    }
}

class User {
    int userID;
    String name;
    int age;
    Friend friendList;  // Head of the friend linked list
    User next;

    public User(int userID, String name, int age) {
        this.userID = userID;
        this.name = name;
        this.age = age;
        this.friendList = null;
        this.next = null;
    }

    // Add friend ID to this user's friend list
    public void addFriend(int friendID) {
        Friend newFriend = new Friend(friendID);
        newFriend.next = friendList;
        friendList = newFriend;
    }

    // Remove friend ID from this user's friend list
    public void removeFriend(int friendID) {
        if (friendList == null) return;
        if (friendList.friendID == friendID) {
            friendList = friendList.next;
            return;
        }
        Friend current = friendList;
        while (current.next != null && current.next.friendID != friendID) {
            current = current.next;
        }
        if (current.next != null) {
            current.next = current.next.next;
        }
    }

    // Display all friends of this user
    public void displayFriends() {
        System.out.print("Friends of " + name + " (ID: " + userID + "): ");
        Friend current = friendList;
        if (current == null) {
            System.out.println("No friends yet.");
            return;
        }
        while (current != null) {
            System.out.print(current.friendID + " ");
            current = current.next;
        }
        System.out.println();
    }

    // Count number of friends
    public int countFriends() {
        int count = 0;
        Friend current = friendList;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }
}

public class SocialMedia {
    private User head;

    public SocialMedia() {
        this.head = null;
    }

    // Add a new user
    public void addUser(int userID, String name, int age) {
        User newUser = new User(userID, name, age);
        newUser.next = head;
        head = newUser;
    }

    // Find user by User ID
    public User findUserByID(int userID) {
        User current = head;
        while (current != null) {
            if (current.userID == userID) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    // Find user by Name
    public User findUserByName(String name) {
        User current = head;
        while (current != null) {
            if (current.name.equalsIgnoreCase(name)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    // Add a friend connection between two users
    public void addFriendConnection(int userID1, int userID2) {
        User user1 = findUserByID(userID1);
        User user2 = findUserByID(userID2);
        if (user1 != null && user2 != null) {
            user1.addFriend(userID2);
            user2.addFriend(userID1);
        }
    }

    // Remove a friend connection
    public void removeFriendConnection(int userID1, int userID2) {
        User user1 = findUserByID(userID1);
        User user2 = findUserByID(userID2);
        if (user1 != null && user2 != null) {
            user1.removeFriend(userID2);
            user2.removeFriend(userID1);
        }
    }

    // Find mutual friends between two users
    public void findMutualFriends(int userID1, int userID2) {
        User user1 = findUserByID(userID1);
        User user2 = findUserByID(userID2);
        if (user1 == null || user2 == null) {
            System.out.println("One or both users not found.");
            return;
        }

        HashSet<Integer> friendsOfUser1 = new HashSet<>();
        Friend current = user1.friendList;
        while (current != null) {
            friendsOfUser1.add(current.friendID);
            current = current.next;
        }

        System.out.print("Mutual Friends of " + user1.name + " and " + user2.name + ": ");
        boolean found = false;
        current = user2.friendList;
        while (current != null) {
            if (friendsOfUser1.contains(current.friendID)) {
                System.out.print(current.friendID + " ");
                found = true;
            }
            current = current.next;
        }
        if (!found) {
            System.out.print("None");
        }
        System.out.println();
    }

    // Display all users and their friends
    public void displayAllUsers() {
        User current = head;
        while (current != null) {
            System.out.println("User: " + current.name + " (ID: " + current.userID + ")");
            current.displayFriends();
            current = current.next;
        }
    }

    public static void main(String[] args) {
        SocialMedia network = new SocialMedia();

        // Adding users
        network.addUser(1, "Pranav", 25);
        network.addUser(2, "Abhay", 27);
        network.addUser(3, "Arun", 22);
        network.addUser(4, "Akshay", 26);

        // Adding friend connections
        network.addFriendConnection(1, 2);
        network.addFriendConnection(1, 3);
        network.addFriendConnection(2, 3);
        network.addFriendConnection(3, 4);

        System.out.println("\nInitial Friend Connections:");
        network.displayAllUsers();

        // Finding mutual friends
        System.out.println("\nFinding Mutual Friends:");
        network.findMutualFriends(1, 3);
        network.findMutualFriends(2, 3);

        // Searching for a user by Name
        System.out.println("\nSearching for a user:");
        User user = network.findUserByName("Pranav");
        if (user != null) {
            System.out.println("User Found: " + user.name + " (ID: " + user.userID + ")");
        }

        // Removing a friend connection
        System.out.println("\nAfter Removing Friend Connection (Pranav & Arun):");
        network.removeFriendConnection(1, 3);
        network.displayAllUsers();

        // Counting friends of each user
        System.out.println("\nFriend Count:");
        User current = network.head;
        while (current != null) {
            System.out.println(current.name + " has " + current.countFriends() + " friends.");
            current = current.next;
        }
    }
}
