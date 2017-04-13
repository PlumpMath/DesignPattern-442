/**
 * Created by DrownFish on 2017/4/10.
 */
public interface AbstractIterator {
    public void next();
    public boolean isLast();
    public void previous();
    public boolean isFirst();
    public Object getNextItem();
    public Object getPreviousItem();
}
