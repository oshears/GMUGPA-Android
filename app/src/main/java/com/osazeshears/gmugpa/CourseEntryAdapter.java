package com.osazeshears.gmugpa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public final class CourseEntryAdapter extends ArrayAdapter<Course> {

    private final int courseItemLayoutResource;

    public CourseEntryAdapter(final Context context, final int newsItemLayoutResource) {
        //Specify the Context to our parent class, ArrayAdapter,
        super(context, 0);
        //THe courseItemLayoutResource tells us which layout we should use for each course item
        this.courseItemLayoutResource = newsItemLayoutResource;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        // We need to get the best view (re-used if possible) and then
        // retrieve its corresponding ViewHolder, which optimizes lookup efficiency
        final View view = getWorkingView(convertView);
        final ViewHolder viewHolder = getViewHolder(view);
        final Course entry = getItem(position);

        // Setting the title view is straightforward
        System.out.println("Text should display: "+entry.courseName);
        viewHolder.titleView.setText(entry.courseName);

        // Setting the subTitle view
        final String formattedSubTitle = String.format("Grade: %s, Credits: %s",
                entry.courseGrade,
                entry.courseCredits
        );

        viewHolder.subTitleView.setText(formattedSubTitle);


        return view;
    }

    private View getWorkingView(final View convertView) {
        // The workingView is basically just the convertView re-used if possible
        // or inflated new if not possible
        View workingView = null;

        if(null == convertView) {
            final Context context = getContext();
            final LayoutInflater inflater = (LayoutInflater)context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);

            workingView = inflater.inflate(courseItemLayoutResource, null);
        } else {
            workingView = convertView;
        }

        return workingView;
    }

    private ViewHolder getViewHolder(final View workingView) {
        // The viewHolder allows us to avoid re-looking up view references
        // Since views are recycled, these references will never change
        final Object tag = workingView.getTag();
        ViewHolder viewHolder = null;


        if(null == tag || !(tag instanceof ViewHolder)) {
            viewHolder = new ViewHolder();

            viewHolder.titleView = (TextView) workingView.findViewById(R.id.list_course);
            viewHolder.subTitleView = (TextView) workingView.findViewById(R.id.list_grade);

            workingView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) tag;
        }

        return viewHolder;
    }

    /**
     * ViewHolder allows us to avoid re-looking up view references
     * Since views are recycled, these references will never change
     */
    private static class ViewHolder {
        public TextView titleView;
        public TextView subTitleView;
    }


}
