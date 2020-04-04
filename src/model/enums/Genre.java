package model.enums;

import java.util.ArrayList;

public enum Genre {
    ROMANCE(1),
    FICCAO_CIENTIFICA(2),
    HISTORIA(3),
    ADMINISTRACAO(4),
    INGLES(5),
    ECONOMIA(6);

    private final int id;

    Genre(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
    
    public static Genre getById(int id) {
        for (Genre e : values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
    
    public static ArrayList<Genre> getAll() {
        ArrayList<Genre> aux = new ArrayList<>();
        for (Genre e : values()) {
            aux.add(e);
        }
        return aux;
    }

}
