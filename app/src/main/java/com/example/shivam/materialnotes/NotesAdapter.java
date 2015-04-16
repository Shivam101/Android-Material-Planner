package com.example.shivam.materialnotes;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Shivam on 13/03/15 at 1:02 PM.
 */
public class NotesAdapter extends ArrayAdapter<ParseObject> {

    Context mContext;
    List<ParseObject> mNotes;

    public NotesAdapter(Context context,int layoutResourceId, List<ParseObject> notes) {
        super(context,R.layout.notes_list_item, notes);
        mContext = context;
        mNotes = notes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.notes_list_item, null);
            holder = new ViewHolder();
            holder.titleLabel = (TextView) convertView.findViewById(R.id.noteTitle);
            holder.contentLabel = (TextView) convertView.findViewById(R.id.noteContent);
            holder.noteHeader = (Toolbar)convertView.findViewById(R.id.toolbar);
            convertView.setTag(holder); //VERY IMPORTANT LINE !!!
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ParseObject note = mNotes.get(position);
        holder.titleLabel.setText(note.getString("Title"));
        holder.contentLabel.setText(note.getString("Note"));
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Roboto-Light.ttf");
        Typeface tf2 = Typeface.createFromAsset(getContext().getAssets(),"RobotoSlab-Regular.ttf");
        holder.titleLabel.setTypeface(tf);
        holder.contentLabel.setTypeface(tf2);
        if(note.getString("Tag").equals("Blue"))
        {
            holder.noteHeader.setBackgroundResource(R.color.nliveo_blue_colorPrimary);
        }
        else if(note.getString("Tag").equals("Green"))
        {
            holder.noteHeader.setBackgroundResource(R.color.nliveo_green_colorPrimary);
        }
        else if(note.getString("Tag").equals("Indigo"))
        {
            holder.noteHeader.setBackgroundResource(R.color.nliveo_indigo_colorPrimary);
        }
        else if(note.getString("Tag").equals("Red"))
        {
            holder.noteHeader.setBackgroundResource(R.color.nliveo_red_colorPrimary);
        }
        else if(note.getString("Tag").equals("Orange"))
        {
            holder.noteHeader.setBackgroundResource(R.color.nliveo_orange_colorPrimary);
        }
        else if(note.getString("Tag").equals("Black"))
        {
            holder.noteHeader.setBackgroundResource(R.color.nliveo_black);
        }
        else if(note.getString("Tag").equals("Teal"))
        {
            holder.noteHeader.setBackgroundResource(R.color.nliveo_teal_colorPrimary);
        }
        return convertView;
    }
    public static class ViewHolder
    {
        TextView titleLabel;
        TextView contentLabel;
        Toolbar noteHeader;
    }

    public void refreshAdapter(List<ParseObject> notes)
    {
        mNotes.clear();
        mNotes.addAll(notes);
        notifyDataSetChanged();
    }


}
