package users.dao;

/**
 * Виключення, яке забезпечує уніфікованість {@link DaoFactory}
 * та {@link GenericDao}. Зокрема, воно дозволяє
 * не зав'язуватися на {@code SqlException}.
 *
 * @author Горох Олександр Сергійович, гр. ІО-31, ФІОТ, НТУУ КПІ
 */
public class PersistException extends Exception {

    public PersistException() {}

    public PersistException(Throwable cause) {
        super(cause);
    }

    public PersistException(String message) { super(message);}
}
