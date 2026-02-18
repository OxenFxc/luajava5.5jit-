package party.iroiro.luajava.docs;
import party.iroiro.luajava.luajit.LuaJit;

import org.junit.jupiter.api.Test;
import party.iroiro.luajava.Lua;

public class ProxyExampleTest {
    @Test
    public void runnableTest() {
// #region runnableTest
try (Lua L = new LuaJit()) {
    L.run("r = { run = function() print('Hello') end }; return r");
    // With LuaValue API
    Runnable r = L.get("r").toProxy(Runnable.class);
    r.run();
    // With stack-based API
    Runnable s = (Runnable) L.createProxy(
        new Class[]{Runnable.class},
        Lua.Conversion.SEMI
    );
    s.run();
}
// #endregion runnableTest
    }
}
