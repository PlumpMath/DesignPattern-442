package utils.iniEdit;

import java.io.*;
import java.util.HashMap;

/**
 * Created by DrownFish on 2017/4/5.
 */
public class IniEditorAdapter implements IniEditorInterface,Serializable {

    //使用单例模式，避免多次调用读取配置文件
    private static IniEditorAdapter iniEditorAdapter = null;
    IniEditor inieditor = null;
    HashMap<String,HashMap<String,String>> elements = new HashMap<>();

    private void init() throws IOException {
        iniEditorAdapter = new IniEditorAdapter();
        inieditor = new IniEditor();
        inieditor.load("myInterface.ini");
    }

    //添加适配器模式，避免IniEditor发生改变，便于后期的修改
    @Override
    public String getValue(String section, String option) throws IOException {
        if(iniEditorAdapter == null || inieditor == null){
            this.init();
        }
        return inieditor.get(section,option);
    }

    @Override
    public void setValue(String section, String option, String value) throws IOException {
        if(iniEditorAdapter == null || inieditor == null){
            this.init();
        }
        inieditor.set(section,option,value);
        inieditor.save("myInterface.ini");
    }
}
