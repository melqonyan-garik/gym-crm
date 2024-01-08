package mock;

import com.epam.model.User;

public class UserMockData {
    public static User getMockedUser() {
        User user = new User();
        user.setId(1);
        user.setFirstname("Alice");
        user.setLastname("Johnson");
        user.setPassword("some password");
        user.setUsername("alice");
        user.setActive(true);
        return user;
    }
}
