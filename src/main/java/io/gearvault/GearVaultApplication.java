package io.gearvault;

import io.gearvault.menu.MainMenu;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GearVaultApplication implements CommandLineRunner {

    @Autowired
    private MainMenu mainMenu;

     static void main(String[] args) {
        SpringApplication.run(GearVaultApplication.class, args);
    }

    @Override
    public void run(String @NonNull ... args) {
        mainMenu.show();
    }

}
