import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by DrownFish on 2017/4/7.
 */
public class ObjectStructure {
    private Collection<Customer> collection = new ArrayList<>();
    public void handleRequest(Visitor visitor){
        for(Customer customer:collection){
            customer.accept(visitor);
        }
    }
    public void addElement(Customer customer){
        this.collection.add(customer);
    }
}
