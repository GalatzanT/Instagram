package galasefu.lab6incercare1.domain;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Mesaj extends Entity<Long> {
    private Utilizator from;
    private List<Utilizator> to;
    private String text;
    private LocalDateTime timp;
    private Mesaj reply;

    public Mesaj(Utilizator from, List<Utilizator> to, LocalDateTime timp, String text) {
        this.from = from;
        this.to = to;
        this.text = text;
        this.timp = timp;
        reply =null;
    }

    public Mesaj(Utilizator from, List<Utilizator> to, String text,LocalDateTime timp, Mesaj reply) {
        this(from,to,timp,text);
        this.reply =reply;
    }


    public Utilizator getFrom() {
        return from;
    }

    public List<Utilizator> getTo() {
        return to;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getTimp() {
        return timp;
    }

    public Mesaj getReply() {
        return reply;
    }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return "[" + timp.format(formatter) + " " +  from.getNume() + "]:" + " " + text;
    }
}

