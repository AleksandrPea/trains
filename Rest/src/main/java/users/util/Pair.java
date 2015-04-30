package users.util;

import java.io.Serializable;

/**
 * ����������� ���� ��'����. ������ �� ��'���� �������������:
 * <ol>
 *  <li>�� ��������� ������ {@code getFirst()} �� {@code getSecond()};</li>
 *  <li>�� ��������� ��������� ����.</li>
 * </ol>
 * 1)������� �������� ��'���� �� ������������ �������, ���� �������
 * �� ��� ����� �������� ������. �����, ������ ��'��� �� � ����������
 * ����� {@code getFirst()}, ������ - {@code getSecond()}.<br>
 * 2)����� � ��'������ � ����������� � ��������� �������� ��������� ����,
 * �� ������������� ��'���. �� ��������� ������ {@code getObject(String)}
 * ����������� ������ �� ��'����.
 *
 * @param <V1> ��� ������� ��'����.
 * @param <V2> ��� ������� ��'����.
 *
 * @author ����� ��������� ���������, ��. ��-31, Բ��, ���� �ϲ
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

    /** ������� ��'���, ���� ��� ������ � ���� � ���������� ������������. */
    public V1 getFirst() {
        return obj1;
    }

    /** ������� ��'���, ���� ��� ������ � ���� � ���������� ������������. */
    public V2 getSecond() {
        return obj2;
    }

    /**
     * ������� ��'���, �� �������������� ����� {@code label}.
     * ���� ����� ���� � ���� ��'���� �� ������� {@code label}
     * ��� {@code label == null}, �� ������� {@code null}.
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
