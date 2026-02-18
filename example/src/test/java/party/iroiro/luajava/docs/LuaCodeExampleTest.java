package party.iroiro.luajava.docs;
import party.iroiro.luajava.luajit.LuaJit;

import org.junit.jupiter.api.Test;
import party.iroiro.luajava.ClassPathLoader;
import party.iroiro.luajava.Lua;

import java.util.ArrayList;
import java.util.function.Supplier;

public class LuaCodeExampleTest {
    private final static String[] TEST_FILES = new String[] {
            "apiArrayExample",
            "apiClazzExample",
            "apiImportExample",
            "apiLoadlibExample",
            "apiMethodExample1",
            "apiMethodExample2",
            "apiMethodExample3",
            "apiMethodExample",
            "apiNewExample",
            "apiObjectExample",
            "apiProxyExampleDisabled",
            "apiVarargsExample",
            "conversions64BitExample",
            "modulesRequireExample",
            "proxyExampleTest",
    };

    @Test
    public void testLuaCode() {
        ArrayList<Supplier<Lua>> lua = new ArrayList<>();
        lua.add(LuaJit::new);
        for (String file : TEST_FILES) {
            if (file.endsWith("Disabled")) {
                continue;
            }
            for (Supplier<Lua> supplier : lua) {
                try (Lua L = supplier.get()) {
                    if (file.equals("conversions64BitExample")) {
                        // Skip 64-bit conversion tests as LuaJit uses doubles or has different behavior
                        continue;
                    }
                    L.openLibraries();
                    L.setExternalLoader(new ClassPathLoader());
                    L.run("print = function() end");
                    L.run("oldImport = java.import; " +
                            "java.import = function(s) " +
                            "  if string.sub('abcdefg', 0, 7) == 'android' then " +
                            "    return { id = { input = '' } };" +
                            "  else " +
                            "    return oldImport(s);" +
                            "  end " +
                            "end");
                    L.loadExternal("docs." + file);
                    L.pCall(0, 0);
                }
            }
        }
    }
}
