package com.example.shivam.materialnotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Shivam on 13/03/15 at 1:02 PM.
 */
public class NotesAdapter extends ArrayAdapter<ParseObject> {

    Context mContext;
    List<ParseObject> mNotes;

    public NotesAdapter(Context context, List<ParseObject> notes) {
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
            convertView.setTag(holder); //VERY IMPORTANT LINE !!!
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ParseObject note = mNotes.get(position);
        holder.titleLabel.setText(note.getString("Title"));
        holder.contentLabel.setText(note.getString("Note"));
        return convertView;
    }
    public static class ViewHolder
    {
        TextView titleLabel;
        TextView contentLabel;
    }

    public void refreshAdapter(List<ParseObject> notes)
    {
        mNotes.clear();
        mNotes.addAll(notes);
        notifyDataSetChanged();
    }


}
