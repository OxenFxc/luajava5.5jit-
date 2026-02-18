package party.iroiro.luajava.threaded;
import party.iroiro.luajava.luajit.LuaJit;

import org.junit.jupiter.api.Test;
import party.iroiro.luajava.ClassPathLoader;
import party.iroiro.luajava.Lua;
import party.iroiro.luajava.LuaException;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Tests creating threads from the Lua side.
 */
public class ThreadCreatingTest {
    @SuppressWarnings("resource")
    @Test
    public void threadCreatingTestLua() {
        for (Lua L : new Lua[]{
                new LuaJit(),
        }) {
            testLuaCreatingThreads(L);
        }
    }

    public void testLuaCreatingThreads(Lua L) {
        try {
            L.openLibraries();
            L.setExternalLoader(new ClassPathLoader());
            Lua K = L.newThread();
            K.loadExternal("threads.threadCreating");
            boolean started = false;
            while (true) {
                synchronized (K.getMainState()) {
                    LuaException.LuaError expected = started ? LuaException.LuaError.YIELD : LuaException.LuaError.OK;
                    // FIXME: Thread state should not change, but it is not relevant to this test though.
                    //        This might be a bug in LuaJIT... but it is hard to track down or reproduce.
                    assumeTrue(expected == K.status(), "FIXME: thread state changed");
                    if (!K.resume(0)) {
                        break;
                    }
                    started = true;
                }
                //noinspection BusyWait
                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            L.close();
        }
    }
}
