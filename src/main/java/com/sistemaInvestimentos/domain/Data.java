package domain;

public class Data {

    private String dataString;

    public Data(String dataString) {
        setData(dataString);
    }

    public String getValor() {
        return dataString;
    }

    public void setData(String dataString) {
        if (dataString == null || !dataString.matches("\\d{8}")) {
            throw new IllegalArgumentException("Data deve estar no formato AAAAMMDD.");
        }

        int year = Integer.parseInt(dataString.substring(0, 4));
        int month = Integer.parseInt(dataString.substring(4, 6));
        int day = Integer.parseInt(dataString.substring(6, 8));

        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Mês inválido.");
        }

        int[] daysInMonth = { 31, (isLeapYear(year) ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        if (day < 1 || day > daysInMonth[month - 1]) {
            throw new IllegalArgumentException("Dia inválido.");
        }

        this.dataString = dataString;
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}
