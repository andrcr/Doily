import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                Doily doily = new Doily();

                doily.init();

                doily.setSize(1000, 800);

                doily.setResizable(true);

                doily.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                doily.setVisible(true);

            }

        });

    }

}
