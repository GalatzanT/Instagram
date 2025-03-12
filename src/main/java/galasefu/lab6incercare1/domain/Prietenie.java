package galasefu.lab6incercare1.domain;


import java.sql.Date;
import java.time.LocalDateTime;

public class Prietenie extends Entity<Long>{
    Long idUser1;
    Long idUser2;
    LocalDateTime friendsFrom;

    public Prietenie(Long idUser1, Long idUser2, LocalDateTime friendsFrom) {
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.friendsFrom = friendsFrom;
    }

    public Long getIdUser1() {
        return idUser1;
    }

    public void setIdUser1(Long idUser1) {
        this.idUser1 = idUser1;
    }

    public Long getIdUser2() {
        return idUser2;
    }

    public void setIdUser2(Long idUser2) {
        this.idUser2 = idUser2;
    }

    public LocalDateTime getFriendsFrom() {
        return friendsFrom;
    }

    public void setFriendsFrom(LocalDateTime friendsFrom) {
        this.friendsFrom = friendsFrom;
    }

    @Override
    public String toString() {
        return "Prietenie{" +
                "idUser1=" + idUser1 +
                ", idUser2=" + idUser2 +
                ", friendsFrom=" + friendsFrom +
                '}';
    }
}
