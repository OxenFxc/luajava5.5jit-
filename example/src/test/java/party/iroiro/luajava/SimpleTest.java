package party.iroiro.luajava;

import org.junit.jupiter.api.Test;
import party.iroiro.luajava.luajit.LuaJit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleTest {
    @Test
    public void test() {
        try (Lua L = new LuaJit()) {
            L.run("return 1 + 1");
            assertEquals(2, L.toInteger(-1));
        }
    }
}
