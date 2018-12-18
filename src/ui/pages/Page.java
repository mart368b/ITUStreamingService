package ui.pages;

import ui.Display;

import javax.swing.*;
import java.awt.*;

public abstract class Page extends JPanel {

    public Page() {
        super();
    }

    public Page(LayoutManager layoutManager) {
        super(layoutManager);
    }

    public void addToDisplay(Display d){
        Container con = d.getContentPane();
        con.add(this);
    }

    public void removeFromDisplay(Display d){
        Container con = d.getContentPane();
        con.remove(this);
    }
}
