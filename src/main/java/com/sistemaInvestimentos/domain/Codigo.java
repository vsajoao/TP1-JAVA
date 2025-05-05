package domain;

public class Codigo {

    private String codigo;

    public Codigo(String codigo) {
        setCodigo(codigo);
    }

    public String getValor() {
        return codigo;
    }

    private void setCodigo(String codigo) {
        if (codigo == null || codigo.isEmpty()) {
            throw new IllegalArgumentException("O código não pode ser nulo ou vazio.");
        }
        if (codigo.length() != 5) {
            throw new IllegalArgumentException("O código deve ter exatos 5 dígitos.");
        }
        if (!codigo.matches("\\d+")) {
            throw new IllegalArgumentException("O código deve conter apenas dígitos.");
        }

        this.codigo = codigo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Codigo outroCodigo = (Codigo) obj;
        return codigo.equals(outroCodigo.codigo);
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }
}
