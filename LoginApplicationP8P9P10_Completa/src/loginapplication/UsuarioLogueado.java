package loginapplication;

public class UsuarioLogueado {
    private static UsuarioLogueado instance;
    private String username;

    private UsuarioLogueado() {}

    public static UsuarioLogueado getInstance() {
        if (instance == null) {
            instance = new UsuarioLogueado();
        }
        return instance;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
