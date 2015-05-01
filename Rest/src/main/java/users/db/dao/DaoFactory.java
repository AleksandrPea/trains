package users.db.dao;

/**
 * ������� ��'���� ��� ������ � ����� �����.
 *
 * @param <Context> ��� ���������� �� ���� �����.
 *
 * @author ����� ��������� ���������, ��. ��-31, Բ��, ���� �ϲ
 */
public interface DaoFactory<Context> {

    public interface DaoCreator<Context> {
        public GenericDao create(Context context);
    }

    /** C������ ���������� �� ���� ����� �� �������������. */
    public Context createContext() throws PersistException;

    /**
     * ������� ��'��� ��� ��������� ������������� ������ ����� ��'����.
     * @param context ���������� �� ���� �����
     * @param dtoClass ����������� ����, ��'������ ����� ��������� ���������.
     */
    public GenericDao getDao(Context context, Class dtoClass) throws PersistException;
}
