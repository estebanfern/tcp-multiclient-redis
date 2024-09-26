package py.una.dao;

import py.una.model.User;
import redis.clients.jedis.Jedis;

import java.util.logging.Logger;

public class UserDao {

    private final Jedis jedis;
    private final Logger logger = Logger.getLogger(UserDao.class.getName());

    public UserDao() {
        jedis = new Jedis("localhost", 6379);
        logger.info("Conexión a Redis satisfactoria");
    }

    public void addUser(User usuario) {
        jedis.hset("usuarios", usuario.getUid(), usuario.getUsername());
        logger.info("Usuario " + usuario.getUsername() + " agregado a Redis.");
    }

    public String getUsers() {
        return jedis.hgetAll("usuarios").values().toString();
    }

    public void deleteUser(String id) {
        jedis.hdel("usuarios", id);
        logger.info("Usuario con ID " + id + " eliminado de Redis.");
    }

    public void close() {
        jedis.close();
    }

}
