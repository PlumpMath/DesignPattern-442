/**
 * Created by DrownFish on 2017/4/9.
 */
public class Originator {
    private String state;
    public Memento createMemento(){
        return new Memento(state);
    }

    /**
     * 修改状态的方法
     * @param memento
     */
    public void restoreMemento(Memento memento){
        state = memento.getState();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
