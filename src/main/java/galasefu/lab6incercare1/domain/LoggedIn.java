package galasefu.lab6incercare1.domain;



public class LoggedIn {
    private static  Utilizator u;

    public static Utilizator getU() {
        return u;
    }

    public static void setU(Utilizator u) {
        LoggedIn.u = u;
    }
}
