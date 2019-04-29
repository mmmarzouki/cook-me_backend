package insat.gl4.cookme.services;

import insat.gl4.cookme.models.Quantity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuantityService {

    public Quantity getQuantityOfSameIngredient(Quantity quantity, List<Quantity> quantities){
        for (Quantity q: quantities) {
            if(q.getIngredient().getName().equals(quantity.getIngredient().getName()))
                return q;
        }
        return null;
    }
}
