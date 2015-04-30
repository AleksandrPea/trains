package users.dao;

/**
 * ����������, ��� ��������� ������������ {@link DaoFactory}
 * �� {@link GenericDao}. �������, ���� ��������
 * �� ���'��������� �� {@code SqlException}.
 *
 * @author ����� ��������� ���������, ��. ��-31, Բ��, ���� �ϲ
 */
public class PersistException extends Exception {

    public PersistException() {}

    public PersistException(Throwable cause) {
        super(cause);
    }

    public PersistException(String message) { super(message);}
}
