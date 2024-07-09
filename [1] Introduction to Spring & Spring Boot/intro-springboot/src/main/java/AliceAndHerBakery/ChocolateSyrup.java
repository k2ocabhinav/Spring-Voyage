package AliceAndHerBakery;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "set.syrup", havingValue = "chocolate")
public class ChocolateSyrup implements Syrup{
    @Override
    public String getSyrupType() {
        return "chocolate syrup.";
    }
}
