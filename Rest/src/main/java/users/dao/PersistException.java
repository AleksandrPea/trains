package users.dao;

/**
 * Зроблений Горохом Олександром,
 * КПІ, ФІОТ, гр. ІО-31
 * on 27.04.2015.
 */
public class PersistException extends Exception {

    public PersistException() {}

    public PersistException(Throwable cause) {
        super(cause);
    }

    public PersistException(String message) { super(message);}
}
