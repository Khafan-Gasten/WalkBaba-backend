import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping(path="/")
    ResponseEntity<String> test() {
        return ResponseEntity.ok("KG if the best fucking mob");
    }

}
