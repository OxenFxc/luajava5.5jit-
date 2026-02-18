package party.iroiro.luajava;
import party.iroiro.luajava.luajit.LuaJit;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testable
public class CoroutineTest {
    @Test
    public void testCoroutine() {
        //noinspection resource
        for (Lua L : new Lua[] {
                new LuaJit(),
        }) {
            coroutineTest(L);
        }
    }

    private void coroutineTest(Lua L) {
        L.openLibraries();
        L.setExternalLoader(new ClassPathLoader());
        L.loadExternal("tests.coTest");
        L.pCall(0, Consts.LUA_MULTRET);
        Lua coL = L.newThread();
        int ignored = L.ref();
        coL.getGlobal("main");
        int i = 1, j = 1;
        for (int l = 0; l < 36; l++) {
            assertTrue(coL.resume(0));
            assertEquals(i, coL.toNumber(-1));
            coL.pop(1);
            int k = i + j;
            i = j;
            j = k;
        }
        L.close();
    }
}
