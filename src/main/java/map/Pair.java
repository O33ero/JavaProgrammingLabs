package map;

import java.util.Objects;

class Pair <F extends Comparable<F>, S> implements Comparable<Pair<F, S>>{
    F first;
    S second;

    /**
     * Конструктор
     * @param first Значение первого элемента
     * @param second Значение второго элемента
     */
    public Pair (F first, S second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Сравнение пар. Пара считается одинаковой, если совпадает первый элемент
     * @param other Сравниваемая пара
     * @return Результата проверки
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) other;

        return Objects.equals(first, pair.first);
    }

    @Override
    public int hashCode() {
        return first != null ? first.hashCode() : 0;
    }

    /**
     * Реализация интерфейса {@link Comparable}
     * @param other Сравниваемая пара
     * @return Результат сравнения
     */
    @Override
    public int compareTo(Pair<F, S> other) {
        return this.first.compareTo(other.first);
    }

    @Override
    public String toString() {
        return "[" + first + ", " + second + "]";
    }
}
