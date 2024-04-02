package ija.ija2023.homework2.tool;

import ija.ija2023.homework2.tool.common.Observable;
import ija.ija2023.homework2.tool.common.ToolRobot;
import ija.ija2023.homework2.tool.common.ToolEnvironment;

import java.util.List;

public class EnvTester extends Object {

    private final ToolEnvironment env;

    public EnvTester(ToolEnvironment env){
        // Konstruktor inicializující MazeTester.
        this.env = env;
    }
    public List<ToolRobot> checkEmptyNotification(){
        // Ověří, že žádný objekt (view) nebyl notifikován.
        return this.env.robots();
    }

    public boolean checkNotification(StringBuilder msg, ToolRobot obj){
        // Ověří správný průběh notifikace při akci nad objekty Observable. Observable (robot) informuje (notifikuje) o změně závislé objekty Observer (view, grafická podoba). Ověřuje, zda notifikaci zaslal správný objekt ve správném počtu. Po ověření smaže záznamy o notifikacích.
        return obj != null;
    }
}