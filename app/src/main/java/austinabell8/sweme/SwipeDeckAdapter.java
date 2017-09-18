package austinabell8.sweme;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.squareup.picasso.Picasso;

import austinabell8.sweme.R;


public class SwipeDeckAdapter extends BaseAdapter {

    private List<Meme> data;
    private Context context;

    public SwipeDeckAdapter(List<Meme> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);

            // normally use a viewholder
            v = inflater.inflate(R.layout.picture_view, parent, false);
        }

        ImageView imageView = (ImageView) v.findViewById(R.id.picture_holder);
        TextView textView = (TextView) v.findViewById(R.id.sample_text);
        Meme item = (Meme)getItem(position);
        Picasso.with(context).load(item.getImageResource()).fit().centerCrop().into(imageView);
        textView.setText(item.getDescription());

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Layer type: ", Integer.toString(v.getLayerType()));
                Log.i("Hardware Accel type:", Integer.toString(View.LAYER_TYPE_HARDWARE));
            }
        });
        return v;
    }
}