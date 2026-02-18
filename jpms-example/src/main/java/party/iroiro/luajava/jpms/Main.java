package party.iroiro.luajava.jpms;

import party.iroiro.luajava.Lua;
import party.iroiro.luajava.luajit.LuaJit;

public class Main implements AutoCloseable {
    public static void main(String[] args) {
        try (Main test = new Main()) {
            test.test();
        }
    }

    private final Lua[] luas;

    private Main() {
        //noinspection resource
        luas = new Lua[] {
                new LuaJit(),
        };
    }

    private void test() {
        for (Lua L : luas) {
            // Accessing java.*
            L.run("String = java.import('java.lang.String')");
            L.run("s = 'Hello World: ' .. _VERSION; assert(String(s):toString() == s)");
            // Accessing explicitly modulized classes
            L.run("c = 'party.iroiro.luajava.jpms.Main'; assert(java.import(c).class:getName() == c)");
        }
    }

    @Override
    public void close() {
        for (Lua L : luas) {
            L.close();
        }
    }
}
