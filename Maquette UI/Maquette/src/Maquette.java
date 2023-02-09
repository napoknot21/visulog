import javax.swing.*;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class Maquette extends JFrame {

    private JLabel test;



    public Maquette(){
        super("Visulog");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(800,600);
        this.setLocationRelativeTo(null);


        JPanel contentPane = (JPanel) this.getContentPane();
        JPanel westComponent = new JPanel();
        JPanel northComponent = new JPanel();
        JPanel middleComponent = new JPanel();



        JButton commits = new JButton("Commits");

        commits.addActionListener(this::launchPluginListener);
        westComponent.add(commits);

        westComponent.add(new JButton("Merges"));
        westComponent.add(new JButton("Caractères"));
        westComponent.add(new JButton("Graphiques"));
        westComponent.add(new JButton("Developpeurs"));

        westComponent.setPreferredSize(new Dimension(100,0));
        westComponent.setLayout(new GridLayout(5,1));

        JLabel titre = new JLabel("VISULOG");
        northComponent.add(titre);


        contentPane.add(westComponent, BorderLayout.WEST);
        contentPane.add(northComponent, BorderLayout.NORTH);
        contentPane.add(middleComponent, BorderLayout.CENTER);
    }

    private void launchPluginListener(ActionEvent actionEvent)  {
        try {
            File htmlFile = new File("\\dev\\L2\\graph.html");
            Desktop.getDesktop().browse(htmlFile.toURI());
            System.out.println("hello");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void launchPluginListener(){
        //String res = "Listes des Commits :\n Soline : 4\n Kevin : 6\n Jackie : 4";

        //System.out.println(res);
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        Maquette m = new Maquette();
        m.setVisible(true);

    }
}
