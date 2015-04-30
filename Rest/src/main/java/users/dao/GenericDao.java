package users.dao;

import java.io.Serializable;
import java.util.List;

/**
 * ����������� ��������� ���������� ������������� ������ �����.
 *
 * @param <T> ��� ��'���� ������������
 * @param <PK> ��� ���������� �����
 *
 * @author ����� ��������� ���������, ��. ��-31, Բ��, ���� �ϲ
 */
public interface GenericDao<T extends Identified<PK>, PK extends Serializable> {

    /** ������� ����� ����� � ��� ����� � ��������� �� ����� ��'���. */
    public T create() throws PersistException;

    /** ������� ����� �����, ���� ������� ��'���� {@code obj}. */
    public T persist(T obj) throws PersistException;

    /** ������� ��'���, ���� ������� ������ � ��������� ������ {@code key}, ��� null. */
    public T getByPK(PK key) throws PersistException;

    /** ������ ���� ��'���� {@code obj} � ��� �����. */
    public void update(T obj) throws PersistException;

    /** ������� �����, ���� ������� ��'����, � ���� �����. */
    public void delete(T obj) throws PersistException;

    /** ������� ������ ��'����, �� ���������� ��� ������� � ��� �����. */
    public List<T> getAll() throws PersistException;
}
