package io.gearvault.menu;

import io.gearvault.interfaces.IMenu;
import org.springframework.stereotype.Component;

@Component
public class GoodbyeMenu implements IMenu {

    @Override
    public void show() {
        System.out.println("Goodbye!");
    }

    @Override
    public void printOptions() {
        //No options
    }

}