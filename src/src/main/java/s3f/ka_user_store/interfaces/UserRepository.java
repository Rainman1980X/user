package s3f.ka_user_store.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import s3f.ka_user_store.dtos.UserDto;

/**
 * Created by MSBurger on 09.09.2016.
 */
public interface UserRepository extends MongoRepository<UserDto, String> {
    public UserDto findOneByUserId(int userId);
    public UserDto findOneByEmail(String Email);
}
