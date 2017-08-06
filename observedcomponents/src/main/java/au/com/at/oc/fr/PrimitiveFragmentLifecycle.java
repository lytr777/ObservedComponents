package au.com.at.oc.fr;

import android.util.Log;

import au.com.ds.ef.*;
import au.com.ds.ef.call.EventHandler;
import au.com.ds.ef.call.ExecutionErrorHandler;
import au.com.ds.ef.err.ExecutionError;

import java.util.concurrent.Executor;

/**
 * Created by lytr777 on 27/07/2017.
 */
public class PrimitiveFragmentLifecycle<C extends StatefulContext> {

    private C context;
    private EasyFlow<C> lifecycleFlow;

    public final State<C> NULL, DESTROYED, CREATED, VIEW_CREATED;
    public final Event<C> onCreate, onCreateView, onDestroy, onDestroyView;

    public PrimitiveFragmentLifecycle(Executor executor, boolean trace) {
        NULL = new State<>("NULL");
        DESTROYED = new State<>("DESTROYED");
        CREATED = new State<>("CREATED");
        VIEW_CREATED = new State<>("VIEW_CREATED");

        onCreate = new Event<>("onCreate");
        onCreateView = new Event<>("onCreateView");
        onDestroy = new Event<>("onDestroy");
        onDestroyView = new Event<>("onDestroyView");

        lifecycleFlow = FlowBuilder
                .from(NULL).transit(
                        onCreate.to(CREATED).transit(
                                onDestroy.to(DESTROYED).transit(
                                        onCreate.to(CREATED)
                                ),
                                onCreateView.to(VIEW_CREATED).transit(
                                        onDestroyView.to(CREATED)
                                )
                        )
                ).executor(executor).whenError(new ExecutionErrorHandler() {
                    @Override
                    public void call(ExecutionError error) {
                        String msg = "Execution Error in State [" + error.getState() + "] ";
                        if (error.getEvent() != null) {
                            msg += "on Event [" + error.getEvent() + "] ";
                        }
                        msg += "with Context [" + error.getContext() + "] ";
                        Log.e(context.toString(), msg);
                        Log.e(context.toString(), error.getCause() + " " + error.getMessage());
                    }
                });
        if (trace)
            lifecycleFlow.trace();
        lifecycleFlow.whenEventTriggered(new EventHandler<C>() {
            @Override
            public void call(Event<C> event, State<C> from, State<C> to, C context) throws Exception {
                Log.d(context.toString(), "state " + from + " ---(" + event + ")---> " + to);
            }
        });
    }

    protected boolean stateIs(State<C> state) {
        return context.getState().equals(state);
    }

    protected void start(C context) {
        this.context = context;
        lifecycleFlow.start(context);
    }
}
