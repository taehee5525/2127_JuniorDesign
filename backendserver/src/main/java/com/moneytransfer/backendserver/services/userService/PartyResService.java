package com.moneytransfer.backendserver.services.userService;

import com.moneytransfer.backendserver.Util;
import com.moneytransfer.backendserver.objects.User2;
import com.moneytransfer.backendserver.repositories.UserRepo;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PartyResService {

    private UserRepo userRepo;

    public PartyResService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * If user exists, return the obj else return null.
     * @param email user email
     * @return user obj
     */
    public JSONObject lookupUser(String email) {
        Optional<User2> userOpt = userRepo.findByUserEmail(email);
        if (userOpt.isEmpty()) {
            return null;
        }
        User2 userObj = userOpt.get();
        JSONObject ret = new JSONObject();

        ret.put("partyIdType", "ACCOUNT_ID");
        ret.put("partyIdentifier", userObj.getUserEmail());

        JSONObject party = new JSONObject();
        JSONObject partyIdInfo = new JSONObject();
        partyIdInfo.put("partyIdType", "ACCOUNT_ID");
        partyIdInfo.put("partyIdentifier", userObj.getUserEmail());
        partyIdInfo.put("fspId", Util.FSP_NAME);
        party.put("partyIdInfo", partyIdInfo);
        party.put("name", userObj.getName());
        ret.put("party", party);

        return ret;
    }


}
