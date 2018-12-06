package ui.cards;

import javax.swing.*;
import java.awt.*;

public abstract class SortTypeCard extends JPanel {

    public SortTypeCard(){
        super();
        add(getContent());
    }

    public abstract Component getContent();

}
