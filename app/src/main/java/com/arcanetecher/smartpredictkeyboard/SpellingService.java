package com.arcanetecher.smartpredictkeyboard;

import android.service.textservice.SpellCheckerService;

/**
 * Created by huysophanna on 4/26/17.
 */

public class SpellingService extends SpellCheckerService {
    @Override
    public Session createSession() {
        return new MySpellingSession();
    }
}
