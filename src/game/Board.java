package game;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    //Adds
    private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private int DOT_SIZE = 10;
    private int ALL_DOTS = 900;
    private final int RAND_POS = 29;
    private final int DELAY = 140;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private int apple_x;
    private int apple_y;
    private int superApple_x;
    private int superApple_y;
    private int points= 290;
    private int obstacle_x;
    private int obstacle_y;

    public  boolean leftDirection = false;
    public  boolean rightDirection = true;
    public boolean upDirection = false;
    public boolean downDirection = false;
    public boolean inGame = true;
    public boolean hasWon = false;

    private Timer timer;
    private Image apple;
    private Image superApple;
    private Image head_right;
    private Image head_left;
    private Image head_up;
    private Image head_down;
    private Image tail_right;
    private Image tail_left;
    private Image tail_up;
    private Image tail_down;
    private Image obstacle;

    public Board() {

        initBoard();
    }

    private void initBoard() {
        //adds mouse controls to game
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int button = e.getButton();
                if((button== MouseEvent.BUTTON1) && rightDirection && !downDirection && !upDirection && !leftDirection) {
                    leftDirection = false;
                    upDirection = true;
                    downDirection = false;
                    rightDirection=false;
                    System.out.println("Up from Right");
                }
                else if((button== MouseEvent.BUTTON3) && (!rightDirection) && upDirection && !downDirection && !leftDirection) {
                    rightDirection= true;
                    leftDirection = false;
                    upDirection = false;
                    downDirection = false;
                    System.out.println("Right");
                }
                else if ((button == MouseEvent.BUTTON3)&& rightDirection && !downDirection && !upDirection && !leftDirection){
                    leftDirection =false;
                    rightDirection = false;
                    upDirection = false;
                    downDirection = true;
                    System.out.println("Down");
                }
                else if ((button == MouseEvent.BUTTON3) && downDirection &&!leftDirection && !rightDirection && !upDirection){
                    leftDirection =true;
                    rightDirection = false;
                    upDirection = false;
                    downDirection = false;
                    System.out.println("Left from Down");
                }
                else if ((button == MouseEvent.BUTTON3) && leftDirection &&!downDirection &&!rightDirection &&!upDirection){
                    leftDirection =false;
                    rightDirection = false;
                    upDirection = true;
                    downDirection = false;
                    System.out.println("Down");
                }
                else if ((button == MouseEvent.BUTTON1) && downDirection && !rightDirection && !upDirection && !leftDirection){
                    leftDirection = false;
                    rightDirection = true;
                    upDirection = false;
                    downDirection = false;
                    System.out.println("Right from Down");
                }
                else if ((button == MouseEvent.BUTTON1)&& upDirection  &&!downDirection && !rightDirection  && !leftDirection){
                    leftDirection = true;
                    rightDirection = false;
                    upDirection = false;
                    downDirection = false;
                    System.out.println("Left from Up");
                }
                else if ((button == MouseEvent.BUTTON1)&& leftDirection &&!downDirection && !rightDirection  &&!upDirection)
                {
                    leftDirection = false;
                    rightDirection = false;
                    upDirection = false;
                    downDirection = true;
                    System.out.println("Up from Left");
                }
            }
        });
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }

    //Loads head,tail, obstacle, apples
    private void loadImages() {
        ImageIcon rTail = new ImageIcon("src/resources/tail_right.png");
        tail_right = rTail.getImage();
        ImageIcon lTail = new ImageIcon("src/resources/tail_left.png");
        tail_left = lTail.getImage();
        ImageIcon uTail = new ImageIcon("src/resources/tail_up.png");
        tail_up = uTail.getImage();
        ImageIcon dTail = new ImageIcon("src/resources/tail_down.png");
        tail_down = dTail.getImage();
       

        ImageIcon sApple = new ImageIcon("src/resources/apple.png");
        apple = sApple.getImage();
        ImageIcon rHead = new ImageIcon("src/resources/head_right.png");
        head_right = rHead.getImage();

        ImageIcon lHead = new ImageIcon("src/resources/head_left.png");
        head_left = lHead.getImage();

        ImageIcon uHead = new ImageIcon("src/resources/head_up.png");
        head_up = uHead.getImage();

        ImageIcon dHead = new ImageIcon("src/resources/head_down.png");
        head_down = dHead.getImage();

        ImageIcon suApple = new ImageIcon("src/resources/Golden_Apple3.png");
        superApple= suApple.getImage();

        ImageIcon sTNT = new ImageIcon("src/resources/tnt2.png");
        obstacle= sTNT.getImage();

    }


    private void initGame() {
        //sets tail length at start of game (1= default)
        dots = 1;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }

        spawnObstacle();
        spawnApple();
        spawnSuperApple();


        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString(String.valueOf(points), B_HEIGHT-150,B_WIDTH-150);

        doDrawing(g);
    }


    private void doDrawing(Graphics g) {

        //game loop
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this);
            g.drawImage(superApple, superApple_x, superApple_y, this);
            g.drawImage(obstacle,obstacle_x,obstacle_y, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0 && rightDirection) {
                    g.drawImage(head_right, x[z], y[z], this);
                }
                else if (z == 0 && leftDirection){
                    g.drawImage(head_left, x[z], y[z], this);
                }
                else if (z == 0 && upDirection){
                    g.drawImage(head_up, x[z], y[z], this);
                }
                else if (z == 0 && downDirection){
                    g.drawImage(head_down, x[z], y[z], this);
                }
                else {
                    if (rightDirection)
                    g.drawImage(tail_right, x[z], y[z], this);
                    else if (leftDirection){
                        g.drawImage(tail_left, x[z], y[z], this);
                    }
                    else if (upDirection){
                        g.drawImage(tail_up, x[z], y[z], this);
                    }
                    else if (downDirection){
                        g.drawImage(tail_down, x[z], y[z], this);
                    }
                }
            }
            if (points >= 300){

                youWon(g);
            }
        }
        else{
            gameOver(g);
        }
    }
    //puts Game over text in middle of screen
    private void gameOver(Graphics g) {
        String msg = "Game over";
        Font small = new Font("Passion One", Font.BOLD, 28);
        FontMetrics metric = getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);

        g.drawString(msg, (B_WIDTH - metric.stringWidth(msg)) / 2, B_HEIGHT / 2);
        System.out.println("Game Over test");
    }
    //puts You won text in middle of screen
    private void youWon(Graphics g) {
        hasWon = true;
        setBackground(Color.YELLOW);
        g.setColor(Color.BLACK);
        String msg = "You won";
        Font small = new Font("Passion One", Font.BOLD, 28);
        FontMetrics metric = getFontMetrics(small);
        g.setFont(small);

        if (hasWon){
            g.drawString(msg, (B_WIDTH - metric.stringWidth(msg)) / 2, B_HEIGHT / 2);
        }

    }
    //Checks if an apple has been eaten
    private void checkApple() {

        if ((x[0] == apple_x) && (y[0] == apple_y)) {

            dots++;
            spawnApple();
            points +=10;
        }
    }
    //Spawns obstacle at random position
    private void spawnObstacle(){
        int r = (int) (Math.random() * RAND_POS);
        obstacle_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        obstacle_y = ((r * DOT_SIZE));
    }
    //Spawns super Apple
    private void spawnSuperApple() {
        int r = (int) (Math.random() * RAND_POS);
        superApple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        superApple_y = ((r * DOT_SIZE));
    }
    //Checks if Super Apple has been eaten
    private void checkSuperApple(){
        if ((x[0] == superApple_x) && (y[0] == superApple_y)) {
            dots++;
            spawnSuperApple();
            points +=15;
        }
    }
    //Move method
    private void move() {

        //Moves tail(follows head)
        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }
    //checks if snake has eaten itself
    private void checkCollision() {


        if ((x[0] == obstacle_x) && (y[0] == obstacle_y)) {
            inGame = false;
        }

        for (int z = dots; z > 0; z--) {
            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] <= 0 && upDirection) {
            y[0] += B_HEIGHT;
        }

        if (y[0] >= 300 && downDirection) {
            y[0] -=B_HEIGHT;
        }

        if (x[0] >= 300 && rightDirection && !leftDirection) {

            x[0] -=B_WIDTH;
        }
        else if (x[0] <= 0 && leftDirection && !rightDirection)
        {
            x[0] += B_WIDTH;
        }

    }
    //Spawns apple
    private void spawnApple() {

        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {
            checkApple();
            checkSuperApple();
            checkCollision();
            move();
        }

        repaint();
    }
    private void Reset(){
        initGame();
     //reset
    }

}
