package insat.gl4.cookme.controller;

import insat.gl4.cookme.models.User;
import insat.gl4.cookme.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    UserRepository userRepository;

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
