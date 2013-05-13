package packMan;

import gameFramework.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PackApplet extends JApplet implements MouseListener {
  /**
	 * 
	 */
  private static final long serialVersionUID = -8955620248597032666L;
  static final int FRAME_WIDTH = 570;
  static final int FRAME_HEIGHT = 660;
  private Game game;
  private Engine engine;
  private Display display;
  private Thread engineThread;

  @Override
  public void init() {
    setLayout(new BorderLayout());
    game = (Game) new PackManGame();
    display = new Display(game);
    display.setPreferredSize(new Dimension(
      game.getPlace().getMap().getSize(2) * game.getScale(),
      game.getPlace().getMap().getSize(1) * game.getScale()));
    setBackground(game.getBackgroundColor());
    display.setBackground(game.getBackgroundColor());
    add(display, BorderLayout.CENTER);
    addKeyListener((KeyListener)game);
    addMouseListener(this);
    setFocusable(true);
    requestFocus();
    System.out.println("applet init");
  }

  @Override
  public void start() {
    engine = new Engine(game, display);
    engineThread = new Thread(engine);
    engineThread.start();
    System.out.println("applet start");
  }
  
  @Override
  public void stop() {
    game.setPaused(true);
    engine.markForDeath();
    System.out.println("applet stop");
  }
  
  @Override
  public void destroy() {
    engine = null;
    engineThread = null;
    display = null;
    game = null;
    System.out.println("applet destroy");

  }

  public static void main(String[] args) {
    PackApplet applet = new PackApplet();
    applet.init();
    JFrame frame = new JFrame("Packman!!1");
    frame.add(applet);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    applet.start();
//    Thread displayThread = new Thread(launcher.display);
//    displayThread.start();
  }

  public void mousePressed(MouseEvent e) { requestFocus(); }
  public void mouseClicked(MouseEvent e) { }
  public void mouseEntered(MouseEvent e) { }
  public void mouseExited(MouseEvent e) { }
  public void mouseReleased(MouseEvent e) { }
}
