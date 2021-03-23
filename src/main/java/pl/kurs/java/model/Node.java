package pl.kurs.java.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.UrlResource;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Node {
    private String jobId;
    private String status;
    private UrlResource urlResource;

    //tutaj powinienem tez nadpisa metode getStatus, zeby zaciagala status bezposrednio z bazy danych
}
