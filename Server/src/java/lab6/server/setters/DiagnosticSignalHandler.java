package lab6.server.setters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Signal;
import sun.misc.SignalHandler;

public class DiagnosticSignalHandler implements SignalHandler {
    private static final Logger logger
            = LoggerFactory.getLogger(DiagnosticSignalHandler.class);
    public static void install(String signalName, SignalHandler handler) {
        Signal signal = new Signal(signalName);
        DiagnosticSignalHandler diagnosticSignalHandler = new DiagnosticSignalHandler();
        SignalHandler oldHandler = Signal.handle(signal, diagnosticSignalHandler);
        diagnosticSignalHandler.setHandler(handler);
        diagnosticSignalHandler.setOldHandler(oldHandler);
    }
    private SignalHandler oldHandler;
    private SignalHandler handler;

    private DiagnosticSignalHandler() {
    }

    private void setOldHandler(SignalHandler oldHandler) {
        this.oldHandler = oldHandler;
    }

    private void setHandler(SignalHandler handler) {
        this.handler = handler;
    }

    // Signal handler method
    @Override
    public void handle(Signal sig) {
        logger.info("Diagnostic Signal handler called for signal " + sig);
        try {
            handler.handle(sig);

            // Chain back to previous handler, if one exists
            if (oldHandler != SIG_DFL && oldHandler != SIG_IGN) {
                oldHandler.handle(sig);
            }

        } catch (Exception e) {
            logger.error("Signal handler failed, reason " + e);
        }
    }
}

