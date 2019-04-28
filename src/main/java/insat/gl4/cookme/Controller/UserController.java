package insat.gl4.cookme.Controller;

import insat.gl4.cookme.models.User;
import insat.gl4.cookme.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller for user Model
 */
@RestController
@CrossOrigin(origins = "*")
public class UserController {

    /**
     * field: repository to manipulate the model
     */
    @Autowired
    UserRepository userRepository;

    /**
     * method to return all users
     * @return Iterable<User>
     */
    @GetMapping(path = "/user")
    public Iterable<User> findAll(){
        return userRepository.findAll();
    }

    /**
     *
     * @param id: id of the user, it is a path variable
     * @return the user with the correpondant id
     */
    @GetMapping(path = "/user/{id}")
    public ResponseEntity<User> findOne(@PathVariable int id){
        Optional<User> user =  userRepository.findById(id);
        if (user.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(user.get());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    /**
     * method to create and save a user
     * @param user as the body of the request
     * @return the user itself with the id after creation and adding it to the database
     */
    @PostMapping(path = "/user")
    public ResponseEntity<User> create(@RequestBody User user) {
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    /**
     * method to update and save a user
     * @param user as the body of the request
     * @return the user after updating in the database
     */
    @PutMapping(path = "/user/{id}")
    public ResponseEntity<User> update(@PathVariable int id, @RequestBody User user) {
        Optional<User> userDb =  userRepository.findById(id);
        if (userDb.isPresent()) {
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     *
     * @param id: id of the user to delete
     * @return empty response with either status Ok or NotFound
     */
    @DeleteMapping(path = "/user/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        Optional<User> user =  userRepository.findById(id);
        if (user.isPresent())
            return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     *
     * @param user that is trying to login
     * @return the user found in the database, the method will return null if user not found
     */
    @PostMapping(path = "/login")
    public ResponseEntity<User> login(@RequestBody User user){
        User userDb =  userRepository.findByEmailAndPassword(user.getEmail(),user.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(userDb);
    }
}
