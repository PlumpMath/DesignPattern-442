package DesignPattern.iniEditAdapter;

import java.io.IOException;

/**
 * Created by DrownFish on 2017/4/5.
 */
public interface IniEditorInterface {
    public String getValue(String section,String option) throws IOException;
    public void setValue(String section,String option,String value) throws IOException;
}
