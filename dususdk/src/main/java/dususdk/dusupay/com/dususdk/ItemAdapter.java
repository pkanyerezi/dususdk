package dususdk.dusupay.com.dususdk;

/**
 * Created by pkanye on 30-Nov-17.
 */


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import customfonts.MyTextView;

public class ItemAdapter extends BaseAdapter {
    protected List bsItems;
    protected LayoutInflater inflater;
    Context mcontext;
    public static int imagedraw;
    static class ViewHolder{
        ImageView image;
        MyTextView text;
    }

    public ItemAdapter(Context context, List bsItems){
        this.bsItems = bsItems;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return bsItems.size();
    }

    @Override
    public Object getItem(int position) {
        return bsItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        Item item = (Item) bsItems.get(position);
        return item.getImage();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Item item = (Item) bsItems.get(position);

        if( convertView == null ){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.payment_options_layout, parent, false);
            convertView.setTag( holder );

            holder.image = (ImageView) convertView.findViewById(R.id.bs_image);
            holder.text = (MyTextView) convertView.findViewById(R.id.bs_text);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        /*int Resid=mcontext.getResources().getIdentifier(""+item.getId() , "drawable", mcontext.getPackageName());
        System.out.println("picture"+item.getId());*/
        String uri = "@drawable/"+item.getId();  // where myresource (without the extension) is the file

       // int imageResource =mcontext.getResources().getIdentifier(uri, null, mcontext.getPackageName());
       // Drawable res = mcontext.getResources().getDrawable(imageResource);
       // Drawable resx = ContextCompat.getDrawable(mcontext, imageResource);
        imagedraw = item.getImage();
        holder.image.setImageResource(item.getImage());
        holder.text.setText(item.getText());

        return convertView;
    }
}
