package party.iroiro.luajava;
import party.iroiro.luajava.luajit.LuaJit;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ClassPathLoaderTest {
    @Test
    public void classPathLoaderTest() {
        ClassPathLoader loader = new ClassPathLoader();
        ArrayList<Lua> luas = new ArrayList<>(Arrays.asList(
                new LuaJit()
        ));
        for (Lua L : luas) {
            assertNull(loader.load("a.module.nowhere.to.be.found", L));
            Buffer buffer = loader.load("suite.importTest", L);
            assertNotNull(buffer);
            assertTrue(buffer.isDirect());
            assertEquals(0, buffer.position());
            assertNotEquals(0, buffer.limit());
            LuaScriptSuite.addAssertThrows(L);
            L.load(buffer, "suite.importTest");
            L.pCall(0, Consts.LUA_MULTRET);
            L.close();
        }
    }

    @Test
    public void outputStreamTest() {
        ByteBuffer b = ByteBuffer.allocate(3);
        try (ClassPathLoader.BufferOutputStream out = new ClassPathLoader.BufferOutputStream(b)) {
            out.write(1);
            out.write(new byte[]{2, 3}, 0, 2);
            b.flip();
            assertEquals(1, b.get());
            assertEquals(2, b.get());
            assertEquals(3, b.get());
        } catch (IOException e) {
            fail(e);
        }
    }
}
