/**
 * Created by DrownFish on 2017/4/9.
 */
public class Caretaker {
    private Memento memento;
    public Memento retrieveMemento(){
        return this.memento;
    }
    public void saveMemento(Memento memento){
        this.memento = memento;
    }
}
