package convertitore;

import net.miginfocom.swing.MigLayout;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.text.DecimalFormat;

public class ConvertitoreFrame extends JFrame {

    private final JLabel jlDistanza = new JLabel("Distanza");
    private final JTextField jtfDistanza = new JTextField(10);

    private final JLabel jlOre = new JLabel("Ore");
    private final JTextField jtfOre = new JTextField(3);

    private final JLabel jlMinuti = new JLabel("Minuti");
    private final JTextField jtfMinuti = new JTextField(3);

    private final JLabel jlSecondi = new JLabel("Secondi");
    private final JTextField jtfSecondi = new JTextField(3);

    private final JLabel jlPasso = new JLabel("Passo");
    private final JTextField jtfPasso = new JTextField(10);

    private final JLabel jlVelocita = new JLabel("Velocità");
    private final JTextField jtfVelocita = new JTextField(10);

    private final JButton jbCalcola = new JButton("Calcola");

    public ConvertitoreFrame () {
        initPanel();
    }

    public static void main (String[] args) {
        ConvertitoreFrame cf = new ConvertitoreFrame();
        cf.setVisible(true);
    }

    private void initPanel () {
        setLayout(new MigLayout());
        setSize(500, 300);

        add(jlDistanza);
        add(jtfDistanza, "wrap");

        add(jlOre);
        add(jtfOre);
        add(jlMinuti);
        add(jtfMinuti);
        add(jlSecondi);
        add(jtfSecondi, "wrap");

        add(jlPasso);
        add(jtfPasso, "wrap");

        add(jlVelocita);
        add(jtfVelocita, "wrap");

        add(jbCalcola);

        jbCalcola.addActionListener(e -> {
            if(checkDati()) {
                final Dati d = calcola(buildDati());
                jtfPasso.setText(d.getPassoMinuti() + " : " + d.getPassoSecondi());
                DecimalFormat df = new DecimalFormat("#.00");
                jtfVelocita.setText(df.format(d.getVelocita()));
            }
            else {
                JOptionPane.showMessageDialog(this, "Controlla i dati");
            }
        });
    }

    private Dati buildDati () {
        final Dati d = new Dati();

        d.setDistanza(Double.parseDouble(jtfDistanza.getText()));
        d.setOre(Integer.parseInt(jtfOre.getText()));
        d.setMinuti(Integer.parseInt(jtfMinuti.getText()));
        d.setSecondi(Integer.parseInt(jtfSecondi.getText()));

        return d;
    }

    private Dati calcola (Dati d) {
        d.setVelocita(d.getDistanza() / (d.getOre() + (d.getMinuti() / 60.0) + (d.getSecondi() / 3600.0)));
        final int minutiCorsa = d.getOre() * 60 + d.getMinuti();
        final int secondiCorsa = minutiCorsa * 60 + d.getSecondi();
        final int minutiPasso = (int) (minutiCorsa / d.getDistanza());
        final int secondiPasso = (int) Math.round(secondiCorsa / d.getDistanza() - minutiPasso * 60);
        d.setPassoMinuti(minutiPasso);
        d.setPassoSecondi(secondiPasso);

        return d;
    }

    private boolean checkDati () {
        return StringUtils.isNotBlank(jtfDistanza.getText()) && StringUtils.isNotBlank(jtfOre.getText()) && StringUtils.isNotBlank(jtfMinuti.getText()) && StringUtils.isNotBlank(jtfSecondi.getText());
    }

}
