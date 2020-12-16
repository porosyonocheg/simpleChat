package client.test;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class JFrameWithImage extends JFrame {
    private JImageComponent jImageComponent;

    public JFrameWithImage(String fileName) throws HeadlessException, IOException {
        this.jImageComponent = new JImageComponent(fileName);
        //setSize(jImageComponent.getWidth(), jImageComponent.getHeight());
        setSize(1200,1200);
        getContentPane().add(jImageComponent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setFocusable(true);
    }
}
