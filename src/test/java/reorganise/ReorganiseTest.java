package reorganise;

import devices.Device;
import devices.FailingPolicy;
import devices.StandardDevice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReorganiseTest {
    private Device device;
    @Mock private FailingPolicy stubFailingPolicy = mock(FailingPolicy.class);
    @Spy private FailingPolicy spyFailingPolicy = spy(FailingPolicy.class);
    @Mock @Spy private FailingPolicy failingPolicy = spy(mock(FailingPolicy.class));

    @Test
    void testCreateDevice(){
        this.device = new StandardDevice(stubFailingPolicy);
        assertNotNull(this.device);
    }

    @Test
    void testInitiallyOff(){
        this.device = new StandardDevice(spyFailingPolicy);
        assertFalse(device.isOn());
        verifyNoInteractions(this.spyFailingPolicy);
    }

    @Test
    void testCanBeSwitchedOn(){
        this.stubFailingPolicy = mock(FailingPolicy.class);
        device = new StandardDevice(this.stubFailingPolicy);
        when(this.stubFailingPolicy.attemptOn()).thenReturn(true);
        device.on();
        assertTrue(device.isOn());
    }

    @Test
    void testCanBeSwitchedOff(){
        this.stubFailingPolicy = mock(FailingPolicy.class);
        device = new StandardDevice(this.stubFailingPolicy);
        when(this.stubFailingPolicy.attemptOn()).thenReturn(true);
        device.on();
        device.off();
        assertFalse(device.isOn());
    }

    @Test
    void testReset(){
        device = new StandardDevice(this.failingPolicy);
        when(this.failingPolicy.attemptOn()).thenReturn(true);
        device.on();
        device.reset();
        assertEquals(2,
                Mockito.mockingDetails(this.failingPolicy).getInvocations().size());

    }

}
