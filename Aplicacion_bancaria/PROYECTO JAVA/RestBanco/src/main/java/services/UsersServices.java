package services;

import dao.UsersDAO;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import model.User;
import org.springframework.transaction.annotation.Transactional;
import utils.PasswordHash;
import utils.Utils;

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
            Utils utils = new Utils();
            String token = utils.generateUserToken(foundUser);
            foundUser.setToken(token);
            System.out.println("USERTOKEN " + foundUser.getToken());
            foundUser = dao.updateUserToken(foundUser);
        }
        return foundUser;
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
    public List<User> getAllUsuarios()
    {
        UsersDAO dao = new UsersDAO();
        return dao.getAllUsers();
    }
    @Transactional
    public User addUsuario(User user) throws Exception
    {
        UsersDAO dao = new UsersDAO();
        user.setPassword(PasswordHash.getInstance().createHash(user.getPassword()));
        return dao.addUsuario(user);
    }
    @Transactional
    public boolean modUsuario(User user) throws Exception
    {
        UsersDAO dao = new UsersDAO();
        user.setPassword(PasswordHash.getInstance().createHash(user.getPassword()));
        return dao.modUsuario(user);
    }
    @Transactional
    public boolean delUsuario(int id) throws Exception
    {
        UsersDAO dao = new UsersDAO();
        User user = new User();
        user.setId(id);
        return dao.delUsuario(user);
    }
}
