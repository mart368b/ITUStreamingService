package maincomponents.controllers;

import debugging.Exceptions.InvalidInputException;
import debugging.Logger;
import maincomponents.AvMinArm;
import ui.Display;
import ui.pages.PageFactory;
import ui.pages.ProfilePickerPage;
import user.User;
import user.UserHandler;

public class UserController {

    public static void signUpUser(String username, String password, String passwordCheck){

        if(username.isEmpty() || password.isEmpty() || passwordCheck.isEmpty()){
            throw new InvalidInputException("You have not filled in all the boxes!");
        }
        if(!password.equals(passwordCheck)){
            throw new InvalidInputException("Your password and confirmed password are not the same!");
        }
        if(UserHandler.getInstance().hasUser(username)){
            throw new InvalidInputException("A user with that name is already created!");
        }

        UserHandler.getInstance().signUpUser(username, password);
        AvMinArm.user = UserHandler.getInstance().getUser(username, passwordCheck);
        Logger.log("New user created with name: " + username);

        ProfilePickerPage profilePickerPage = (ProfilePickerPage) PageFactory.getPage(PageFactory.PROFILEPICKERPAGE);
        profilePickerPage.updateUsers();
        Display.setPage(profilePickerPage);
    }

    public static void deleteUser(){
        int answer = JOptionPane.showConfirmDialog(new JFrame(),
                "Are you sure you want to delete this user?",
                "Are you sure?",
                JOptionPane.YES_NO_OPTION);
        if(answer == JOptionPane.YES_OPTION){
            UserHandler.getInstance().removeUser(AvMinArm.user);
            Display.setPage(PageHandler.LOGINPAGE);
        }
    }

    public static void logInUser(String username, String password) {
        if(username.isEmpty() || password.isEmpty()){
            throw new InvalidInputException("You have not filled in all the boxes!");
        }
        User user = UserHandler.getInstance().getUser(username, password);
        if(user == null){
            throw new InvalidInputException("Password or username is wrong!");
        }
        AvMinArm.user = user;
        Logger.log("User with name: " + username + " logged in!");
        ProfilePickerPage profilePickerPage = (ProfilePickerPage) PageFactory.getPage(PageFactory.PROFILEPICKERPAGE);
        profilePickerPage.updateUsers();
        Display.setPage(profilePickerPage);
    }
}
