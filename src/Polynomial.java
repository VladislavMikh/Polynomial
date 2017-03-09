import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by HP on 16.02.2017.
 * Целочисленный полином
 */
public final class Polynomial {
    /**
     * Класс для работы с целочисленными полиномами
     *
     * Public методы:
     * getCoeff - возвращает коэффициент при произвольноый степени.
     * setCoeff - задаёт коэффициент при произвольной степени.
     * plus - Сложение двух полиномов.
     * unaryMinus - Обратное значение полинома (умножение полинома на -1).
     * minus - Вычитание двух полиномов.
     * multiply - Умножение двух полиномов.
     * (?) div - Деление двух полиномов нацело.
     * (?) mod - Остаток от деления одного полинома на другой.
     * value - Расчёт значения полинома при данном целом 'x'.
     * equal - Сравнение двух полиномов на равенство.
     */
    private Map <Integer, Integer> coeff;// ассоциативный массив: степень x — коэффициент
    private int higherExp; // старшая степень полинома
    private int lowerExp; // младшая степень полинома

    public Polynomial(int[] coeff, int lowerExp, int higherExp) {
        // Конструктор. Создаёт hashmap из передаваемого map`а
        this.coeff = new HashMap<>();
        for (int i = lowerExp, j = 0; i <= higherExp ; i++, j++) {
            this.coeff.put(i, coeff[j]);
        }
        this.higherExp = higherExp;
        this.lowerExp = lowerExp;
    }

    public int getCoeff(int exp) {
        // Геттер, возвращает коэффициент при нужной степени x. Если член с такой степенью отсутствует, вернёт ноль
        return (coeff.containsKey(exp)) ? coeff.get(exp) : 0;
    }

    public void setCoeff(int exp, int value) {
        // Сеттер, позволяет изменить коэффициент при нужной степени x.
        coeff.put(exp, value);
    }

    public Polynomial plus (Polynomial other) {
        // Сложение двух полиномов
        int lower = Math.min(this.lowerExp, other.lowerExp);
        int higher = Math.max(this.higherExp, other.higherExp);
        int[] coeff = new int[higher - lower + 1];
        for (int j= 0, i = lower; i <= higher; i++, j++) {
            coeff[j] = this.getCoeff(i) + other.getCoeff(i);
        }
        return new Polynomial(coeff, lower, higher);
    }

    public Polynomial unaryMinus() {
        // Меняет значение полинома на противоположное по знаку
        Polynomial p = new Polynomial(new int[higherExp - lowerExp + 1],lowerExp, higherExp);
        p.coeff = new HashMap<>();
        p.coeff.putAll(this.coeff);
        for (Map.Entry<Integer, Integer> entry : p.coeff.entrySet()) {
            p.coeff.put(entry.getKey(), entry.getValue() * -1);
        }
        return p;
    }

    public  Polynomial minus(Polynomial other) {
        // Разность двух полиномов
        return plus(other.unaryMinus());
    }

    public  Polynomial multiply(Polynomial other) {
        // Умножение двух полиномов
        int lower = this.lowerExp + other.lowerExp;
        int higher = this.higherExp + other.higherExp;
        int[] coeff = new int[higher - lower + 1];
        for (int j = 0, i = this.lowerExp; i <= this.higherExp; i++, j++) {
            for (int k = 0, l = other.lowerExp; l <= other.higherExp; l++, k++){
                coeff[j+k] += this.getCoeff(i) * other.getCoeff(l);
            }
        }
        return new Polynomial(coeff, lower, higher);
    }

    private Polynomial universalDiv(Polynomial other, boolean excess) {
        // Внутренний метод, возвращает целую часть или остаток от деления в зависимости от булевого аргумента на входе
        int lower = this.lowerExp - other.higherExp;
        int higher = this.higherExp - other.lowerExp;
        int[] coeff = new int[higher - lower + 1];
        int[] excessCoeff = new int[higher - lower + 1];
        return (excess) ? new Polynomial(excessCoeff, lower, higher) : new Polynomial(coeff, lower, higher);
    }

    public Polynomial div(Polynomial other) {
        // Деление нацело одного полинома на другой
        return universalDiv(other, false);
    }

    public Polynomial mod(Polynomial other) {
        // Остаток от деления одного полинома на другой
        return universalDiv(other, true);
    }

    public int evaluate(int x) {
        // Возвращает значение полинома при данном x
        int value = 0;
        for (int i = higherExp; i >= 0; i--) value = this.getCoeff(i) + (x * value);
            return value;
    }

    // Переопределение стандартных функций класса Object:
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Polynomial) {
            Polynomial otherPolynomial = (Polynomial) obj;
            if (this.higherExp != otherPolynomial.higherExp) return false;
            for (int i = this.higherExp; i >= 0; i--){
                if (this.getCoeff(i) != otherPolynomial.getCoeff(i)) return false;
            }
            return true;
        }
        else return false;
    }

    //@Override
    //public int hashCode() {}

    @Override
    public String toString() {
        return coeff.entrySet().stream().map(entry -> entry.getValue()+"x^"+entry.getKey()).collect(Collectors.joining("+"));
    }
}
