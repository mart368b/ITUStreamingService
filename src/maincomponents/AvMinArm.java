package maincomponents;

import reader.MediaHandler;
import ui.Display;
import ui.pages.PageHandler;
import user.Profile;
import user.User;
import user.UserHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class AvMinArm {

    public static User user;
    public static Profile profile;

    public AvMinArm(){}

    public static void main( String[] args){
        new SplashScreenDriver();
        ImageHandler.getInstance().init();
        UserHandler.getInstance().init();
        MediaHandler.getInstance();
        Display.setPage(PageHandler.LOGINPAGE);
    }

    public static class SplashScreenDriver {

        protected JProgressBar bar;

        public SplashScreenDriver(){
            BufferedImage img = null;
            try {
                File file = new File("res/button-images/Logo.png");
                img = ImageIO.read(file);
            }catch(Exception e){
                e.printStackTrace();
            }
            if(img == null){
                return;
            }
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            ImageIcon icon = new ImageIcon(img);
            JLabel label = new JLabel();
            label.setIcon(icon);
            bar = new JProgressBar();
            bar.setStringPainted(true);
            bar.setBackground(Color.BLACK);
            bar.setForeground(Color.YELLOW);
            panel.add(label);
            panel.add(Box.createRigidArea(new Dimension(0,20)));
            panel.add(bar);
            panel.setBackground(new Color(0,0,0,0));

            JWindow splash = new JWindow();
            splash.setBackground(new Color(0,0,0,0));
            splash.add(panel);
            splash.pack();
            splash.setLocationRelativeTo(null);
            splash.setVisible(true);

            int load = 2000;
            long start = System.currentTimeMillis();
            long stop = start + load;
            long time, lastupdate = start;
            setProgress(0);
            while ((time = System.currentTimeMillis()) < stop) {
                if (time - lastupdate > 50) {
                    lastupdate = time;
                    setProgress((int) (load - (stop - time))/(load/1000));
                }
            }

            splash.removeAll();
            splash.setVisible(false);
        }

        private void setProgress(final int progress){
            final int procentage = (progress /  bar.getMaximum()) * 10;

            SwingUtilities.invokeLater(new Runnable(){
                @Override
                public void run() {
                    bar.setValue(progress / 10);
                    String s = "Loading: " + procentage + "%";
                    bar.setString(s);
                }
            });
        }
    }
}
