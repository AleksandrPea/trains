package users.util;

import java.io.Serializable;

/**
 * Представляє пару об'єктів. Доступ до об'єктів забезпечується:
 * <ol>
 *  <li>За допомогою методів {@code getFirst()} та {@code getSecond()};</li>
 *  <li>За допомогою текстових міток.</li>
 * </ol>
 * 1)Порядок передачі об'єктів до конструктора визначає, яким методом
 * до них можна отримати доступ. Тобто, перший об'єкт має у відповідність
 * метод {@code getFirst()}, другий - {@code getSecond()}.<br>
 * 2)Поряд з об'єктами у конструктор є можливість передачі текстових міток,
 * що ідентифікують об'єкт. За допомогою метода {@code getObject(String)}
 * здійснюється доступ до об'єкту.
 *
 * @param <V1> тип першого об'єкту.
 * @param <V2> тип другого об'єкту.
 *
 * @author Горох Олександр Сергійович, гр. ІО-31, ФІОТ, НТУУ КПІ
 */
public class Pair<V1, V2> implements Serializable {
    private static final long serialVersionUID = 1L;

    private String label1;
    private String label2;
    private V1 obj1;
    private V2 obj2;

    public Pair(String label1, V1 obj1, String label2, V2 obj2) {
        this.label1 = label1;
        this.label2 = label2;
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    public Pair(V1 obj1, V2 obj2) {
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    /** Повертає об'єкт, який був першим з пари в параметрах конструктора. */
    public V1 getFirst() {
        return obj1;
    }

    /** Повертає об'єкт, який був другим з пари в параметрах конструктора. */
    public V2 getSecond() {
        return obj2;
    }

    /**
     * Повертає об'єкт, що ідентифікується міткою {@code label}.
     * Якщо жодна мітка з пари об'єктів не відповідає {@code label}
     * або {@code label == null}, то повертає {@code null}.
     */
    public Object getObject(String label) {
        if (label == null) {
            return null;
        }
        if (label.equals(label1)) {
            return obj1;
        } else if (label.equals(label2)) {
            return obj2;
        } else return null;
    }

    public void setFirstLabel(String label1) {
        this.label1 = label1;
    }

    public void setSecondLabel(String label2) {
        this.label2 = label2;
    }
}
