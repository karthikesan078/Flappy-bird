import javax.swing.*;
public class App {
    public static void main(String[] args) throws Exception {
        int boardwidth=360;//screen width
        int boardheight=640;//screen height

        JFrame frame=new JFrame("Flappy Bird");
        //frame.setVisible(true);
        frame.setSize(boardwidth,boardheight);
        frame.setLocationRelativeTo(null);//to keep it in the middle of the screen
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//red cross box

        FlappyBird flappybird = new FlappyBird();
        frame.add(flappybird);
        frame.pack();//if we dont give pack(),then the total size of the game would be the size of the titlebar
        flappybird.requestFocus();                      
        frame.setVisible(true);//making it visible only after creating the frame

    }
}
