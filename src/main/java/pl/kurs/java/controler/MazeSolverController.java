package pl.kurs.java.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.kurs.java.model.Coordinate;
import pl.kurs.java.model.Maze;
import pl.kurs.java.service.ImageToMazeConverter;
import pl.kurs.java.service.MazePathFinder;
import pl.kurs.java.service.MazeSolvePainter;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/processor")
public class MazeSolverController {

    @Autowired
    ImageToMazeConverter imageToMazeConverter;
    @Autowired
    MazePathFinder mazePathFinder;
    @Autowired
    MazeSolvePainter mazeSolvePainter;

    @RequestMapping("/")
    public ResponseEntity<String> welcome() {
        return ResponseEntity.status(HttpStatus.OK).body("Hello in maze-processor-service");
    }

    @PostMapping("/solve")
    public void solveMaze(@RequestParam("maze") MultipartFile file, @RequestParam("jobId") String jobId, HttpServletResponse response) throws IOException {
        Maze maze = imageToMazeConverter.convertImage(file.getInputStream());
        List<Coordinate> solve = mazePathFinder.solve(maze);
        mazeSolvePainter.solvePainter(file.getInputStream(), solve, jobId, response);
    }

    @GetMapping("/result/{jobId}")
    public ResponseEntity<File[]> getResultOfJob(@PathVariable("jobId") String jobId, HttpServletResponse response){
        return ResponseEntity.status(HttpStatus.OK).body(new File(jobId).listFiles());
    }
}
