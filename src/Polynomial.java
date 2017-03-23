import java.util.*;
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
     * getExp - возвращает старшую степень полинома.
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
     *
     * Private методы:
     * universalDiv - метод, вызываемый методами div и mod.
     */

    private Map <Integer, Integer> coeff; // ассоциативный массив: степень x — коэффициент

    public Polynomial(int[] coeff) {
        // Конструктор: получает на вход массив из коэффициентов при x; обрезает нули после старшей степени (если есть).
        this.coeff = new HashMap<>();
        int firstNotNull;
        for (firstNotNull = coeff.length - 1; firstNotNull >= 0; firstNotNull--) {
            if (coeff[firstNotNull] != 0) break;
        }
        for (int i = 0; i <= firstNotNull ; i++) {
            this.coeff.put(i, coeff[i]);
        }
    }

    //Конструктор клонирования
    private Polynomial(Polynomial other) {
        for (Map.Entry<Integer, Integer> entry: other.coeff.entrySet()) {
            this.coeff.put(entry.getKey(), entry.getValue());
        }
    }

    public  int getExp() {
        //Геттер, возвращает старшую степень полинома.
        return coeff.size()-1;
    }

    public int getCoeff(int exp) {
        // Геттер, возвращает коэффициент при нужной степени x. Если член с такой степенью отсутствует, вернёт ноль.
        return (coeff.containsKey(exp)) ? coeff.get(exp) : 0;
    }

    public void setCoeff(int exp, int value) {
        // Сеттер, позволяет изменить коэффициент при нужной степени x.
        coeff.put(exp, value);
    }

    public Polynomial plus (Polynomial other) {
        // Сложение двух полиномов.
        //int lower = Math.min(this.coeff.size()-1, other.coeff.size()-1);
        int lower = 0;
        int higher = Math.max(this.coeff.size()-1, other.coeff.size()-1);
        int[] coeff = new int[higher + 1];
        for (int j= 0, i = lower; i <= higher; i++, j++) {
            coeff[j] = this.getCoeff(i) + other.getCoeff(i);
        }
        return new Polynomial(coeff);
    }


    public Polynomial unaryMinus() {
        /* Решение через конструктнор копирования
        Polynomial newPoly =  new Polynomial(this);
        newPoly.coeff = newPoly.coeff.entrySet().stream().
                collect(Collectors.toMap(E -> E.getKey(), E -> -E.getValue()));
        return newPoly;
        */

        //Решение через обычный конструктор с пересчетом коэффициентов перед передачей
        return new Polynomial(this.coeff.entrySet().stream().mapToInt(E -> -E.getValue()).toArray());
    }

    public  Polynomial minus(Polynomial other) {
        // Разность двух полиномов
        return plus(other.unaryMinus());
    }

    public  Polynomial multiply(Polynomial other) {
        // Умножение двух полиномов
        int higher = this.coeff.size()-1 + other.coeff.size()-1;
        int[] coeff = new int[higher + 1];
        for (int j = 0, i = 0; i <= this.coeff.size()-1; i++, j++) {
            for (int k = 0, l = 0; l <= other.coeff.size()-1; l++, k++){
                coeff[j+k] += this.getCoeff(i) * other.getCoeff(l);
            }
        }
        return new Polynomial(coeff);
    }

    //private Polynomial universalDiv(Polynomial other, boolean excess) {
        // Внутренний метод, возвращает целую часть или остаток от деления в зависимости от булевого аргумента на входе
        //int lower = this.lowerExp - other.higherExp;
        //int higher = this.higherExp - other.lowerExp;
        //int[] coeff = new int[higher - lower + 1];
        //int[] excessCoeff = new int[higher - lower + 1];
        //return (excess) ? new Polynomial(excessCoeff, lower, higher) : new Polynomial(coeff, lower, higher);
    //}

    //public Polynomial div(Polynomial other) {
        // Деление нацело одного полинома на другой
        //return universalDiv(other, false);
    //}

    //public Polynomial mod(Polynomial other) {
        // Остаток от деления одного полинома на другой
        //return universalDiv(other, true);
    //}

    public int evaluate(int x) {
        // Возвращает значение полинома при данном x
        int value = 0;
        for (int i = coeff.size(); i >= 0; i--) {
            value = this.getCoeff(i) + (x * value);
        }
        return value;
    }

    // Переопределение стандартных функций класса Object:
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Polynomial) {
            Polynomial otherPolynomial = (Polynomial) obj;
            if (this.coeff.size()-1 != otherPolynomial.coeff.size()-1) return false;
            for (int i = this.coeff.size()-1; i >= 0; i--){
                if (this.getCoeff(i) != otherPolynomial.getCoeff(i)) return false;
            }
            return true;
        }
        else return false;
    }

    @Override
    public int hashCode() {
        return 31 * coeff.size() + coeff.hashCode();
    }

    @Override
    public String toString() {
        List<Map.Entry<Integer, Integer>> set = new ArrayList<>(coeff.entrySet());
        Collections.reverse(set);
        return set.stream().filter(E -> E.getValue() != 0)
                .map(entry -> entry.getValue()+"x^"+entry.getKey())
                .collect(Collectors.joining("+"));
    }
}
