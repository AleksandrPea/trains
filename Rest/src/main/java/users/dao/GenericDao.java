package users.dao;

import java.io.Serializable;
import java.util.List;

/**
 * ��������� ������� �����������,
 * �ϲ, Բ��, ��. ��-31
 * on 27.04.2015.
 */
public interface GenericDao<T extends Identified<PK>, PK extends Serializable> {

    public T create() throws PersistException;
    public T persist(T obj) throws PersistException;
    public T getByPK(PK key) throws PersistException;
    public void update(T obj) throws PersistException;
    public void delete(T obj) throws PersistException;
    public List<T> getAll() throws PersistException;
}
