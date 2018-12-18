package maincomponents.controllers;

import debugging.Exceptions.InvalidInputException;
import debugging.Logger;
import maincomponents.AvMinArm;
import reader.MediaHandler;
import ui.Display;
import ui.cards.HeaderCard;
import ui.pages.PageHandler;
import ui.pages.ProfileChangePage;
import ui.pages.ProfilePickerPage;
import user.Profile;

import javax.swing.*;

public class ProfileController extends Controller {

    public static void updateProfile(String name, String age, String imgName) {
        if(!name.isEmpty()) {
            if(name.length() > 10){
                throw new InvalidInputException("Name is too long!\nMax 10 letters!");
            }
            if (AvMinArm.user.hasProfile(name)) {
                throw new InvalidInputException("Name already taken by another profile!");
            } else {
                AvMinArm.profile.setName(name);
            }
        }
        if(!age.isEmpty()){
            if(!isNumber(age)){
                throw new InvalidInputException("Age has to be a number!");
            }else{
                AvMinArm.profile.setAge(Integer.parseInt(age));
            }
        }

        AvMinArm.profile.setPicture(imgName);

        ProfileChangePage profilePage = (ProfileChangePage) PageHandler.getPage(PageHandler.CHANGEPROFILEPAGE);
        profilePage.update(AvMinArm.profile.getProfilePictureName());
    }

    public static void createProfile(String name, String age, String imgName){
        if(name.isEmpty() || age.isEmpty()){
            throw new InvalidInputException("You have not filled in all the boxes!");
        }
        if(AvMinArm.user.hasProfile(name)){
            throw new InvalidInputException("You already have a profile with that name!");
        }
        if(!isNumber(age)){
            throw new InvalidInputException("Age has to be a number!");
        }
        if(imgName == null || imgName.isEmpty()){
            throw new InvalidInputException("No picture chosen!");
        }
        Logger.log("New profile " + name + " for user: " + AvMinArm.user.getUsername());
        Profile profile = new Profile(name, Integer.parseInt(age), imgName);

        AvMinArm.user.signUpProfile(profile);

        ProfilePickerPage profilePickerPage = (ProfilePickerPage) PageHandler.getPage(PageHandler.PROFILEPICKERPAGE);
        profilePickerPage.updateUsers();
        Display.setPage(profilePickerPage);
    }

    public static void deleteCurrentProfile() {
        int answer = JOptionPane.showConfirmDialog(new JFrame(),
                "Are you sure you want to delete this profile?",
                "Are you sure?",
                JOptionPane.YES_NO_OPTION);
        if(answer == JOptionPane.YES_OPTION){
            AvMinArm.user.removeProfile(AvMinArm.profile);

            ProfilePickerPage profilePickerPage = (ProfilePickerPage) PageHandler.getPage(PageHandler.PROFILEPICKERPAGE);
            profilePickerPage.updateUsers();
            Display.setPage(profilePickerPage);
        }
    }

    public static void setProfile(Profile profile) {
        AvMinArm.profile = profile;
        HeaderCard.getInstance().setProfilePicture(profile);
        MediaHandler.updateMediaCards(profile.getFavorites());
        Display.setPage(PageHandler.PREVIEWPAGE);
    }
}
