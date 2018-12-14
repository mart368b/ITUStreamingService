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

    private final static double spacewpercentage = 12.0/695.0;
    private final static double picwpercentage = 130/695.0;
    private final static double spacehpercentage = 12.0/552.0;
    private final static double pichpercentage = 130/552.0;

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

    public void setSelected(String imgName){
        String[] parts = imgName.split("-");
        type = 0;
        String t = parts[0];
        for (int i = 0; i < ImageHandler.types.length; i++){
            if (t.equals(ImageHandler.types[i])){
                type = i;
                break;
            }
        }
        col = 0;
        String c = parts[1];
        for (int i = 0; i < ImageHandler.colors.length; i++){
            if (c.equals(ImageHandler.colors[i])){
                col = i;
                break;
            }
        }
        selected = imgName;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Color color = g2.getColor();
        Stroke oldStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.green);
        dwspace = getWidth()*spacewpercentage;
        dhspace = getHeight()*spacehpercentage;
        dwpic = getWidth()*picwpercentage;
        dhpic = getHeight()*pichpercentage;
        g2.drawRect((int)(col*dwpic+col*dwspace), (int)(type*dhpic+type*dhspace), (int)(dwpic), (int)(dhpic));
        g2.setColor(color);
        g2.setStroke(oldStroke);
    }

    public String getSelected(){
        return selected;
    }

    public void reset(){
        selected = ImageHandler.types[0] + "-" + ImageHandler.colors[0];
        dx = 0;
        dy = 0;
    }
}
