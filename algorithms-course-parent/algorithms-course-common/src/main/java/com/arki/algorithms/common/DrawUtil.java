package com.arki.algorithms.common;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class DrawUtil {

    public static void drawHistogram(List<? extends Number> list){

        int size = list.size();
        double max = 0;
        for (int i = 0; i < list.size(); i++) {
            double temp = list.get(i).doubleValue();
            max = temp > max ? temp : max;
        }

        int canvasWidth = 500;
        int canvasHeight = 309;
        StdDraw.setCanvasSize(canvasWidth,canvasHeight);
        double xScale = size * 1.2;
        double yScale = max * 1.2;
        StdDraw.setXscale(0, xScale);
        StdDraw.setYscale(0, yScale);

        StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);

        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
        double interval = 1;
        double halfWidth = interval * 0.25;
        for (int i = 0; i < list.size(); i++) {
            double y = list.get(i).doubleValue();
            StdDraw.filledRectangle(interval * i + halfWidth, y / 2, halfWidth, y / 2);
        }
    }


    /**
     * Draw rectangle by specifying the left-bottom vertex, width, and height.
     * @param x x-coordinate of the left-bottom vertex.
     * @param y y-coordinate of the left-bottom vertex.
     * @param width
     * @param height
     */
    public static void rectangleByVertex(double x, double y, double width, double height) {
        StdDraw.rectangle(x + width / 2, y + height / 2, width / 2, height / 2);
    }

    /**
     * Draw filled rectangle by specifying the left-bottom vertex, width, and height.
     * @param x x-coordinate of the left-bottom vertex.
     * @param y y-coordinate of the left-bottom vertex.
     * @param width
     * @param height
     */
    public static void filledRectangleByVertex(double x, double y, double width, double height) {
        StdDraw.filledRectangle(x + width / 2, y + height / 2, width / 2, height / 2);
    }

    public static void main(String[] args) {
//        ArrayList<Integer> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            list.add(i);
//        }
//        drawHistogram(list);
        Pen pen = new Pen();

    }


    private static final class Pen implements ActionListener, MouseListener, MouseMotionListener, KeyListener {


        /**
         * Components:
         */
        private JFrame frame;
        private BufferedImage bufferedImage;
        private Graphics2D graphics;

        /**
         * Parameters:
         */
        private int width = 500;
        private int height = 309;
        private double xmin = 0;
        private double xmax = 1;
        private double ymin = 0;
        private double ymax = 1;
        private Color backgroundColor = Color.WHITE;
        private Color penColor = Color.BLACK;
        private double penRadius = 1;



        // for synchronization
        private static Object mouseLock = new Object();
        private static Object keyLock = new Object();

        // mouse state
        private static boolean isMousePressed = false;
        private static double mouseX = 0;
        private static double mouseY = 0;

        // queue of typed key characters
        private static LinkedList<Character> keysTyped = new LinkedList<Character>();

        // set of key codes currently pressed down
        private static TreeSet<Integer> keysDown = new TreeSet<Integer>();

        public Pen(){
            init();
        }
        private void init(){
            frame = new JFrame();
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            graphics = bufferedImage.createGraphics();
            graphics.setColor(backgroundColor);
            graphics.fillRect(0,0,width,height);
            show();

            // add antialiasing
            RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            graphics.addRenderingHints(hints);

            // frame stuff
            ImageIcon icon = new ImageIcon(bufferedImage);
            JLabel draw = new JLabel(icon);

            draw.addMouseListener(this);
            draw.addMouseMotionListener(this);


            frame.setContentPane(draw);
            frame.addKeyListener(this);    // JLabel cannot get keyboard focus
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            // closes all windows
            frame.setTitle("Pen");
            frame.setJMenuBar(createMenuBar());
            frame.pack();
            frame.requestFocusInWindow();
            frame.setVisible(true);
        }
        public void setCanvas(int canvasWidth, int canvasHeight, Color backgroundColor){
            width=canvasWidth;
            height=canvasHeight;
            this.backgroundColor=backgroundColor;
            init();
        }
        public void setPenColor(Color color){
            penColor = color;
            graphics.setColor(color);
        }
        public void setPenRadius(double radius){
            penRadius=radius;
            BasicStroke basicStroke = new BasicStroke((float)radius, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            graphics.setStroke(basicStroke);
        }
        public void show(){
            graphics.drawImage(bufferedImage, 0, 0, null);
            frame.repaint();
        }
        private JMenuBar createMenuBar(){
            JMenuBar menuBar = new JMenuBar();
            JMenu menu = new JMenu("File");
            menuBar.add(menu);
            JMenuItem menuItem = new JMenuItem("Save...   ");
            menuItem.addActionListener(this);
            menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            menu.add(menuItem);
            return menuBar;
        }
        public void save(String fileName){
            if (fileName == null) throw new IllegalArgumentException();
            File file = new File(fileName);
            String suffix = fileName.substring(fileName.lastIndexOf('.') + 1);

            // png files
            if ("png".equalsIgnoreCase(suffix)) {
                try {
                    ImageIO.write(bufferedImage, suffix, file);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // need to change from ARGB to RGB for JPEG
            // reference: http://archives.java.sun.com/cgi-bin/wa?A2=ind0404&L=java2d-interest&D=0&P=2727
            else if ("jpg".equalsIgnoreCase(suffix)) {
                WritableRaster raster = bufferedImage.getRaster();
                WritableRaster newRaster;
                newRaster = raster.createWritableChild(0, 0, width, height, 0, 0, new int[] {0, 1, 2});
                DirectColorModel cm = (DirectColorModel) bufferedImage.getColorModel();
                DirectColorModel newCM = new DirectColorModel(cm.getPixelSize(),
                        cm.getRedMask(),
                        cm.getGreenMask(),
                        cm.getBlueMask());
                BufferedImage rgbBuffer = new BufferedImage(newCM, newRaster, false,  null);
                try {
                    ImageIO.write(rgbBuffer, suffix, file);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            else {
                System.out.println("Invalid image file type: " + suffix);
            }
        }


        /**
         * This method cannot be called directly.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            FileDialog fileDialog = new FileDialog(frame, "Use a .png or .jpg extension", FileDialog.SAVE);
            fileDialog.setVisible(true);
            String filename = fileDialog.getFile();
            if (filename != null) {
                save(fileDialog.getDirectory() + File.separator + fileDialog.getFile());
            }
        }


        /***************************************************************************
         *  Mouse interactions.
         ***************************************************************************/

        /**
         * Returns true if the mouse is being pressed.
         *
         * @return {@code true} if the mouse is being pressed; {@code false} otherwise
         */
        public static boolean isMousePressed() {
            synchronized (mouseLock) {
                return isMousePressed;
            }
        }

        /**
         * Returns the <em>x</em>-coordinate of the mouse.
         *
         * @return the <em>x</em>-coordinate of the mouse
         */
        public static double mouseX() {
            synchronized (mouseLock) {
                return mouseX;
            }
        }

        /**
         * Returns the <em>y</em>-coordinate of the mouse.
         *
         * @return <em>y</em>-coordinate of the mouse
         */
        public static double mouseY() {
            synchronized (mouseLock) {
                return mouseY;
            }
        }


        /**
         * This method cannot be called directly.
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            // this body is intentionally left empty
        }

        /**
         * This method cannot be called directly.
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            // this body is intentionally left empty
        }

        /**
         * This method cannot be called directly.
         */
        @Override
        public void mouseExited(MouseEvent e) {
            // this body is intentionally left empty
        }

        /**
         * This method cannot be called directly.
         */
        @Override
        public void mousePressed(MouseEvent e) {
            synchronized (mouseLock) {
                isMousePressed = true;
            }
        }

        /**
         * This method cannot be called directly.
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            synchronized (mouseLock) {
                isMousePressed = false;
            }
        }

        /**
         * This method cannot be called directly.
         */
        @Override
        public void mouseDragged(MouseEvent e)  {
            synchronized (mouseLock) {
            }
        }

        /**
         * This method cannot be called directly.
         */
        @Override
        public void mouseMoved(MouseEvent e) {
            synchronized (mouseLock) {
            }
        }


        /***************************************************************************
         *  Keyboard interactions.
         ***************************************************************************/

        /**
         * Returns true if the user has typed a key (that has not yet been processed).
         *
         * @return {@code true} if the user has typed a key (that has not yet been processed
         *         by {@link #nextKeyTyped()}; {@code false} otherwise
         */
        public static boolean hasNextKeyTyped() {
            synchronized (keyLock) {
                return !keysTyped.isEmpty();
            }
        }

        /**
         * Returns the next key that was typed by the user (that your program has not already processed).
         * This method should be preceded by a call to {@link #hasNextKeyTyped()} to ensure
         * that there is a next key to process.
         * This method returns a Unicode character corresponding to the key
         * typed (such as {@code 'a'} or {@code 'A'}).
         * It cannot identify action keys (such as F1 and arrow keys)
         * or modifier keys (such as control).
         *
         * @return the next key typed by the user (that your program has not already processed).
         * @throws NoSuchElementException if there is no remaining key
         */
        public static char nextKeyTyped() {
            synchronized (keyLock) {
                if (keysTyped.isEmpty()) {
                    throw new NoSuchElementException("your program has already processed all keystrokes");
                }
                return keysTyped.remove(keysTyped.size() - 1);
                // return keysTyped.removeLast();
            }
        }

        /**
         * Returns true if the given key is being pressed.
         * <p>
         * This method takes the keycode (corresponding to a physical key)
         *  as an argument. It can handle action keys
         * (such as F1 and arrow keys) and modifier keys (such as shift and control).
         * See {@link KeyEvent} for a description of key codes.
         *
         * @param  keycode the key to check if it is being pressed
         * @return {@code true} if {@code keycode} is currently being pressed;
         *         {@code false} otherwise
         */
        public static boolean isKeyPressed(int keycode) {
            synchronized (keyLock) {
                return keysDown.contains(keycode);
            }
        }


        /**
         * This method cannot be called directly.
         */
        @Override
        public void keyTyped(KeyEvent e) {
            synchronized (keyLock) {
                keysTyped.addFirst(e.getKeyChar());
            }
        }

        /**
         * This method cannot be called directly.
         */
        @Override
        public void keyPressed(KeyEvent e) {
            synchronized (keyLock) {
                keysDown.add(e.getKeyCode());
            }
        }

        /**
         * This method cannot be called directly.
         */
        @Override
        public void keyReleased(KeyEvent e) {
            synchronized (keyLock) {
                keysDown.remove(e.getKeyCode());
            }
        }

    }

}
