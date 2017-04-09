/**
 * Created by DrownFish on 2017/4/7.
 */
public class PersonalCustomer extends Customer {
    private String telephone;
    private int age;
    @Override
    public void accept(Visitor visitor) {
        visitor.visitPersonalCustomer(this);
    }

    public String getTelephone() {
        return telephone;
    }

    public int getAge() {
        return age;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
