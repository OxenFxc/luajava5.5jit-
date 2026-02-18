package party.iroiro.luajava.docs;
import party.iroiro.luajava.luajit.LuaJit;

import org.junit.jupiter.api.Test;
import party.iroiro.luajava.Lua;

public class HelloWorldTest {
    @Test
    public void test() {
try (Lua L = new LuaJit()) {
    L.openLibraries();
    L.run("System = java.import('java.lang.System')");
    L.run("System.out:println('Hello World from Lua!')");
}
    }
}
