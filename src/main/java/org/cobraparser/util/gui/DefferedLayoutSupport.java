package org.cobraparser.util.gui;

import java.util.concurrent.Future;

public interface DefferedLayoutSupport {
    public Future<Boolean> layoutCompletion();
}
