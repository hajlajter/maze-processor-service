package pl.kurs.java.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.kurs.java.model.Coordinate;
import pl.kurs.java.model.Maze;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageToMazeConverter {
    public Maze convertImage(InputStream inputStream) throws IOException {
        BufferedImage image = ImageIO.read(inputStream);
        int[][] array = new int[image.getHeight()][image.getWidth()];
        boolean[][] visited = new boolean[image.getHeight()][image.getWidth()];
        Coordinate start = null;
        Coordinate end = null;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int color = image.getRGB(j, i);
                if (color == -16777216) {
                    array[i][j] = 1;
                }
                if (color == -14503604) {
                    if (start == null) {
                        start = Coordinate.builder().x(i).y(j).build();
                    } else {
                        end = Coordinate.builder().x(i).y(j).build();
                    }
                }
            }
        }
        return pl.kurs.java.model.Maze.builder()
                .maze(array)
                .visited(visited)
                .end(end)
                .start(start)
                .build();
    }
}
