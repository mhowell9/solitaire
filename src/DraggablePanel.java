import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;

public class DraggablePanel extends JPanel implements MouseInputListener {

    public static final int PANEL_SIZE = 100;
    private int clickX;
    private int clickY;
    private Point clickPoint;
    private int differenceX;
    private int differenceY;
    private boolean isDragging;
    private DraggablePanel target;

    public DraggablePanel() {
        super();
        System.out.println("panel");
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    private void paintComponent() {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(e.getX() + " " + e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("h");
        if (e.getSource().getClass() == DraggablePanel.class && !isDragging) {
            target = (DraggablePanel) e.getSource();
            clickX = e.getXOnScreen();
            clickY = e.getYOnScreen();
            clickPoint = e.getPoint();
            isDragging = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isDragging = false;
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

        target.setLocation(clickX + deltaX - clickPoint.x, clickY + deltaY - clickPoint.y);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
