package AliceAndHerBakery;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "set.syrup", havingValue = "strawberry")
public class StrawberrySyrup implements Syrup{
    @Override
    public String getSyrupType() {
        return "strawberry syrup.";
    }
}
