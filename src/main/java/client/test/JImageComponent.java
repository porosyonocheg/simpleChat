package client.test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class JImageComponent extends JPanel {
    private Image backgroundImage;
    public JImageComponent(String fileName) throws IOException {
        backgroundImage = ImageIO.read(new URL(fileName));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, this);
    }
}
