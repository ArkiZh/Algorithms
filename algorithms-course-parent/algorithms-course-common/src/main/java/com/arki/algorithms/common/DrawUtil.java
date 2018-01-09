package com.arki.algorithms.common;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DrawUtil {

    public static void drawHistogram(List<? extends Number> list){

        int size = list.size();
        double max = 0;
        for (int i = 0; i < list.size(); i++) {
            double temp = list.get(i).doubleValue();
            max = temp > max ? temp : max;
        }

        Pen pen = new Pen();
        int canvasWidth = 500;
        int canvasHeight = 309;
        pen.setCanvas(canvasWidth,canvasHeight);
        double xmax = list.size() * 1.2;
        double ymax = max * 1.2;
        pen.setXrange(0, xmax);
        pen.setYrange(0, ymax);
        pen.setUserOrigin(0.1 * list.size(), 0.1 * max);
        pen.setPenColor(Color.red);
        pen.rectangle(-0.05 * list.size(), 0, list.size() * 1.05, max * 1.05);
        pen.setPenColor(Color.BLACK);

        double interval = 1;
        double width = interval/2;
        for (int i = 0; i < list.size(); i++) {
            pen.fillRectangle(interval * i, 0, width, list.get(i).doubleValue());
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i+10);
        }
        drawHistogram(list);

//        Pen pen = new Pen();
//        pen.setCanvas(500, 500);
//        pen.setXrange(0, 1000);
//        pen.setYrange(0, 1000);
//        pen.setPenRadius(1);
//
//        pen.line(0, 0, 500, 500).line(500, 500, 1000, 0).circle(500, 750, 250).pixel(500,750);
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
        // Show draw immediately or wait until next show.
        private boolean displayDefer = false;
        // Canvas size in pixel.
        private int width = 500;
        private int height = 309;
        // The region for drawing. It will be mapped to screen coordinate system.
        private double xmin = 0;
        private double xmax = 1;
        private double ymin = 0;
        private double ymax = 1;
        // Style.
        private Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;
        private Color DEFAULT_PEN_COLOR = Color.BLACK;

        private Color backgroundColor = DEFAULT_BACKGROUND_COLOR;
        private Color penColor = DEFAULT_PEN_COLOR;
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
            setPenColor(DEFAULT_PEN_COLOR);
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
        public void setCanvas(int canvasWidth, int canvasHeight){
            setCanvas(canvasWidth,canvasHeight,DEFAULT_BACKGROUND_COLOR);
        }
        public void setPenColor(Color color){
            penColor = color;
            graphics.setColor(color);
        }

        /**
         * Set the pen line width in pixel unit.
         * @param radius
         */
        public void setPenRadius(double radius){
            penRadius = radius;
            BasicStroke basicStroke = new BasicStroke((float)radius, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            graphics.setStroke(basicStroke);
        }
        private void draw(){
            if(!displayDefer) show();
        }
        private void show(){
            graphics.drawImage(bufferedImage, 0, 0, null);
            frame.repaint();
        }

        /**
         * Show draw until next show.
         */
        public void enableDisplayDefer(){
            displayDefer = true;
        }

        /**
         * Show draw immediately.
         */
        public void disableDisplayDefer(){
            displayDefer = false;
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
        private double transferWorldToScreenY(double y){ return height * (ymax - yWorldOrigin - y) / (ymax - ymin);}
        private double transferUserToScreenX(double x){ return width * (x + xUserOrigin - xmin) / (xmax - xmin);}
        private double transferUserToScreenY(double y){ return height * (ymax - yUserOrigin - y) / (ymax - ymin);}

        /**
         * Transfer width from user coordinate system to screen coordinate system in x-orientation.
         * @param w
         * @return
         */
        private double factorX(double w){ return width * w / Math.abs(xmax-xmin);}

        /**
         * Transfer height from user coordinate system to screen coordinate system in y-orientation.
         * @param h
         * @return
         */
        private double factorY(double h){ return height * h / Math.abs(ymax-ymin);}

        public void setXrange(double xmin, double xmax){
            this.xmin = xmin;
            this.xmax = xmax;
        };
        public void setYrange(double ymin, double ymax){
            this.ymin = ymin;
            this.ymax = ymax;
        };

        public void setUserOrigin(double x,double y){
            xUserOrigin = x;
            yUserOrigin = y;
        }


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

        public Pen pixel(double x, double y) {
            graphics.fillRect((int) Math.round(transferUserToScreenX(x)), (int) Math.round(transferUserToScreenY(y)), 1, 1);
            draw();
            return this;
        }

        public Pen point(double x, double y) {
            if(penRadius <= 1) pixel(x, y);
            else{
                graphics.fill(new Ellipse2D.Double(transferUserToScreenX(x) - penRadius / 2, transferUserToScreenY(y) - penRadius / 2, penRadius, penRadius));
            }
            draw();
            return this;
        }

        public Pen line(double x1, double y1, double x2, double y2) {
            graphics.draw(new Line2D.Double(transferUserToScreenX(x1), transferUserToScreenY(y1), transferUserToScreenX(x2), transferUserToScreenY(y2)));
            draw();
            return this;
        }

        /**
         * Draw a rectangle. Specify the left-bottom point, width and height.
         * @param x x-coordinate of the left-bottom point.
         * @param y y-coordinate of the left-bottom point.
         * @param width
         * @param height
         * @return
         */
        public Pen rectangle(double x, double y, double width, double height) {
            double w = factorX(width);
            double h = factorY(height);
            graphics.draw(new Rectangle2D.Double(transferUserToScreenX(x), transferUserToScreenY(y) - h, w, h));
            draw();
            return this;
        }

        /**
         * Draw a color-filled rectangle. Specify the left-bottom point, width and height.
         * @param x x-coordinate of the left-bottom point.
         * @param y y-coordinate of the left-bottom point.
         * @param width
         * @param height
         * @return
         */
        public Pen fillRectangle(double x, double y, double width, double height) {
            double w = factorX(width);
            double h = factorY(height);
            graphics.fill(new Rectangle2D.Double(transferUserToScreenX(x), transferUserToScreenY(y) - h, w, h));
            draw();
            return this;
        }

        /**
         * Draw a square. Specify the left-bottom point, width and height.
         * @param x x-coordinate of the left-bottom point.
         * @param y y-coordinate of the left-bottom point.
         * @param edgeLength
         * @return
         */
        public Pen square(double x, double y, double edgeLength) {
            return rectangle(x, y, edgeLength, edgeLength);
        }
        /**
         * Draw a color-filled square. Specify the left-bottom point, width and height.
         * @param x x-coordinate of the left-bottom point.
         * @param y y-coordinate of the left-bottom point.
         * @param edgeLength
         * @return
         */
        public Pen fillSquare(double x, double y, double edgeLength) {
            return fillRectangle(x, y, edgeLength, edgeLength);
        }

        public Pen ellipse(double x, double y, double xSemiAxis, double ySemiAxis) {
            double w = factorX(xSemiAxis);
            double h = factorY(ySemiAxis);
            graphics.draw(new Ellipse2D.Double(transferUserToScreenX(x) - w, transferUserToScreenY(y) - h, w * 2, h * 2));
            draw();
            return this;
        }

        public Pen fillEllipse(double x, double y, double xSemiAxis, double ySemiAxis) {
            double w = factorX(xSemiAxis);
            double h = factorY(ySemiAxis);
            graphics.fill(new Ellipse2D.Double(transferUserToScreenX(x) - w, transferUserToScreenY(y) - h, w * 2, h * 2));
            draw();
            return this;
        }

        public Pen circle(double x, double y, double radius) {
            return ellipse(x, y, radius, radius);
        }

        public Pen fillCircle(double x, double y, double radius) {
            return fillEllipse(x, y, radius, radius);
        }






        /***************************************************************************
         *   Geometric drawing method end
         ***************************************************************************/

    }

}
