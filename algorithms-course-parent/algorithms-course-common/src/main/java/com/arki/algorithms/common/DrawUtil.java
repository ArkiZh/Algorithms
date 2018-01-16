package com.arki.algorithms.common;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawUtil {


    public static void histogramDataListAdd(List<HistogramData> histogramDatalist, Number[] dataArray, Integer[] colorIndex, Color color) {
        HistogramData histogramData = new HistogramData();
        histogramData.setDataArray(ArrayUtil.copyArray(dataArray));
        Map<Integer, Color> colorMap = histogramData.getColorMap();
        if (colorIndex != null) {
            for (int i = 0; i < colorIndex.length; i++) {
                colorMap.put(colorIndex[i], color);
            }
        }
        histogramDatalist.add(histogramData);
    }

    public static Pen drawHistogramList(List<HistogramData> histogramDatalist) {

        int M = histogramDatalist.size();
        HistogramData histogramData = histogramDatalist.get(0);
        Number[] dataArray = histogramData.getDataArray();
        int N = dataArray.length;
        double max = 0;
        for (int i = 0; i < N; i++) {
            double temp = dataArray[i].doubleValue();
            max = temp > max ? temp : max;
        }

        double leftMargin = (N + 2) * 0.1;
        double bottomMargin = max * 0.1;
        double topPadding = max * 0.1;
        double xmax = leftMargin * 2 + N + 2;
        double ymax = bottomMargin * 2 + max + topPadding;
        double ymaxFull = ymax * M;

        Pen pen = new Pen();
        pen.enableDisplayDefer();
        pen.setCanvas(500, 309 * M);
        pen.setXrange(0, xmax);
        pen.setYrange(0, ymaxFull);

        for (int i = 0; i < M; i++) {
            HistogramData data = histogramDatalist.get(i);
            Number[] array = data.getDataArray();
            Map<Integer, Color> map = data.getColorMap();
            double tempY = ymaxFull - ymax * i - ymax;
            pen.setUserOrigin(0, tempY);
            pen.setPenColor(Color.LIGHT_GRAY);
            pen.fillRectangle(leftMargin, bottomMargin, N + 2, max + topPadding);
            pen.line(0, 0, xmax, 0);

            pen.setUserOrigin(leftMargin + 1, tempY + bottomMargin);
            Color dataColor = Color.GRAY;
            pen.setPenColor(dataColor);

            double interval = 1;
            double width = interval/2;
            for (int j = 0; j < N; j++) {
                if(map.containsKey(j)) pen.setPenColor(map.get(j));
                else pen.setPenColor(dataColor);
                pen.fillRectangle(interval * j, 0, width, array[j].doubleValue());
            }
        }

        pen.show();

        return pen;
    }

    /**
     * Draw a single histogram.
     * @param list
     * @return
     */
    public static Pen drawHistogram(List<? extends Number> list){

        int N = list.size();
        double max = 0;
        for (int i = 0; i < N; i++) {
            double temp = list.get(i).doubleValue();
            max = temp > max ? temp : max;
        }

        Pen pen = new Pen();
        double leftMargin = (N + 2) * 0.1;
        double bottomMargin = max * 0.1;
        double topPadding = max * 0.1;
        double xmax = leftMargin * 2 + N + 2;
        double ymax = bottomMargin * 2 + max + topPadding;
        pen.setXrange(0, xmax);
        pen.setYrange(0, ymax);
        pen.setPenColor(Color.LIGHT_GRAY);

        pen.fillRectangle(leftMargin, bottomMargin, N + 2, max + topPadding);
        pen.setUserOrigin(leftMargin + 1, bottomMargin);
        pen.resetPenColor();

        double interval = 1;
        double width = interval/2;
        for (int i = 0; i < N; i++) {
            pen.fillRectangle(interval * i, 0, width, list.get(i).doubleValue());
        }
        return pen;
    }

    public static Pen createPen(){
        return new Pen();
    }

    public static void main(String[] args) {


        List<HistogramData> list = new ArrayList<>();
        HistogramData histogramData = new HistogramData();
        Map<Integer, Color> colorMap = histogramData.getColorMap();
        colorMap.put(1, Color.RED);
        histogramData.setDataArray(new Integer[]{1, 2, 3, 4, 5, 6});
        list.add(histogramData);
        list.add(histogramData);
        drawHistogramList(list);


//        ArrayList<Integer> list = new ArrayList<>();
//        for (int i = 0; i < 16; i++) {
//            list.add(i+10);
//        }
//        Pen pen = drawHistogram(list);

//        Pen pen = new Pen();
//        pen.enableDisplayDefer();
//        pen.setCanvas(500, 500);
//        pen.setXrange(0, 1000);
//        pen.setYrange(0, 1000);
//        pen.setPenRadius(1);
//        for (int i = 0; i < 180; i++) {
//            pen.clear(Color.LIGHT_GRAY);
//            pen.line(0, 0, 500, 500).line(500, 500, 1000, 0).circle(500, 750, 250).pixel(500,750);
//            pen.show();
//            pen.pause(250);
//            pen.clear(Color.blue);
//            pen.show();
//            pen.pause(250);
//        }
    }


    public static final class Pen implements ActionListener {


        /***************************************************************************
         *  Components.
         ***************************************************************************/
        private JFrame frame;
        private BufferedImage bufferedImageHidden;
        private Graphics2D graphicsHidden;
        private BufferedImage bufferedImageVisible;
        private Graphics2D graphicsVisible;


        /***************************************************************************
         *  Parameters.
         ***************************************************************************/
        // Show draw immediately or wait until next show.
        private boolean displayDefer = false;
        // Canvas size in pixel.
        private final int DEFAULT_WIDTH = 500;
        private final int DEFAULT_HEIGHT = 309;
        private int width = DEFAULT_WIDTH;
        private int height = DEFAULT_HEIGHT;
        // The region for drawing. It will be mapped to screen coordinate system.
        private final double DEFAULT_XMIN = 0;
        private final double DEFAULT_XMAX = 1;
        private final double DEFAULT_YMIN = 0;
        private final double DEFAULT_YMAX = 1;
        private double xmin = DEFAULT_XMIN;
        private double xmax = DEFAULT_XMAX;
        private double ymin = DEFAULT_YMIN;
        private double ymax = DEFAULT_YMAX;
        // Style.
        private final Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;
        private final Color DEFAULT_PEN_COLOR = Color.BLACK;
        private final double DEFAULT_PEN_RADIUS = 1;
        private Color backgroundColor = DEFAULT_BACKGROUND_COLOR;
        private Color penColor = DEFAULT_PEN_COLOR;
        private double penRadius = DEFAULT_PEN_RADIUS;
        private Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN, 16);
        private Font font = DEFAULT_FONT;
        // Coordinate system. WCS, world coordinate system. UCS, user coordinate system.
        private final double DEFAULT_X_WORLD_ORIGIN = 0;
        private final double DEFAULT_Y_WORLD_ORIGIN = 0;
        private final double DEFAULT_X_USER_ORIGIN = 0;
        private final double DEFAULT_Y_USER_ORIGIN = 0;
        private double xWorldOrigin = DEFAULT_X_WORLD_ORIGIN;
        private double yWorldOrigin = DEFAULT_Y_WORLD_ORIGIN;
        private double xUserOrigin = DEFAULT_X_USER_ORIGIN;
        private double yUserOrigin = DEFAULT_Y_USER_ORIGIN;


        /***************************************************************************
         *  Init.
         ***************************************************************************/
        public Pen(){
            init();
        }
        private void init(){
            if (frame != null) frame.setVisible(false);
            frame = new JFrame();
            bufferedImageVisible = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            graphicsVisible = bufferedImageVisible.createGraphics();
            bufferedImageHidden = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            graphicsHidden = bufferedImageHidden.createGraphics();
            clear();

            // add antialiasing
            RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            graphicsHidden.addRenderingHints(hints);

            // frame stuff
            ImageIcon icon = new ImageIcon(bufferedImageVisible);
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

        public void clear(Color color) {
            backgroundColor = color;
            graphicsHidden.setColor(color);
            graphicsHidden.fillRect(0,0,width,height);
            setPenColor(DEFAULT_PEN_COLOR);
            draw();
        }
        public void clear() {
            clear(DEFAULT_BACKGROUND_COLOR);
        }


        public void setPenColor(Color color){
            penColor = color;
            graphicsHidden.setColor(color);
        }
        public void resetPenColor(){
           setPenColor(DEFAULT_PEN_COLOR);
        }

        /**
         * Set the pen line width in pixel unit.
         * @param radius
         */
        public void setPenRadius(double radius){
            penRadius = radius;
            BasicStroke basicStroke = new BasicStroke((float)radius, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            graphicsHidden.setStroke(basicStroke);
        }
        public void resetPenRadius(){
            setPenRadius(DEFAULT_PEN_RADIUS);
        }
        private void draw(){
            if(!displayDefer) show();
        }
        private void show(){
            graphicsVisible.drawImage(bufferedImageHidden, 0, 0, null);
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

        public void pause(int milliseconds){
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
        public File save(String fileName){
            if (fileName == null) throw new IllegalArgumentException();
            File file = new File(fileName);
            String suffix = fileName.substring(fileName.lastIndexOf('.') + 1);

            try {
                if ("png".equalsIgnoreCase(suffix)) {
                    // png files
                    ImageIO.write(bufferedImageHidden, suffix, file);
                } else if ("jpg".equalsIgnoreCase(suffix)) {
                    // need to change from ARGB to RGB for JPEG
                    // reference: http://archives.java.sun.com/cgi-bin/wa?A2=ind0404&L=java2d-interest&D=0&P=2727
                    WritableRaster raster = bufferedImageHidden.getRaster();
                    WritableRaster newRaster;
                    newRaster = raster.createWritableChild(0, 0, width, height, 0, 0, new int[]{0, 1, 2});
                    DirectColorModel cm = (DirectColorModel) bufferedImageHidden.getColorModel();
                    DirectColorModel newCM = new DirectColorModel(cm.getPixelSize(),
                            cm.getRedMask(),
                            cm.getGreenMask(),
                            cm.getBlueMask());
                    BufferedImage rgbBuffer = new BufferedImage(newCM, newRaster, false, null);
                    ImageIO.write(rgbBuffer, suffix, file);
                } else {
                    file = null;
                    System.out.println("Invalid image file type: " + suffix);
                }
            } catch (IOException e) {
                file = null;
                e.printStackTrace();
            }
            return file;
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
            graphicsHidden.fillRect((int) Math.round(transferUserToScreenX(x)), (int) Math.round(transferUserToScreenY(y)), 1, 1);
            draw();
            return this;
        }

        public Pen point(double x, double y) {
            if(penRadius <= 1) pixel(x, y);
            else{
                graphicsHidden.fill(new Ellipse2D.Double(transferUserToScreenX(x) - penRadius / 2, transferUserToScreenY(y) - penRadius / 2, penRadius, penRadius));
            }
            draw();
            return this;
        }

        public Pen line(double x1, double y1, double x2, double y2) {
            graphicsHidden.draw(new Line2D.Double(transferUserToScreenX(x1), transferUserToScreenY(y1), transferUserToScreenX(x2), transferUserToScreenY(y2)));
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
            graphicsHidden.draw(new Rectangle2D.Double(transferUserToScreenX(x), transferUserToScreenY(y) - h, w, h));
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
            graphicsHidden.fill(new Rectangle2D.Double(transferUserToScreenX(x), transferUserToScreenY(y) - h, w, h));
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

        /**
         * Draws a polygon with the vertices
         * (<em>x</em><sub>0</sub>, <em>y</em><sub>0</sub>),
         * (<em>x</em><sub>1</sub>, <em>y</em><sub>1</sub>), ...,
         * (<em>x</em><sub><em>n</em>–1</sub>, <em>y</em><sub><em>n</em>–1</sub>).
         *
         * @param  x an array of all the <em>x</em>-coordinates of the polygon
         * @param  y an array of all the <em>y</em>-coordinates of the polygon
         * @throws IllegalArgumentException unless {@code x[]} and {@code y[]}
         *         are of the same length
         */
        public  Pen polygon(double[] x, double[] y) {
            if (x == null) throw new IllegalArgumentException("x-coordinate array is null");
            if (y == null) throw new IllegalArgumentException("y-coordinate array is null");
            int n1 = x.length;
            int n2 = y.length;
            if (n1 != n2) throw new IllegalArgumentException("arrays must be of the same length");
            int n = n1;
            if (n == 0) return this;

            GeneralPath path = new GeneralPath();
            path.moveTo((float) transferUserToScreenX(x[0]), (float) transferUserToScreenY(y[0]));
            for (int i = 0; i < n; i++)
                path.lineTo((float) transferUserToScreenX(x[i]), (float) transferUserToScreenX(y[i]));
            path.closePath();
            graphicsHidden.draw(path);
            draw();
            return this;
        }

        /**
         * Draws a polygon with the vertices
         * (<em>x</em><sub>0</sub>, <em>y</em><sub>0</sub>),
         * (<em>x</em><sub>1</sub>, <em>y</em><sub>1</sub>), ...,
         * (<em>x</em><sub><em>n</em>–1</sub>, <em>y</em><sub><em>n</em>–1</sub>).
         *
         * @param  x an array of all the <em>x</em>-coordinates of the polygon
         * @param  y an array of all the <em>y</em>-coordinates of the polygon
         * @throws IllegalArgumentException unless {@code x[]} and {@code y[]}
         *         are of the same length
         */
        public void fillPolygon(double[] x, double[] y) {
            if (x == null) throw new IllegalArgumentException("x-coordinate array is null");
            if (y == null) throw new IllegalArgumentException("y-coordinate array is null");
            int n1 = x.length;
            int n2 = y.length;
            if (n1 != n2) throw new IllegalArgumentException("arrays must be of the same length");
            int n = n1;
            if (n == 0) return;

            GeneralPath path = new GeneralPath();
            path.moveTo((float) transferUserToScreenX(x[0]), (float) transferUserToScreenY(y[0]));
            for (int i = 0; i < n; i++)
                path.lineTo((float) transferUserToScreenX(x[i]), (float) transferUserToScreenX(y[i]));
            path.closePath();
            graphicsHidden.fill(path);
            draw();
        }

        public Pen ellipse(double x, double y, double xSemiAxis, double ySemiAxis) {
            double w = factorX(xSemiAxis);
            double h = factorY(ySemiAxis);
            graphicsHidden.draw(new Ellipse2D.Double(transferUserToScreenX(x) - w, transferUserToScreenY(y) - h, w * 2, h * 2));
            draw();
            return this;
        }

        public Pen fillEllipse(double x, double y, double xSemiAxis, double ySemiAxis) {
            double w = factorX(xSemiAxis);
            double h = factorY(ySemiAxis);
            graphicsHidden.fill(new Ellipse2D.Double(transferUserToScreenX(x) - w, transferUserToScreenY(y) - h, w * 2, h * 2));
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
         *   Images drawing method start
         ***************************************************************************/

        // get an image from the given filename
        private Image getImage(String filename) {
            if (filename == null) throw new IllegalArgumentException();

            // to read from file
            ImageIcon icon = new ImageIcon(filename);

            // try to read from URL
            if ((icon == null) || (icon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
                try {
                    URL url = new URL(filename);
                    icon = new ImageIcon(url);
                }
                catch (MalformedURLException e) {
                /* not a url */
                }
            }

            // in case file is inside a .jar (classpath relative to StdDraw)
            if ((icon == null) || (icon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
                URL url = StdDraw.class.getResource(filename);
                if (url != null)
                    icon = new ImageIcon(url);
            }

            // in case file is inside a .jar (classpath relative to root of jar)
            if ((icon == null) || (icon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
                URL url = StdDraw.class.getResource("/" + filename);
                if (url == null) throw new IllegalArgumentException("image " + filename + " not found");
                icon = new ImageIcon(url);
            }

            return icon.getImage();
        }

        /***************************************************************************
         * [Summer 2016] Should we update to use ImageIO instead of ImageIcon()?
         *               Seems to have some issues loading images on some systems
         *               and slows things down on other systems.
         *               especially if you don't call ImageIO.setUseCache(false)
         *               One advantage is that it returns a BufferedImage.
         ***************************************************************************/
/*
    private static BufferedImage getImage(String filename) {
        if (filename == null) throw new IllegalArgumentException();

        // from a file or URL
        try {
            URL url = new URL(filename);
            BufferedImage image = ImageIO.read(url);
            return image;
        }
        catch (IOException e) {
            // ignore
        }

        // in case file is inside a .jar (classpath relative to StdDraw)
        try {
            URL url = StdDraw.class.getResource(filename);
            BufferedImage image = ImageIO.read(url);
            return image;
        }
        catch (IOException e) {
            // ignore
        }

        // in case file is inside a .jar (classpath relative to root of jar)
        try {
            URL url = StdDraw.class.getResource("/" + filename);
            BufferedImage image = ImageIO.read(url);
            return image;
        }
        catch (IOException e) {
            // ignore
        }
        throw new IllegalArgumentException("image " + filename + " not found");
    }
*/
        /**
         * Draws the specified image centered at (<em>x</em>, <em>y</em>).
         * The supported image formats are JPEG, PNG, and GIF.
         * As an optimization, the picture is cached, so there is no performance
         * penalty for redrawing the same image multiple times (e.g., in an animation).
         * However, if you change the picture file after drawing it, subsequent
         * calls will draw the original picture.
         *
         * @param  x the center <em>x</em>-coordinate of the image
         * @param  y the center <em>y</em>-coordinate of the image
         * @param  filename the name of the image/picture, e.g., "ball.gif"
         * @throws IllegalArgumentException if the image filename is invalid
         */
        public Pen picture(double x, double y, String filename) {
            // BufferedImage image = getImage(filename);
            Image image = getImage(filename);
            double xs = transferUserToScreenX(x);
            double ys = transferUserToScreenY(y);
            // int ws = image.getWidth();    // can call only if image is a BufferedImage
            // int hs = image.getHeight();
            int ws = image.getWidth(null);
            int hs = image.getHeight(null);
            if (ws < 0 || hs < 0) throw new IllegalArgumentException("image " + filename + " is corrupt");

            graphicsHidden.drawImage(image, (int) Math.round(xs - ws/2.0), (int) Math.round(ys - hs/2.0), null);
            draw();
            return this;
        }

        /**
         * Draws the specified image centered at (<em>x</em>, <em>y</em>),
         * rotated given number of degrees.
         * The supported image formats are JPEG, PNG, and GIF.
         *
         * @param  x the center <em>x</em>-coordinate of the image
         * @param  y the center <em>y</em>-coordinate of the image
         * @param  filename the name of the image/picture, e.g., "ball.gif"
         * @param  degrees is the number of degrees to rotate counterclockwise
         * @throws IllegalArgumentException if the image filename is invalid
         */
        public Pen picture(double x, double y, String filename, double degrees) {
            // BufferedImage image = getImage(filename);
            Image image = getImage(filename);
            double xs = transferUserToScreenX(x);
            double ys = transferUserToScreenY(y);
            // int ws = image.getWidth();    // can call only if image is a BufferedImage
            // int hs = image.getHeight();
            int ws = image.getWidth(null);
            int hs = image.getHeight(null);
            if (ws < 0 || hs < 0) throw new IllegalArgumentException("image " + filename + " is corrupt");

            graphicsHidden.rotate(Math.toRadians(-degrees), xs, ys);
            graphicsHidden.drawImage(image, (int) Math.round(xs - ws/2.0), (int) Math.round(ys - hs/2.0), null);
            graphicsHidden.rotate(Math.toRadians(+degrees), xs, ys);

            draw();
            return this;
        }

        /**
         * Draws the specified image centered at (<em>x</em>, <em>y</em>),
         * rescaled to the specified bounding box.
         * The supported image formats are JPEG, PNG, and GIF.
         *
         * @param  x the center <em>x</em>-coordinate of the image
         * @param  y the center <em>y</em>-coordinate of the image
         * @param  filename the name of the image/picture, e.g., "ball.gif"
         * @param  scaledWidth the width of the scaled image (in screen coordinates)
         * @param  scaledHeight the height of the scaled image (in screen coordinates)
         * @throws IllegalArgumentException if either {@code scaledWidth}
         *         or {@code scaledHeight} is negative
         * @throws IllegalArgumentException if the image filename is invalid
         */
        public Pen picture(double x, double y, String filename, double scaledWidth, double scaledHeight) {
            Image image = getImage(filename);
            if (scaledWidth  < 0) throw new IllegalArgumentException("width  is negative: " + scaledWidth);
            if (scaledHeight < 0) throw new IllegalArgumentException("height is negative: " + scaledHeight);
            double xs = transferUserToScreenX(x);
            double ys = transferUserToScreenY(y);
            double ws = factorX(scaledWidth);
            double hs = factorY(scaledHeight);
            if (ws < 0 || hs < 0) throw new IllegalArgumentException("image " + filename + " is corrupt");
            if (ws <= 1 && hs <= 1) pixel(x, y);
            else {
                graphicsHidden.drawImage(image, (int) Math.round(xs - ws/2.0),
                        (int) Math.round(ys - hs/2.0),
                        (int) Math.round(ws),
                        (int) Math.round(hs), null);
            }
            draw();
            return this;
        }


        /**
         * Draws the specified image centered at (<em>x</em>, <em>y</em>), rotated
         * given number of degrees, and rescaled to the specified bounding box.
         * The supported image formats are JPEG, PNG, and GIF.
         *
         * @param  x the center <em>x</em>-coordinate of the image
         * @param  y the center <em>y</em>-coordinate of the image
         * @param  filename the name of the image/picture, e.g., "ball.gif"
         * @param  scaledWidth the width of the scaled image (in screen coordinates)
         * @param  scaledHeight the height of the scaled image (in screen coordinates)
         * @param  degrees is the number of degrees to rotate counterclockwise
         * @throws IllegalArgumentException if either {@code scaledWidth}
         *         or {@code scaledHeight} is negative
         * @throws IllegalArgumentException if the image filename is invalid
         */
        public Pen picture(double x, double y, String filename, double scaledWidth, double scaledHeight, double degrees) {
            if (scaledWidth < 0) throw new IllegalArgumentException("width is negative: " + scaledWidth);
            if (scaledHeight < 0) throw new IllegalArgumentException("height is negative: " + scaledHeight);
            Image image = getImage(filename);
            double xs = transferUserToScreenX(x);
            double ys = transferUserToScreenY(y);
            double ws = factorX(scaledWidth);
            double hs = factorY(scaledHeight);
            if (ws < 0 || hs < 0) throw new IllegalArgumentException("image " + filename + " is corrupt");
            if (ws <= 1 && hs <= 1) pixel(x, y);

            graphicsHidden.rotate(Math.toRadians(-degrees), xs, ys);
            graphicsHidden.drawImage(image, (int) Math.round(xs - ws/2.0),
                    (int) Math.round(ys - hs/2.0),
                    (int) Math.round(ws),
                    (int) Math.round(hs), null);
            graphicsHidden.rotate(Math.toRadians(+degrees), xs, ys);

            draw();
            return this;
        }


    }

    public static class HistogramData {

        private String title;
        private Number[] dataArray;
        private Map<Integer, Color> colorMap = new HashMap<>();

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Number[] getDataArray() {
            return dataArray;
        }

        public void setDataArray(Number[] dataArray) {
            this.dataArray = dataArray;
        }

        public Map<Integer, Color> getColorMap() {
            return colorMap;
        }

        public void setColorMap(Map<Integer, Color> colorMap) {
            this.colorMap = colorMap;
        }
    }
}
