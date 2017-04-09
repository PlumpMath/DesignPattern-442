/**
 * Created by DrownFish on 2017/4/7.
 */
public class ServiceRequestVisitor implements Visitor {
    @Override
    public void visitEnterpriseCustomer(EnterpriseCustomer enterpriseCustomer) {
        System.out.println(enterpriseCustomer.getLinkman() +
                "企业提出服务请求");
    }

    @Override
    public void visitPersonalCustomer(PersonalCustomer personalCustomer) {

        System.out.println("客户" +personalCustomer.getTelephone() +
                "提出服务请求");
    }
}
