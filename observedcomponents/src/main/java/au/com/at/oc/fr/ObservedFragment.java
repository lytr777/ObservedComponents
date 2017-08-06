package au.com.at.oc.fr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by lytr777 on 27/07/2017.
 */

public class ObservedFragment extends Fragment {

    private ObserverFragmentAutomata observer;

    public void setObserver(ObserverFragmentAutomata observer) {
        this.observer = observer;
        observer.setFragmentReference(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (observer != null) {
            observer.getLifecycle().onCreate.trigger(observer.getContext());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (observer != null)
            observer.getLifecycle().onCreateView.trigger(observer.getContext());
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (observer != null)
            observer.getLifecycle().onDestroyView.trigger(observer.getContext());
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (observer != null)
            observer.getLifecycle().onDestroy.trigger(observer.getContext());
        super.onDestroy();
    }

    @Override
    public String toString() {
        return "Observed";
    }
}
