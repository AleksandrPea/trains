package users.db.dao;

import java.io.Serializable;

/**
 * ��������� ��'����, �� ���������������.
 *
 * @author ����� ��������� ���������, ��. ��-31, Բ��, ���� �ϲ
 */
public interface Identified<PK extends Serializable> {

     /** ������� ������������� ��'����. */
     public PK getId();
}
