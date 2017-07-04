import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для работы с целочисленными полиномами
 *
 * Public методы:
 * getExp - Возвращает старшую степень полинома.
 * getCoeff - Возвращает коэффициент при произвольной степени.
 * setCoeff - Задаёт коэффициент при произвольной степени.
 * plus - Сложение двух полиномов.
 * unaryMinus - Обратное значение полинома (умножение полинома на -1).
 * minus - Вычитание двух полиномов.
 * multiply - Умножение двух полиномов.
 * div - Деление двух полиномов нацело.
 * mod - Остаток от деления одного полинома на другой.
 * value - Расчёт значения полинома при данном целом 'x'.
 * equals - Сравнение двух полиномов на равенство.
 * hashCode;
 * toString;
 *
 * Private методы:
 * universalDiv - метод, вызываемый методами div и mod.
 */
public final class Polynomial {

    private Map <Integer, Integer> coeff; // ассоциативный массив: степень x — коэффициент

    // Конструктор: получает на вход массив из коэффициентов при x; обрезает нули после старшей степени (если есть)
    public Polynomial(int[] coeff) {
        this.coeff = new HashMap<>();
        int firstNotNull;
        for (firstNotNull = coeff.length - 1; firstNotNull >= 0; firstNotNull--) {
            if (coeff[firstNotNull] != 0) break;
        }
        for (int i = 0; i <= firstNotNull ; i++) {
            this.coeff.put(i, coeff[i]);
        }
    }

    //Конструктор клонирования:
    private Polynomial(Polynomial other) {
        this.coeff = new HashMap<Integer,Integer>();
        for (Map.Entry<Integer, Integer> entry: other.coeff.entrySet()) {
            this.coeff.put(entry.getKey(), entry.getValue());
        }
    }

    //Конструктор клонирования:
    //private Polynomial(Polynomial other) {
      //  this.coeff = new HashMap<>();
        //int[] coeff = other.coeff.entrySet().stream().mapToInt(E -> E.getValue()).toArray();
        //int firstNotNull;
        //for (firstNotNull = coeff.length - 1; firstNotNull >= 0; firstNotNull--) { // убирает возможные нули в начале
        //if (coeff[firstNotNull] != 0) break;
        //}
        //for (int i = 0; i <= firstNotNull ; i++) {
        //   this.coeff.put(i, coeff[i]);
        //}
    //}

    //Геттер, возвращает старшую степень полинома:
    public  int getExp() {
        return coeff.size()-1;
    }

    // Геттер, возвращает коэффициент при нужной степени x. Если член с такой степенью отсутствует, вернёт ноль:
    public int getCoeff(int exp) {
        return (coeff.containsKey(exp)) ? coeff.get(exp) : 0;
    }

    // Сеттер, позволяет изменить коэффициент при нужной степени x:
    public void setCoeff(int exp, int value) {
        coeff.put(exp, value);
    }

    // Сложение двух полиномов:
    public Polynomial plus(Polynomial other) {
        int lower = 0;
        int higher = Math.max(this.getExp(), other.getExp());
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
         //Map <Integer, Integer> newCoeffs; //.filter coeff.entrySet().stream().mapToInt(E -> -E.getValue()).toArray()
        return new Polynomial(this.coeff.entrySet().stream().mapToInt(E -> -E.getValue()).toArray());
        // Передается во владение новому объекту, конструктор выше
        //newCoeffs = null; //говорит о том, что ссылку больше не используется нигде, кроме как для создания нового объекта
       // Map <Integer, Integer> newCoeff = new HashMap<Integer, Integer>();
       // return newCoeff.entrySet().stream().filter(); ??

    }

    // Разность двух полиномов:
    public  Polynomial minus(Polynomial other) {
        return plus(other.unaryMinus());
    }

    // Умножение двух полиномов:
    public  Polynomial multiply(Polynomial other) {
        int higher = this.getExp() + other.getExp();
        int[] coeff = new int[higher + 1];
        for (int j = 0, i = 0; i <= this.getExp(); i++, j++) {
            for (int k = 0, l = 0; l <= other.getExp(); l++, k++){
                coeff[j+k] += this.getCoeff(i) * other.getCoeff(l);
            }
        }
        return new Polynomial(coeff);
    }

    // Внутренний метод, через него работают методы div и mod:
    private Polynomial universalDiv(Polynomial other, boolean needExcess) {

        if (other.getExp() == 0 && other.getCoeff(0) == 0) {
            throw new ArithmeticException("Деление на ноль невозможно");
        }

        Polynomial dividend = new Polynomial(this); // - делимое
        final Polynomial divisor = new Polynomial(other); //  - делитель
        Polynomial quotient = new Polynomial(new int[]{0}); // - частное
        Polynomial temp; // - произведение делителя и текущего (неокончательного) значения частного
        Polynomial excess = dividend; // - остаток, по умолчанию равен делимому, т.к.:

        if (this.getExp() < other.getExp()) {
            return (needExcess) ? excess: new Polynomial(new int[] {0}); // (x^2 mod x^3 = x^2); (x^2 div x^3 = 0).
        }

        int currentExp = dividend.getExp() - divisor.getExp(); // - текущая степень частного
        int[] x = new int[currentExp + 1];
        while (currentExp >= 0) { // пока степень делимого больше степени делителя:
            int currentCoeff = dividend.getCoeff(currentExp) / divisor.getCoeff(currentExp); // делит на ноль
            x[currentExp] = currentCoeff;
            quotient.plus(new Polynomial(x)); //  Прибавление к частному нового одночлена
            temp = divisor.multiply(quotient);
            excess = dividend.minus(temp);
            currentExp--;
        }
        return (needExcess) ? excess : quotient;
    }

    //Деление нацело одного полинома на другой:
    public Polynomial div(Polynomial other) {
        return universalDiv(other, false);
    }

    // Остаток от деления одного полинома на другой:
    public Polynomial mod(Polynomial other) {
        return universalDiv(other, true);
    }

    // Возвращает значение полинома при данном x:
    public int evaluate(int x) {
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
            if (this.getExp() != otherPolynomial.getExp()) return false;
            for (int i = this.getExp(); i >= 0; i--){
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
