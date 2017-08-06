package au.com.at.oc.fr;

import au.com.ds.ef.State;
import au.com.ds.ef.SyncExecutor;

/**
 * Created by lytr777 on 27/07/2017.
 */
public class StaticFragmentAutomata<C extends ObservedFragment> implements ObserverFragmentAutomata<C> {

    private FragmentLifecycleContext<C> lifecycleContext;
    private final PrimitiveFragmentLifecycle<FragmentLifecycleContext<C>> lifecycle;

    public StaticFragmentAutomata() {
        lifecycle = new PrimitiveFragmentLifecycle<>(new SyncExecutor(), true);

        lifecycleContext = new FragmentLifecycleContext<>();
    }

    public void start() {
        lifecycle.start(lifecycleContext);
    }

    @Override
    public void setFragmentReference(C reference) {
        lifecycleContext.setFragment(reference);
    }

    public PrimitiveFragmentLifecycle<FragmentLifecycleContext<C>> getLifecycle() {
        return lifecycle;
    }

    @Override
    public boolean stateIs(State<FragmentLifecycleContext<C>> state) {
        return lifecycle.stateIs(state);
    }

    public FragmentLifecycleContext<C> getContext() {
        return lifecycleContext;
    }
}
