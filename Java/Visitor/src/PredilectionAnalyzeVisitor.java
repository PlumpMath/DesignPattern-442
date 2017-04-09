/**
 * Created by DrownFish on 2017/4/7.
 */
public class PredilectionAnalyzeVisitor implements Visitor {
    @Override
    public void visitEnterpriseCustomer(EnterpriseCustomer enterpriseCustomer) {
        System.out.println("对企业用户进行分析");
    }

    @Override
    public void visitPersonalCustomer(PersonalCustomer personalCustomer) {
        System.out.println("对个人用户进行分析");
    }
}
