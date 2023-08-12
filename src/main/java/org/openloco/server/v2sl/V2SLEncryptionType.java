package org.openloco.server.v2sl;

public enum V2SLEncryptionType {
    AES(2, "AES/CFB/NoPadding");

    private final int type;
    private final String name;

    V2SLEncryptionType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int type() {
        return type;
    }

    public String encryptionName() {
        return name;
    }

    public static V2SLEncryptionType fromType(int type) {
        for (V2SLEncryptionType encryptionType : values()) {
            if (encryptionType.type() == type) {
                return encryptionType;
            }
        }

        throw new IllegalArgumentException("Unknown encryption type: " + type);
    }
}
