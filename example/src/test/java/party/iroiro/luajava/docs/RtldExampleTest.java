package party.iroiro.luajava.docs;
import party.iroiro.luajava.luajit.LuaJit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import party.iroiro.luajava.Lua;

public class RtldExampleTest {
    @Disabled
    @Test
    public void loadAsGlobalTest() {
// #region loadAsGlobalTest
try (Lua L = new LuaJit()) {
    L.getLuaNatives().loadAsGlobal();
    L.run("require('lfs')");
}
// #endregion loadAsGlobalTest
    }
}
