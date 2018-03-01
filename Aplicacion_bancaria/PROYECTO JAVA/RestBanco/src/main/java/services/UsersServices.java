package services;

import dao.UsersDAO;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import model.User;
import utils.PasswordHash;

public class UsersServices {
    public User doLogin(User loginUser)
    {
        UsersDAO dao = new UsersDAO();
        loginUser.setToken(null);
        User foundUser = dao.getUserByDni(loginUser);
        boolean validPass;
        try
        {
            validPass = PasswordHash.getInstance().validatePassword(loginUser.getPassword(), foundUser.getPassword());
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException | NullPointerException ex)
        {
            validPass = false;
        }
        if(validPass)
        {
            /*
            Si el login va bien devuelve el token de inicio de sesión
            y se guarda en el local storage.
            */
            String token = dao.generateLoginToken(loginUser);
            loginUser.setToken(token);
        }
        return loginUser;
    }
    public boolean checkToken(String token)
    {
        UsersDAO dao = new UsersDAO();
        boolean validToken = false;
        User user = new User();
        user.setToken(token);
        user = dao.getUserByToken(user);
        if(0 != user.getId())
        {
            validToken = true;
        }
        return validToken;
    }
}
