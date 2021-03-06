package prokure.it.prokure.Search.NewSearch;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import prokure.it.prokure.FooterBarLayout;

/**
 * Created by Prokure User on 12/14/2015.
 */
public class CustomBehavior extends CoordinatorLayout.Behavior<RecyclerView> {

    public CustomBehavior() {

    }

    public CustomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RecyclerView child, View dependency) {

        //We are dependent on FooterBarLayout
        return dependency instanceof FooterBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, RecyclerView child, View dependency) {

        updateRecyclerViewPosition(parent, child, dependency);
        return false;
    }

    @Override
    public void onDependentViewRemoved(CoordinatorLayout parent, RecyclerView child, View dependency) {
        super.onDependentViewRemoved(parent, child, dependency);
    }

    private void updateRecyclerViewPosition(CoordinatorLayout parent, RecyclerView child, View dependency) {

        if (child.getVisibility() != View.VISIBLE) {
            return;
        }
        final float translationY = getRecyclerViewTranslationForFooter(parent, child);
        ViewCompat.setTranslationY(child, translationY);
    }

    private float getRecyclerViewTranslationForFooter(CoordinatorLayout parent, RecyclerView child) {

        float minOffset = 0;
        final List<View> dependencies = parent.getDependencies(child);
        for (int i = 0, z = dependencies.size(); i < z; i++) {
            final View view = dependencies.get(i);
            if (view instanceof FooterBarLayout && parent.doViewsOverlap(child, view)) {
                minOffset = Math.min(minOffset, ViewCompat.getTranslationY(view) - view.getHeight());
            }

        }
        return minOffset;

    }
}
