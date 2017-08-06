package au.com.at.oc.fr;

import au.com.ds.ef.State;

/**
 * Created by lytr777 on 27/07/2017.
 */

public interface ObserverFragmentAutomata<C extends ObservedFragment> {

    void setFragmentReference(C reference);

    PrimitiveFragmentLifecycle<FragmentLifecycleContext<C>> getLifecycle();

    boolean stateIs(State<FragmentLifecycleContext<C>> state);

    FragmentLifecycleContext<C> getContext();
}
