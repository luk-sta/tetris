package cz.hyperion;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

public final class SoundUtil {

    public static void playGameStart() {
        playSound("game-start.wav");
    }

    public static void playBottomTap() {
        playSound("tap.wav");
    }

    public static void playRowDone() {
        playSound("row-done.wav");
    }

    public static void playSound(String name) {
        final URL soundFileUrl = ClassLoader.getSystemResource(name);
        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFileUrl)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.addLineListener(event -> {
                if (event.getType() == javax.sound.sampled.LineEvent.Type.STOP) {
                    clip.close();
                    System.out.println("Clip closed.");
                }
            });
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
