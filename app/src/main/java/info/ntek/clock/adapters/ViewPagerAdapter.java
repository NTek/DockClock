package info.ntek.clock.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import info.ntek.R;
import info.ntek.gsp.data.time.HourItem;

import java.util.ArrayList;

/**
 * @author Milos Milanovic
 */
public class ViewPagerAdapter extends PagerAdapter {

    private ArrayList<HourItem> mItems = null;

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        View lView = LayoutInflater.from(collection.getContext())
                .inflate(R.layout.layout_viewpager_item, collection, false);
        collection.addView(lView);
        setUpView(lView, position);
        return lView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
        view = null;
    }

    @Override
    public int getCount() {
        return mItems != null ? mItems.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return ((View) object).equals(view);
    }

    private void setUpView(View view, int position) {
        ((TextView) view.findViewById(R.id.times)).setText(" " + mItems.get(position).toString());
    }

    public void addItems(ArrayList items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public ArrayList<HourItem> getItems() {
        return mItems;
    }
}
