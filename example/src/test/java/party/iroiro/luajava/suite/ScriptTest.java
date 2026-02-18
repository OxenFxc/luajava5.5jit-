package party.iroiro.luajava.suite;
import party.iroiro.luajava.luajit.LuaJit;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import party.iroiro.luajava.*;

public class ScriptTest {
    public static final int REPEATED = 30;

    @RepeatedTest(REPEATED)
    public void luaJitTest() {
        try (LuaJit L = new LuaJit()) {
            new LuaScriptSuite<>(L).test();
        }
    }

    @Test
    public void memoryTest() {
        //noinspection resource
        Lua[] Ls = new Lua[]{
                new LuaJit(),
        };
        for (Lua L : Ls) {
            LuaScriptSuite.memoryTest(L);
            L.close();
        }
    }
}
