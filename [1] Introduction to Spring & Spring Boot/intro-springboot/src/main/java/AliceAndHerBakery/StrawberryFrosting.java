package AliceAndHerBakery;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "set.frosting", havingValue = "strawberry")
public class StrawberryFrosting implements Frosting {
    @Override
    public String getFrostingType() {
        return "strawberry frosting";
    }
}
