package users.dao;

import java.io.Serializable;

/**
 * ��������� ������� �����������,
 * �ϲ, Բ��, ��. ��-31
 * on 27.04.2015.
 */
public interface Identified<PK extends Serializable> {

   public PK getId();
}
