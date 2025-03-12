package galasefu.lab6incercare1.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CererePrietenie extends Entity<Long> {

    private Long id_user1;
    private Long id_user2;
    private LocalDateTime date;
    private String status;



    public CererePrietenie(Long id_user1, Long id_user2, LocalDateTime date, String status) {
        this.id_user2 = id_user2;
        this.id_user1 = id_user1;
        this.date = date;
        this.status = status;
    }

    public Long getId_user2() {
        return id_user2;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Long getId_user1() {
        return id_user1;
    }


    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // or your desired format
        return "Ora: " + date.format(formatter) + " | Status: " + status;
    }

}
