package towerDefence;

import towerDefence.tiles.BackgroundTile;
import towerDefence.tiles.Tile;
import towerDefence.tiles.TowerTile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SwingControl extends JFrame implements MouseListener {
    private static final SwingControl swingControl = new SwingControl();
    private static JPanel panel;

    public static Image imgSand;
    public static Image imgGrass;
    public static Image imgBush;
    public static Image imgEnemy1;
    public static Image imgEnemy2;
    public static Image imgEnemy3;
    public static Image imgEnemy4;
    public static Image imgDarkGrass;
    public static Image imgWater;
    public static Image imgTowerRed;
    public static Image imgTowerGreen;
    public static Image imgTowerBase;
    public static Image imgPlacer;
    public static Image imgPlacerOverlay;

    /**
     * Initializes the JFrame with the values from the Config file
     * Adds the Mouse Listener to JFrame
     */
    private static void initializeJFrame(){
        swingControl.setSize(Config.width*40, Config.height*40);
        swingControl.setVisible(true);

        swingControl.setName(Config.gameName);
        swingControl.setTitle(Config.gameName);

        swingControl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("src/towerDefence/assets/cursors/Arrow_blue.png");
        image = image.getScaledInstance(Config.cursorWidth, Config.cursorHeight, Image.SCALE_SMOOTH);

        Cursor c = toolkit.createCustomCursor(image , new Point(swingControl.getX(), swingControl.getY()-20), "img");
        swingControl.setCursor (c);

        swingControl.addMouseListener(swingControl);
    }

    /**
     * Initialize the JPanel with the custom MyPanel class
     */
    private static void initializeJPanel(){
        panel = new MyPanel();

        swingControl.add(panel);
        swingControl.pack();
    }

    /**
     * Initializes the JFrame and it's components
     * Initializes the JPanel
     * Reads in all the images (tower defence assets)
     * @throws IOException for the image reading (file not found)
     */
    public static void init() throws IOException {
        initImages();
        initializeJFrame();
        initializeJPanel();

        swingControl.setSize(Config.width*40 + 5, Config.height*40 + 32);
    }

    private static void initImages() throws IOException {
        imgSand = ImageIO.read(new File("src/towerDefence/assets/tiles/sand_tile.png"));
        imgGrass = ImageIO.read(new File("src/towerDefence/assets/tiles/grass_tile_3.png"));
        imgDarkGrass = ImageIO.read(new File("src/towerDefence/assets/tiles/grass_tile_2.png"));
        imgWater = ImageIO.read(new File("src/towerDefence/assets/tiles/0.png"));

        imgPlacer = ImageIO.read(new File("src/towerDefence/assets/tiles/placer.png"));
        imgPlacerOverlay = ImageIO.read(new File("src/towerDefence/assets/tiles/buildOverlay.png"));

        imgBush = ImageIO.read(new File("src/towerDefence/assets/objects/smallBush.png"));
        imgEnemy1 = ImageIO.read(new File("src/towerDefence/assets/characters/soldier1.png"));
        imgEnemy2 = ImageIO.read(new File("src/towerDefence/assets/characters/woodenEnemy.png"));
        imgEnemy3 = ImageIO.read(new File("src/towerDefence/assets/characters/terminator.png"));
        imgEnemy4 = ImageIO.read(new File("src/towerDefence/assets/characters/megaKnight.png"));

        imgTowerBase = ImageIO.read(new File("src/towerDefence/assets/towers/base.png"));
        imgTowerGreen = ImageIO.read(new File("src/towerDefence/assets/towers/turretGreen.png"));
        imgTowerRed = ImageIO.read(new File("src/towerDefence/assets/towers/turretRed.png"));
    }

    /**
     * @param e Spawns tower then mouse click is detected
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int tileX = e.getX()/40;
        int tileY = (e.getY() - 30)/40;
        System.out.println(e.getX()/40 + "  " + (e.getY() - 30)/40);
        TowerCore.spawnTower(tileX, tileY);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
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

    public static class MyPanel extends JPanel implements ActionListener {

        Timer timer=new Timer(5, this);

        /**
         * - read the Board class's board and draw the current board
         * - read the Board class's enemy list and draw them with their respective health bars
         * - read the Board class's tower list and draw those with their respective rotations
         */
        public void paint(Graphics g) {
            for(int i = 0; i< Config.height; i++){
                for(int j = 0; j< Config.width; j++){
                    Image img;
                    Tile f = Board.getBoard()[i][j];
                    if(f.getSt().equals(BackgroundTile.State.GRASS)){
                        img = imgGrass;
                    }else if (f.getSt().equals(BackgroundTile.State.SAND)){
                        img = imgSand;
                    }else if(f.getSt().equals(BackgroundTile.State.DARK_GRASS)){
                        img = imgDarkGrass;
                    }else{
                        img = imgWater;
                    }
                    ArrayList<BackgroundTile> bt = Board.neighbors(j, i, Config.width, Config.height);
                    boolean d = false;
                    for(BackgroundTile p : bt){
                        if(p.getState()== Tile.State.SAND){
                            d = true;
                            break;
                        }
                    }
                    g.drawImage(img, j*40, i*40, 40, 40, null);
                    if(d && (img == imgGrass || imgDarkGrass == img)) g.drawImage(imgPlacerOverlay, j*40, i*40, 40, 40, null);
                }
            }
            ArrayList<Enemy> ens = Board.getEnemies(); //solve races
            for(Enemy en : ens){
                g.drawRect(en.getX()*40 + 10, en.getY()*40 + 2, 20, Config.healthBarSizeHeight);
                double health = en.getHealth();
                g.setColor(Color.green);
                if(health <= 30) g.setColor(Color.red);
                g.fillRect(en.getX()*40 + 10, en.getY()*40 + 2, (int)(Config.healthBarSizeWidth * (health/en.getLevel().getHealth())), Config.healthBarSizeHeight);
                g.setColor(Color.black);
                Image enImg;
                if(en.getLevel() == Enemy.type.BASIC) enImg = imgEnemy1;
                else if(en.getLevel() == Enemy.type.WOODEN) enImg = imgEnemy2;
                else if(en.getLevel() == Enemy.type.TERMINATOR) enImg = imgEnemy3;
                else enImg = imgEnemy4;
                g.drawImage(enImg, en.getX()*40, en.getY()*40, 40, 40, null);
            }
            for(TowerTile t : Board.getTowers()){
                if(t.getTowerLevel() == 3) g.drawImage(imgTowerBase, t.getX()*40, t.getY()*40, 40, 40, null);
                BufferedImage d;
                if(t.getTowerLevel() == 2){
                    d= new BufferedImage(imgTowerRed.getWidth(null), imgTowerRed.getHeight(null), BufferedImage.TYPE_INT_ARGB);
                }
                else if(t.getTowerLevel() == 3){
                    d= new BufferedImage(imgTowerRed.getWidth(null)-10, imgTowerRed.getHeight(null)-10, BufferedImage.TYPE_INT_ARGB);
                }
                else d= new BufferedImage(imgTowerGreen.getWidth(null), imgTowerGreen.getHeight(null), BufferedImage.TYPE_INT_ARGB);
                Graphics2D gr = d.createGraphics();


                if(t.getTowerLevel() >= 2) gr.drawImage(imgTowerRed, 0, 0, null);
                else if(t.getTowerLevel() == 1) gr.drawImage(imgTowerGreen, 0, 0, null);

                d = rotateImageByDegrees(d, t.rotationRad);

                g.drawImage(d, t.getX()*40, t.getY()*40, 40, 40, null);
            }

            g.setColor(Color.white);
            g.setFont(Font.getFont(Config.font));
            g.drawString("$"+DifficultyLevelController.getMoneys() + "", Config.moneyLocationX, Config.hudY);
            g.drawString("Level "+DifficultyLevelController.getLevel() + "", Config.levelLocationX, Config.hudY);
            g.setColor(Color.black);

            timer.start();
        }

        /**
         * Repaint the board every 5 milliseconds
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==timer){
                repaint();// this will call at every 1 second
            }
        }

        /**
         *
         * @param img Buffered image input
         * @param angle angle from π/2 on a euclidean plane clockwise in radians
         * @return Rotated BufferedImage
         *
         * Copied from stackoverflow
         */
        public BufferedImage rotateImageByDegrees(BufferedImage img, double angle) {
            int width = img.getWidth();
            int height = img.getHeight();

            BufferedImage newImage = new BufferedImage(
                    img.getWidth(), img.getHeight(), img.getType());

            Graphics2D g2 = newImage.createGraphics();

            g2.rotate(angle, width / 2,
                    height / 2);
            g2.drawImage(img, null, 0, 0);

            // Return rotated buffer image
            return newImage;
        }

    }
}
