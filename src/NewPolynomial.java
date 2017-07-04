
public class NewPolynomial {
    private int[] coeffs; // массив, хранит коэффициенты полинома

    // Конструктор, получает на вход 'coeffs' - массив коэффициентов полинома
    // coeff - локальное обозначение массива, используется только для работы конструктора
    public NewPolynomial(int[] coeffs) {
        int[] coeff = new int[getExp(coeffs) + 1];
        if (coeffs.length == 0) throw new IllegalArgumentException("Многочлен не существует");
        if (coeffs == null) throw new NullPointerException("Null");
        System.arraycopy(coeffs, 0, coeff, 0, getExp(coeffs) + 1);
        this.coeffs = coeff;
    }

    // Геттер, возвращает старшую степень полинома:
    public int getExp(int[] array) {
        int result = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0)
                result = i;
        }
        return result;
    }

    // Сумма двух полиномов:
    public NewPolynomial plus(NewPolynomial other){
        int maxLength = Math.max(this.coeffs.length, other.coeffs.length);
        int minLength = Math.min(this.coeffs.length, other.coeffs.length);
        // this.coeffs.length >= other.coeffs.length ? int[] max =  this.coeffs : int[] max = other.coeffs; // не сработал, idk
        int[] max;
        int[] min; // п1
        if (this.coeffs.length >= other.coeffs.length) {
            max = this.coeffs; //п2
            min = other.coeffs;
        } else {
            max = this.coeffs;
            min = other.coeffs;
        }
        // В новом массиве, по размерам соответствующем большему полиному, хранятся коэффициенты меньшего полинома
        int[] sameSized = new int[maxLength];
        for (int i = 0; i < minLength; i++){
            //Пришлось отдельно инициализировать оба массива в п1, а не в п2, т.к. иначе идея запрещала писать тут min[i];
            sameSized[i] = min[i];
        }
        // Сложение коэффициентов массивов равных размеров
        for (int i = 0; i < maxLength; i++) {
            sameSized[i] += max[i];
        }
        return new NewPolynomial(sameSized);
    }

    //Разность полиномов:
    public NewPolynomial minus(NewPolynomial other){
        int maxLength = Math.max(this.coeffs.length, other.coeffs.length);
        int minLength = Math.min(this.coeffs.length, other.coeffs.length);
        int[] min;
        if (this.coeffs.length <= other.coeffs.length) {
            min = this.coeffs;
        } else {
            min = other.coeffs;
        }
        int[] maxSizedResult = new int[maxLength];
        int[] maxSizedMin = new int[maxLength];
        for (int i = 0; i < minLength; i++) {
            maxSizedMin[i] = min[i];
        }
        if (getExp(this.coeffs) < getExp(other.coeffs)) {
            for (int i = 0; i < maxLength; i++)
                maxSizedResult[i] = maxSizedMin[i] - other.coeffs[i];
        } else {
            for (int i = 0; i < maxLength; i++)
                maxSizedResult[i] = this.coeffs[i] - maxSizedMin[i];
        }
        NewPolynomial result = new NewPolynomial(maxSizedResult);
        return result;
    }
    // Произведение 1, на вход поступает другой полином
    public NewPolynomial multiply(NewPolynomial other) {
        int[] product = new int[getExp(this.coeffs) + getExp(other.coeffs) + 1]; //x^5 * x^7=/5+7=x^12, но еще +1 тк массив с 0.
        for (int i = 0; i < this.coeffs.length; i++) {
            for (int j = 0; j < other.coeffs.length; j++) {
                product[i + j] += this.coeffs[i] * other.coeffs[j];
            }
        }
        return new NewPolynomial(product);
    }
    //Произведение 2, вызывается методом div() для вычисления остатка от деления полиномов:
    private NewPolynomial multiply(int currentCoeff, int currentExp) {
        int[] product = new int[getExp(this.coeffs) + currentExp + 1];
        for (int i = 0; i < this.coeffs.length; i++)
            product[i + currentExp] = this.coeffs[i] * currentCoeff;
        return new NewPolynomial(product);
    }

    // Деление двух полиномов нацело:
    public NewPolynomial div(NewPolynomial other) {
        if (getExp(other.coeffs) == 0 && other.coeffs[0] == 0) // && проверит первый операнд false? => false
            throw new ArithmeticException("Деление на ноль");
        NewPolynomial temp;
        NewPolynomial a = this; // п1 ???
        int[] x = new int[this.coeffs.length];
        int dividentExp = getExp(this.coeffs);
        int divisorExp = getExp(other.coeffs);
        while (dividentExp >= divisorExp) {
            x[dividentExp - divisorExp] = this.coeffs[dividentExp] / other.coeffs[divisorExp];
            temp = other.multiply(x[dividentExp - divisorExp], dividentExp - divisorExp);
            a = a.minus(temp); // не укорачивается с п.1 ???
            dividentExp--;
        }
        return new NewPolynomial(x);
    }

    // Остаток от деления полинома на полином:
    public NewPolynomial mod(NewPolynomial other) {
        return this.minus((this.div(other)).multiply(other));
    }

    //Возвращает значение полинома при данном X:
    public int evaluate(int x) {
        int result = 0;
        int currentExp = 1;
        for (int i = 0; i < coeffs.length; i++) {
            result += coeffs[i] * currentExp;
            currentExp *= x;
        }
        return result;
    }

    // Переопределение стандартных функций класса Object:
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (obj instanceof NewPolynomial) {
            NewPolynomial other = (NewPolynomial) obj;
            if (getExp(((NewPolynomial) obj).coeffs) != getExp(other.coeffs)) return false;
            for (int i = 0; i <= getExp(other.coeffs); i++)
                if (((NewPolynomial) obj).coeffs[i] != other.coeffs[i]) return false;
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return java.util.Arrays.hashCode(coeffs);
    }

    @Override
    public String toString() {
        return "NewPolynomial{" + coeffs + '}';
    }
}
