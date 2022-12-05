package com.moneytransfer.backendserver.services.userService;
import com.google.common.primitives.UnsignedLong;
import org.interledger.core.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Transactional(readOnly = true)
public class MojaILPService {

    private InterledgerRejectPacket getIlpRejectPacket(String errCode, String triggeredBy, String msg) {
        InterledgerRejectPacket ilpRejectPacket = InterledgerRejectPacket.builder()
                .code(getIlpErrCode(errCode))
                .triggeredBy(getILPAddress(triggeredBy))
                .message(msg)
                .data(new byte[32])
                .build();

        return ilpRejectPacket;
    }

    private InterledgerErrorCode getIlpErrCode(String code) {
        return InterledgerErrorCode.valueOf(code);
    }

    private InterledgerFulfillPacket getIlpFPPacket() {

        InterledgerFulfillPacket ilpfppacket = InterledgerFulfillPacket.builder()
                .fulfillment(InterledgerFulfillment.of(new byte[64]))
                .data(new byte[32])
                .build();

        return ilpfppacket;
    }

    private InterledgerPreparePacket getIlpPacket(UnsignedLong amount, int expiredate, String addr) {

        InterledgerPreparePacket ilpPacket = InterledgerPreparePacket.builder()
                .amount(amount)
                .expiresAt(Instant.now().plus(expiredate, ChronoUnit.DAYS))
                .executionCondition(InterledgerCondition.of(new byte[64]))
                .destination(getILPAddress(addr))
                .data(new byte[32])
                .build();

        return ilpPacket;
    }

    private InterledgerAddress getILPAddress(String addr) {
        InterledgerAddress destinationAddress = InterledgerAddress.builder()
                .value(addr)
                .build();
        return destinationAddress;
    }
}
