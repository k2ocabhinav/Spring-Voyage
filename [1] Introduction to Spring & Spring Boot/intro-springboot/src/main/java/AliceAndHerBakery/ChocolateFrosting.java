package AliceAndHerBakery;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "set.frosting", havingValue = "chocolate")
public class ChocolateFrosting implements Frosting{
    @Override
    public String getFrostingType() {
        return "chocolate frosting";
    }
}
