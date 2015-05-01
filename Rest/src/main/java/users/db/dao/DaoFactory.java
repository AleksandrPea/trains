package users.db.dao;

/**
 * Фабрика об'єктів для роботи з базою даних.
 *
 * @param <Context> тип підключення до бази даних.
 *
 * @author Горох Олександр Сергійович, гр. ІО-31, ФІОТ, НТУУ КПІ
 */
public interface DaoFactory<Context> {

    public interface DaoCreator<Context> {
        public GenericDao create(Context context);
    }

    /** Cтворює підключення до бази даних за замовчуванням. */
    public Context createContext() throws PersistException;

    /**
     * Повертає об'єкт для управління персистентним станом інших об'єктів.
     * @param context підключення до бази даних
     * @param dtoClass представляє клас, об'єктами якого необхідно управляти.
     */
    public GenericDao getDao(Context context, Class dtoClass) throws PersistException;
}
