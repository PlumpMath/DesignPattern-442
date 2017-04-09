/**
 * Created by DrownFish on 2017/4/7.
 */
public class EnterpriseCustomer extends Customer {
    private String linkman;
    private String linkTelephone;
    private String registerAddress;

    @Override
    public void accept(Visitor visitor) {
        visitor.visitEnterpriseCustomer(this);
    }

    public String getLinkman() {
        return linkman;
    }

    public String getLinkTelephone() {
        return linkTelephone;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public void setLinkTelephone(String linkTelephone) {
        this.linkTelephone = linkTelephone;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }
}
