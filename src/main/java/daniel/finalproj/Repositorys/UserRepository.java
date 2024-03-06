package daniel.finalproj.Repositorys;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import daniel.finalproj.Objects.User;




@Repository
public interface UserRepository extends MongoRepository<User, String>
{
    public User findByUsername(String Username);
}