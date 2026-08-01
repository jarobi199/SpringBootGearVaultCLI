package io.gearvault.menu;

import io.gearvault.authentication.SessionContext;
import io.gearvault.enums.Role;
import io.gearvault.interfaces.IMenu;
import io.gearvault.service.UserService;
import io.gearvault.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettingsMenu implements IMenu {

    @Autowired
    private UserService userService;

    @Override
    public void show() {
        int choice;
        do {
            printOptions();
            choice = InputHandler.getIntegerInput();
            switch (choice) {
                case 1 -> changePassword();
                case 2 -> setOverdueGraceDays();
                case 3 -> addUser();
                case 4 -> deleteUser();
            }
        }
        while (choice != 0);
    }

    public void deleteUser() {
        if(verifyAdmin()) {
            System.out.println("Enter the username of the user that you want to delete:");
            String username = InputHandler.getStringInput();
            System.out.println("Are you sure you want to delete this user? (Y/N):");
            String answer = InputHandler.getStringInput();
            if("Y".equalsIgnoreCase(answer)) {
                userService.deleteUser(username);
            }
        }
    }

    public void addUser() {
        if(verifyAdmin()) {
            System.out.println("Enter your full name:");
            String fullName = InputHandler.getStringInput();
            System.out.println("Enter your username:");
            String username = InputHandler.getStringInput();
            System.out.println("Enter your password:");
            String password = InputHandler.getStringInput();
            System.out.println("Enter your role (ADMINISTRATOR, USER):");
            Role role = Role.valueOf(InputHandler.getStringInput());
            System.out .println("Enter the number of overdue grace days:");
            int days = InputHandler.getIntegerInput();

            userService.addUser(fullName, username, password, role, days);
            System.out.println("Your user has been added!");
        }
    }

    public void setOverdueGraceDays() {
        System.out .println("Enter the new number of overdue grace days:");
        int highValue = InputHandler.getIntegerInput();
        userService.setOverdueGraceDays(highValue);
    }

    public void changePassword() {
        System.out .println("Enter the new password:");
        String newPassword = InputHandler.getStringInput();
        userService.changePassword(newPassword);
    }

    public void printOptions() {
        System.out.println();
        System.out.println("[1] Change password");
        System.out.println("[2] Set overdue grace days");
        if(verifyAdmin()) {
            System.out.println("[3] Add user");
            System.out.println("[4] Delete user");
        }
        System.out.println("[0] Exit");
        System.out.println("Please make a selection:");
    }

    private boolean verifyAdmin() {
        return Role.ADMINISTRATOR.equals(SessionContext.getUser().getRole());
    }
}
