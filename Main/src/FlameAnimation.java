import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FlameAnimation extends JPanel {
    private final BufferedImage[] frames;
    private int currentFrame = 0;
    private final int frameWidth;
    private final int frameHeight;

    public FlameAnimation(String imagePath, int frameCount, int delay) throws IOException {
        BufferedImage spriteSheet = ImageIO.read(new File(imagePath));
        frameWidth = spriteSheet.getWidth() / frameCount;
        frameHeight = spriteSheet.getHeight();
        frames = new BufferedImage[frameCount];

        for (int i = 0; i < frameCount; i++) {
            frames[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
        }

        Timer timer = new Timer(delay, _ -> {
            currentFrame = (currentFrame + 1) % frameCount;
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(frames[currentFrame], 0, 0, null);
        g.drawImage(frames[currentFrame], 200, 0, null); // Второй набор анимаций
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(frameWidth * 2 + 200, frameHeight); // Учитываем расстояние между анимациями
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                JFrame frame = new JFrame("Flame Animation");
                FlameAnimation animationPanel = new FlameAnimation("C:\\Users\\golov\\IdeaProjects\\Main\\src\\FlameAnimation.jpg", 8, 100); // Убедитесь, что путь к изображению корректен
                frame.add(animationPanel);
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
