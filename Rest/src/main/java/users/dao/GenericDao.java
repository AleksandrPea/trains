package users.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Уніфікований інтерфейс управлінням персистентним станом обєктів.
 *
 * @param <T> тип об'єкту персистенції
 * @param <PK> тип первинного ключа
 *
 * @author Горох Олександр Сергійович, гр. ІО-31, ФІОТ, НТУУ КПІ
 */
public interface GenericDao<T extends Identified<PK>, PK extends Serializable> {

    /** Створює новий запис в базі даних і відповідний до нього об'єкт. */
    public T create() throws PersistException;

    /** Створює новий запис, який відповідає об'єкту {@code obj}. */
    public T persist(T obj) throws PersistException;

    /** Повертає об'єкт, який відповідає запису з первинним ключом {@code key}, або null. */
    public T getByPK(PK key) throws PersistException;

    /** Зберігає стан об'єкту {@code obj} в базі даних. */
    public void update(T obj) throws PersistException;

    /** Видаляє запис, який відповідає об'єкту, з бази даних. */
    public void delete(T obj) throws PersistException;

    /** Повертає список об'єктів, які відповідають усім записам у базі даних. */
    public List<T> getAll() throws PersistException;
}
