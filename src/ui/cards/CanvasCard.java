package ui.cards;

import maincomponents.ImageHandler;
import ui.components.ImageButton;
import user.Profile;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CanvasCard extends ImageButton {

    private String selected;

    private double dx, dy;
    private double dwspace, dwpic, dhspace, dhpic;
    private int x, y, col, type;

    public CanvasCard(){
        super("canvas");
        init();
    }

    public CanvasCard(Profile profile){
        super(profile.getProfilePictureName());
        init();
    }

    private void init(){
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setMaximumSize(new Dimension(230,184));
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PointerInfo info = MouseInfo.getPointerInfo();
                Point loc = info.getLocation();
                Point picloc = getLocationOnScreen();

                double spacewpercentage = 12.0/695.0;
                double picwpercentage = 130/695.0;
                double spacehpercentage = 12.0/552.0;
                double pichpercentage = 130/552.0;

                dwspace = getWidth()*spacewpercentage;
                dwpic = getWidth()*picwpercentage;
                dhspace = getHeight()*spacehpercentage;
                dhpic = getHeight()*pichpercentage;

                x = loc.x-picloc.x;
                dx = getWidth()/ImageHandler.colors.length;
                y = loc.y-picloc.y;
                dy = getHeight()/ImageHandler.types.length;

                col = (int)(x/dx);
                type = (int)(y/dy);
                String clicked = ImageHandler.types[type] + "-" + ImageHandler.colors[col];
                selected = clicked;
            }
        });
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(dx == 0 && dy == 0) return;
        Graphics2D g2 = (Graphics2D) g;
        Color color = g2.getColor();
        Stroke oldStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.green);
        g2.drawRect((int)(col*dwpic+col*dwspace), (int)(type*dhpic+type*dhspace), (int)(dwpic), (int)(dhpic));
        g2.setColor(color);
        g2.setStroke(oldStroke);
    }

    public String getSelected(){
        return selected;
    }

    public void reset(){
        selected = null;
        dx = 0;
        dy = 0;
    }
}
