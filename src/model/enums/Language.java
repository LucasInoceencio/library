package model.enums;

public enum Language {
    INGLES(1),
    PORTUGUES(2),
    CHINES(3),
    ESPANHOL(4),
    FRANCES(5);

    private final int id;

    Language(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static Language getById(int id) {
        for (Language e : values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }

}
