package packMan;

import gameFramework.*;
import zaneAI.*;
import java.awt.*; // Color
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.*;

public class PackManGame extends Game implements KeyListener {

/*-----------------------Constants and Variables------------------------------*/

  private final Color BACKGROUND_COLOR = Color.BLACK;
  private final int DEFAULT_SCALE = 30;
  private final int SPAWN_RATE = 50;
  private final int SUPER_PELLET_DURATION = 100;
  private final int DEFAULT_TURN_FREQUENCY = 400; // milliseconds
  private Entity packSpawner;
  private Entity ghostSpawner;
  private Queue<Person> spawnQueue = new LinkedList<Person>();
  private PackMan packMan = new PackMan(new int[] {1, 0, 0}, this);
  private Ghost[] ghostArray;
  private JFrame menu;
  private int pelletCount = 0;
  private int lifeCount = 3;
  private int time = 0;
  private int spawnMod = 0;
  private int superCount = 0;
  private int[][] locations;
  private int[][] coordToIndex;
  private int[][] adjacency;
  private AStarTree aStar;
  private PackDataAnalyzer analyzer = new PackDataAnalyzer();
  private int selectedMap = 0;
  private String[] MAP_NAMES = {"Classic Map", "Classic Map 2", "Teresa Map",
    "Test Map"};
  private int[] ghostAI = {4, 0, 2, 1};
  private int[][][] mapLayout =
   {{{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
     {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1},
     {1,0,1,1,0,1,1,1,0,1,0,1,1,1,0,1,1,0,1},
     {1,6,1,1,0,1,1,1,0,1,0,1,1,1,0,1,1,6,1},
     {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
     {1,0,1,1,0,1,0,1,1,1,1,1,0,1,0,1,1,0,1},
     {1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1},
     {1,1,1,1,0,1,1,1,4,1,4,1,1,1,0,1,1,1,1},
     {4,4,4,1,0,1,4,4,4,4,4,4,4,1,0,1,4,4,4},
     {1,1,1,1,0,1,4,1,1,3,1,1,4,1,0,1,1,1,1},
     {4,4,4,4,0,4,4,1,1,1,1,1,4,4,0,4,4,4,4},
     {1,1,1,1,0,1,4,1,1,1,1,1,4,1,0,1,1,1,1},
     {4,4,4,1,0,1,4,4,4,4,4,4,4,1,0,1,4,4,4},
     {1,1,1,1,0,1,4,1,1,1,1,1,4,1,0,1,1,1,1},
     {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1},
     {1,0,1,1,0,1,1,1,0,1,0,1,1,1,0,1,1,0,1},
     {1,6,0,1,0,0,0,0,0,2,0,0,0,0,0,1,0,6,1},
     {1,1,0,1,0,1,0,1,1,1,1,1,0,1,0,1,0,1,1},
     {1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1},
     {1,0,1,1,1,1,1,1,0,1,0,1,1,1,1,1,1,0,1},
     {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
     {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}},

    {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
     {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1},
     {1,0,1,1,0,1,1,1,0,1,0,1,1,1,0,1,1,0,1},
     {1,0,1,1,0,1,1,1,0,1,0,1,1,1,0,1,1,0,1},
     {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
     {1,0,1,1,0,1,0,1,1,1,1,1,0,1,0,1,1,0,1},
     {1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1},
     {1,1,1,1,0,1,1,1,4,1,4,1,1,1,0,1,1,1,1},
     {4,4,4,1,0,1,4,4,4,4,4,4,4,1,0,1,4,4,4},
     {1,1,1,1,0,1,4,1,1,3,1,1,4,1,0,1,1,1,1},
     {4,4,4,4,0,4,4,1,1,1,1,1,4,4,0,4,4,4,4},
     {1,1,1,1,0,1,4,1,1,1,1,1,4,1,0,1,1,1,1},
     {4,4,4,1,0,1,4,4,4,4,4,4,4,1,0,1,4,4,4},
     {1,1,1,1,0,1,4,1,1,1,1,1,4,1,0,1,1,1,1},
     {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1},
     {1,0,1,1,0,1,1,1,0,1,0,1,1,1,0,1,1,0,1},
     {1,0,0,1,0,0,0,0,0,2,0,0,0,0,0,1,0,0,1},
     {1,1,0,1,0,1,0,1,1,1,1,1,0,1,0,1,0,1,1},
     {1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1},
     {1,0,1,1,1,1,1,1,0,1,0,1,1,1,1,1,1,0,1},
     {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
     {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}},

    {{1,1,1,1,1,1,1,1,1,1,1,1,1,1},
     {1,0,0,0,0,0,0,0,0,0,1,0,0,1},
     {1,0,1,1,0,1,1,1,0,1,1,1,0,1},
     {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
     {1,1,1,0,1,1,1,1,0,1,0,1,0,1},
     {1,0,0,0,0,1,0,0,0,1,0,0,0,1},
     {1,0,1,1,0,1,0,3,0,1,0,1,1,1},
     {1,0,1,0,0,1,1,1,0,1,0,0,0,1},
     {1,0,0,0,1,1,2,1,1,1,0,1,0,1},
     {1,0,1,0,0,0,0,0,0,0,0,1,0,1},
     {1,0,1,1,1,1,0,1,0,1,0,1,0,1},
     {1,0,0,0,1,0,0,1,0,1,0,1,0,1},
     {1,0,1,0,0,0,1,1,0,0,0,0,0,1},
     {1,1,1,1,1,1,1,1,1,1,1,1,1,1}},

    {{1,1,1,1,1,1,1,1,1},
     {1,0,0,0,0,0,0,0,1},
     {1,0,1,1,1,1,1,0,1},
     {1,0,1,4,4,4,1,0,1},
     {1,0,1,4,3,4,1,0,1},
     {1,0,1,4,4,4,1,0,1},
     {1,0,1,1,1,1,1,0,1},
     {1,0,0,0,2,0,0,0,1},
     {1,1,1,1,1,1,1,1,1}}};

/*------------------------------Constructor-----------------------------------*/

  public PackManGame() {
    setBackgroundColor(BACKGROUND_COLOR);
    setScale(DEFAULT_SCALE);
    setTurnFrequency(DEFAULT_TURN_FREQUENCY);
    setPaused(true);
    resetMap();
  }

/*------------------------------Accessors-------------------------------------*/

  public int getLifeCount() { return lifeCount; }

  public int getPelletCount() { return pelletCount; }

  public int getSpawnRate() { return SPAWN_RATE; }

  public int getTime() { return time; }
  
  // Temporary Ghost Accessor Methods
  int[][] getCoordToIndex() { return coordToIndex; }
  int[][] getAdjacency() { return adjacency; }
  int[][] getLocations() { return locations; }
  int[][][] getMapLayout() { return mapLayout; }
  int getSelectedMap() { return selectedMap; }
  Person getPackMan() { return packMan; }
  AStarTree getAStar() { return aStar; }

/*-------------------------------Methods--------------------------------------*/

  /** Reset Map */
  private void resetMap() {
    if(getWindow() != null) {
      getWindow().call("post_score", new String[]{"0"});
      getWindow().call("post_lives", new String[]{"3"});
    }
    
    Map map = new Map3D(mapLayout[selectedMap][0].length,
      mapLayout[selectedMap].length, 2 + ghostAI.length);
    setPlace(new Place(map));
    ghostArray = new Ghost[ghostAI.length];
    time = 0;
    lifeCount = 3;
    pelletCount = 0;
    int locationSize = 0;
    int k = 0;
    int z = 0;
    spawnQueue = new LinkedList<Person>();
    setQueue(new LinkedList<Person>());
    for(int i = 0; i < mapLayout[selectedMap].length; ++i) {
      for(int j = 0; j < mapLayout[selectedMap][0].length; ++j) {
        switch(mapLayout[selectedMap][i][j]) {
          // Add unvisited path
          case 0:  map.setEntity(new Floor(new int[] {0, i, j}, true, false),
                     new int[] {0, i, j});
                   ++pelletCount;
                   ++locationSize;
                   break;
          // Add wall
          case 1:  map.setEntity(new Wall(new int[] {0, i, j}),
                     new int[] {0, i, j});
                   break;
          // Set packMan spawn point
          case 2:  packSpawner = new Floor(new int[] {0, i, j});
                   packMan = new PackMan(new int[] {1, i, j}, this);
                   map.setEntity(packSpawner, packSpawner.getLocation());
                   ++locationSize;
                   break;
          // Set ghost spawn point
          case 3:  ghostSpawner = new Wall(new int[] {0, i, j});
                   for(int m = 0; m < ghostArray.length; m++)
                     ghostArray[m] = new Ghost(new int[] {m + 2, i, j},
                         ghostAI[m], this);
                   map.setEntity(ghostSpawner, ghostSpawner.getLocation());
                   ++locationSize;
                   break;
          // Set visited path
          case 4:  map.setEntity(new Floor(new int[] {0, i, j}),
                     new int[] {0, i, j});
                   ++locationSize;
                   break;
          // Set path with super pellet
          case 6:  map.setEntity(new Floor(new int[] {0, i, j}, false, true),
                       new int[] {0, i, j});
                   ++locationSize;
                   break;
          default:
        }
      }
    }
    getQueue().add(packMan);
    map.setEntity(packMan, packMan.getLocation());
    for(int i = 0; i < ghostArray.length; ++i) {
      getQueue().add(ghostArray[i]);
      map.setEntity(ghostArray[i], ghostArray[i].getLocation());
      spawnQueue.add(ghostArray[i]);
    }

    // Initialize and populate locations list and index list
    k = 0;
    locations = new int[locationSize][2];
    coordToIndex =
      new int[mapLayout[selectedMap].length][mapLayout[selectedMap][0].length];
    for(int i = 0; i < mapLayout[selectedMap].length; ++i) {
      for(int j = 0; j < mapLayout[selectedMap][0].length; ++j) {
        if(mapLayout[selectedMap][i][j] != 1) {
          locations[k][0] = i;
          locations[k][1] = j;
          coordToIndex[i][j] = k;
          ++k;
        }
      }
    }

    // Initialize and populate adjacency list
    adjacency = new int[locationSize][4];
    for(int i = 0; i < locations.length; ++i) {
      z = 0;
      for(int j = 0; j < locations.length; ++j) {
        if(mapLayout[selectedMap][locations[j][0]][locations[j][1]] % 2 == 0
          && dist(i, j) == 1) {
          adjacency[i][z++] = j;
        }
      }
      while(z < 4)
        adjacency[i][z++] = -1;
    }
    aStar = new AStarTree(adjacency, locations);
  }

  /** Reset PackMan */
  private void resetPackMan() {
    try {
      getPlace().getMap().setEntity(null, packMan.getLocation());
    } catch(Exception ex) { } // Can throw irrelevant exception if new game
    packMan.reset();
    getPlace().getMap().setEntity(packMan, packMan.getLocation());
  }

  /** Reset All Ghosts */
  private void resetGhosts() {
    spawnMod = time % SPAWN_RATE;
    spawnQueue = new LinkedList<Person>();
    for(int i = 0; i < ghostArray.length; i++) {
      resetGhost(ghostArray[i]);
    }
  }
  
  private void resetGhost(Ghost ghost) {
    ghost.setScared(false);
    try {
      getPlace().getMap().setEntity(null, ghost.getLocation());
    } catch(Exception ex) { } // Can throw irrelevant exception if new game
    ghost.reset();
    getPlace().getMap().setEntity(ghost, ghost.getLocation());
    spawnQueue.add(ghost);
  }

  /** Spawn Ghost */
  private void spawnGhost() {
    if(!spawnQueue.isEmpty()) {
      if((time - spawnMod) % SPAWN_RATE == 0) {
        Person ghost = spawnQueue.remove();
        ghost.setDirection(-1);
        int adj = adjacency[coordToIndex[ghostSpawner.getLocation(1)]
          [ghostSpawner.getLocation(2)]][0];
        int[] move = {ghost.getLocation(0), locations[adj][0],
          locations[adj][1]};
        ghost.getQueue().add(move);
      }
    }
  }

  /** Calculate distance between two points */
  private int dist(int i, int j) {
    int distance = 0;
    for(int k = 0; k < 2; ++k) {
      distance += Math.abs(locations[i][k] - locations[j][k]);
    }
    return distance;
  }

  /** Heart and soul of the game */
  public int checkMap() {   
    if(isWin()) {
      saveScore();
      analyzer.push(this);
      setPaused(true);
      resetMap();
      resetPackMan();
      resetGhosts();
      return 1;
    }
    
    if(getWindow() != null)
        getWindow().call("post_score",
          new String[]{Integer.toString(packMan.getScore())});
    
    ++time;
    spawnGhost();
    if(isDead(packMan.getLocation())) {
      setPaused(true);
      packMan.augmentScore(-250);
      System.out.println("DEAD");
      --lifeCount;
      
      if(getWindow() != null) {
          getWindow().call("post_lives",
            new String[]{Integer.toString(lifeCount)});
          getWindow().call("post_score",
            new String[]{Integer.toString(packMan.getScore())});
      }
      
      if(lifeCount <= 0) {
//        if(getWindow() != null)
//          getWindow().call("post_score",
//            new String[]{Integer.toString(packMan.getScore())});

        saveScore();
        analyzer.push(this);
        resetMap();
      } else {
        resetPackMan();
        resetGhosts();
      }
      if(getWindow() != null)
          getWindow().call("post_score",
            new String[]{Integer.toString(packMan.getScore())});
      return -1;
    }
    else {
      if(superCount > 0) {
        superCount--;
        if(superCount <= 0) {
          disableSuperMode();
        }
      }
    }
    return 0;
  }
  
  private void saveScore() {
	String score = Integer.toString(packMan.getScore());
	String message = "<html><table>" +
					 "<tr><td>Final Score:</td><td>"       + score        + "</td></tr>" +
					 "<tr><td>Pellets remaining:</td><td>" + pelletCount  + "</td></tr>" +
					 "<tr><td>Lives remaining:</td><td>"   + lifeCount    + "</td></tr>" +
					 "</table>";
					 
	if(getWindow() != null) {
	  message += "</br><p>Please input your name to save your score:</p></html>";
	  String name = JOptionPane.showInputDialog(null, message, "Game Over", JOptionPane.DEFAULT_OPTION);
	  getWindow().call("post_score", new String[] { score });
	  getWindow().call("save_score", new String[] { score, name });
	} else {
	  message += "</html>";
	  JOptionPane.showMessageDialog(null, message, "Game Over", JOptionPane.DEFAULT_OPTION);
	}
  }

  /** Check for death of packman and ghosts */
  private boolean isDead(int[] packLoc) {
    boolean dead = false;
    Map map = getPlace().getMap();
    for(int i = 2; i < map.getSize(0); ++i) {
      map.setLocation(1, packLoc[1]);
      map.setLocation(2, packLoc[2]);
      map.setLocation(0, i);
      Ghost ghost = (Ghost)map.getEntity();
      if(ghost != null) {
        if(ghost.isScared()) {
          packMan.augmentScore(100);
          resetGhost(ghost);
          if(spawnQueue.isEmpty()) {
            spawnMod = time % SPAWN_RATE;
          }
        } else {
          dead = true;
        }
      }
    }
    return dead;
  }

  /** Consume pellets/super pellets, check for win */
  private boolean isWin() {
    Map map = getPlace().getMap();
    map.setLocation(0, 0);
    map.setLocation(1, packMan.getLocation(1));
    map.setLocation(2, packMan.getLocation(2));
    Floor floor = (Floor)map.getEntity();
    if(floor.isSuperPellet()) {
      floor.setSuperPellet(false);
      enableSuperMode();
    }
    if(floor.isPellet()) {
      packMan.augmentScore(10);
      floor.setPellet(false);
      --pelletCount;
      if(pelletCount == 0) {
        packMan.augmentScore(1000);
        System.out.println("WIN");
        return true;
      }
    }
    return false;
  }

  /** Turn packman into the predator */
  private void enableSuperMode() {
    superCount = SUPER_PELLET_DURATION;
    for(int i = 0; i < ghostArray.length; i++) {
      ghostArray[i].setScared(true);
      ghostArray[i].getQueue().clear();
    }
  }

  /** Turn packman back into the prey */
  private void disableSuperMode() {
    for(int i = 0; i < ghostArray.length; i++) {
      if(ghostArray[i].isScared()) {
        if(ghostArray[i].getDirection() != 0) {
          ghostArray[i].setDirection(-1);
        }
        ghostArray[i].setScared(false);
      }
    }
  }

  private void showMenu() {
    if(menu == null) {
      int menuSize = ghostArray.length + 1;
      menu = new JFrame("Menu");
      menu.setLocationRelativeTo(null);
      JPanel contentPane = new JPanel();
      contentPane.setLayout(new GridLayout(menuSize, 1));

      // Add map selector
      final JComboBox<String> mapBox = new JComboBox<String>(MAP_NAMES);
      mapBox.setSelectedIndex(selectedMap);
      mapBox.addItemListener(new ItemListener() {
        public void itemStateChanged(ItemEvent e) {
          selectedMap = mapBox.getSelectedIndex();
          analyzer = new PackDataAnalyzer();
          resetMap();
        }
      });
      contentPane.add(mapBox);

      // Add ghost color change options
      for(int i = 0; i < ghostArray.length; ++i) {
        final int k = i;
        final JComboBox<String> ghostBox = new JComboBox<String>(Ghost.getTypes());
        ghostBox.setSelectedIndex(ghostArray[i].getType());
        ghostBox.addItemListener(new ItemListener() {
          public void itemStateChanged(ItemEvent e) {
            ghostArray[k].setType(ghostBox.getSelectedIndex());
            ghostArray[k].getQueue().clear();
          }
        });
        contentPane.add(ghostBox);
      }
      menu.add(contentPane, BorderLayout.CENTER);
      menu.pack();
    }
    menu.setVisible(true);
  }

/*---------------------------Keyboard Shortcuts-------------------------------*/

  // Assign keyboard shortcuts
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    switch(key) {
      // north
      case KeyEvent.VK_UP:
      case KeyEvent.VK_W:     packMan.setDirection(1);
                              setPaused(false);
                              break;
      // south
      case KeyEvent.VK_DOWN:
      case KeyEvent.VK_S:     packMan.setDirection(3);
                              setPaused(false);
                              break;
      // west
      case KeyEvent.VK_LEFT:
      case KeyEvent.VK_A:     packMan.setDirection(4);
                              setPaused(false);
                              break;
      // east
      case KeyEvent.VK_RIGHT:
      case KeyEvent.VK_D:     packMan.setDirection(2);
                              setPaused(false);
                              break;
      // pause/resume
      case KeyEvent.VK_SPACE: setPaused(!getPaused());
                              break;
      // display menu
      case KeyEvent.VK_M:     setPaused(true);
                              showMenu();
                              break;
      // display game stats
      case KeyEvent.VK_N:     setPaused(true);
                              JOptionPane.showMessageDialog(null,
                                analyzer.toString(), "Data",
                                JOptionPane.INFORMATION_MESSAGE);
                              System.out.println(analyzer.toString());
                              break;
      default:
    }
  }

  public void keyTyped(KeyEvent e) { }

  public void keyReleased(KeyEvent e) { }
}