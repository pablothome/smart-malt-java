import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatLightLaf;

import view.TelaLogin;

public class Main {

    public static void main(String[] args) throws UnsupportedLookAndFeelException {

        FlatLightLaf.setup();

        new TelaLogin().setVisible(true);
        
        UIManager.setLookAndFeel(
                new FlatLightLaf()
        );
    }
}