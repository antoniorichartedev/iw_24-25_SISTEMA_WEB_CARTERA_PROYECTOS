package projectum;

import projectum.data.entidades.Usuario;

import java.util.List;

public class ApiResponse {
    private List<Usuario> data;

    public List<Usuario> getData() {
        return data;
    }

    public void setData(List<Usuario> data) {
        this.data = data;
    }
}
