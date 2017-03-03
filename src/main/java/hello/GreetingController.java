package hello;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    static Map<String,Integer> awardTypeIdx = new HashMap<>();
    public static enum AwardType {GOLD,SILVER,BRONZE};
    static {
        awardTypeIdx.put(AwardType.GOLD.name(),0);
        awardTypeIdx.put(AwardType.SILVER.name(),1);
        awardTypeIdx.put(AwardType.BRONZE.name(),2);
    }
    static Double[][] limits = new Double[][]{{Double.valueOf(5500),null,Double.valueOf(1000)},{Double.valueOf(4500),Double.valueOf(1500),Double.valueOf(500)}};

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    /**
     *
     * @param gradeLevel
     * @param awardType
     * @param response
     * @return 404 if not found
     *         200 OK {}
     */
    @RequestMapping("/limits")
    public Double limit(@RequestParam(value = "gradeLevel") String gradeLevel,@RequestParam("awardType") String awardType,HttpServletResponse response){



        int gradIdx = Integer.valueOf(gradeLevel).intValue();
        Optional<AwardType> awardTypeName = Arrays.asList(AwardType.values()).stream().filter(type -> type.name().equalsIgnoreCase(awardType)).findAny();
        if(!awardTypeName.isPresent()){
            response.setStatus(400);
            return null;
        }
        int awardIdx = awardTypeIdx.get(awardTypeName.get().name());
        Double result = limits[gradIdx][awardIdx];
        if(result == null){
            response.setStatus(404);

        }
        return result;

    }
}
