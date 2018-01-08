package com.arki.algorithms.common;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
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
        pen.setCanvas(500, 500, Color.WHITE);
        pen.setXrange(0, 100);
        pen.setYrange(0, 100);
        pen.setPenRadius(10);
        pen.setPenColor(Color.BLACK);
        pen.point(0, 0);
        pen.setPenRadius(1);
        pen.fillCircle(50,50,250);
//        StdDraw.setCanvasSize(500, 500);
//        StdDraw.rectangle(0, 0, 0.5, 0.5);

    }


    private static final class Pen implements ActionListener {


        /***************************************************************************
         *  Components.
         ***************************************************************************/
        private JFrame frame;
        private BufferedImage bufferedImage;
        private Graphics2D graphics;


        /***************************************************************************
         *  Parameters.
         ***************************************************************************/
        // Canvas size in pixel.
        private int width = 500;
        private int height = 309;
        // The region for drawing. It will be mapped to screen coordinate system.
        private double xmin = 0;
        private double xmax = 1;
        private double ymin = 0;
        private double ymax = 1;
        // Style.
        private Color backgroundColor = Color.WHITE;
        private Color penColor = Color.BLACK;
        private double penRadius = 1;
        // Coordinate system. WCS, world coordinate system. UCS, user coordinate system.
        private final double xWorldOrigin = 0;
        private final double yWorldOrigin = 0;
        private double xUserOrigin = 0;
        private double yUserOrigin = 0;


        /***************************************************************************
         *  Init.
         ***************************************************************************/
        public Pen(){
            init();
        }
        private void init(){
            if (frame != null) frame.setVisible(false);
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

            frame.setContentPane(draw);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            // closes all windows
            frame.setTitle("Pen");
            frame.setJMenuBar(createMenuBar());
            frame.pack();
            frame.requestFocusInWindow();
            frame.setVisible(true);
        }

        /***************************************************************************
         *  Basic settings about drawing style.
         ***************************************************************************/

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
            penRadius = radius;
            BasicStroke basicStroke = new BasicStroke((float)radius, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            graphics.setStroke(basicStroke);
        }
        private void show(){
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


        /***************************************************************************
         *   Coordinate system transfer between user and screen.
         ***************************************************************************/
        private double transferWorldToScreenX(double x){ return width * (x + xWorldOrigin - xmin) / (xmax - xmin);}
        private double transferWorldToScreenY(double y){ return height * (ymax + yWorldOrigin - y) / (ymax - ymin);}
        private double transferUserToScreenX(double x){ return width * (x + xUserOrigin - xmin) / (xmax - xmin);}
        private double transferUserToScreenY(double y){ return height * (ymax + yUserOrigin - y) / (ymax - ymin);}
        private double scaleUserToScreenX(double x){return 0;}// TODO

        public void setXrange(int xmin, int xmax){
            this.xmin = xmin;
            this.xmax = xmax;
        };
        public void setYrange(int ymin, int ymax){
            this.ymin = ymin;
            this.ymax = ymax;
        };


        /***************************************************************************
         *  Save drawing to a file.
         ***************************************************************************/
        public void save(String fileName){
            if (fileName == null) throw new IllegalArgumentException();
            File file = new File(fileName);
            String suffix = fileName.substring(fileName.lastIndexOf('.') + 1);

            // png files
            if ("png".equalsIgnoreCase(suffix)) {
                try {
                    ImageIO.write(bufferedImage, suffix, file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // need to change from ARGB to RGB for JPEG
            // reference: http://archives.java.sun.com/cgi-bin/wa?A2=ind0404&L=java2d-interest&D=0&P=2727
            else if ("jpg".equalsIgnoreCase(suffix)) {
                WritableRaster raster = bufferedImage.getRaster();
                WritableRaster newRaster;
                newRaster = raster.createWritableChild(0, 0, width, height, 0, 0, new int[]{0, 1, 2});
                DirectColorModel cm = (DirectColorModel) bufferedImage.getColorModel();
                DirectColorModel newCM = new DirectColorModel(cm.getPixelSize(),
                        cm.getRedMask(),
                        cm.getGreenMask(),
                        cm.getBlueMask());
                BufferedImage rgbBuffer = new BufferedImage(newCM, newRaster, false, null);
                try {
                    ImageIO.write(rgbBuffer, suffix, file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
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
         *   Geometric drawing method start
         ***************************************************************************/

        private void pixel(double x, double y) {
            graphics.fillRect((int) Math.round(transferUserToScreenX(x)), (int) Math.round(transferUserToScreenY(y)), 1, 1);
        }

        public void point(double x, double y) {
            if(penRadius <= 1) pixel(x, y);
            else{
                graphics.fill(new Ellipse2D.Double(transferUserToScreenX(x) - penRadius / 2, transferWorldToScreenY(y) - penRadius / 2, penRadius, penRadius));
            }
            show();
        }

        public void circle(double x, double y, double radius) {
            graphics.draw(new Ellipse2D.Double(transferUserToScreenX(x) - radius, transferUserToScreenY(y) - radius, radius * 2, radius * 2));
            show();
        }

        public void fillCircle(double x, double y, double radius) {
            graphics.fill(new Ellipse2D.Double(transferUserToScreenX(x) - radius, transferUserToScreenY(y) - radius, radius * 2, radius * 2));
            show();
        }





        /***************************************************************************
         *   Geometric drawing method end
         ***************************************************************************/

    }

}
