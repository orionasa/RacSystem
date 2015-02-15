package com.racsystem.login;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ApplicationScoped
@ManagedBean(name = "loginBean")
public class LoginBean implements Serializable {

    private String password;
    private String username;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUname() {
        return username;
    }

    public void setUname(String uname) {
        this.username = uname;
    }

    public void login() throws IOException {
        if (new LoginControl().login(username, password) /*&& (!username.equals("") || !password.equals(""))*/) {
            HttpSession session = Util.getSession();
            session.setAttribute("username", username);
            FacesContext.getCurrentInstance().getExternalContext().redirect("home.jsf");
        }
    }

    public String logout() {
        HttpSession session = Util.getSession();
        session.invalidate();
        return "login";
    }

    public String getWelcome() {
        return "<span>Welcome, " + username + " </span>";
    }
}
