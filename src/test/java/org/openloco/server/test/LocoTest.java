package org.openloco.server.test;

import org.junit.jupiter.api.Test;
import org.openloco.server.protocol.LocoHeader;
import org.openloco.server.protocol.LocoProtocol;

public class LocoTest {

    @Test
    public void equalsTest() {
        LocoHeader header = new LocoHeader(0, (short) 0, "TEST", 0);
        byte[] body = new byte[0];

        LocoProtocol protocol = new LocoProtocol(header, body);

        byte[] protocolData = protocol.toByteArray();

        LocoProtocol protocol2 = LocoProtocol.fromByteArray(protocolData);

        System.out.println("BEFORE: " + protocol);
        System.out.println("AFTER: " + protocol2);

        assert protocol.header().equals(protocol2.header());

        assert protocol.body().length == protocol2.body().length;

        for (int i = 0; i < protocol.body().length; i++) {
            assert protocol.body()[i] == protocol2.body()[i];
        }
    }

}
