
package com.racsystem.login;

import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class LoginControl {

    public boolean login(String username, String password) {
        try {
            if (checkUsername(username) && checkPassword(password)) {
                return true;
            } else {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Invalid Login!",
                                "Please Try Again!"));
                return false;
            }
        } catch (HibernateException ex) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Could not connect to Database.",
                            ""));
            return false;
        }
    }

    public boolean checkUsername(String username) {
        List users = createSession().createQuery("FROM UserData").list();
        for (Iterator iter = users.iterator(); iter.hasNext();) {
            UserData user = (UserData) iter.next();
            if (username.equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public boolean checkPassword(String password) {
        List users = createSession().createQuery("FROM UserData").list();
        for (Iterator iter = users.iterator(); iter.hasNext();) {
            UserData user = (UserData) iter.next();
            if (password.equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }

    public Session createSession() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        return factory.openSession();
    }
}
