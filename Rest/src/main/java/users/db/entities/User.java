package users.db.entities;


import users.db.dao.Identified;

import java.util.Date;

/**
 * Об'єктне представлення сутності "Користувач".
 *
 * @author Горох Олександр Сергійович, гр. ІО-31, ФІОТ, НТУУ КПІ
 */
public class User implements Identified<Integer> {

    private Integer id;
    private String email;
    private String password;
    private Date create_date;
    private String lastName;
    private String firstName;
    private String address;
    private Integer carrier_id = null;

    public Integer getId() {
        return id;
    }

    protected void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCarrier_id() {
        return carrier_id;
    }

    public void setCarrier_id(Integer carrier_id) {
        this.carrier_id = carrier_id;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public User() {}

}
