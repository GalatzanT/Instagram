package galasefu.lab6incercare1.domain;

import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class Utilizator extends Entity<Long> {
    private String nume;
    private String prenume;
    private String email;
    private String password;
    private List<Utilizator> prieteni;
    private List<CererePrietenie> cereri;
    private String ocupatie;
    private String bio;
    private String poza;

    public String getOcupatie() {
        return ocupatie;
    }

    public void setOcupatie(String ocupatie) {
        this.ocupatie = ocupatie;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPoza() {
        return poza;
    }

    public void setPoza(String poza) {
        this.poza = poza;
    }
//Lab 6 prietenii si cereri

    public void addFriend(Utilizator u){
        if(!prieteni.contains(u)){
            prieteni.add(u);
        }
    }

    public void deleteFriend(Utilizator u){
        prieteni.remove(u);
    }

    public List<Utilizator> getPrieteni() {
        return prieteni;
    }

    public void addCerere(CererePrietenie c){
        cereri.add(c);
    }

    public void deleteCerere(CererePrietenie c){
        cereri.remove(c);
    }

    public List<CererePrietenie> getCereri(){
        return cereri;
    }


    public Utilizator(String nume, String prenume, String email, String password, String ocupatie, String bio, String poza) {
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.password = password;
        this.ocupatie = ocupatie;
        this.bio = bio;
        this.poza = poza;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setPrieteni(List<Utilizator> prieteni) {
        this.prieteni = prieteni;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilizator that = (Utilizator) o;
        return Objects.equals(getPrenume(), that.getPrenume()) && Objects.equals(getNume(), that.getNume()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getPassword(), that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrenume(), getNume(), getEmail(), getPassword());
    }

    @Override
    public String toString() {
        return nume + " " + prenume;
    }
}
