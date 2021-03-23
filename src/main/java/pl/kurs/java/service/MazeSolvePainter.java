package pl.kurs.java.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.kurs.java.model.Coordinate;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.UUID;

@Service
public class MazeSolvePainter {

    public void solvePainter(InputStream inputStream, List<Coordinate> path, String jobId, HttpServletResponse response) throws IOException {
        BufferedImage image = ImageIO.read(inputStream);
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        g2d.setColor(Color.GREEN);
        g2d.setStroke(new BasicStroke(2));

        for (int i = 0; i < path.size() - 1; i++) {
            Coordinate coordinate = path.get(i);
            Coordinate nextCoordinate = path.get(i + 1);
            g2d.drawLine(coordinate.getY(), coordinate.getX(), nextCoordinate.getY(), nextCoordinate.getX());
        }

        new File(jobId).mkdirs();
        String name = UUID.randomUUID().toString() + ".png";
        File newFile = new File(jobId, name);

        ImageIO.write(image, "png", newFile);

        response.setContentLength((int) newFile.length());
        response.setHeader("Content-Disposition", "attachment; filename=\"solvedMaze.png\"");
        byte[] buffer = new byte[10240];
        try (
                InputStream input = new FileInputStream(newFile);
                OutputStream output = response.getOutputStream();
        ) {
            for (int length = 0; (length = input.read(buffer)) > 0; ) {
                output.write(buffer, 0, length);
            }
        }

    }
}
