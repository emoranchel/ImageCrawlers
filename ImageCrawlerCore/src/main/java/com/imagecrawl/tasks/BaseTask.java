package com.imagecrawl.tasks;

import com.imagecrawl.api.API;
import com.imagecrawl.api.AnalizeAction;
import com.imagecrawl.api.CrawlerMessage;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.MessEngine;
import org.asmatron.messengine.event.EventId;
import org.asmatron.messengine.event.ValueEvent;

public abstract class BaseTask<T> implements Runnable {

    private final ExecutorService executorService;
    private final MessEngine messEngine;
    private final ControlEngine controlEngine;
    protected final AnalizeAction action;
    protected final T value;

    public BaseTask(ExecutorService executorService, MessEngine messEngine,
            ControlEngine controlEngine, CrawlerMessage<T> crawlerMessage) {
        this.executorService = executorService;
        this.messEngine = messEngine;
        this.controlEngine = controlEngine;
        this.action = crawlerMessage.getAction();
        this.value = crawlerMessage.getBody();
    }

    @Override
    public final void run() {
        if (executorService.isShutdown()) {
            return;
        }
        try {
            process();
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } finally {
            sendMessage(API.Messages.PROCESS_COMPLETE, null);
        }
    }

    protected final <T> void sendMessage(String messageId, T body) {
        messEngine.send(new CrawlerMessage<>(messageId, action, body));
    }

    protected HttpResponse httpGetDownload(String string) throws Exception {
        HttpHandler httpHandler = controlEngine.get(API.Model.HTTP_HANDLER);
        return httpHandler.getDownload(string);     
    }
    
    protected HttpResponse httpGet(String string) throws Exception {
        HttpHandler httpHandler = controlEngine.get(API.Model.HTTP_HANDLER);
        return httpHandler.get(string);
    }

    public <T extends Object> void fireValueEvent(EventId<ValueEvent<T>> arg0, T arg1) {
        controlEngine.fireValueEvent(arg0, arg1);
    }

    protected abstract void process() throws Exception;
}
