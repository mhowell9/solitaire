import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Panel that sits underneath CardBank, allowing the card bank to be restocked with cards from
 * the river after it is depleted.
 * @author mhowell9
 */

public class CardBankClickable extends JPanel implements MouseListener {

    private static final int POS_X = 20;
    private static final int POS_Y = 20;
    private static final int SIZE_X = 65;
    private static final int SIZE_Y = 100;

    public CardBankClickable() {
        super();
        this.setBounds(POS_X, POS_Y, SIZE_X, SIZE_Y);
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        GamePanel.cardRiver.pushToBank();
    }

    // UNUSED OVERRIDES

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
