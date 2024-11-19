import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;

public class DraggablePanel extends JPanel implements MouseInputListener {

    private int clickX;
    private int clickY;
    private int panelX;
    private int panelY;

    public DraggablePanel() {
        super();
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(e.getX() + " " + e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        clickX = e.getXOnScreen();
        clickY = e.getYOnScreen();
        panelX = getX();
        panelY = getY();
        if (this.getParent() instanceof JLayeredPane) ((JLayeredPane) this.getParent()).moveToFront(this);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int deltaX = e.getXOnScreen() - clickX;
        int deltaY = e.getYOnScreen() - clickY;

        setLocation(panelX + deltaX, panelY + deltaY);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
