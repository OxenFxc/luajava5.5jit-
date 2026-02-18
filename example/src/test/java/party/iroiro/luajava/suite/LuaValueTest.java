package party.iroiro.luajava.suite;
import party.iroiro.luajava.luajit.LuaJit;

import org.junit.jupiter.api.RepeatedTest;
import party.iroiro.luajava.value.LuaValueSuite;

public class LuaValueTest {
    public static final int REPEATED = 20;

    @RepeatedTest(REPEATED)
    public void luaJitTest() {
        try (LuaJit L = new LuaJit()) {
            new LuaValueSuite<>(L).test();
        }
    }
}
