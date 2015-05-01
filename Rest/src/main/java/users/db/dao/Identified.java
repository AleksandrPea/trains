package users.db.dao;

import java.io.Serializable;

/**
 * Інтерфейс об'єктів, що ідентифікуються.
 *
 * @author Горох Олександр Сергійович, гр. ІО-31, ФІОТ, НТУУ КПІ
 */
public interface Identified<PK extends Serializable> {

     /** Повертає ідентифікатор об'єкту. */
     public PK getId();
}
