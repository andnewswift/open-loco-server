package org.openloco.server.test;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import org.openloco.server.protocol.LocoHeader;
import org.openloco.server.protocol.LocoProtocol;

import java.util.Arrays;

public class LocoTest {

    @Test
    public void equalsTest() {
        LocoProtocol protocol = LocoProtocol.create("TEST", new JsonObject());

        byte[] protocolData = protocol.toByteArray();

        System.out.println("Test: " + Arrays.toString(protocolData));

        LocoProtocol protocol2 = LocoProtocol.fromByteArray(protocolData);

        System.out.println("BEFORE: " + protocol);
        System.out.println("AFTER: " + protocol2);

        assert protocol.header().equals(protocol2.header());

        assert protocol.body().equals(protocol2.body());
    }

}
