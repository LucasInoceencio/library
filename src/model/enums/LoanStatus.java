package model.enums;

public enum LoanStatus {
    ATIVO(1),
    ENCERRADO(2);
    
    private final int id;

    LoanStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
    
    public static LoanStatus getById(int id) {
        for (LoanStatus e : values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
