import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

/**
 * Class extending the JPanel class allowing the panel to be dragged across a component
 * using the mouse. Moves the panel to the front of a JLayeredPane if appropriate
 * @author mhowell9
 */


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
    public void mousePressed(MouseEvent e) {
        clickX = e.getXOnScreen();
        clickY = e.getYOnScreen();
        panelX = getX();
        panelY = getY();
        if (this.getParent() instanceof JLayeredPane) ((JLayeredPane) this.getParent()).moveToFront(this);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int deltaX = e.getXOnScreen() - clickX;
        int deltaY = e.getYOnScreen() - clickY;

        setLocation(panelX + deltaX, panelY + deltaY);
    }

    // UNUSED OVERRIDES

    @Override
    public void mouseMoved(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {}
}
