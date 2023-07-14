import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {

    @GetMapping
    ResponseEntity<String> test() {
        return ResponseEntity.ok("KG if the best fucking mob");
    }
}
