package au.com.at.oc.fr;

import java.lang.ref.WeakReference;

import au.com.ds.ef.StatefulContext;

/**
 * Created by lytr777 on 27/07/2017.
 */

public class FragmentLifecycleContext<C extends ObservedFragment> extends StatefulContext {

    private WeakReference<C> reference;

    public FragmentLifecycleContext() {
        reference = null;
    }

    protected void setFragment(C fragment) {
        if (fragment != null)
            reference = new WeakReference<C>(fragment);
    }

    public C getFragment() {
        if (reference != null) {
            return reference.get();
        }
        return null;
    }

    @Override
    public String toString() {
        if (reference != null && reference.get() != null)
            return reference.get().toString();
        else
            return "Undefined";
    }
}
