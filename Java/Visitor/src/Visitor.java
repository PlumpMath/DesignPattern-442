/**
 * Created by DrownFish on 2017/4/7.
 */
public interface Visitor {
    public void visitEnterpriseCustomer(EnterpriseCustomer enterpriseCustomer);
    public void visitPersonalCustomer(PersonalCustomer personalCustomer);

}
